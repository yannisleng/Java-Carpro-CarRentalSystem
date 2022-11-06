package com.model;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class database {

    public database(){}

    private String path = "src/main/resources/com/example/carpro/database/";

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
    }
}
