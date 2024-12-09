package odiowpf.medidietasdesktop.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import odiowpf.medidietasdesktop.modelos.Alimento;
import odiowpf.medidietasdesktop.modelos.Comida;


public class ComidasController {
    @FXML
    private TableView<Comida> tablaComidas;

    private ObservableList<Comida> comidas;

    @FXML
    public void initialize() {
        configurarTabla();
        llenarDatosEjemplo();
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

    private void llenarDatosEjemplo() {
        comidas = FXCollections.observableArrayList(
                new Comida(1, "Pasta", "preparacion_video_1", "Receta de pasta", true),
                new Comida(2, "Ensalada", "preparacion_video_2", "Receta de ensalada", true)
        );
        tablaComidas.setItems(comidas);
    }
}