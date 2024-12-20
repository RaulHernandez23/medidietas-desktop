package odiowpf.medidietasdesktop.daos;

import javafx.scene.image.Image;
import odiowpf.medidietasdesktop.grpc.ServicioImagenPerfil;
import odiowpf.medidietasdesktop.modelos.ExpertoNutricion;
import odiowpf.medidietasdesktop.utilidades.Constantes;
import odiowpf.medidietasdesktop.utilidades.GestorToken;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ExpertoNutricionDAO {

    public static final String RUTA = "expertos/";

    public static HashMap<String, Object> logIn(String correo, String contrasena) {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_REST + RUTA + "login";

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
            JSONObject jsonObject = new JSONObject(cuerpoRespuesta);

            if (jsonObject.has("experto")) {
                JSONObject expertoJson = jsonObject.getJSONObject("experto");

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

                Image foto = ServicioImagenPerfil.descargarImagenPerfil(experto.getFoto());

                GestorToken.TOKEN = respuestaHttp.headers().firstValue("x-token").get();

                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put(Constantes.KEY_OBJETO, experto);
                respuesta.put(Constantes.KEY_IMAGEN, foto);
            }

            if (jsonObject.has("msg")) {
                respuesta.put(Constantes.KEY_MENSAJE, jsonObject.getString("msg"));
            }
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.ERROR_CONEXION);
            System.out.println(ex.getMessage());
        }

        return respuesta;
    }

    public static HashMap<String, Object> logOut() {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_REST + RUTA + "logout";
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitudHttp = HttpRequest.newBuilder().uri(URI.create(apiUrl))
                .header("x-token", GestorToken.TOKEN)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();

            JSONObject respuestaJson = new JSONObject(cuerpoRespuesta);
            String mensaje = respuestaJson.getString("msg");

            respuesta.put(Constantes.KEY_MENSAJE, mensaje);
            respuesta.put(Constantes.KEY_ERROR, false);

        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }

        return respuesta;
    }
}
