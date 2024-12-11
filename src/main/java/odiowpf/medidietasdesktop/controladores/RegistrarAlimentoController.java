package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import odiowpf.medidietasdesktop.daos.AlimentoDAO;
import odiowpf.medidietasdesktop.grpc.ServicioImagenComida;
import odiowpf.medidietasdesktop.modelos.Alimento;
import odiowpf.medidietasdesktop.modelos.Categoria;
import odiowpf.medidietasdesktop.modelos.UnidadMedida;
import odiowpf.medidietasdesktop.utilidades.Alertas;
import odiowpf.medidietasdesktop.utilidades.Constantes;
import odiowpf.medidietasdesktop.utilidades.ConversorImagen;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrarAlimentoController {

    @FXML
    private BorderPane bpImagenAlimento;

    @FXML
    private MFXButton btnRegistrar;

    @FXML
    private ComboBox<Categoria> cbCategoria;

    @FXML
    private ComboBox<UnidadMedida> cbUnidadMedida;

    @FXML
    private ImageView ivImagenAlimento;

    @FXML
    private Label lblTituloVentana;

    @FXML
    private Label lblErrorCarbohidratos;

    @FXML
    private Label lblErrorGrasas;

    @FXML
    private Label lblErrorMarca;

    @FXML
    private Label lblErrorNombreAlimento;

    @FXML
    private Label lblErrorProteinas;

    @FXML
    private Label lblErrorTamanoRacion;

    @FXML
    private TextField tfCalorias;

    @FXML
    private Label lblErrorCalorias;

    @FXML
    private TextField tfCarbohidratos;

    @FXML
    private TextField tfGrasas;

    @FXML
    private TextField tfMarca;

    @FXML
    private TextField tfNombreAlimento;

    @FXML
    private TextField tfProteinas;

    @FXML
    private TextField tfTamanoRacion;

    private Image imagenAlimento;

    public void initialize() {
        lblTituloVentana.setText(Constantes.TITULO_REGISTRAR_ALIMENTO);
        crearListeners();
        btnRegistrar.setDisable(true);
        obtenerUnidadesDeMedida();
        obtenerCategorias();
    }

    @FXML
    void actionCancelar(ActionEvent event) {
        if (Alertas.mostrarAlertaConfirmacion(Constantes.ALERTA_CONFIRMACION_TITULO,
                Constantes.ALERTA_CONFIRMACION_CONTENIDO)) {
            cerrarVentana();
        }
    }

    @FXML
    void actionCargarImagen(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(Constantes.CARGAR_IMAGEN_TITULO);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagen", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) bpImagenAlimento.getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);
        if (archivoSeleccionado != null) {
            imagenAlimento = new Image(archivoSeleccionado.toURI().toString());

            Image squareImage = ConversorImagen.ajustarImagenConRelleno(imagenAlimento, 60);

            // Crear el ImageView con la imagen cuadrada
            ivImagenAlimento = new ImageView(squareImage);
            ivImagenAlimento.setFitWidth(400);
            ivImagenAlimento.setFitHeight(400);

            // Crear un clip circular
            Circle clip = new Circle(200); // Radio del círculo

            // Centrar el clip
            clip.setCenterX(200);
            clip.setCenterY(200);

            // Asignar el clip al ImageView
            ivImagenAlimento.setClip(clip);
            ivImagenAlimento.setOnMouseClicked(this::actionCargarImagen);

            bpImagenAlimento.setCenter(ivImagenAlimento);
        }
    }

    @FXML
    void actionRegistrar(ActionEvent event) {
        registrarAlimento();
    }

    private void cerrarVentana() {
        Stage escenario = (Stage) tfNombreAlimento.getScene().getWindow();
        escenario.close();
    }

    private void obtenerUnidadesDeMedida() {
        HashMap<String, Object> respuesta = AlimentoDAO.obtenerUnidadesMedida();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            ArrayList<UnidadMedida> listaUnidades = (ArrayList<UnidadMedida>) respuesta.get(Constantes.KEY_OBJETO);
            ObservableList<UnidadMedida> unidadesObservableList = FXCollections.observableArrayList(listaUnidades);
            cbUnidadMedida.setItems(unidadesObservableList);
        } else {
            Alertas.mostrarAlertaError(Constantes.ALERTA_ERROR_TITULO,
                    respuesta.get(Constantes.KEY_MENSAJE).toString());
        }
    }

    private void obtenerCategorias() {
        HashMap<String, Object> respuesta = AlimentoDAO.obtenerCategorias();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            ArrayList<Categoria> listaCategorias = (ArrayList<Categoria>) respuesta.get(Constantes.KEY_OBJETO);
            ObservableList<Categoria> categoriasObservableList = FXCollections.observableArrayList(listaCategorias);
            cbCategoria.setItems(categoriasObservableList);
        } else {
            Alertas.mostrarAlertaError(Constantes.ALERTA_ERROR_TITULO,
                    respuesta.get(Constantes.KEY_MENSAJE).toString());
        }
    }

    private void crearListeners() {
        tfNombreAlimento.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeErrorNombres(tfNombreAlimento.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorNombreAlimento.setText(mensajeError);
                lblErrorNombreAlimento.setVisible(true);
            } else {
                lblErrorNombreAlimento.setVisible(false);
            }
            activarBotonRegistrar();
        });

        tfMarca.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeErrorMarca(tfMarca.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorMarca.setText(mensajeError);
                lblErrorMarca.setVisible(true);
            } else {
                lblErrorMarca.setVisible(false);
            }
            activarBotonRegistrar();
        });

        tfTamanoRacion.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeErrorCantidades(tfTamanoRacion.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorTamanoRacion.setText(mensajeError);
                lblErrorTamanoRacion.setVisible(true);
            } else {
                lblErrorTamanoRacion.setVisible(false);
            }
            activarBotonRegistrar();
        });

        cbUnidadMedida.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            activarBotonRegistrar();
        });

        cbCategoria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            activarBotonRegistrar();
        });

        tfCalorias.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeErrorCantidades(tfCalorias.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorCalorias.setText(mensajeError);
                lblErrorCalorias.setVisible(true);
            } else {
                lblErrorCalorias.setVisible(false);
            }
            activarBotonRegistrar();
        });

        tfGrasas.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeErrorCantidades(tfGrasas.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorGrasas.setText(mensajeError);
                lblErrorGrasas.setVisible(true);
            } else {
                lblErrorGrasas.setVisible(false);
            }
            activarBotonRegistrar();
        });

        tfCarbohidratos.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeErrorCantidades(tfCarbohidratos.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorCarbohidratos.setText(mensajeError);
                lblErrorCarbohidratos.setVisible(true);
            } else {
                lblErrorCarbohidratos.setVisible(false);
            }
            activarBotonRegistrar();
        });

        tfProteinas.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeErrorCantidades(tfProteinas.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorProteinas.setText(mensajeError);
                lblErrorProteinas.setVisible(true);
            } else {
                lblErrorProteinas.setVisible(false);
            }
            activarBotonRegistrar();
        });

        bpImagenAlimento.getChildren().addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    System.out.println("Cambio detectado en el BorderPane");
                    activarBotonRegistrar();
                }
            }
        });

    }

    private String obtenerMensajeErrorNombres(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "El campo no puede estar vacío.";
        }
        if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            return "El campo no debe contener caracteres especiales.";
        }
        return "";
    }

    private String obtenerMensajeErrorMarca(String texto) {
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
        boolean nombreAlimentoValido = obtenerMensajeErrorNombres(tfNombreAlimento.getText()).isEmpty();
        boolean marcaValida = obtenerMensajeErrorMarca(tfMarca.getText()).isEmpty();
        boolean tamanoRacionValido = obtenerMensajeErrorCantidades(tfTamanoRacion.getText()).isEmpty();
        boolean UnidadMedidaSeleccionada = cbUnidadMedida.getSelectionModel().getSelectedItem() != null;
        boolean categoriaSeleccionada = cbCategoria.getSelectionModel().getSelectedItem() != null;
        boolean caloriasValidas = obtenerMensajeErrorCantidades(tfCalorias.getText()).isEmpty();
        boolean grasasValidas = obtenerMensajeErrorCantidades(tfGrasas.getText()).isEmpty();
        boolean carbohidratosValidos = obtenerMensajeErrorCantidades(tfCarbohidratos.getText()).isEmpty();
        boolean proteinasValidas = obtenerMensajeErrorCantidades(tfProteinas.getText()).isEmpty();
        boolean imagenSeleccionada = imagenAlimento != null;

        btnRegistrar.setDisable(!nombreAlimentoValido
                || !marcaValida
                || !tamanoRacionValido
                || !UnidadMedidaSeleccionada
                || !categoriaSeleccionada
                || !caloriasValidas
                || !grasasValidas
                || !carbohidratosValidos
                || !proteinasValidas
                || !imagenSeleccionada);
    }

    private void registrarAlimento(){
        try {
            String nombre = tfNombreAlimento.getText();
            String marca = tfMarca.getText();
            double tamanoRacion = Double.parseDouble(tfTamanoRacion.getText());
            int calorias = Integer.parseInt(tfCalorias.getText());
            double grasas = Double.parseDouble(tfGrasas.getText());
            double carbohidratos = Double.parseDouble(tfCarbohidratos.getText());
            double proteinas = Double.parseDouble(tfProteinas.getText());
            Categoria categoria = cbCategoria.getSelectionModel().getSelectedItem();
            UnidadMedida unidadMedida = cbUnidadMedida.getSelectionModel().getSelectedItem();

            String extension = null;
            byte[] datosImagen = null;
            String nombreImagen = "";
            if (imagenAlimento != null) {
                String urlImagen = imagenAlimento.getUrl();
                extension = urlImagen.substring(urlImagen.lastIndexOf(".") + 1);
                nombreImagen = nombre + "." + extension;

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(SwingFXUtils.fromFXImage(imagenAlimento, null), extension, outputStream);
                datosImagen = outputStream.toByteArray();
            }

            Alimento nuevoAlimento =
                    new Alimento(nombre, calorias, carbohidratos,
                            grasas, proteinas, nombreImagen, tamanoRacion,
                            marca, categoria.getId(), unidadMedida.getId());

            HashMap<String, Object> respuesta = AlimentoDAO.registrarAlimento(nuevoAlimento, extension, datosImagen);
            if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
                Alertas.mostrarAlertaInformacion(Constantes.ALERTA_REGISTRO_ALIMENTO_TITULO,
                        respuesta.get(Constantes.KEY_MENSAJE).toString());
                cerrarVentana();
            } else {
                Alertas.mostrarAlertaError(Constantes.ALERTA_ERROR_TITULO,
                        respuesta.get(Constantes.KEY_MENSAJE).toString());
            }
        } catch (IOException e) {
            Alertas.mostrarAlertaError(Constantes.ALERTA_ERROR_TITULO,
                    "Error al procesar la imagen");
            e.printStackTrace();
        }
    }
}