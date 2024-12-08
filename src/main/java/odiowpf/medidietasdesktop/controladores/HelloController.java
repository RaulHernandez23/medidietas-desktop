package odiowpf.medidietasdesktop.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import odiowpf.medidietasdesktop.daos.AlimentoDAO;
import odiowpf.medidietasdesktop.grpc.ServicioImagenComida;
import odiowpf.medidietasdesktop.modelos.Alimento;
import odiowpf.medidietasdesktop.utilidades.Constantes;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ImageView iv;

    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome to JavaFX Application!");

    }

    @FXML
    public void botonPrueba(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.jpeg", "*.png")
        );

        File archivoSeleccionado = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());

        if (archivoSeleccionado != null) {
            try {
                byte[] datosImagen = Files.readAllBytes(archivoSeleccionado.toPath());

                String nombreImagen = archivoSeleccionado.getName();
                String extension = nombreImagen.substring(nombreImagen.lastIndexOf(".") + 1);

                String respuesta = ServicioImagenComida.subirImagenComida("Leche", extension, datosImagen);

                System.out.println(respuesta);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}