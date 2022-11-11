package com.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDB extends database{

    private final String fileName = "user.txt";

    public List<User> getAllData(){
        List<User> ls= new ArrayList<User>();
        ArrayList<String> data = new ArrayList<String>();

        data = readFile(fileName);
        for (int i=0;i< data.size();i++){
            User user = new User();
            String[] arr = data.get(i).split( "`",12);
            user.setId(arr[0]);
            user.setFirstName(arr[1]);
            user.setLastName(arr[2]);
            user.setDateOfBirth(arr[3]);
            user.setGender(arr[4]);
            user.setEmail(arr[5]);
            user.setAddress(arr[6]);
            user.setPostCode(arr[7]);
            user.setState(arr[8]);
            user.setRole(arr[9]);
            user.setPassword(arr[10]);
            user.setProfilePic(arr[11]);
            ls.add(user);
        }
        return ls;
    };


    public void updateData(Object object){};
    public void addData(){};
    public void deleteData(String fileName){};
}
