package de.xuuniversity.co1.friendapp.dataservices;

import de.xuuniversity.co1.friendapp.dataclasses.Friend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FriendListImportService {


    public static List<Friend> importFriends() {
        List<Friend> friendList = new ArrayList<>();
        try {
            // Create a reader for the file
            BufferedReader reader = new BufferedReader(new FileReader("data/friends.txt"));

            // Go through the file line by line and parse the content to friend objects
            String line = reader.readLine();
            while (line != null) {
                friendList.add(parseFriend(line));
                line = reader.readLine();
            }
            reader.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }

        // Return the list of friends
        return friendList;
    }

    private static Friend parseFriend(String line) {
        // Set constants for the indexes of the name and phone number in friendData Array
        final int NAME_POSITION = 0;
        final int PHONE_NUMBER_POSITION = 1;

        // Split the line into an array
        String[] friendData = line.split(",");

        // Create a new Friend object and return it
        return new Friend(friendData[NAME_POSITION], friendData[PHONE_NUMBER_POSITION]);
    }
}
