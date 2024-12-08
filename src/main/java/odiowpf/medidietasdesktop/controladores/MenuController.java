package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/odiowpf/medidietasdesktop/vistas/FXMLInicioSesion.fxml"));
            Scene scene = new Scene(loader.load(), 1280, 720);

            Stage stage = new Stage();
            stage.setTitle("Inicio de sesión");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            Stage stageActual = (Stage) paneRaiz.getScene().getWindow();
            stageActual.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void actionAlimentos(ActionEvent actionEvent) {
        resetButtonsStyle();
        btnAlimentos.setStyle("-fx-background-color: -fx-azul; -fx-text-fill: -fx-blanco;");

        cargarVista("FXMLAlimentos.fxml");
    }

    @javafx.fxml.FXML
    public void actionComidas(ActionEvent actionEvent) {
        resetButtonsStyle();
        btnComidas.setStyle("-fx-background-color: -fx-azul; -fx-text-fill: -fx-blanco;");
    }

    private void cargarVista(String nombreFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/odiowpf/medidietasdesktop/vistas/" + nombreFxml));
            Node nuevaVista = loader.load();

            paneContenido.getChildren().setAll(nuevaVista);

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    private void resetButtonsStyle() {
        String defaultStyle = "-fx-background-color: -fx-blanco; -fx-text-fill: -fx-azul;";
        btnAlimentos.setStyle(defaultStyle);
        btnComidas.setStyle(defaultStyle);
    }
}