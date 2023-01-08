import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class FriendApp extends Application {
    public static void main(String[] args){

        launch(args);

    }


    @Override
    public void start(Stage stage) throws Exception {
        URL urlToLayout = getClass().getResource("guidata/Layout.fxml");

        assert urlToLayout != null;
        Parent root = FXMLLoader.load(urlToLayout);

        stage.setTitle("Friends-Organizer");
        stage.setScene(new Scene(root));
        stage.show();
    }
}