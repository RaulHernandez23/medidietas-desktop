package odiowpf.medidietasdesktop;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import odiowpf.medidietasdesktop.grpcclient.ClienteImagenGrpc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome to JavaFX Application!");

        ClienteImagenGrpc cliente = new ClienteImagenGrpc();

        byte[] imagenBytes = cliente.descargarImagen();

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG Images", "*.jpg", "*.jpeg"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Images", "*.png"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            guardarImagen(imagenBytes, file);
        }
    }

    private void guardarImagen(byte[] imagenBytes, File archivo) {
        try {
            // Escribir los bytes de la imagen en el archivo
            try (FileOutputStream fos = new FileOutputStream(archivo)) {
                fos.write(imagenBytes);
                showSuccessMessage("Imagen guardada exitosamente");
            } catch (IOException e) {
                showErrorMessage("Error al guardar la imagen");
                e.printStackTrace();
            }
        } catch (Exception e) {
            showErrorMessage("Error al procesar la imagen");
            e.printStackTrace();
        }
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para mostrar un mensaje de error
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}