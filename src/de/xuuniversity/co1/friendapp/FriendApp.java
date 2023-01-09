package de.xuuniversity.co1.friendapp;

import de.xuuniversity.co1.friendapp.gui.GuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;

public class FriendApp extends Application {
    public static void main(String[] args){

        launch(args);

    }


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/Layout.fxml"));
        Parent root = loader.load();
        GuiController controller = loader.getController();

        stage.setTitle("Friends-Organizer");

        // Set icon of the application
        // Icon source = https://icons8.com/icon/qyyjUL6iltGF/people
        Image icon = new Image("file:src/de/xuuniversity/co1/friendapp/gui/icon_100x100.png");
        stage.getIcons().add(icon);

        // Add task-bar icon for MacOS
        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();

            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                var dockIcon = defaultToolkit.getImage("src/de/xuuniversity/co1/friendapp/gui/icon_100x100.png");
                taskbar.setIconImage(dockIcon);
            }
        }

        stage.setScene(new Scene(root));

        stage.show();

        stage.setOnCloseRequest(event -> controller.shutdown());


    }
}