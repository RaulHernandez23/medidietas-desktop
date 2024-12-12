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

        String apiUrl = Constantes.URL_REST + RUTA;
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
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerComidaPorId(int id) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_REST + RUTA + id;
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitudHttp = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("x-token", GestorToken.TOKEN)
                .GET()
                .build();

        try {
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();
            JSONObject comidaJson = new JSONObject(cuerpoRespuesta);

            Comida comida = new Comida(
                    comidaJson.getInt("id"),
                    comidaJson.getString("nombre"),
                    comidaJson.getString("preparacion_video"),
                    comidaJson.getString("receta"),
                    comidaJson.getBoolean("estado")
            );

            JSONArray alimentosJson = comidaJson.getJSONArray("alimentos");
            HashMap<String, Double> alimentos = new HashMap<>();
            for(int i = 0; i < alimentosJson.length(); i++){
                JSONObject alimentoJson = alimentosJson.getJSONObject(i);
                alimentos.put(alimentoJson.getString("nombre"), alimentoJson.getDouble("cantidad"));
            }
            comida.setAlimentos(alimentos);

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_OBJETO, comida);
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> registrarComida(Comida comida) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);

        // Se crea manualmente ya que si se mapean los atributos son transformados de snake_case a camelCase
        try {
            JSONObject comidaJson = new JSONObject();
            comidaJson.put("nombre", comida.getNombre());
            comidaJson.put("preparacion_video", comida.getPreparacionVideo());
            comidaJson.put("receta", comida.getReceta());
            comidaJson.put("estado", comida.getEstado());
            JSONArray alimentosJson = new JSONArray();
            for(String key : comida.getAlimentos().keySet()){
                JSONObject alimentoJson = new JSONObject();
                alimentoJson.put("nombre", key);
                alimentoJson.put("cantidad", comida.getAlimentos().get(key));
                alimentosJson.put(alimentoJson);
            }
            comidaJson.put("alimentos", alimentosJson);

            String apiUrl = Constantes.URL_REST + RUTA;
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest solicitudHttp = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("x-token", GestorToken.TOKEN)
                    .POST(HttpRequest.BodyPublishers.ofString(comidaJson.toString()))
                    .build();
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();
            JSONObject respuestaJson = new JSONObject(cuerpoRespuesta);

            respuesta.put(Constantes.KEY_ERROR, false);
            if (respuestaJson.has("mensaje")) {
                respuesta.put(Constantes.KEY_MENSAJE, respuestaJson.getString("mensaje"));
            } else {
                respuesta.put(Constantes.KEY_MENSAJE, respuestaJson.getString("error"));
            }
        }
        catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> editarComida(Comida comida) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);

        try {
            JSONObject comidaJson = new JSONObject();
            comidaJson.put("nombre", comida.getNombre());
            comidaJson.put("preparacion_video", comida.getPreparacionVideo());
            comidaJson.put("receta", comida.getReceta());
            comidaJson.put("estado", comida.getEstado());
            JSONArray alimentosJson = new JSONArray();
            for(String key : comida.getAlimentos().keySet()){
                JSONObject alimentoJson = new JSONObject();
                alimentoJson.put("nombre", key);
                alimentoJson.put("cantidad", comida.getAlimentos().get(key));
                alimentosJson.put(alimentoJson);
            }
            comidaJson.put("alimentos", alimentosJson);

            String apiUrl = Constantes.URL_REST + RUTA + comida.getId();
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest solicitudHttp = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("x-token", GestorToken.TOKEN)
                    .PUT(HttpRequest.BodyPublishers.ofString(comidaJson.toString()))
                    .build();
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

    public static HashMap<String, Object> eliminarComida(int id) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_REST + RUTA + id;
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
