package odiowpf.medidietasdesktop.controladores;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import odiowpf.medidietasdesktop.modelos.Alimento;

import java.util.ArrayList;
import java.util.Comparator;

public class AlimentosController
{
    @javafx.fxml.FXML
    private MFXTableView tablaAlimentos;

    @javafx.fxml.FXML
    public void initialize() {
        configurarTabla();
        tablaAlimentos.autosizeColumnsOnInitialization();
    }

    private void configurarTabla() {
        MFXTableColumn<Alimento> columnaNombre = new MFXTableColumn<>("Nombre", false, Comparator.comparing(Alimento::getNombre));
        MFXTableColumn<Alimento> columnaCategoria = new MFXTableColumn<>("Categoria");
        MFXTableColumn<Alimento> columnaRacion = new MFXTableColumn<>("Racion");
        MFXTableColumn<Alimento> columnaCalorias = new MFXTableColumn<>("Caloria");

        tablaAlimentos.getTableColumns().addAll(columnaNombre, columnaCategoria, columnaRacion, columnaCalorias);


    }
}