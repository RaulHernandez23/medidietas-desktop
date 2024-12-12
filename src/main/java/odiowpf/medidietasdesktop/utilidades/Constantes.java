package odiowpf.medidietasdesktop.utilidades;

import java.text.SimpleDateFormat;

public class Constantes {

    // KEYS para los HashMaps
    public static final String KEY_ERROR = "error";
    public static final String KEY_MENSAJE = "mensaje";
    public static final String KEY_OBJETO = "objeto";
    public static final String KEY_IMAGEN = "imagen";

    // Constantes para la conexión con la API
    public static final String PUERTO_REST = "8081";
    public static final String PUERTO_GRPC = "50052";
    public static final String HOST = "localhost";
    public static final String URL_REST = "http://" + HOST + ":" + PUERTO_REST + "/api/medidietas/";
    public static final String URL_GRPC = HOST + ":" + PUERTO_GRPC;

    // Formatos
    public static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("yyyy-MM-dd");

    // Mensajes
    public static final String ALERTA_CONFIRMACION_TITULO = "Confirmación";
    public static final String ALERTA_CONFIRMACION_CONTENIDO = "¿Estas seguro de cancelar la operación?";
    public static final String ALERTA_ERROR_TITULO = "Error";
    public static final String ALERTA_MODIFICACION_COMIDA_TITULO = "Modificación de comida exitosa";
    public static final String ALERTA_MODIFICACION_COMIDA_CONTENIDO = "La comida se modificó correctamente.";
    public static final String ALERTA_ELIMINAR_COMIDA = "¿Estas seguro de eliminar esta comida?";
    public static final String ALERTA_ELIMINAR_COMIDA_EXITO = "La comida fue eliminada correctamente";
    public static final String ALERTA_MODIFICACION_ALIMENTO_TITULO = "Modificación de alimento exitosa";
    public static final String ALERTA_MODIFICACION_ALIMENTO_CONTENIDO = "El alimento se modificó correctamente.";
    public static final String ALERTA_ELIMINAR_ALIMENTO = "¿Estas seguro de eliminar este alimento?";
    public static final String ALERTA_ELIMINAR_ALIMENTO_EXITO = "El alimento fue eliminado correctamente";

    //Titulos
    public static final String TITULO_REGISTRAR_COMIDA = "Registrar Comida";
    public static final String TITULO_MODIFICAR_COMIDA = "Modificar comida";
    public static final String TITULO_REGISTRAR_ALIMENTO = "Registrar Alimento";
    public static final String CARGAR_IMAGEN_TITULO = "Cargar imagen de alimento";
    public static final String ALERTA_REGISTRO_ALIMENTO_TITULO = "Registro de alimento";
    public static final String TITULO_MODIFICAR_ALIMENTO = "Modificar alimento";

    public static final Object ERROR_CONEXION = "No se pudo conectar a la red, intentelo de nuevo más tarde";
}
