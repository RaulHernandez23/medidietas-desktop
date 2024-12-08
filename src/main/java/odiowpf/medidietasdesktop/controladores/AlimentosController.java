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


public class AlimentosController {
    @FXML
    private TableView<Alimento> tablaAlimentos;

    private ObservableList<Alimento> alimentos;

    @FXML
    public void initialize() {
        configurarTabla();
        llenarDatosEjemplo();
    }

    private void configurarTabla() {
        TableColumn<Alimento, String> alimentoColumna = new TableColumn<>("Alimento");
        alimentoColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Alimento, String> racionColumna = new TableColumn<>("Ración");
        racionColumna.setCellValueFactory(new PropertyValueFactory<>("racion"));

        TableColumn<Alimento, Double> caloriasColumna = new TableColumn<>("Calorías");
        caloriasColumna.setCellValueFactory(new PropertyValueFactory<>("calorias"));

        TableColumn<Alimento, Integer> cantidadColumna = new TableColumn<>("Cantidad");
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        // Columna para editar
        TableColumn<Alimento, Void> editarColumna = new TableColumn<>("Editar");
        editarColumna.setCellFactory(param -> new TableCell<Alimento, Void>() {
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
                    Alimento alimento = getTableView().getItems().get(getIndex());
                    System.out.println("Editar: " + alimento.getNombre());
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
        TableColumn<Alimento, Void> eliminarColumna = new TableColumn<>("Eliminar");
        eliminarColumna.setCellFactory(param -> new TableCell<Alimento, Void>() {
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
                    Alimento alimento = getTableView().getItems().get(getIndex());
                    System.out.println("Eliminar: " + alimento.getNombre());
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
        alimentoColumna.prefWidthProperty().bind(tablaAlimentos.widthProperty().multiply(0.25));
        racionColumna.prefWidthProperty().bind(tablaAlimentos.widthProperty().multiply(0.20));
        caloriasColumna.prefWidthProperty().bind(tablaAlimentos.widthProperty().multiply(0.20));
        cantidadColumna.prefWidthProperty().bind(tablaAlimentos.widthProperty().multiply(0.13));
        editarColumna.prefWidthProperty().bind(tablaAlimentos.widthProperty().multiply(0.10));
        eliminarColumna.prefWidthProperty().bind(tablaAlimentos.widthProperty().multiply(0.10));

        tablaAlimentos.getColumns().addAll(alimentoColumna, racionColumna, caloriasColumna, cantidadColumna, editarColumna, eliminarColumna);
    }

    private void llenarDatosEjemplo() {
        alimentos = FXCollections.observableArrayList(
                new Alimento(1, "Patata", 200, 17.0, 0.1, 2.0, "path/to/patata.png", 150.0, true, "Marca A", 1, 2),
                new Alimento(2, "Pollo", 250, 0.0, 14.0, 27.0, "path/to/pollo.png", 200.0, true, "Marca B", 2, 3)
        );
        tablaAlimentos.setItems(alimentos);
    }
}