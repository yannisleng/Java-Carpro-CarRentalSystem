package com.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarDB extends database <Car>{

    public List<Car> getAllData(){
        List<Car> ls= new ArrayList<Car>();
        ArrayList<String> data = new ArrayList<String>();

        data = readFile(carPath);
        for (int i=0;i< data.size();i++){
            Car car = new Car();
            String[] arr = data.get(i).split( "`",12);
            car.setId(arr[0]);
            car.setBrand(arr[1]);
            car.setModel(arr[2]);
            car.setNumPlate(arr[3]);
            car.setPrice(Float.parseFloat(arr[4]));
            car.setSeat(Integer.parseInt(arr[5]));
            car.setFuel(Integer.parseInt(arr[6]));
            car.setStatus(arr[7]);
            car.setAddress(arr[8]);
            car.setPostCode(arr[9]);
            car.setState(arr[10]);
            car.setImgsrc(arr[11]);
            ls.add(car);
        }
        return ls;
    };

    public void addData(Car car){
        String data = car.toString();
        System.out.println(data);
        try{
            FileWriter file = new FileWriter(carPath, true);
            file.write(data);
            file.close();
            System.out.println("Done");
        }catch (Exception e){
            System.out.println("Database error");
            e.printStackTrace();
        }
    };

    public List<Car> searchData(String input){
        List<Car> carList = new ArrayList<>();
        List <Car> newList = new ArrayList<>();
        carList = getAllData();
        for(int i=0;i< carList.size();i++){
            if(carList.get(i).getId().equals(input)){
                newList.add(carList.get(i));
            }
        }
        return newList;
    }

    public void deleteData(String removeData){
        File currentFile = new File(carPath);
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

    };

    public void updateData(Car car){
        List<Car> cars = new ArrayList<>(getAllData());
        for(int i = 0; i < cars.size(); i++){
            if(car.getId().equals(cars.get(i).getId())){
                cars.set(i,car);
                try {
                    FileWriter file = new FileWriter(carPath);
                    for(Car item: cars){
                        file.write(String.valueOf(item));
                    }
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
