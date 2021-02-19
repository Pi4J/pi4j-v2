package com.pi4j.runtime.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultRuntime.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.context.Context;
import com.pi4j.event.EventDelegate;
import com.pi4j.event.EventManager;
import com.pi4j.event.InitializedEvent;
import com.pi4j.event.InitializedListener;
import com.pi4j.event.ShutdownEvent;
import com.pi4j.event.ShutdownListener;
import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.extension.Plugin;
import com.pi4j.extension.impl.DefaultPluginService;
import com.pi4j.extension.impl.PluginStore;
import com.pi4j.platform.Platform;
import com.pi4j.platform.impl.DefaultRuntimePlatforms;
import com.pi4j.platform.impl.RuntimePlatforms;
import com.pi4j.provider.Provider;
import com.pi4j.provider.impl.DefaultRuntimeProviders;
import com.pi4j.provider.impl.RuntimeProviders;
import com.pi4j.registry.impl.DefaultRuntimeRegistry;
import com.pi4j.registry.impl.RuntimeRegistry;
import com.pi4j.runtime.Runtime;
import com.pi4j.runtime.RuntimeProperties;
import com.pi4j.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>DefaultRuntime class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultRuntime implements Runtime {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Context context;
    private final RuntimeRegistry registry;
    private final RuntimeProviders providers;
    private final RuntimePlatforms platforms;
    private final RuntimeProperties properties;
    private final List<Plugin> plugins = new ArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean isShutdown = false;
    private final EventManager<Runtime, ShutdownListener, ShutdownEvent> shutdownEventManager;
    private final EventManager<Runtime, InitializedListener, InitializedEvent> initializedEventManager;

    /**
     * <p>newInstance.</p>
     *
     * @param context a {@link com.pi4j.context.Context} object.
     * @return a {@link com.pi4j.runtime.Runtime} object.
     */
    public static Runtime newInstance(Context context) {
        return new DefaultRuntime(context);
    }

    // private constructor
    private DefaultRuntime(Context context) {

        // set local references
        this.context = context;
        this.properties = DefaultRuntimeProperties.newInstance(context);
        this.registry = DefaultRuntimeRegistry.newInstance(this);
        this.providers = DefaultRuntimeProviders.newInstance(this);
        this.platforms = DefaultRuntimePlatforms.newInstance(this);
        this.shutdownEventManager = new EventManager(this,
                (EventDelegate<ShutdownListener, ShutdownEvent>) (listener, event) -> listener.onShutdown(event));
        this.initializedEventManager = new EventManager(this,
                (EventDelegate<InitializedListener, InitializedEvent>) (listener, event) -> listener.onInitialized(event));

        logger.debug("Pi4J runtime context successfully created & initialized.'");

        // listen for shutdown to properly clean up
        // TODO :: ADD PI4J INTERNAL SHUTDOWN CALLBACKS/EVENTS
        java.lang.Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                // shutdown Pi4J
                if(!isShutdown) shutdown();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }, "pi4j-shutdown"));
    }

    /** {@inheritDoc} */
    @Override
    public Context context() { return this.context; }

    /** {@inheritDoc} */
    @Override
    public RuntimeRegistry registry() { return this.registry; }

    /** {@inheritDoc} */
    @Override
    public RuntimeProviders providers() {
        return this.providers;
    }

    /** {@inheritDoc} */
    @Override
    public RuntimePlatforms platforms() {
        return this.platforms;
    }

    /** {@inheritDoc} */
    @Override
    public RuntimeProperties properties() {
        return this.properties;
    }

    /** {@inheritDoc} */
    @Override
    public Runtime shutdown() throws ShutdownException {
        if(!isShutdown) { // re-entrant calls should not perform shutdown again
            isShutdown = true;
            logger.trace("invoked 'shutdown();'");
            try {
                // remove shutdown monitoring thread
                //java.lang.Runtime.getRuntime().removeShutdownHook(this.shutdownThread);

                // remove all I/O instances
                this.registry.shutdown();

                // shutdown platforms
                this.platforms.shutdown();

                // shutdown all providers
                this.providers.shutdown();

                // shutdown all plugins
                for (Plugin plugin : this.plugins) {
                    try {
                        plugin.shutdown(this.context);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                logger.error("failed to 'shutdown(); '", e);
                throw new ShutdownException(e);
            }

            logger.debug("Pi4J context/runtime successfully shutdown.'");

            // notify shutdown event listeners
            shutdownEventManager.dispatch(new ShutdownEvent(this.context));

            // remove all shutdown event listeners
            this.shutdownEventManager.clear();

        } else{
            logger.debug("Pi4J context/runtime is already shutdown.'");
        }

        return this;
    }

    @Override
    public Future<Context> asyncShutdown() {
        return executor.submit(() -> {
            try {
                shutdown();
            }
            catch (Exception e){
                logger.error(e.getMessage(), e);
            }
            return context;
        });
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    /** {@inheritDoc} */
    @Override
    public Runtime initialize() throws InitializeException {
        logger.trace("invoked 'initialize();'");
        try {
            // clear plugins container
            plugins.clear();

            // container sets for providers and platforms to load
            Set<Provider> providers = Collections.synchronizedSet(new HashSet<>());
            Set<Platform> platforms = Collections.synchronizedSet(new HashSet<>());

            // copy all configured platforms and providers defined in the context configuration
            providers.addAll(context().config().getProviders());
            platforms.addAll(context().config().getPlatforms());

            // only attempt to load platforms and providers from the classpath if an auto detect option is enabled
            if(context.config().autoDetectPlatforms() || context.config().autoDetectProviders()) {

                // detect available Pi4J Plugins by scanning the classpath looking for plugin instances
                var plugins = ServiceLoader.load(Plugin.class);
                for (var plugin : plugins) {
                    if (plugin != null) {
                        logger.trace("detected plugin: [{}] in classpath; calling 'initialize()'",
                                plugin.getClass().getName());
                        try {
                            // add plugin to internal cache
                            this.plugins.add(plugin);

                            PluginStore store = new PluginStore();
                            plugin.initialize(DefaultPluginService.newInstance(this.context(), store));

                            // if auto-detect providers is enabled,
                            // then add any detected providers to the collection to load
                            if(context.config().autoDetectProviders())
                                providers.addAll(store.providers);

                            // if auto-detect platforms is enabled,
                            // then add any detected platforms to the collection to load
                            if(context.config().autoDetectPlatforms())
                                platforms.addAll(store.platforms);

                        } catch (Exception ex) {
                            // unable to initialize this provider instance
                            logger.error("unable to 'initialize()' plugin: [{}]; {}",
                                    plugin.getClass().getName(), ex.getMessage(), ex);
                            continue;
                        }
                    }
                }
            }

            // initialize I/O registry
            this.registry.initialize();

            // initialize all providers
            this.providers.initialize(providers);

            // initialize all platforms
            this.platforms.initialize(platforms);

            // now auto-load any defined I/O injection instances available in the context config
            try {
                // ensure that the auto-injection option is enabled for this context
                if(this.context().config().autoInject()) {

                    // get potential injection candidates
                    Map<String,String> candidates = PropertiesUtil.keysEndsWith(this.context().properties().all(), "inject");

                    // iterate over injection candidate and determine if it is configured/enabled for injection and perform injection
                    for (String candidateKey : candidates.keySet()) {
                        try {
                            boolean candidateInject =  Boolean.parseBoolean(candidates.getOrDefault(candidateKey, "false"));
                            if(candidateInject) {
                                this.context().create(candidateKey);
                            }
                        } catch (Exception e) {
                            logger.error("FAILED TO AUTO-INJECT [{}]; {}'", candidateKey, e.getMessage(), e);
                            throw new InitializeException(e);
                        }
                    }
                }
            } catch (Exception e){
                logger.error(e.getMessage(), e);
            }

        } catch (Exception e) {
            logger.error("failed to 'initialize(); '", e);
            throw new InitializeException(e);
        }

        logger.debug("Pi4J context/runtime successfully initialized.'");

        // notify initialized event listeners
        notifyInitListeners();

        return this;
    }

    private void notifyInitListeners() {
        // TODO
    }

    @Override
    public Runtime addListener(ShutdownListener... listener) {
        return shutdownEventManager.add(listener);
    }

    @Override
    public Runtime removeListener(ShutdownListener... listener) {
        return shutdownEventManager.remove(listener);
    }

    @Override
    public Runtime removeAllShutdownListeners() {
        return shutdownEventManager.clear();
    }

    @Override
    public Runtime removeAllInitializedListeners() {
        return initializedEventManager.clear();
    }

    @Override
    public Runtime addListener(InitializedListener... listener) {
        return initializedEventManager.add(listener);
    }

    @Override
    public Runtime removeListener(InitializedListener... listener) {
        return initializedEventManager.remove(listener);
    }
}
