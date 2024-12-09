package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import odiowpf.medidietasdesktop.daos.ComidaDAO;
import odiowpf.medidietasdesktop.modelos.Comida;
import odiowpf.medidietasdesktop.utilidades.Constantes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class ComidasController {
    @FXML
    private TableView<Comida> tablaComidas;

    private ObservableList<Comida> comidas;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private MFXButton btnRegistrar;

    @FXML
    public void initialize() {
        configurarTabla();
        llenarDatos();
    }

    private void configurarTabla() {
        TableColumn<Comida, String> columnaComida = new TableColumn<>("Comida");
        columnaComida.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Comida, String> columnaReceta = new TableColumn<>("Receta");
        columnaReceta.setCellValueFactory(new PropertyValueFactory<>("receta"));

        // Columna para editar
        TableColumn<Comida, Void> columnaEditar = new TableColumn<>("Editar");
        columnaEditar.setCellFactory(param -> new TableCell<Comida, Void>() {
            private final Button btnEditar = new Button();

            {
                ImageView imageView = new ImageView(new Image(getClass()
                        .getResource("/odiowpf/medidietasdesktop/imagenes/editar-icono.png")
                        .toExternalForm()));
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                btnEditar.setGraphic(imageView);
                btnEditar.setStyle("-fx-background-color: orange;");
                btnEditar.setOnAction(event -> {
                    Comida comida = getTableView().getItems().get(getIndex());
                    System.out.println("Editar: " + comida.getNombre());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEditar);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        // Columna para eliminar
        TableColumn<Comida, Void> columnaEliminar = new TableColumn<>("Eliminar");
        columnaEliminar.setCellFactory(param -> new TableCell<Comida, Void>() {
            private final Button btnEliminar = new Button();

            {
                ImageView imageView = new ImageView(new Image(getClass()
                        .getResource("/odiowpf/medidietasdesktop/imagenes/eliminar-icono.png")
                        .toExternalForm()));
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                btnEliminar.setGraphic(imageView);
                btnEliminar.setStyle("-fx-background-color: red;");
                btnEliminar.setOnAction(event -> {
                    Comida comida = getTableView().getItems().get(getIndex());
                    System.out.println("Eliminar: " + comida.getNombre());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEliminar);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        // Distribuir el espacio disponible proporcionalmente entre las columnas
        columnaComida.prefWidthProperty().bind(tablaComidas.widthProperty().multiply(0.28));
        columnaReceta.prefWidthProperty().bind(tablaComidas.widthProperty().multiply(0.50));
        columnaEditar.prefWidthProperty().bind(tablaComidas.widthProperty().multiply(0.10));
        columnaEliminar.prefWidthProperty().bind(tablaComidas.widthProperty().multiply(0.10));

        tablaComidas.getColumns().addAll(columnaComida, columnaReceta, columnaEditar, columnaEliminar);
    }

    private void llenarDatos() {
        HashMap<String, Object> respuesta = ComidaDAO.obtenerComidas();
        ArrayList<Comida> listaComidas = (ArrayList<Comida>) respuesta.get(Constantes.KEY_OBJETO);
        comidas = FXCollections.observableArrayList(listaComidas);
        tablaComidas.setItems(comidas);
    }

    @FXML
    public void actionRegistrar(ActionEvent actionEvent) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass()
                            .getResource("/odiowpf/medidietasdesktop/vistas/FXMLRegistrarComida.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar comida");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }
}