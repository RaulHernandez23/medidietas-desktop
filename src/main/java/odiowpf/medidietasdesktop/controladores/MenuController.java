package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import odiowpf.medidietasdesktop.daos.ExpertoNutricionDAO;
import odiowpf.medidietasdesktop.modelos.ExpertoNutricion;
import odiowpf.medidietasdesktop.utilidades.ConversorImagen;

import java.io.IOException;
import java.util.HashMap;

public class MenuController
{
    private ExpertoNutricion experto;
    private Image fotoPerfil;
    @javafx.fxml.FXML
    private AnchorPane paneRaiz;
    @javafx.fxml.FXML
    private StackPane paneContenido;
    @javafx.fxml.FXML
    private MFXButton btnAlimentos;
    @javafx.fxml.FXML
    private MFXButton btnComidas;
    @FXML
    private ImageView ivFotoPerfil;
    @FXML
    private BorderPane bpFotoPerfil;
    @FXML
    private Label lbTituloVentana;
    @FXML
    private Label lblNombreExperto;

    @javafx.fxml.FXML
    public void initialize() {
    }

    public void cargarDatos(ExpertoNutricion experto, Image fotoPerfil) {
        this.experto = experto;
        this.fotoPerfil = fotoPerfil;
        configurarLabelNombre();
        configurarFotoPerfil();
        inicializarComponentesVisuales();
    }

    @javafx.fxml.FXML
    public void actionSalir(ActionEvent actionEvent) {
        HashMap<String, Object> respuesta = ExpertoNutricionDAO.logOut();
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
        lbTituloVentana.setText("Alimentos");
        resetButtonsStyle();
        btnAlimentos.setStyle("-fx-background-color: -fx-azul; -fx-text-fill: -fx-blanco;");

        cargarVista("FXMLAlimentos.fxml");
    }

    @javafx.fxml.FXML
    public void actionComidas(ActionEvent actionEvent) {
        lbTituloVentana.setText("Comidas");
        resetButtonsStyle();
        btnComidas.setStyle("-fx-background-color: -fx-azul; -fx-text-fill: -fx-blanco;");

        cargarVista("FXMLComidas.fxml");
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

    private void configurarFotoPerfil() {
        Image squareImage = ConversorImagen.ajustarImagenConRelleno(fotoPerfil, 60);

        // Crear el ImageView con la imagen cuadrada
        ivFotoPerfil = new ImageView(squareImage);

        // Crear un clip circular
        Circle clip = new Circle(30); // Radio del círculo

        // Centrar el clip
        clip.setCenterX(30);
        clip.setCenterY(30);

        // Asignar el clip al ImageView
        ivFotoPerfil.setClip(clip);

        // Colocar el ImageView dentro del BorderPane
        StackPane stackPane = new StackPane(ivFotoPerfil);
        bpFotoPerfil.setCenter(stackPane);
    }

    private void inicializarComponentesVisuales() {
        lbTituloVentana.setText("");
    }

    private void configurarLabelNombre() {
        String nombreCompleto = experto.getNombre()
                + " " + experto.getApellidoPaterno()
                + " " + experto.getApellidoMaterno();
        lblNombreExperto.setText(nombreCompleto);
    }

}