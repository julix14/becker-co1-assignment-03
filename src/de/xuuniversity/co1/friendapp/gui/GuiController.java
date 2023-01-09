package de.xuuniversity.co1.friendapp.gui;

import de.xuuniversity.co1.friendapp.dataclasses.Friend;
import de.xuuniversity.co1.friendapp.dataservices.FriendListExportService;
import de.xuuniversity.co1.friendapp.dataservices.FriendListImportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
    private TextField search;


    private List<Friend> friendList;
    private List<Friend> filteredList;
    private Friend currentFriend;
    private AtomicReference<List<String>> friendNames;
    private boolean wasChanged = false;


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        friendList = new FriendListImportService().importFriends();
        filteredList = friendList;
        updateFriendList();

        // Fill friends List initially
        friendsListView.getItems().addAll(friendNames.get());
    }

    // Ask the user if he wants to save the changes
    public void shutdown() {
        if (wasChanged) {
            // Initialize the upcoming alert
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Do you want to save your changes?",
                    ButtonType.YES,
                    ButtonType.NO);

            // Show the alert and act accordingly
            if (alert.showAndWait().get() == ButtonType.YES) {
                new FriendListExportService().exportFriends(friendList);
                System.out.println("Saved");
            } else {
                System.out.println("Changes not saved");
            }
        }
    }

    // Delete the selected friend
    public void deleteFriend(ActionEvent e) {
        Alert alert = new Alert(
                Alert.AlertType.WARNING,
                "Sure, you want to delete the contact",
                ButtonType.YES,
                ButtonType.NO);

        if (alert.showAndWait().get() == ButtonType.YES) {
            friendList.remove(currentFriend);
            friendsListView.getItems().remove(currentFriend.getName());
            resetCurrentFriendFields();
            wasChanged = true;

        }
    }

    // Get the content of the search-textfield and display the results
    public void searchFriends(ActionEvent e) {
        updateFriendList();
        filteredList = friendList.stream().filter(
                        friend -> friend.getName().toLowerCase()
                                .contains(search.getText().toLowerCase()))
                .toList();
        if (filteredList.size() > 0) {
            friendsListView.getItems().addAll(filteredList.stream().map(Friend::getName).toList());
        } else {
            friendsListView.getItems().addAll("No results found");
        }
        resetCurrentFriendFields();
    }

    // Edit the selected friend
    public void saveChangesOnFriend(ActionEvent e) {
        if (!selectedFriendName.getText().equals("") && !selectedFriendNumber.getText().equals("")) {
            currentFriend.setName(selectedFriendName.getText());
            currentFriend.setPhoneNumber(selectedFriendNumber.getText());

            friendsListView.getItems().set(
                    friendsListView.getSelectionModel().getSelectedIndex(),
                    currentFriend.getName());

            wasChanged = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill out all fields", ButtonType.OK);
            alert.showAndWait();
        }

    }

    // Add a new friend and show them in the list
    public void addFriend(ActionEvent e) {
        if (!newFriendName.getText().equals("") && !newFriendNumber.getText().equals("")) {
            Friend newFriend = new Friend();
            newFriend.setName(newFriendName.getText());
            newFriend.setPhoneNumber(newFriendNumber.getText());
            friendList.add(newFriend);
            updateFriendList();
            friendsListView.getItems().addAll(friendNames.get());

            wasChanged = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill out all fields", ButtonType.OK);
            alert.showAndWait();
        }


    }

    public void selectFriend() {
        currentFriend = filteredList.get(friendsListView.getSelectionModel().getSelectedIndex());
        if (currentFriend != null) {
            selectedFriendName.setText(currentFriend.getName());
            selectedFriendNumber.setText(currentFriend.getPhoneNumber());
            selectedFriendName.setEditable(true);
            selectedFriendNumber.setEditable(true);
            selectedFriendName.setDisable(false);
            selectedFriendNumber.setDisable(false);
        }
    }

    private void updateFriendList() {
        friendsListView.getItems().clear();
        friendNames = new AtomicReference<>(filteredList.stream().map(Friend::getName).toList());
    }

    private void resetCurrentFriendFields() {
        selectedFriendName.setText("Select a friend from list");
        selectedFriendNumber.setText("");
        selectedFriendName.setEditable(false);
        selectedFriendNumber.setEditable(false);
        selectedFriendName.setDisable(true);
        selectedFriendNumber.setDisable(true);
    }


}
