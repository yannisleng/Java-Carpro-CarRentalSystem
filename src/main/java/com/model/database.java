package com.model;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class database<T> {

    final String path = "src/main/resources/com/example/carpro/database/";

    public ArrayList<String> readFile(String fileName){
        ArrayList<String> data = new ArrayList<String>();

        try{
            File file = new File(path+fileName);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                data.add(reader.nextLine());
            }
            reader.close();
        }catch (FileNotFoundException e){
            System.out.println("Database error");
            e.printStackTrace();
        }

        return data;
    };

    public void updateFile(String fileName,String data){
        try{
            Writer file = new FileWriter(path+fileName, true);
            file.write(data);
        }catch (Exception e){
            System.out.println("Database error");
            e.printStackTrace();
        }


        /*if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        //checking whether the data already exist or not
        boolean found = false;

        while(file.getFilePointer() < file.length()){

        }*/

    }

    public void editFile(String fileName){

    }
    public abstract List<T> searchData(String input);
    public abstract List<T> getAllData();
    public abstract void updateData(Object object);
    public abstract void addData(T t);
    public abstract void deleteData(String fileName);
}
