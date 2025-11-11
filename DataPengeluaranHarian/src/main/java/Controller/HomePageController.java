/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Model.Pengeluaran;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class HomePageController implements Initializable {

    @FXML
    private TextField desk;
    @FXML
    private TextField juml;
    @FXML
    private DatePicker tgl;
    @FXML
    private Button add;
    @FXML
    private Button edit;
    @FXML
    private Button delete;
    @FXML
    private Button clear;
    @FXML
    private Label total;
    @FXML
    private TableColumn<Pengeluaran, Integer> jumlah;
    @FXML
    private TableColumn<Pengeluaran, LocalDate> tanggal;
    @FXML
    private TableColumn<Pengeluaran, String> kategori;
    @FXML
    private TableView<Pengeluaran> tablePengeluaran;
    @FXML
    private ComboBox<String> kate;
    private ObservableList<Pengeluaran> data;
    @FXML
    private TableColumn<Pengeluaran, String> deskripsi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        deskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        jumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        tanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        kategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        kate.setItems(FXCollections.observableArrayList(
            "Makanan", "Transportasi", "Hiburan", "Belanja", "Lainnya"
        ));
        tablePengeluaran.setItems(data);
        updateTotal();
        tablePengeluaran.setOnMouseClicked(this::handleTableClick);
    }    

    @FXML
    private void goAdd(ActionEvent event) {
        if (!desk.getText().isEmpty() && !juml.getText().isEmpty() && tgl.getValue() != null && kate.getValue() != null) {
            try {
                int jumlahInt = Integer.parseInt(juml.getText());
                Pengeluaran p = new Pengeluaran(
                        desk.getText(),
                        jumlahInt,
                        tgl.getValue(),
                        kate.getValue()
                );
                data.add(p);
                clearField();
                updateTotal();
            } catch (NumberFormatException e) {
                System.out.println("Jumlah harus berupa angka!");
            }
        }
    }

    @FXML
    private void goEdit(ActionEvent event) {
        Pengeluaran selected = tablePengeluaran.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                int jumlahInt = Integer.parseInt(juml.getText().replaceAll("[^0-9]", ""));
                selected.setDeskripsi(desk.getText());
                selected.setJumlah(jumlahInt);
                selected.setTanggal(tgl.getValue());
                selected.setKategori(kate.getValue());
                tablePengeluaran.refresh();
                clearField();
                updateTotal();
            } catch (NumberFormatException e) {
                System.out.println("Jumlah harus berupa angka!");
            }
        }
    }

    @FXML
    private void goDelete(ActionEvent event) {
        Pengeluaran selected = tablePengeluaran.getSelectionModel().getSelectedItem();
        if (selected != null) {
            data.remove(selected);
            clearField();
            updateTotal();
        }
    }

    @FXML
    private void goClear(ActionEvent event) {
        clearField();
        data.clear();
        updateTotal();
    }
    private void handleTableClick(MouseEvent event) {
        Pengeluaran selected = tablePengeluaran.getSelectionModel().getSelectedItem();
        if (selected != null) {
            desk.setText(selected.getDeskripsi());
            juml.setText(String.valueOf(selected.getJumlah()));
            tgl.setValue(selected.getTanggal());
            kate.setValue(selected.getKategori());
        }
    }
    private void clearField() {
        desk.clear();
        juml.clear();
        tgl.setValue(null);
        kate.setValue(null);
        tablePengeluaran.getSelectionModel().clearSelection();
    }

    private void updateTotal() {
        int totalRp = data.stream().mapToInt(Pengeluaran::getJumlah).sum();
        total.setText("Total: Rp " + totalRp);
    }
}
