package odiowpf.medidietasdesktop.daos;

import javafx.scene.image.Image;
import odiowpf.medidietasdesktop.grpc.ServicioImagenComida;
import odiowpf.medidietasdesktop.modelos.Alimento;
import odiowpf.medidietasdesktop.modelos.Categoria;
import odiowpf.medidietasdesktop.modelos.UnidadMedida;
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

public class AlimentoDAO {

    private static final String RUTA = "alimentos/";

    public static HashMap<String, Object> obtenerUnidadesMedida() {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_REST + RUTA + "unidades-medida";
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitudHttp = HttpRequest.newBuilder().uri(URI.create(apiUrl))
                .header("x-token", GestorToken.TOKEN)
                .build();

        try {
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();
            JSONArray unidadesMedidaJson = new JSONArray(cuerpoRespuesta);
            ArrayList<UnidadMedida> unidadesMedida = new ArrayList<UnidadMedida>();

            for (int i = 0; i < unidadesMedidaJson.length(); i++) {
                UnidadMedida unidadMedida = new UnidadMedida(
                        unidadesMedidaJson.getJSONObject(i).getInt("id"),
                        unidadesMedidaJson.getJSONObject(i).getString("nombre")
                );
                unidadesMedida.add(unidadMedida);
            }

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_OBJETO, unidadesMedida);
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerCategorias() {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_REST + RUTA + "categorias";
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitudHttp = HttpRequest.newBuilder().uri(URI.create(apiUrl))
                .header("x-token", GestorToken.TOKEN)
                .build();

        try {
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();
            JSONArray categoriasJson = new JSONArray(cuerpoRespuesta);
            ArrayList<Categoria> categorias = new ArrayList<Categoria>();

            for (int i = 0; i < categoriasJson.length(); i++) {
                Categoria categoria = new Categoria(
                        categoriasJson.getJSONObject(i).getInt("id"),
                        categoriasJson.getJSONObject(i).getString("nombre")
                );
                categorias.add(categoria);
            }

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_OBJETO, categorias);
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerAlimentos() {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_REST + RUTA;
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

        String apiUrl = Constantes.URL_REST + RUTA + id;
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
            Image imagenComida = ServicioImagenComida.descargarImagenComida(alimento.getImagen());
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_OBJETO, alimento);
            respuesta.put(Constantes.KEY_IMAGEN, imagenComida);

        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> eliminarAlimento(int id) {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        String apiUrl = Constantes.URL_REST + RUTA + id;
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitudHttp = HttpRequest.newBuilder().uri(URI.create(apiUrl))
                .header("x-token", GestorToken.TOKEN)
                .DELETE()
                .build();

        try {
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();
            JSONObject respuestaJson = new JSONObject(cuerpoRespuesta);

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_MENSAJE, respuestaJson.getString("mensaje"));
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> registrarAlimento(Alimento alimento, String extension, byte[] datosImagen) {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        try {
            String fotoGuardada = ServicioImagenComida.subirImagenComida(alimento.getNombre(), extension, datosImagen);
            alimento.setImagen(fotoGuardada);

            // Se crea manualmente ya que si se mapean los atributos son transformados de snake_case a camelCase
            JSONObject alimentoJson = new JSONObject();
            alimentoJson.put("nombre", alimento.getNombre());
            alimentoJson.put("calorias", alimento.getCalorias());
            alimentoJson.put("carbohidratos", alimento.getCarbohidratos());
            alimentoJson.put("grasas", alimento.getGrasas());
            alimentoJson.put("imagen", alimento.getImagen());
            alimentoJson.put("proteinas", alimento.getProteinas());
            alimentoJson.put("tamano_racion", alimento.getTamanoRacion());
            alimentoJson.put("marca", alimento.getMarca());
            alimentoJson.put("id_categoria", alimento.getIdCategoria());
            alimentoJson.put("id_unidad_medida", alimento.getIdUnidadMedida());

            String apiUrl = Constantes.URL_REST + RUTA;
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest solicitudHttp = HttpRequest.newBuilder().uri(URI.create(apiUrl))
                    .header("x-token", GestorToken.TOKEN)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(alimentoJson.toString()))
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
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
            // TO DO Implementar eliminacion de la imagen si falla el registro
        }
        return respuesta;
    }

    public static HashMap<String, Object> editarAlimento(Alimento alimento, String extension, byte[] datosImagen) {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(Constantes.KEY_ERROR, true);

        try{
            // TO DO Implementar servicio de eliminacion de imagen
            if(extension != null) {
                String nuevaFoto = ServicioImagenComida.subirImagenComida(alimento.getNombre(), extension, datosImagen);
                alimento.setImagen(nuevaFoto);
            }
            JSONObject alimentoJson = new JSONObject();
            alimentoJson.put("nombre", alimento.getNombre());
            alimentoJson.put("calorias", alimento.getCalorias());
            alimentoJson.put("carbohidratos", alimento.getCarbohidratos());
            alimentoJson.put("grasas", alimento.getGrasas());
            alimentoJson.put("imagen", alimento.getImagen());
            alimentoJson.put("proteinas", alimento.getProteinas());
            alimentoJson.put("tamano_racion", alimento.getTamanoRacion());
            alimentoJson.put("marca", alimento.getMarca());
            alimentoJson.put("id_categoria", alimento.getIdCategoria());
            alimentoJson.put("id_unidad_medida", alimento.getIdUnidadMedida());

            String apiUrl = Constantes.URL_REST + RUTA + alimento.getId();
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest solicitudHttp = HttpRequest.newBuilder().uri(URI.create(apiUrl))
                    .header("x-token", GestorToken.TOKEN)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(alimentoJson.toString()))
                    .build();
            HttpResponse<String> respuestaHttp = cliente.send(solicitudHttp, HttpResponse.BodyHandlers.ofString());
            String cuerpoRespuesta = respuestaHttp.body();
            JSONObject respuestaJson = new JSONObject(cuerpoRespuesta);

            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_MENSAJE, respuestaJson.getString("mensaje"));
        } catch (Exception ex) {
            respuesta.put(Constantes.KEY_MENSAJE, "Error: " + ex.getMessage());
        }
        return respuesta;
    }
}
