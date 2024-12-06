package odiowpf.medidietasdesktop.grpcclient;

import imageService.ImageService;
import imageService.ProfileImageServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Base64;

public class ClienteImagenGrpc {

    ManagedChannel canal = NettyChannelBuilder.forTarget("localhost:50051").usePlaintext().build();

    public byte[] descargarImagen() {
        ProfileImageServiceGrpc.ProfileImageServiceBlockingStub stub = ProfileImageServiceGrpc.newBlockingStub(canal);
        byte[] imageBytes = null;
        try{
            ImageService.DownloadFileResponse respuesta =
                    stub.downloadFile(ImageService.DownloadFileRequest.newBuilder().setPath(
                            "C:\\Users\\wizar\\Desktop\\Repos\\medidietas-api-grpc\\resources\\normalUser\\1.jpg"
                    ).build());

            if (respuesta.getImageData() != null && !respuesta.getImageData().isEmpty()) {
                try {
                    imageBytes = Base64.getDecoder().decode(respuesta.getImageData());
                }
                catch (Exception e) {
                    System.out.println("Error al decodificar la imagen");
                    System.out.println(e.getMessage());
                }
                System.out.println("Imagen descargada correctamente");
            } else {
                System.out.println("No se ha podido descargar la imagen");
            }

        } catch (Exception e) {
            System.out.println("Error al descargar la imagen");
            System.out.println(e.getMessage());
        } finally {
            canal.shutdown();
        }

        return imageBytes;
    }
}
