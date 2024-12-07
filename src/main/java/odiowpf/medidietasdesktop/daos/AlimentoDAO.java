package odiowpf.medidietasdesktop.daos;

import odiowpf.medidietasdesktop.modelos.Alimento;
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
import java.util.List;

public class AlimentoDAO {

    private static final String RUTA = "alimentos/";

    public static HashMap<String, Object> obtenerAlimentos() {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_BASE + RUTA;
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitudHttp = HttpRequest.newBuilder().uri(URI.create(apiUrl))
                .header("x-token", GestorToken.TOKEN)
                .build();

        try{
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();
            JSONArray alimentosJson = new JSONArray(cuerpoRespuesta);
            ArrayList<Alimento> alimentos = new ArrayList<Alimento>();

            for (int i = 0; i < alimentosJson.length(); i++) {
                Alimento alimento = new Alimento(
                        alimentosJson.getJSONObject(i).getInt("id"),
                        alimentosJson.getJSONObject(i).getString("nombre"),
                        alimentosJson.getJSONObject(i).getInt("calorias"),
                        alimentosJson.getJSONObject(i).getDouble("carbohidratos"),
                        alimentosJson.getJSONObject(i).getDouble("grasas"),
                        alimentosJson.getJSONObject(i).getDouble("proteinas"),
                        alimentosJson.getJSONObject(i).getString("imagen"),
                        alimentosJson.getJSONObject(i).getDouble("tamano_racion"),
                        alimentosJson.getJSONObject(i).getBoolean("estado"),
                        alimentosJson.getJSONObject(i).getString("marca"),
                        alimentosJson.getJSONObject(i).getInt("id_categoria"),
                        alimentosJson.getJSONObject(i).getInt("id_unidad_medida")
                );
                alimentos.add(alimento);
            }

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_OBJETO, alimentos);
        } catch (Exception ex){
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerAlimentoPorId(int id) {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_BASE + RUTA + id;
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitudHttp = HttpRequest.newBuilder().uri(URI.create(apiUrl))
                .header("x-token", GestorToken.TOKEN)
                .build();

        try {
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();
            JSONObject alimentoJson = new JSONObject(cuerpoRespuesta);

            Alimento alimento = new Alimento(
                    alimentoJson.getInt("id"),
                    alimentoJson.getString("nombre"),
                    alimentoJson.getInt("calorias"),
                    alimentoJson.getDouble("carbohidratos"),
                    alimentoJson.getDouble("grasas"),
                    alimentoJson.getDouble("proteinas"),
                    alimentoJson.getString("imagen"),
                    alimentoJson.getDouble("tamano_racion"),
                    alimentoJson.getBoolean("estado"),
                    alimentoJson.getString("marca"),
                    alimentoJson.getInt("id_categoria"),
                    alimentoJson.getInt("id_unidad_medida")
            );
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_OBJETO, alimento);
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }

}
