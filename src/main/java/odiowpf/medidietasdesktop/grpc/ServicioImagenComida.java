package odiowpf.medidietasdesktop.grpc;

import imageService.FoodImageServiceGrpc;
import imageService.ImageService;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import javafx.scene.image.Image;
import odiowpf.medidietasdesktop.utilidades.Constantes;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class ServicioImagenComida {

    private static ManagedChannel canal = NettyChannelBuilder.forTarget(Constantes.URL_GRPC).usePlaintext().build();

    public static Image descargarImagenComida(String nombreImagen) {
        FoodImageServiceGrpc.FoodImageServiceBlockingStub stub = FoodImageServiceGrpc.newBlockingStub(canal);
        byte[] imagenBytes = null;

        try {
            ImageService.DownloadImageRequest solicitud = ImageService.DownloadImageRequest.newBuilder()
                    .setName(nombreImagen.toLowerCase())
                    .build();
            ImageService.DownloadImageResponse respuesta = stub.downloadFoodImage(solicitud);

            if (respuesta.getImageData() != null && !respuesta.getImageData().isEmpty()) {
                try {
                    imagenBytes = Base64.getDecoder().decode(respuesta.getImageData());
                } catch (Exception ex) {
                    System.out.println("Error al decodificar la imagen");
                    System.out.println(ex.getMessage());
                }
                System.out.println("Imagen descargada correctamente");
            } else {
                System.out.println("No se ha podido descargar la imagen");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(imagenBytes);
        Image imagen = new Image(bis);

        return imagen;
    }

    public static String subirImagenComida(String nombre, String extension, byte[] datosImagen) {
        FoodImageServiceGrpc.FoodImageServiceBlockingStub stub = FoodImageServiceGrpc.newBlockingStub(canal);
        String respuesta = null;
        try {
            ImageService.UploadImageRequest solicitud = ImageService.UploadImageRequest.newBuilder()
                    .setName(nombre.toLowerCase())
                    .setExtension(extension)
                    .setImageData(Base64.getEncoder().encodeToString(datosImagen))
                    .build();

            ImageService.UploadFileResponse respuestaServicio = stub.uploadFoodImage(solicitud);

            if (respuestaServicio.getResult()) {
                System.out.println(respuestaServicio.getImageName());
                respuesta = respuestaServicio.getImageName();
            } else {
                System.out.println("No se ha podido subir la imagen");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return respuesta;
    }
}
