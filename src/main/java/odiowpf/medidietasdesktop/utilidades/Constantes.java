package odiowpf.medidietasdesktop.utilidades;

import java.text.SimpleDateFormat;

public class Constantes {

    // KEYS para los HashMaps
    public static final String KEY_ERROR = "error";
    public static final String KEY_MENSAJE = "mensaje";
    public static final String KEY_OBJETO = "objeto";

    // Constantes para la conexión con la API
    public static final String PUERTO = "8086";
    public static final String URL_BASE = "http://localhost:" + PUERTO + "/api/medidietas/";
    public static final String URL_GRPC = "localhost:8087";

    // Formatos
    public static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("yyyy-MM-dd");

    // Mensajes

}
