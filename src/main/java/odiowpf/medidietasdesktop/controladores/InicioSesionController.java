package odiowpf.medidietasdesktop.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class InicioSesionController {

    @FXML
    private AnchorPane paneRaiz;

    @FXML
    public void btnEntrar(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/odiowpf/medidietasdesktop/vistas/FXMLMenu.fxml"));
            Scene scene = new Scene(loader.load(), 1280, 720);

            Stage stage = new Stage();
            stage.setTitle("Menú principal");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            Stage stageActual = (Stage) paneRaiz.getScene().getWindow();
            stageActual.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

    }
}
