package odiowpf.medidietasdesktop.daos;

import odiowpf.medidietasdesktop.modelos.Comida;
import odiowpf.medidietasdesktop.utilidades.Constantes;
import odiowpf.medidietasdesktop.utilidades.GestorToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

public class ComidaDAO {

    private static final String RUTA = "comidas/";

    public static HashMap<String, Object> obtenerComidas() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_BASE + RUTA;
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitudHttp = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("x-token", GestorToken.TOKEN)
                .GET()
                .build();

        try{
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();
            JSONArray comidasJson = new JSONArray(cuerpoRespuesta);
            ArrayList<Comida> comidas = new ArrayList<>();

            for(int i = 0; i < comidasJson.length(); i++){
                JSONObject comidaJson = comidasJson.getJSONObject(i);
                Comida comida = new Comida(
                        comidaJson.getInt("id"),
                        comidaJson.getString("nombre"),
                        comidaJson.getString("preparacion_video"),
                        comidaJson.getString("receta"),
                        comidaJson.getBoolean("estado")
                );
                comidas.add(comida);
            }

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_OBJETO, comidas);
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerComidaPorId(int id) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);

        return respuesta;
    }

    public static HashMap<String, Object> registrarComida() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);

        return respuesta;
    }

    public static HashMap<String, Object> editarComida() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);

        return respuesta;
    }

    public static HashMap<String, Object> eliminarComida(int id) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_BASE + RUTA + id;
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitudHttp = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("x-token", GestorToken.TOKEN)
                .DELETE()
                .build();

        try {
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();
            JSONObject respuestaJson = new JSONObject(cuerpoRespuesta);

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_MENSAJE, respuestaJson.getString(Constantes.KEY_MENSAJE));
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }
}
