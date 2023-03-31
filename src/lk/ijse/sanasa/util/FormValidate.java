package lk.ijse.sanasa.util;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class FormValidate {
    private static FormValidate validate;
    private ArrayList<JFXTextField> textFieldsList;
    private ArrayList<JFXComboBox> comboBoxes;
    private ArrayList<String> regexList;

    private FormValidate() {
    }

    //Check validate for one item
    public boolean  isValidate(String regexPattern, String text) {
        return Pattern.compile(regexPattern).matcher(text).matches();
    }

    public static FormValidate getInstance() {
        if(validate==null){
            validate=new FormValidate();
            return validate;
        }else {
            return validate;
        }
    }

    //Check validation by all labels of (__)Notify
    public boolean isValidateSuccess(Label...label){
        for (int i = 0; i < label.length; i++) {
            if (!label[i].getText().equals("Validate success....")) {
                label[i].setText("Validate fail....");
                label[i].setStyle("-fx-text-fill: red");
                return false;
            }
        }
        return true;
    }

    //Check while typing and notify
    public void instanceWarningLabel(Label label, boolean validate, JFXTextField jfxTextField){
        if (validate) {
            label.setText("Validate success....");
            label.setStyle("-fx-text-fill: green");
            jfxTextField.setFocusColor(Paint.valueOf("green"));
        }else if(jfxTextField.getText().isEmpty()){
            label.setText("");
            jfxTextField.setFocusColor(Paint.valueOf("#4059a9"));
        } else {
            label.setText("Validate fail....");
            label.setStyle("-fx-text-fill: red");
            jfxTextField.setFocusColor(Paint.valueOf("red"));
        }
    }

    //Get regex Patterns to a ArrayList.
    public void setRegexList(String... args) {
        regexList=null;
        regexList=new ArrayList<>();
        for (String arg : args) {
            getRegexList().add(arg);
        }
    }

    //Get JTextFields to a ArrayList.
    public void setTextFieldsList(JFXTextField... args) {
        textFieldsList=null;
        textFieldsList=new ArrayList<>();
        textFieldsList.addAll(Arrays.asList(args));
    }

    //Get JFXComboboxes to a ArrayList.
    public void setComboboxes(JFXComboBox... args) {
        comboBoxes=null;
        comboBoxes=new ArrayList<>();
        comboBoxes.addAll(Arrays.asList(args));
    }

    //Get validation failed JTextFields to a ArrayList.
    public boolean checkValidation() {
        ArrayList<JFXTextField> failedValidationTextFields = new ArrayList<>();
        ArrayList<JFXComboBox> failedValidationComboboxes = new ArrayList<>();

        if(getComboBoxes()!=null) {
            for (int i = 0; i < comboBoxes.size(); i++) {
                if (comboBoxes.get(i).getSelectionModel().isEmpty()) {
                    failedValidationComboboxes.add(comboBoxes.get(i));
                }
            }
        }
        if(getTextFieldsList()!=null){
            for (int i = 0; i < getTextFieldsList().size(); i++) {
                if (!isValidate(getRegexList().get(i), getTextFieldsList().get(i).getText())) {
                    failedValidationTextFields.add(getTextFieldsList().get(i));
                }
            }
        }

        for (int i = failedValidationTextFields.size()-1; i >= 0; i--) {
            failedValidationTextFields.get(i).setFocusColor(Paint.valueOf("red"));
            failedValidationTextFields.get(i).requestFocus();
            }
        for (JFXComboBox failedValidationCombobox : failedValidationComboboxes) {
            failedValidationCombobox.setFocusColor(Paint.valueOf("red"));
            failedValidationCombobox.requestFocus();
        }
        return failedValidationComboboxes.size()==0 & failedValidationTextFields.size()==0;
    }

    public void makeInstanceWarningConfirm(Label label, String message){
        label.setText(message);
        label.setStyle("-fx-text-fill: green");
        new Alert(Alert.AlertType.CONFIRMATION,message).show();
    }

    public void makeInstanceWarning(Label label, String message){
        label.setText(message);
        label.setStyle("-fx-text-fill: orange");
        new Alert(Alert.AlertType.WARNING,message).show();
    }

    public void makeInstanceWarningError(Label label, String message){
        label.setText(message);
        label.setStyle("-fx-text-fill: red");
        new Alert(Alert.AlertType.ERROR,message).show();
    }

    public ArrayList<JFXTextField> getTextFieldsList() {
        return textFieldsList;
    }

    public ArrayList<JFXComboBox> getComboBoxes() {
        return comboBoxes;
    }

    public ArrayList<String> getRegexList() {
        return regexList;
    }
}