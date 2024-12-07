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
//        MFXTableColumn<Alimento> columnaNombre = new MFXTableColumn<>("Nombre", false, Comparator.comparing(Alimento::getNombre));
//        MFXTableColumn<Alimento> columnaCategoria = new MFXTableColumn<>("Categoria", false, Comparator.comparing(Alimento::getCategoria));
//        MFXTableColumn<Alimento> columnaRacion = new MFXTableColumn<>("Racion", false, Comparator.comparing(Alimento::getTamanoRacion));
//        MFXTableColumn<Alimento> columnaCalorias = new MFXTableColumn<>("Calorias", false, Comparator.comparing(Alimento::getCalorias));
//
//        columnaNombre.setRowCellFactory(alimento -> new MFXTableRowCell<Alimento, Object>(Alimento::getNombre));
//        columnaCategoria.setRowCellFactory(alimento -> new MFXTableRowCell<Alimento, Object>(Alimento::getCategoria));
//        columnaRacion.setRowCellFactory(alimento -> new MFXTableRowCell<Alimento, Object>(Alimento::getTamanoRacion) {{
//            setAlignment(Pos.CENTER);
//        }});
//        columnaCalorias.setRowCellFactory(alimento -> new MFXTableRowCell<Alimento, Object>(Alimento::getCalorias));
//        Alimento alimento1 = new Alimento("nombre", 100, 10.0, 10.0, 10.0, "imagen", 10.0, true, "marca", "categoria", 1);
//        Alimento alimento2 = new Alimento("nombre2", 200, 20.0, 20.0, 20.0, "imagen2", 20.0, true, "marca2", "categoria2", 2);
//        ArrayList<Alimento> alimentos = new ArrayList<Alimento>();
//        alimentos.add(alimento1);
//        alimentos.add(alimento2);
//
//        ObservableList<Alimento> observableAlimentos = FXCollections.observableArrayList(alimentos);
//
//        tablaAlimentos.getTableColumns().addAll(columnaNombre, columnaCategoria, columnaRacion, columnaCalorias);
//        tablaAlimentos.getFilters().addAll(
//                new StringFilter<>("Nombre", Alimento::getNombre),
//                new StringFilter<>("Categoria", Alimento::getCategoria)
//        );
//        tablaAlimentos.setItems(observableAlimentos);
    }
}