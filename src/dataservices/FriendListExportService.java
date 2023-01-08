package dataservices;

import dataclasses.Friend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class FriendListExportService {

        public void exportFriends(List<Friend> friendList) {
            try {
                // Create a writer for the file
                BufferedWriter writer = new BufferedWriter(new FileWriter("data/friends.txt"));

                // Go through the list of friends and write them to the file
                for (Friend friend : friendList) {
                    writer.write(friendToString(friend));
                    writer.newLine();
                }
                // Close the writer
                writer.close();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        private String friendToString(Friend friend) {
            // Create a String out of the friend object
            return friend.getName() + "," + (friend.getPhoneNumber() == null ? "No number found" : friend.getPhoneNumber());
        }
}
