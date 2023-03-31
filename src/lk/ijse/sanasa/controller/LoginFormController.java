package lk.ijse.sanasa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane pane;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/lk/ijse/sanasa/view/MainDashboardForm.fxml")));
    }
}
