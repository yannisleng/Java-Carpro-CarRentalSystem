package com.model;

import java.util.Date;

public class User {
    private String id;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String address;
    private String postCode;
    private String state;
    private String role;
    private String profilePic;

    public User(){}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getDateOfBirth() {return dateOfBirth;}

    public void setDateOfBirth(String dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    public String getGender() {return gender;}

    public void setGender(String gender) {this.gender = gender;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getPostCode() {return postCode;}

    public void setPostCode(String postCode) {this.postCode = postCode;}

    public String getState() {return state;}

    public void setState(String state) {this.state = state;}

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}

    public String getProfilePic() {return profilePic;}

    public void setProfilePic(String profilePic) {this.profilePic = profilePic;}


}
