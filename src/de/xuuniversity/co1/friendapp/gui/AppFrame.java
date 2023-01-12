package de.xuuniversity.co1.friendapp.gui;

import de.xuuniversity.co1.friendapp.dataclasses.Friend;
import de.xuuniversity.co1.friendapp.dataservices.FriendListExportService;
import de.xuuniversity.co1.friendapp.dataservices.FriendListImportService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class AppFrame extends JFrame {
    // Initialize variables
    List<Friend> originalFriendList;
    List<Friend> friendList;


    public AppFrame() {
        super("Friends Organizer");
        // Original Friend List is used to check if the list was changed
        originalFriendList = FriendListImportService.importFriends();
        friendList = new ArrayList<>(originalFriendList);

        // Create the root panel
        JPanel rootPanel = new JPanel();

        // Center the window on the screen
        int width = 600;
        int height = 450;
        this.setSize(width, height);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (int) ((screenSize.getWidth() - width) / 2);
        int y = (int) ((screenSize.getHeight() - height) / 2);
        this.setLocation(x, y);

        // Set basic options to the JFrame
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the components and add them to the frame
        initRootComponents();
        this.add(rootPanel);
        this.setVisible(true);

    }

    private void initRootComponents() {
        // Set basic layout options to root panel
        LayoutManager layout = new FlowLayout(FlowLayout.CENTER);
        this.setPreferredSize(new Dimension(300, 450));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(layout);

        //Create the different GUI-Items:
        //Buttons
        JButton addButton = new JButton("Add new friend");
        JButton deleteButton = new JButton("Delete");
        JButton searchButton = new JButton("Search");

        //RadioButtons
        JRadioButton addRadioButton = new JRadioButton("Add new");
        JRadioButton viewRadioButton = new JRadioButton("View friends");
        viewRadioButton.setSelected(true);
        ButtonGroup viewAddRadioGroup = new ButtonGroup();
        viewAddRadioGroup.add(addRadioButton);
        viewAddRadioGroup.add(viewRadioButton);


        //Text-fields
        JTextField searchField = new JTextField("", 12);
        JTextField nameContentField = new JTextField("", 12);
        nameContentField.setEditable(false);
        JTextField phoneNumberContentField = new JTextField("", 12);
        phoneNumberContentField.setEditable(false);


        //Labels
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setPreferredSize(new Dimension(70, 20));
        JLabel phoneNumberLabel = new JLabel("Number: ");
        phoneNumberLabel.setPreferredSize(new Dimension(70, 20));


        //List
        JList<Friend> friendsJList = new JList<>(friendList.toArray(new Friend[0]));

        //Scroll-pane
        JScrollPane scrollPane = new JScrollPane(friendsJList);


        //Combine the different GUI-Items:
        //Panels
        //Crate the List-panel. It contains the list/scroll-pane and the search-field
        JPanel listPanel = new JPanel();
        listPanel.setPreferredSize(new Dimension(250, 400));
        listPanel.setBackground(Color.WHITE);
        listPanel.setLayout(new BorderLayout());

        //Sub-panel for the search-field
        //Search-panel contains the searchbar and the search button
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        //Add the scroll-pane and the search-panel to the list-panel
        listPanel.add(scrollPane, BorderLayout.CENTER);
        listPanel.add(searchPanel, BorderLayout.SOUTH);

        //Create the content-panel. It contains the name, number and the delete-button and add-button
        JPanel actions = new JPanel();
        actions.setPreferredSize(new Dimension(250, 400));
        actions.setBackground(Color.WHITE);
        actions.setLayout(new BorderLayout());

        //Sub-panel for radio buttons to change the mode from "view" to "add new"
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(1, 2));
        radioPanel.add(addRadioButton);
        radioPanel.add(viewRadioButton);

        //Create the sub-panel for the name and number
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout(3, 1));

        //Sub-panel for the name
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        namePanel.add(nameLabel);
        namePanel.add(nameContentField);

        //Sub-panel for the number
        JPanel phoneNumberPanel = new JPanel();
        phoneNumberPanel.setLayout(new FlowLayout());
        phoneNumberPanel.add(phoneNumberLabel);
        phoneNumberPanel.add(phoneNumberContentField);

        //Add the sub-panels to the content-panel
        infoPanel.add(namePanel, BorderLayout.NORTH);
        infoPanel.add(phoneNumberPanel, BorderLayout.CENTER);

        //Sub-panel for the add and delete button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);


        //Build up the actions-panel
        actions.add(radioPanel, BorderLayout.NORTH);
        actions.add(infoPanel, BorderLayout.CENTER);
        actions.add(buttonPanel, BorderLayout.SOUTH);


        // Add the two parent-panels to the root-panel
        this.add(listPanel);
        this.add(actions);


        //Add the different listeners:
        //Button-Listeners
        //Add new friend button
        searchButton.addActionListener(e -> {
            //Create a new list with the search results
            List<Friend> searchResult = new ArrayList<>();

            //Go through the friendList and check if the search-field contains the name of the friend
            for (Friend friend : friendList) {
                if (friend.getName().toLowerCase().contains(
                        searchField.getText().toLowerCase())
                ) {
                    searchResult.add(friend);
                }
            }
            friendsJList.setListData(searchResult.toArray(new Friend[0]));
        });

        //Add new friend button
        addButton.addActionListener(e -> {
            //Check if all fields are filled
            if (nameContentField.getText().isEmpty() || phoneNumberContentField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields");
            } else {
                //Create a new friend with the given name and number
                Friend newFriend = new Friend(nameContentField.getText(), phoneNumberContentField.getText());

                //Add the new friend to the friendList and display them in the JList
                friendList.add(newFriend);
                friendsJList.setListData(friendList.toArray(new Friend[0]));

                //Clear the text-fields
                nameContentField.setText("");
                phoneNumberContentField.setText("");
            }
        });

        //Delete a friend button
        deleteButton.addActionListener(e -> {
            // Get the selected friend and check if it's null
            Friend selectedFriend = friendsJList.getSelectedValue();
            if (selectedFriend == null) {
                // If it's null, show a message
                JOptionPane.showMessageDialog(this, "Please select a friend");
                return;
            }
            // If it's valid, remove the friend from the list and update the JList
            friendList.remove(selectedFriend);
            friendsJList.setListData(friendList.toArray(new Friend[0]));
        });

        //RadioButton-Listener
        //Radio button to change the mode from "view" to "add new"
        //Set up the layout so new friends can be added
        addRadioButton.addActionListener(e -> {
            nameContentField.setEditable(true);
            nameContentField.setText("");

            phoneNumberContentField.setEditable(true);
            phoneNumberContentField.setText("");

            addButton.setEnabled(true);
            deleteButton.setEnabled(false);
        });

        //Radio button to change the mode from "add new" to "view"
        //Set up the layout so friends can be viewed and not changed
        viewRadioButton.addActionListener(e -> {
            nameContentField.setEditable(false);
            phoneNumberContentField.setEditable(false);

            addButton.setEnabled(false);
            deleteButton.setEnabled(true);
        });

        //List-Listener
        //Show the selected friend in the text-fields
        friendsJList.addListSelectionListener(e -> {
            Friend selectedFriend = friendsJList.getSelectedValue();
            if (selectedFriend != null) {
                nameContentField.setText(selectedFriend.getName());
                phoneNumberContentField.setText(selectedFriend.getPhoneNumber());
            }
        });

        //Window-Listener
        //If there are changes in the friendList, ask the user if he wants to save the changes
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!originalFriendList.equals(friendList)) {
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            "Do you want to save changes?",
                            "Save changes?",
                            JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        FriendListExportService.exportFriends(friendList);
                        System.out.println("Changes saved");
                    } else {
                        System.out.println("Changes not saved");
                    }
                    System.out.println("Goodbye");
                    System.exit(0);
                }
            }
        });
    }
}
