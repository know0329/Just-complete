package com.example.accompany;

public class User {
    public String userName;
    public String email;
    public String gender;
    public String birthday;
    public String homeaddress;
    public String phonenumber;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userName, String email, String gender, String homeaddess, String phonenumber) {
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.homeaddress = homeaddess;
        this.phonenumber = phonenumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {return gender;}

    public String getBirthday() {return birthday;}

    public String getHomeaddress() {return homeaddress;}

    public void setHomeaddress(String homeaddess) {this.homeaddress = homeaddess;}

    public String getPhonenumber() {return phonenumber;}

    public void setPhonenumber(String phonenumber) {this.phonenumber = phonenumber;}

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

