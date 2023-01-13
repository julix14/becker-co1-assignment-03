package de.xuuniversity.co1.friendapp.dataservices;

import de.xuuniversity.co1.friendapp.dataclasses.Friend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.List;

public abstract class FriendListExportService {

    public static void exportFriends(List<Friend> friendList) {
        BufferedWriter writer = null;
        try {
            // Create a writer for the file
            writer = new BufferedWriter(new FileWriter("data/friends.txt"));

            friendList.sort(Comparator.comparing(Friend::getName));

            // Go through the list of friends and write them to the file
            for (Friend friend : friendList) {
                writer.write(friendToString(friend));
                writer.newLine();
            }
            // Close the writer
            writer.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Close the writer
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
        }

    private static String friendToString(Friend friend) {
        // Create a String out of the friend object
        return friend.getName() + "," + (friend.getPhoneNumber() == null ? "No number found" : friend.getPhoneNumber());
    }
}
