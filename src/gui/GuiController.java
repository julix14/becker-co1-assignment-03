package gui;

import dataclasses.Friend;
import dataservices.FriendListImportService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class GuiController implements Initializable {
    @FXML
    private ListView<String> friendsListView;

    @FXML
    private Label friendName;

    @FXML
    private Label friendPhoneNumber;

    List<Friend> friendList;
    Friend currentFriend;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        friendList = new FriendListImportService().importFriends();
        List<String> friendNames = friendList.stream().map(Friend::getName).toList();

        friendsListView.getItems().addAll(friendNames);

        friendsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            currentFriend = friendList.stream().filter(friend -> friend.getName().equals(newValue)).findFirst().get();
            friendName.setText(currentFriend.getName());
            friendPhoneNumber.setText(currentFriend.getPhoneNumber());
        });


    }
}
