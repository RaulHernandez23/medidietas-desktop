package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import odiowpf.medidietasdesktop.daos.AlimentoDAO;
import odiowpf.medidietasdesktop.daos.ComidaDAO;
import odiowpf.medidietasdesktop.grpc.ServicioImagenComida;
import odiowpf.medidietasdesktop.modelos.Alimento;
import odiowpf.medidietasdesktop.modelos.Categoria;
import odiowpf.medidietasdesktop.modelos.Comida;
import odiowpf.medidietasdesktop.modelos.UnidadMedida;
import odiowpf.medidietasdesktop.utilidades.Constantes;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
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
        Comida comida = new Comida(3, "nombre",
                "https://www.youtube.com/watch?v=lA54Fs79Gtc&pp=ygUTZW5zYWxhZGEgZGUgbWFuemFuYQ%3D%3D",
                "Prepararla",
                true);
        HashMap<String, Double> alimentos = new HashMap<>();
        alimentos.put("Manzana", 5.0);
        alimentos.put("Pera", 5.0);
        comida.setAlimentos(alimentos);
        HashMap<String, Object> respuesta = ComidaDAO.editarComida(comida);
        String mensaje = (String) respuesta.get(Constantes.KEY_MENSAJE);
        System.out.println(mensaje);
    }
}