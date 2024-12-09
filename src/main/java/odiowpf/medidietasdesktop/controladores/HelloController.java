package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import odiowpf.medidietasdesktop.utilidades.ConversorUrlYoutube;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private WebView marcoVideo;


    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void botonPrueba(ActionEvent actionEvent) {
        cargarVideo();
    }

    private void cargarVideo(){
        String videoUrlCorta = "https://youtu.be/lA54Fs79Gtc";
        String videoUrlEmbed = "https://www.youtube.com/embed/lA54Fs79Gtc";
        String videoUrlCompleta = "https://www.youtube.com/watch?v=lA54Fs79Gtc&pp=ygUTZW5zYWxhZGEgZGUgbWFuemFuYQ%3D%3D";

        String conversion = ConversorUrlYoutube.convertirUrl(videoUrlCorta);
        marcoVideo.getEngine().load(videoUrlEmbed);
    }
}