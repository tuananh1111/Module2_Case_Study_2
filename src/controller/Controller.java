package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Supplement;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private TableView<Supplement> table;

    @FXML
    private TableColumn<Supplement, Integer> idColumn;

    @FXML
    private TableColumn<Supplement, String> nameColumn;

    @FXML
    private TableColumn<Supplement, Double> massColumn;

    @FXML
    private TableColumn<Supplement, String> sourceColumn;

    @FXML
    private TableColumn<Supplement, Integer> amountColumn;

    @FXML
    private TableColumn<Supplement, Integer> priceColumn;

    private ObservableList<Supplement> supplementList;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField massText;

    @FXML
    private TextField sourceText;

    @FXML
    private TextField amountText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField searchText;

    private final NumberFormat numberFormat= new DecimalFormat("#,###");



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplementList = FXCollections.observableArrayList(
                readFile()
        );
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        massColumn.setCellValueFactory(new PropertyValueFactory<>("mass"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setCellFactory(tc -> new TableCell<Supplement, Integer>() {
            @Override
            protected void updateItem(Integer price, boolean empty) {
                super.updateItem(price, empty);
                if (price == null || empty) {
                    setText(null);
                } else {
                    setText(numberFormat.format(price));
                }
            }
        });
        table.setItems(supplementList);
        this.search();
    }

    public void search() {
        FilteredList<Supplement> searchList = new FilteredList<>(supplementList, b -> true);
        searchText.textProperty().addListener(((observable, oldValue, newValue) -> {
            searchList.setPredicate(supplement -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseValue = newValue.toLowerCase();
                if (String.valueOf(supplement.getId()).indexOf(lowercaseValue) != -1) return true;
                else if (supplement.getName().toLowerCase().indexOf(lowercaseValue) != -1) return true;
                else if (supplement.getSource().toLowerCase().indexOf(lowercaseValue) != -1) return true;
                else if (String.valueOf(supplement.getMass()).indexOf(lowercaseValue) != -1) return true;
                else if (String.valueOf(supplement.getAmount()).indexOf(lowercaseValue) != -1) return true;
                else if (String.valueOf(supplement.getPrice()).indexOf(lowercaseValue) != -1) return true;
                else return false;
            });
        }));
        table.setItems(searchList);
    }

    public void add() {
        Supplement supplement = new Supplement();

        try {
            supplement.setId(Integer.parseInt(idText.getText()));
            supplement.setName(nameText.getText());
            supplement.setMass(Double.parseDouble(massText.getText()));
            supplement.setSource(sourceText.getText());
            supplement.setAmount(Integer.parseInt(amountText.getText()));
            supplement.setPrice(Integer.parseInt(priceText.getText()));

            boolean checkID = true;
            for (Supplement supplement1 : supplementList) {
                if (Integer.parseInt(idText.getText()) == supplement1.getId()) {
                    checkID = false;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Lỗi lặp ID sản phẩm");
                    alert.setHeaderText(null);
                    alert.setContentText("ID đã tồn tại");
                    alert.showAndWait();
                }
            }
            if (checkID) {
                supplementList.add(supplement);
            }
        } catch (NumberFormatException exception) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không đủ thành phần của sản phẩm !!!");
            alert.showAndWait();
        }
        resetText();
    }

    public void delete() {
        Supplement supplementSelect = table.getSelectionModel().getSelectedItem();
        if (supplementSelect != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete ?");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc muốn xóa không?");
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.get() == ButtonType.OK) {
                supplementList.remove(supplementSelect);
            }
        }
    }

    public void select() {
        Supplement supplementSelect = table.getSelectionModel().getSelectedItem();
        if (supplementSelect != null) {
            idText.setText(String.valueOf(supplementSelect.getId()));
            nameText.setText(supplementSelect.getName());
            massText.setText(String.valueOf(supplementSelect.getMass()));
            sourceText.setText(supplementSelect.getSource());
            amountText.setText(String.valueOf(supplementSelect.getAmount()));
            priceText.setText(String.valueOf(supplementSelect.getPrice()));
//            supplementList.remove(supplementSelect);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Not found ?");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa lựa chọn sản phẩm gì?");
            alert.showAndWait();
        }
    }

    public void update() {
        try {
            for (int i = 0; i < supplementList.size(); i++) {
                if (supplementList.get(i).getId() == Integer.parseInt(idText.getText())) {
                    supplementList.get(i).setId(Integer.parseInt(idText.getText()));
                    supplementList.get(i).setName(nameText.getText());
                    supplementList.get(i).setSource(sourceText.getText());
                    supplementList.get(i).setAmount(Integer.parseInt(amountText.getText()));
                    supplementList.get(i).setPrice(Integer.parseInt(priceText.getText()));
                    supplementList.get(i).setMass(Double.parseDouble(massText.getText()));
                    supplementList.set(i, supplementList.get(i));
                }
            }
        } catch (NumberFormatException exception) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Not found ?");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa lựa chọn sản phẩm để thay đổi?");
            alert.showAndWait();
        }
    }

    public void writeFile() {
        FileOutputStream file;
        ObjectOutputStream object;

        try {
            file = new FileOutputStream("supplement.dat");
            object = new ObjectOutputStream(file);

            for (Supplement supplement : supplementList) {
                object.writeObject(supplement);
            }

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Supplement> readFile() {
        List<Supplement> list = new ArrayList<>();
        FileInputStream file;
        ObjectInputStream object;
        try {
            file = new FileInputStream("supplement.dat");
            object = new ObjectInputStream(file);
            while (true) {
                list.add((Supplement) object.readObject());
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (EOFException exception) {
            exception.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void resetText() {
        idText.clear();
        nameText.clear();
        massText.clear();
        sourceText.clear();
        amountText.clear();
        priceText.clear();
    }
}

