package lk.ijse.sanasa.util;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ManageController {
    private static ManageController manageController;
    ArrayList<JFXTextField> textFields =new ArrayList<>();
    ArrayList<JFXComboBox> comboBoxes =new ArrayList<>();
    ArrayList<Label> labels =new ArrayList<>();
    ArrayList<DatePicker> datePickers =new ArrayList<>();

    private ManageController(){}

    public static ManageController getInstance(){
        return manageController==null?manageController=new ManageController():manageController;
    }

    public ArrayList<DatePicker> getDatePickers() {
        return datePickers;
    }

    public ArrayList<JFXTextField> getTextFields() {
        return textFields;
    }

    public ArrayList<JFXComboBox> getComboBoxes() {
        return comboBoxes;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void setDatePickers(DatePicker...args) {
        getLabels().clear();
        for (DatePicker datePicker : args) {
            getDatePickers().add(datePicker);
        }
    }

    public void setLabels(Label...args) {
        getLabels().clear();
        for (Label label : args) {
            getLabels().add(label);
        }
    }

    public void setTextFields(JFXTextField...args) {
        getTextFields().clear();
        for (JFXTextField textField : args) {
            getTextFields().add(textField);
        }
    }

    public void setComboBoxes(JFXComboBox...args) {
        getComboBoxes().clear();
        for (JFXComboBox comboBox : args) {
            getComboBoxes().add(comboBox);
        }
    }

    //Generate last account number

    public static String generateLastId(String id, String table, String regex) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select " + id + " from " + table + " order by " + id + " desc limit 1");
        if(resultSet.next()){
            String tableId=resultSet.getString(1);
            String[] split = tableId.split(regex);
            int lastDigit = Integer.valueOf(split[1])+1;
            return String.format(
                    regex+"%08d",
                    lastDigit
            );
        }
        return regex+"00000001";
    }

    //Clear form

    public void clearForm(){
        //Clear datepickers
        for (int i = 0; i < datePickers.size(); i++) {
            getDatePickers().get(i).setValue(LocalDate.now());
        }

        //Clear textFields
        for (int i = 0; i < getTextFields().size(); i++) {
            getTextFields().get(i).clear();
            getTextFields().get(i).setFocusColor(Paint.valueOf("white"));
        }

        //Clear labels
        for (int i = 0; i < getLabels().size(); i++) {
            getLabels().get(i).setText("");
        }

        //Clear comboBox
        for (int i = 0; i < getComboBoxes().size(); i++) {
            getComboBoxes().get(i).setItems(null);
        }
        getTextFields().clear();
        getComboBoxes().clear();
        getLabels().clear();
    }
}