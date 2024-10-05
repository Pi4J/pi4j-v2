
module com.pi4j.library.kernel {

    // SLF4J
    requires org.slf4j;

    // PI4J
    requires com.pi4j;


    // JNA
    requires jdk.unsupported;
    requires com.sun.jna;

    // EXPORTS
    exports com.pi4j.library.kernel;
    }
