module odiowpf.medidietasdesktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens odiowpf.medidietasdesktop to javafx.fxml;
    exports odiowpf.medidietasdesktop;
}