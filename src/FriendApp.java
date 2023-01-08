import dataservices.FriendListExportService;
import gui.GuiController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.FocusModel;
import javafx.stage.Stage;

import java.net.URL;

public class FriendApp extends Application {
    public static void main(String[] args){

        launch(args);

    }


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("guidata/Layout.fxml"));
        Parent root = loader.load();

        stage.setTitle("Friends-Organizer");
        stage.setScene(new Scene(root));
        stage.show();

        GuiController controller = loader.getController();

        stage.setOnCloseRequest(event -> {
            controller.shutdown();
        });


    }
}