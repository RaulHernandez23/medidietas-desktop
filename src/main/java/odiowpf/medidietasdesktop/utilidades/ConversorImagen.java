package odiowpf.medidietasdesktop.utilidades;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class ConversorImagen {
    public static Image ajustarImagenConRelleno(Image originalImage, int size) {
        // Crear un lienzo cuadrado
        WritableImage writableImage = new WritableImage(size, size);

        // Dibujar la imagen original centrada en el lienzo
        double originalWidth = originalImage.getWidth();
        double originalHeight = originalImage.getHeight();
        double scale = Math.min(size / originalWidth, size / originalHeight); // Escalar la imagen para que quepa
        double offsetX = (size - originalWidth * scale) / 2; // Calcular el desplazamiento horizontal
        double offsetY = (size - originalHeight * scale) / 2; // Calcular el desplazamiento vertical

        // Crear un SnapshotParameters para mantener la transparencia
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        // Crear un Canvas para dibujar la imagen escalada
        Canvas canvas = new Canvas(size, size);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.TRANSPARENT); // Fondo transparente
        gc.fillRect(0, 0, size, size); // Llenar el fondo

        // Dibujar la imagen original escalada y centrada en el Canvas
        gc.drawImage(originalImage, offsetX, offsetY, originalWidth * scale, originalHeight * scale);

        // Tomar una instantánea del Canvas y devolverla como Image
        return canvas.snapshot(params, writableImage);
    }
}
