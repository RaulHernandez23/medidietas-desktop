package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuController
{

    @javafx.fxml.FXML
    private AnchorPane paneRaiz;
    @javafx.fxml.FXML
    private HBox cabeceraVentana;
    @javafx.fxml.FXML
    private VBox barraNavegacion;
    @javafx.fxml.FXML
    private StackPane contenedorLogo;
    @javafx.fxml.FXML
    private StackPane paneContenido;
    @javafx.fxml.FXML
    private MFXButton btnSalir;
    @javafx.fxml.FXML
    private MFXButton btnAlimentos;
    @javafx.fxml.FXML
    private MFXButton btnComidas;

    @javafx.fxml.FXML
    public void initialize() {

    }

    @javafx.fxml.FXML
    public void actionSalir(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void actionAlimentos(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void actionComidas(ActionEvent actionEvent) {
    }
}