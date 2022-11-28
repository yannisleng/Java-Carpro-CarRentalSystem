package com.model;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class database<T> {

    static String carPath = "src/main/resources/com/example/carpro/database/car.txt";
    static String brandPath = "src/main/resources/com/example/carpro/database/brand.txt";
    static String modelPath = "src/main/resources/com/example/carpro/database/model.txt";
    static String userPath = "src/main/resources/com/example/carpro/database/user.txt";
    static String bookingPath = "src/main/resources/com/example/carpro/database/booking.txt";

    public ArrayList<String> readFile(String fileName){
        ArrayList<String> data = new ArrayList<String>();

        try{
            File file = new File(fileName);
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

    public void updateFile(String path,String oldString, String newString){
        try{
            FileReader fr = new FileReader(path);
            String line;
            String lines = "";
            try (BufferedReader br = new BufferedReader(fr)) {

                while ((line = br.readLine()) != null) {
                    lines += line+"\n";
                }
                lines = lines.replaceAll(oldString,newString);
                FileWriter fw = new FileWriter(path);
                fw.write(lines);
                fw.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteFile(String path, String removeData){
        File currentFile = new File(path);
        File tempFile = new File(path+"tempFile.txt");
        String currentLine;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(currentFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            System.out.println(removeData);

            //rewrite data into new file
            while ((currentLine = reader.readLine())!=null){
                if(null!=currentLine && !currentLine.equalsIgnoreCase(removeData)){
                    writer.write(currentLine + "\n");
                }
            }

            writer.close();
            reader.close();

            currentFile.delete();
            boolean successful = tempFile.renameTo(currentFile);
            System.out.println(successful);
        }catch (Exception e){
            System.out.println("Delete data error");
            e.printStackTrace();
        }
    }

    public abstract List<T> searchData(String input);
    public abstract List<T> getAllData();
    public abstract void updateData(T t);
    public abstract void addData(T t);
    public abstract void deleteData(String fileName);
}
