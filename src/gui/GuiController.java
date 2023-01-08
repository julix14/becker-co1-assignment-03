package gui;

import dataclasses.Friend;
import dataservices.FriendListExportService;
import dataservices.FriendListImportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

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
    private TextField search;


    public List<Friend> friendList;
    Friend currentFriend;
    List<String> friendNames;


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        friendList = new FriendListImportService().importFriends();
        friendNames = (friendList.stream().map(Friend::getName).toList());

        // Fill friends List initially
        friendsListView.getItems().addAll(friendNames);


        // Add listener to the list that updates the list
        friendsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            currentFriend = friendList.stream().filter(friend -> friend.getName().equals(newValue)).findFirst().get();
            selectedFriendName.setText(currentFriend.getName());
            selectedFriendNumber.setText(currentFriend.getPhoneNumber());
        });
    }

    // Ask the user if he wants to save the changes
    public void shutdown() {
        // Initialize the upcomming alert
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to save your changes?", ButtonType.YES, ButtonType.NO);

        // Show the alert and act accordingly
        if (alert.showAndWait().get() == ButtonType.YES) {
            new FriendListExportService().exportFriends(friendList);
            System.out.println("Saved");
        } else {
            System.out.println("Changes not saved");
        }
    }

    // Delete the selected friend
    public void deleteFriend(ActionEvent e) {
        friendList.remove(currentFriend);
        friendsListView.getItems().remove(currentFriend.getName());
    }

    // Get the content of the search-textfield and display the results
    public void searchFriends(ActionEvent e) {
        friendsListView.getItems().clear();
        friendNames = friendList.stream().map(Friend::getName).toList();
        List<String> searchResults = friendNames.stream().filter(name -> name.contains(search.getText())).toList();
        if (searchResults.size() > 0) {
            friendsListView.getItems().addAll(searchResults);
        } else {
            friendsListView.getItems().addAll("No results found");
        }

    }

    // Edit the selected friend
    public void saveChangesOnFriend(ActionEvent e) {
        currentFriend.setName(selectedFriendName.getText());
        currentFriend.setPhoneNumber(selectedFriendNumber.getText());
        friendsListView.getItems().set(friendsListView.getSelectionModel().getSelectedIndex(), currentFriend.getName());
    }

    // Add a new friend and show them in the list
    public void addFriend(ActionEvent e) {
        Friend newFriend = new Friend();
        newFriend.setName(newFriendName.getText());
        newFriend.setPhoneNumber(newFriendNumber.getText());
        friendList.add(newFriend);
    }


}
