package dataclasses;


public class Friend {

    private String name;

    private String phoneNumber;

    public String getName() {
        return name;
    }

    public Friend setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Friend setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
