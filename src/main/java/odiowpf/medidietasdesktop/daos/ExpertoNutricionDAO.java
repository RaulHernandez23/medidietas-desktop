package odiowpf.medidietasdesktop.daos;

import odiowpf.medidietasdesktop.modelos.ExpertoNutricion;
import odiowpf.medidietasdesktop.utilidades.Constantes;
import odiowpf.medidietasdesktop.utilidades.GestorToken;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ExpertoNutricionDAO {

    public static final String RUTA = "expertos/";

    public static HashMap<String, Object> logIn(String correo, String contrasena) {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_BASE + RUTA + "login";

        try {
            HttpClient cliente = HttpClient.newHttpClient();

            JSONObject json = new JSONObject();
            json.put("correo", correo);
            json.put("contrasena", contrasena);

            HttpRequest solicitudHttp = HttpRequest.newBuilder().uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString(), StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());

            String cuerpoRespuesta = respuestaHttp.body();

            JSONObject respuestaJson = new JSONObject(cuerpoRespuesta);
            String mensaje = respuestaJson.getString("msg");

            JSONObject expertoJson = respuestaJson.getJSONObject("experto");

            ExpertoNutricion experto = new ExpertoNutricion(
                    expertoJson.getInt("id"),
                    expertoJson.getString("nombre"),
                    expertoJson.getString("apellido_paterno"),
                    expertoJson.getString("apellido_materno"),
                    expertoJson.getString("contrasena"),
                    expertoJson.getString("correo"),
                    Constantes.FORMATO_FECHA.parse(expertoJson.getString("fecha_nacimiento")),
                    expertoJson.getString("foto"),
                    expertoJson.getString("educacion"),
                    expertoJson.getString("perfil_profesional")
            );

            GestorToken.TOKEN = respuestaHttp.headers().firstValue("x-token").get();

            respuesta.put(Constantes.KEY_MENSAJE, mensaje);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_OBJETO, experto);
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }

        return respuesta;
    }
}
