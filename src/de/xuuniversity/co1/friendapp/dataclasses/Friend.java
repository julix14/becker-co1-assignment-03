package de.xuuniversity.co1.friendapp.dataclasses;


public class Friend {

    private final String name;

    private final String phoneNumber;

    public Friend(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
