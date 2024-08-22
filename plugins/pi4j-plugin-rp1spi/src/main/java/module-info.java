
import com.pi4j.plugin.rp1spi.Rp1SpiPlugin;


module com.pi4j.plugin.rp1spi {
    requires com.pi4j;
    requires com.pi4j.library.kernel;
    requires org.slf4j;
    requires jdk.unsupported;

    //  uses com.pi4j.extension.Plugin;

    exports com.pi4j.plugin.rp1spi;

    exports com.pi4j.plugin.rp1spi.provider;

    provides com.pi4j.extension.Plugin
        with com.pi4j.plugin.rp1spi.Rp1SpiPlugin;

}
