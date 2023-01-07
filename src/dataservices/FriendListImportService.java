package dataservices;

import dataclasses.Friend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FriendListImportService {


    public List<Friend> importFriends() {
        List<Friend> friendList = new ArrayList<>();
        try{
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

    private Friend parseFriend(String line) {
        // Set constants for the indexes of the name and phone number in friendData Array
        final int NAME_POSITION = 0;
        final int PHONE_NUMBER_POSITION = 1;

        // Split the line into an array
        String[] friendData = line.split(",");

        // Create a new Friend object and return it
        Friend friend = new Friend();
        friend.setName(friendData[NAME_POSITION]).setPhoneNumber(friendData[PHONE_NUMBER_POSITION]);
        return friend;
    }
}
