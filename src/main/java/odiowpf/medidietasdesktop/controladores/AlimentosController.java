package odiowpf.medidietasdesktop.controladores;

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
import odiowpf.medidietasdesktop.daos.AlimentoDAO;
import odiowpf.medidietasdesktop.modelos.Alimento;
import odiowpf.medidietasdesktop.modelos.Comida;
import odiowpf.medidietasdesktop.utilidades.Alertas;
import odiowpf.medidietasdesktop.utilidades.Constantes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class AlimentosController {
    @FXML
    private TableView<Alimento> tablaAlimentos;

    @FXML
    private TextField tfBusquedaAlimento;

    private ObservableList<Alimento> alimentos;

    @FXML
    public void initialize() {
        configurarTabla();
        llenarDatos();
        configurarBusqueda();
    }

    private void configurarTabla() {
        TableColumn<Alimento, String> alimentoColumna = new TableColumn<>("Alimento");
        alimentoColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Alimento, Double> racionColumna = new TableColumn<>("Ración");
        racionColumna.setCellValueFactory(new PropertyValueFactory<>("tamanoRacion"));

        TableColumn<Alimento, Double> caloriasColumna = new TableColumn<>("Calorías");
        caloriasColumna.setCellValueFactory(new PropertyValueFactory<>("calorias"));

        TableColumn<Alimento, Integer> cantidadColumna = new TableColumn<>("Marca");
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("marca"));

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
                    abrirVentanaEdicion(alimento.getId());
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
                    eliminarAlimento(alimento.getId());
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

    private void llenarDatos() {
        HashMap<String, Object> respuesta = AlimentoDAO.obtenerAlimentos();
        ArrayList<Alimento> listaAlimentos = (ArrayList<Alimento>) respuesta.get(Constantes.KEY_OBJETO);

        ArrayList<Alimento> alimentosVigentes = new ArrayList<>();
        for (Alimento alimento : listaAlimentos) {
            if (alimento.isEstado()) {
                alimentosVigentes.add(alimento);
            }
        }
        alimentos = FXCollections.observableArrayList(alimentosVigentes);
        tablaAlimentos.setItems(alimentos);
    }

    private void configurarBusqueda() {
        tfBusquedaAlimento.textProperty().addListener((observable, oldValue, newValue) -> filtrarAlimentos(newValue));
    }

    private void filtrarAlimentos(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            tablaAlimentos.setItems(alimentos);
            configurarTabla();
        } else {
            ObservableList<Alimento> alimentosFiltrados = FXCollections.observableArrayList();
            for (Alimento alimento : alimentos) {
                if (alimento.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    alimentosFiltrados.add(alimento);
                }
            }
            tablaAlimentos.setItems(alimentosFiltrados);

        }

    }

    public void actionRegistrar(ActionEvent actionEvent) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass()
                            .getResource("/odiowpf/medidietasdesktop/vistas/FXMLRegistrarAlimento.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar alimento");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
            llenarDatos();

        } catch (IOException e) {
            Alertas.mostrarAlertaErrorConexion();
        }
    }

    private void abrirVentanaEdicion(int alimentoId) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass()
                            .getResource("/odiowpf/medidietasdesktop/vistas/FXMLRegistrarAlimento.fxml"));
            Parent root = loader.load();
            RegistrarAlimentoController controlador = loader.getController();
            controlador.InicializarModificarAlimento(alimentoId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Alimento");
            stage.initModality(Modality.APPLICATION_MODAL);
            tfBusquedaAlimento.clear();
            stage.setResizable(false);
            stage.showAndWait();
            llenarDatos();

        } catch (IOException e) {
            Alertas.mostrarAlertaErrorConexion();
        }
    }

    private void eliminarAlimento(int alimentoId) {
        if (Alertas.mostrarAlertaConfirmacion(Constantes.ALERTA_CONFIRMACION_TITULO, Constantes.ALERTA_ELIMINAR_ALIMENTO)) {
            HashMap<String, Object> respuesta = AlimentoDAO.eliminarAlimento(alimentoId);
            Boolean error = (Boolean) respuesta.get(Constantes.KEY_ERROR);
            if (!error) {
                Alertas.mostrarAlertaInformacion(Constantes.ALERTA_CONFIRMACION_TITULO,
                        Constantes.ALERTA_ELIMINAR_ALIMENTO_EXITO);
                llenarDatos();
            } else {
                Alertas.mostrarAlertaErrorConexion();
            }
        }
    }
}