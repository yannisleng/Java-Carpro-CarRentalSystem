package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String address;
    private String postCode;
    private String state;
    private String role;
    private String profilePic;

    public User(){}

    public User(String username, String firstName, String lastName, String dateOfBirth, String gender, String email, String phoneNum,
                String address, String postCode, String state, String role, String password, String profilePic) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNum = phoneNum;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.postCode = postCode;
        this.state = state;
        this.role = role;
        this.profilePic = profilePic;
    }

    @Override
    public String toString(){
        return username+"`"+firstName+"`"+lastName+"`"+dateOfBirth+"`"+gender+"`"+email+"`"+phoneNum+"`"+address+"`"+
                postCode+"`"+state+"`"+role+"`"+password+"`"+profilePic+"\n";
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPhoneNum() {return phoneNum;}

    public void setPhoneNum(String phoneNum) {this.phoneNum = phoneNum;}

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

    public boolean usernameValidation(String input){
        dataFactory dataFactory = new dataFactory();
        database db = dataFactory.getDB("user");
        List<User> userList = new ArrayList<>(db.getAllData());

        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getUsername().equals(input)){
                return false;
            }
        }
        return true;
    }

    public boolean commonValidation(String input, String type){
        String phoneNumReg = "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4,5})$";
        String emailReg = "^[A-Za-z0-9+_.-]+@(.+)$";
        String addressReg = "[A-Za-z0-9'\\.\\-\\s\\,]";
        String postCodeReg = "\\d{5}";

        String regex = "";
        switch (type){
            case "phoneNum":
                regex = phoneNumReg;
                break;
            case "email":
                regex = emailReg;
                break;
            case "address":
                regex = addressReg;
                break;
            case "postCode":
                regex = postCodeReg;
                break;
        }

        Matcher matcher = Pattern.compile(regex).matcher(input);
        if(matcher.find()){
            return true;
        }else{
            return false;
        }
    }
}
