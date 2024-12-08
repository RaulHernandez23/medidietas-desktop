package odiowpf.medidietasdesktop.controladores;

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
        HashMap<String, Object> respuesta = ComidaDAO.obtenerComidas();
        ArrayList<Comida> comidas = (ArrayList<Comida>) respuesta.get(Constantes.KEY_OBJETO);
        for (Comida comida : comidas) {
            System.out.println(comida.getNombre());
        }

    }
}