module odiowpf.medidietasdesktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.common;
    requires annotations.api;
    requires io.grpc;
    requires io.grpc.stub;
    requires io.grpc.protobuf;
    requires com.google.protobuf;
    requires io.grpc.netty.shaded;
    requires MaterialFX;

    opens odiowpf.medidietasdesktop to javafx.fxml;
    exports odiowpf.medidietasdesktop;
    exports odiowpf.medidietasdesktop.controladores;
    opens odiowpf.medidietasdesktop.controladores to javafx.fxml;
}