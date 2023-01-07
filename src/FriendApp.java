import dataclasses.Friend;
import dataservices.FriendListExportService;
import dataservices.FriendListImportService;

import java.util.List;

public class FriendApp {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        FriendListImportService friendListImportService = new FriendListImportService();
        List<Friend> friendList = friendListImportService.importFriends();

        friendList.add(new Friend().setName("John").setPhoneNumber("1234567890"));
        friendList.add(new Friend().setName("Jane").setPhoneNumber("0987654321"));

        FriendListExportService friendListExportService = new FriendListExportService();
        friendListExportService.exportFriends(friendList);
        System.out.println("Done!");

    }
}