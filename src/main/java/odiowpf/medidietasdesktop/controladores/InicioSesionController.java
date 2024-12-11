package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import odiowpf.medidietasdesktop.daos.ExpertoNutricionDAO;
import odiowpf.medidietasdesktop.modelos.ExpertoNutricion;
import odiowpf.medidietasdesktop.utilidades.Constantes;

import java.util.HashMap;

public class InicioSesionController {
    private ExpertoNutricion experto;

    @FXML
    private AnchorPane paneRaiz;
    @FXML
    private PasswordField txtPass;
    @FXML
    private TextField txtCorreo;
    @FXML
    private MFXButton btnEntrar;
    @FXML
    private BorderPane borderMensaje;
    @FXML
    private Label lblError;

    @FXML
    public void initialize() {
        borderMensaje.setVisible(false);
        btnEntrar.setDisable(true);
        txtCorreo.textProperty().addListener((observable, oldValue, newValue) -> validarCampos());
        txtPass.textProperty().addListener((observable, oldValue, newValue) -> validarCampos());

        // Hardcoded login depuración
        txtCorreo.setText("raulh230600@gmail.com");
        txtPass.setText("pass");
    }

    @FXML
    public void actionEntrar(ActionEvent actionEvent) {
        HashMap<String, Object> respuesta = ExpertoNutricionDAO.logIn(txtCorreo.getText(), txtPass.getText());

        if(!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/odiowpf/medidietasdesktop/vistas/FXMLMenu.fxml"));
                Scene scene = new Scene(loader.load(), 1280, 720);
                MenuController controlador = loader.getController();
                controlador.cargarDatos((ExpertoNutricion) respuesta.get(Constantes.KEY_OBJETO),
                        (Image) respuesta.get(Constantes.KEY_IMAGEN));
                Stage stage = new Stage();
                stage.setTitle("Menú principal");
                stage.setResizable(false);
                stage.setScene(scene);

                stage.show();

                Stage stageActual = (Stage) paneRaiz.getScene().getWindow();
                stageActual.close();


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            lblError.setText("Usuario y/o contraseña incorrectos. Por favor, verifíquelos");
            borderMensaje.setVisible(true);
        }
    }

    private void validarCampos() {
        boolean camposLlenos = !txtCorreo.getText().trim().isEmpty() && !txtPass.getText().trim().isEmpty();
        btnEntrar.setDisable(!camposLlenos);
    }
}
