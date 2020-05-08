package com.example.bilboard;

public class UserData {
    String name;
    String location;
    String age;

//    String country;
//    String capital;

    public UserData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public UserData(String name, String location, String age) {
        this.name = name;
        this.location = location;
        this.age = age;
    }
}
