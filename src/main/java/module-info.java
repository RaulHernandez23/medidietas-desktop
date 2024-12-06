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

    exports odiowpf.medidietasdesktop.controladores;

    // Abrir el paquete de controladores para permitir el acceso mediante reflexión
    opens odiowpf.medidietasdesktop.controladores to javafx.fxml;

    // Si necesitas abrir el paquete principal (para otros casos de reflexión)
    opens odiowpf.medidietasdesktop to javafx.fxml;
    exports odiowpf.medidietasdesktop;
    exports odiowpf.medidietasdesktop.controladores;
    opens odiowpf.medidietasdesktop.controladores to javafx.fxml;
}