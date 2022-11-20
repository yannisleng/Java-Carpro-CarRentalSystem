package com.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDB extends database <User>{

    private final String fileName = "user.txt";

    public List<User> getAllData(){
        List<User> ls= new ArrayList<User>();
        ArrayList<String> data = new ArrayList<String>();

        data = readFile(fileName);
        for (int i=0;i< data.size();i++){
            User user = new User();
            String[] arr = data.get(i).split( "`",13);
            user.setUsername(arr[0]);
            user.setFirstName(arr[1]);
            user.setLastName(arr[2]);
            user.setDateOfBirth(arr[3]);
            user.setGender(arr[4]);
            user.setEmail(arr[5]);
            user.setPhoneNum(arr[6]);
            user.setAddress(arr[7]);
            user.setPostCode(arr[8]);
            user.setState(arr[9]);
            user.setRole(arr[10]);
            user.setPassword(arr[11]);
            user.setProfilePic(arr[12]);
            ls.add(user);
        }
        return ls;
    };

    public void addData(User user){
        String data = user.toString();
        System.out.println(data);
        try{
            FileWriter file = new FileWriter(path+fileName, true);
            file.write(data);
            file.close();
            System.out.println("Done");
        }catch (Exception e){
            System.out.println("Database error");
            e.printStackTrace();
        }
    };

    public List<User> searchData(String input){
        List<User> userList = new ArrayList<>();
        List<User> newList = new ArrayList<>();
        userList = getAllData();
        for(int i=0;i< userList.size();i++){
            if(userList.get(i).getUsername().equals(input)){
                newList.add(userList.get(i));
            }
        }
        return newList;
    }

    public void updateData(Object object){};
    public void deleteData(String fileName){};
}
