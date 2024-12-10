package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import odiowpf.medidietasdesktop.daos.AlimentoDAO;
import odiowpf.medidietasdesktop.daos.ComidaDAO;
import odiowpf.medidietasdesktop.modelos.Alimento;
import odiowpf.medidietasdesktop.modelos.Comida;
import odiowpf.medidietasdesktop.utilidades.Alertas;
import odiowpf.medidietasdesktop.utilidades.Constantes;
import odiowpf.medidietasdesktop.utilidades.ConversorUrlYoutube;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrarComidaController {

    @FXML
    private MFXButton btnAgregarAlimento;

    @FXML
    private MFXButton btnRegistrar;

    @FXML
    private ComboBox<String> cbAlimentos;

    @FXML
    private Label lblErrorCantidad;

    @FXML
    private Label lblErrorLinkVideo;

    @FXML
    private Label lblErrorNombreComida;

    @FXML
    private Label lblErrorReceta;

    @FXML
    private TextField tfCantidad;

    @FXML
    private TextField tfLinkVideo;

    @FXML
    private TextField tfNombreComida;

    @FXML
    private TextArea tfReceta;

    @FXML
    private Label lblTituloVentana;

    @FXML
    private TableView<Map.Entry<String, Double>> tvTablaAlimentos;

    private HashMap<String, Double> alimentos;

    @FXML
    private WebView wvVideo;

    private int comidaid;

    private boolean esModificacion = false;

    private ObservableList<String> nombresAlimentos;

    private Comida comidaAModificar;

    @FXML
    public void initialize() {
        lblTituloVentana.setText(Constantes.TITULO_REGISTRAR_COMIDA);
        crearListeners();
        obtenerAlimentos();

        btnRegistrar.setDisable(true);
        btnAgregarAlimento.setDisable(true);

        configurarTablaAlimentos();
    }

    public void InicializarModificarComida(int comidaId) {
        lblTituloVentana.setText(Constantes.TITULO_MODIFICAR_COMIDA);
        this.comidaid = comidaId;
        crearListeners();
        obtenerAlimentos();
        btnRegistrar.setDisable(true);
        btnAgregarAlimento.setDisable(true);
        configurarTablaAlimentos();
        obtenerDetalleComida();
        esModificacion = true;
        btnRegistrar.setText(Constantes.TITULO_MODIFICAR_COMIDA);
    }

    @FXML
    void actionAgregar(ActionEvent event) {
        agregarAlimentoATabla();
    }

    @FXML
    void actionCancelar(ActionEvent event) {
        if (Alertas.mostrarAlertaConfirmacion(Constantes.ALERTA_CONFIRMACION_TITULO,
                Constantes.ALERTA_CONFIRMACION_CONTENIDO)) {
            cerrarVentana();
        }
    }

    @FXML
    void actionRegistrar(ActionEvent event) {
        if (!esModificacion ) {
            registrarComida();
        }else{
            modificarComida();
        }
    }

    private void registrarComida() {
        String nombre = tfNombreComida.getText();
        String receta = tfReceta.getText();
        String videoLink = tfLinkVideo.getText();
        Comida comida = new Comida(nombre, videoLink, receta, alimentos);
        HashMap<String, Object> resultado = ComidaDAO.registrarComida(comida);
        if (!(boolean) resultado.get("error")) {
            Alertas.mostrarAlertaInformacion("Registro exitoso", resultado.get("mensaje").toString());
            cerrarVentana();
        } else {
            Alertas.mostrarAlertaError("Error al registrar", resultado.get("mensaje").toString());
        }
    }

    private void cerrarVentana() {
        Stage escenario = (Stage) tfNombreComida.getScene().getWindow();
        escenario.close();
    }

    private void crearListeners() {
        tfNombreComida.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(tfNombreComida.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorNombreComida.setText(mensajeError);
                lblErrorNombreComida.setVisible(true);
            } else {
                lblErrorNombreComida.setVisible(false);
            }
            activarBotonRegistrar();
        });

        tfCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeErrorCantidades(tfCantidad.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorCantidad.setText(mensajeError);
                lblErrorCantidad.setVisible(true);
            } else {
                lblErrorCantidad.setVisible(false);
            }
            activarBotonAgregar();
        });

        tfReceta.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeErrorReceta(tfReceta.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorReceta.setText(mensajeError);
                lblErrorReceta.setVisible(true);
            } else {
                lblErrorReceta.setVisible(false);
            }
            activarBotonRegistrar();
        });

        tfLinkVideo.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeErrorReceta(tfLinkVideo.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorLinkVideo.setText(mensajeError);
                lblErrorLinkVideo.setVisible(true);
            } else {
                cargarVideo();
                lblErrorLinkVideo.setVisible(false);
            }
            activarBotonRegistrar();
        });

        cbAlimentos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean cantidadValida = obtenerMensajeErrorCantidades(tfCantidad.getText()).isEmpty();

            if (cbAlimentos.getSelectionModel().getSelectedItem() != null && cantidadValida) {
                activarBotonAgregar();
            }
        });

        tvTablaAlimentos.itemsProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                activarBotonRegistrar();
            }
        });

        tvTablaAlimentos.getItems().addListener((ListChangeListener<Map.Entry<String, Double>>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    activarBotonRegistrar();
                }
            }
        });
    }

    private String obtenerMensajeError(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "El campo no puede estar vacío.";
        }
        if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            return "El campo no debe contener caracteres especiales.";
        }
        return "";
    }

    private String obtenerMensajeErrorReceta(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "El campo no puede estar vacío.";
        }
        return "";
    }

    private String obtenerMensajeErrorCantidades(String texto) {
        if (texto == null || texto.trim().isEmpty() ) {
            return "Cantidad vacía";
        }
        if (!texto.matches("\\d+(\\.\\d+)?")) {
            return "Cantidad inválida.";
        }
        return "";
    }

    private void activarBotonRegistrar() {
        boolean nombreComidaValido = obtenerMensajeError(tfNombreComida.getText()).isEmpty();
        boolean recetaValida = obtenerMensajeErrorReceta(tfReceta.getText()).isEmpty();
        //La implementacion de link de video aun no esta completa, falta agregar que el WebView devuelva un bool
        boolean linkVideoValido = obtenerMensajeErrorReceta(tfLinkVideo.getText()).isEmpty();
        boolean tablaLlena = !alimentos.isEmpty();
        imprimirAlimentos();
        btnRegistrar.setDisable(!nombreComidaValido
                || !recetaValida
                || !linkVideoValido
                || !tablaLlena);
    }

    private void activarBotonAgregar() {
        boolean cantidadValida = obtenerMensajeErrorCantidades(tfCantidad.getText()).isEmpty();
        boolean alimentoSeleccionado = cbAlimentos.getSelectionModel().getSelectedItem() != null;
        btnAgregarAlimento.setDisable(!cantidadValida
                || !alimentoSeleccionado);
    }

    private void obtenerAlimentos() {
        HashMap<String, Object> respuesta = AlimentoDAO.obtenerAlimentos();
        ArrayList<Alimento> listaAlimentos = (ArrayList<Alimento>) respuesta.get(Constantes.KEY_OBJETO);

        nombresAlimentos = FXCollections.observableArrayList();
        for (Alimento alimento : listaAlimentos) {
            nombresAlimentos.add(alimento.getNombre());
        }
        cbAlimentos.setItems(nombresAlimentos);
    }

    private void configurarTablaAlimentos(){
        alimentos = new HashMap<>();

        TableColumn<Map.Entry<String, Double>, String> nombreColumn = new TableColumn<>("Nombre");
        nombreColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));

        TableColumn<Map.Entry<String, Double>, String> cantidadColumn = new TableColumn<>("Cantidad");
        cantidadColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getValue())));

        // Columna para eliminar
        TableColumn<Map.Entry<String, Double>, Void> eliminarColumna = new TableColumn<>("Eliminar");
        eliminarColumna.setCellFactory(param -> new TableCell<Map.Entry<String, Double>, Void>() {
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
                    Map.Entry<String, Double> alimento = getTableView().getItems().get(getIndex());
                    alimentos.remove(alimento.getKey()); // Elimina del HashMap
                    getTableView().getItems().remove(alimento); // Elimina de la tabla
                    activarBotonRegistrar();
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

        nombreColumn.prefWidthProperty().bind(tvTablaAlimentos.widthProperty().multiply(0.35));
        cantidadColumn.prefWidthProperty().bind(tvTablaAlimentos.widthProperty().multiply(0.35));
        eliminarColumna.prefWidthProperty().bind(tvTablaAlimentos.widthProperty().multiply(0.25));

        tvTablaAlimentos.getColumns().addAll(nombreColumn, cantidadColumn, eliminarColumna);
    }

    private void agregarAlimentoATabla() {
        String selectedNombre = cbAlimentos.getSelectionModel().getSelectedItem();
        Double cantidad = Double.parseDouble(tfCantidad.getText());
        alimentos.put(selectedNombre, cantidad);
        ObservableList<Map.Entry<String, Double>> data = FXCollections.observableArrayList(alimentos.entrySet());
        tvTablaAlimentos.setItems(data);
    }

    private void cargarVideo(){
        String videoUrl = tfLinkVideo.getText();
        String videoUrlConvertida = ConversorUrlYoutube.convertirUrl(videoUrl);

        if (videoUrlConvertida != null && !videoUrlConvertida.isEmpty()) {
            wvVideo.getEngine().load(videoUrlConvertida);
        }
    }

    //Metodo de depuracion
    private void imprimirAlimentos() {
        if (alimentos.isEmpty()) {
            System.out.println("No hay alimentos registrados.");
        } else {
            System.out.println("Alimentos registrados:");
            for (Map.Entry<String, Double> entry : alimentos.entrySet()) {
                System.out.println("Nombre: " + entry.getKey() + ", Cantidad: " + entry.getValue());
            }
        }
    }

    private void obtenerDetalleComida(){
        HashMap<String, Object> respuesta = ComidaDAO.obtenerComidaPorId(comidaid);

        Boolean error = (Boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!error) {
            comidaAModificar = (Comida) respuesta.get(Constantes.KEY_OBJETO);

            if (comidaAModificar != null) {
                tfNombreComida.setText(comidaAModificar.getNombre());
                tfLinkVideo.setText(comidaAModificar.getPreparacionVideo());
                tfReceta.setText(comidaAModificar.getReceta());

                alimentos = comidaAModificar.getAlimentos();
                if (alimentos != null) {
                    ObservableList<Map.Entry<String, Double>> items =
                            FXCollections.observableArrayList(alimentos.entrySet());
                    tvTablaAlimentos.setItems(items);
                }
            }
        } else {
            String mensajeError = (String) respuesta.get(Constantes.KEY_MENSAJE);
            Alertas.mostrarAlertaError(Constantes.ALERTA_ERROR_TITULO, mensajeError);
        }
    }

    private boolean compararComidaConCampos() {
        if (comidaAModificar == null) {
            return false;
        }

        boolean nombreIgual = tfNombreComida.getText().equals(comidaAModificar.getNombre());
        boolean linkVideoIgual = tfLinkVideo.getText().equals(comidaAModificar.getPreparacionVideo());
        boolean recetaIgual = tfReceta.getText().equals(comidaAModificar.getReceta());

        boolean alimentosIguales = alimentos.equals(comidaAModificar.getAlimentos());

        return nombreIgual && linkVideoIgual && recetaIgual && alimentosIguales;
    }

    private void modificarComida() {
        if(!compararComidaConCampos()) {
            String nombre = tfNombreComida.getText();
            String receta = tfReceta.getText();
            String videoLink = tfLinkVideo.getText();

            Comida comidaEditada = new Comida(comidaAModificar.getId(), nombre, videoLink, receta, comidaAModificar.getEstado(), alimentos);
            HashMap<String, Object> respuesta = ComidaDAO.editarComida(comidaEditada);
            if (!(boolean) respuesta.get("error")) {
                Alertas.mostrarAlertaInformacion(Constantes.ALERTA_MODIFICACION_COMIDA_TITULO
                        , respuesta.get("mensaje").toString());
                cerrarVentana();
            } else {
                Alertas.mostrarAlertaError(Constantes.ALERTA_ERROR_TITULO, respuesta.get("mensaje").toString());
            }
        }else{
            Alertas.mostrarAlertaInformacion(Constantes.ALERTA_MODIFICACION_COMIDA_TITULO,
                    Constantes.ALERTA_MODIFICACION_COMIDA_CONTENIDO);
            cerrarVentana();
        }


    }

}
