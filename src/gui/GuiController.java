package gui;

import dataclasses.Friend;
import dataservices.FriendListExportService;
import dataservices.FriendListImportService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GuiController implements Initializable {
    @FXML
    private ListView<String> friendsListView;

    @FXML
    private TextField selectedFriendName;

    @FXML
    private TextField selectedFriendNumber;

    @FXML
    private TextField newFriendName;

    @FXML
    private TextField newFriendNumber;

    @FXML
    private Button addNewFriend;

    @FXML
    private Button deleteFriend;

    @FXML
    private Button editFriend;

    @FXML
    private TextField search;

    @FXML
    private Button searchButton;

    public List<Friend> friendList;
    Friend currentFriend;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        friendList = new FriendListImportService().importFriends();
        AtomicReference<List<String>> friendNames = new AtomicReference<>(friendList.stream().map(Friend::getName).toList());


        friendsListView.getItems().addAll(friendNames.get());

        friendsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            currentFriend = friendList.stream().filter(friend -> friend.getName().equals(newValue)).findFirst().get();
            selectedFriendName.setText(currentFriend.getName());
            selectedFriendNumber.setText(currentFriend.getPhoneNumber());
        });

        searchButton.setOnAction(event -> {
            friendsListView.getItems().clear();
            friendNames.set(friendList.stream().map(Friend::getName).filter(name -> name.toLowerCase().contains(search.getText().toLowerCase())).toList());
            friendsListView.getItems().addAll(friendNames.get());
        });

        addNewFriend.setOnAction(event -> {
            Friend newFriend = new Friend();
            newFriend.setName(newFriendName.getText());
            newFriend.setPhoneNumber(newFriendNumber.getText());
            friendList.add(newFriend);
            friendsListView.getItems().add(newFriend.getName());
        });

        deleteFriend.setOnAction(event -> {
            friendList.remove(currentFriend);
            friendsListView.getItems().remove(currentFriend.getName());
        });

        editFriend.setOnAction(event -> {
            currentFriend.setName(selectedFriendName.getText());
            currentFriend.setPhoneNumber(selectedFriendNumber.getText());
            friendsListView.getItems().set(friendsListView.getSelectionModel().getSelectedIndex(), currentFriend.getName());
        });

    }

    public void shutdown() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save your changes?", ButtonType.YES, ButtonType.NO);

        if (alert.showAndWait().get() == ButtonType.YES) {
            new FriendListExportService().exportFriends(friendList);
            System.out.println("Saved");
        }else {
            System.out.println("Changes not saved");
        }
    }
}
