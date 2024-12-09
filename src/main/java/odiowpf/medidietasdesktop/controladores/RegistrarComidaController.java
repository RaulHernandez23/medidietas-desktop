package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class RegistrarComidaController {

    @FXML
    private MFXButton btnAgregarAlimento;

    @FXML
    private MFXButton btnRegistrar;

    @FXML
    private ComboBox<?> cbAlimentos;

    @FXML
    private Label lblErrorCantidad;

    @FXML
    private Label lblErrorLinkVideo;

    @FXML
    private Label lblErrorNombreComida;

    @FXML
    private Label lblErrorReceta;

    @FXML
    private Label lblTituloVentana;

    @FXML
    private TextField tfCantidad;

    @FXML
    private TextField tfLinkVideo;

    @FXML
    private TextField tfNombreComida;

    @FXML
    private TextArea tfReceta;

    @FXML
    private TableView<?> tvTablaAlimentos;

    @FXML
    private WebView wvVideo;

    @javafx.fxml.FXML
    public void initialize() {
        crearListeners();

        btnRegistrar.setDisable(true);
        btnAgregarAlimento.setDisable(true);
    }

    @FXML
    void actionAgregar(ActionEvent event) {

    }

    @FXML
    void actionCancelar(ActionEvent event) {

    }

    @FXML
    void actionRegistrar(ActionEvent event) {

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
                lblErrorLinkVideo.setVisible(false);
            }
            activarBotonRegistrar();
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
        btnRegistrar.setDisable(!nombreComidaValido
                || !recetaValida
                || !linkVideoValido);
    }

    private void activarBotonAgregar() {
        boolean cantidadValida = obtenerMensajeErrorCantidades(tfCantidad.getText()).isEmpty();
        btnAgregarAlimento.setDisable(!cantidadValida);
    }

}
