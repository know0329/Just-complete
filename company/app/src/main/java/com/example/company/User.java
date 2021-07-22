package com.example.company;

public class User {
    public String userName;
    public String gender;
    public String homeaddress;

    public User() {}

    public User(String userName, String gender, String homeaddress) {
        this.userName = userName;
        this.gender = gender;
        this.homeaddress = homeaddress;
    }

    public String getUserName() {
        return userName;
    }
    public String getGender() {
        return gender;
    }
    public String getHomeaddress() {
        return homeaddress;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setGender(String gender) {this.gender = gender;}
    public void setHomeaddress(String homeaddress) {this.homeaddress = homeaddress;}

    @Override
    public String toString() {
        return "User{" + "userName='" + userName + '\'' + ", gender='" + gender + '\'' + ", homeaddress='" + homeaddress + '\'' + '}';
    }


}