package odiowpf.medidietasdesktop;

import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import odiowpf.medidietasdesktop.daos.AlimentoDAO;
import odiowpf.medidietasdesktop.modelos.Alimento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vistas/FXMLInicioSesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Inicio de sesión");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        HashMap<String, Object> resultado = AlimentoDAO.obtenerAlimentos("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjEsImlhdCI6MTczMzU0OTk2NywiZXhwIjoxNzMzNTU3MTY3fQ.l7RY6alkiIqcSMILpygqCpRnlE2xwqV1bAFoAF9W4LI");
        ArrayList<Alimento> alimentos = (ArrayList<Alimento>) resultado.get("objeto");
        for (Alimento alimento : alimentos) {
            System.out.println(alimento.getNombre());
            System.out.println(alimento.getCalorias());
            System.out.println(alimento.getCarbohidratos());
            System.out.println(alimento.getGrasas());
            System.out.println(alimento.getProteinas());
            System.out.println(alimento.getImagen());
            System.out.println(alimento.getTamanoRacion());
            System.out.println(alimento.isEstado());
            System.out.println(alimento.getMarca());
            System.out.println(alimento.getIdUnidadMedida());
            System.out.println(alimento.getId());
        }
    }

    public static void main(String[] args) {

        launch();

    }
}