package odiowpf.medidietasdesktop.utilidades;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alertas {
    public static void mostrarAlertaInformacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Alerta de Advertencia
    public static void mostrarAlertaAdvertencia(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Alerta de Error
    public static void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Alerta de Confirmación
    public static boolean mostrarAlertaConfirmacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        // Espera la respuesta del usuario
        return alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    public static void mostrarAlertaErrorConexion() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(Constantes.ALERTA_ERROR_TITULO);
        alerta.setHeaderText(null);
        alerta.setContentText(Constantes.ERROR_CONEXION);
        alerta.showAndWait();
        System.exit(0);

    }
}
