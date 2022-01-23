package com.company.sample;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Faz4 {
    static HashMap<String , String> map;
    public static void main(String[] args) throws IOException {
        map = new HashMap<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\Admin\\Desktop"));
        int result = fileChooser.showOpenDialog(null);
        File faz2;
        if (result == JFileChooser.APPROVE_OPTION){
            faz2 = fileChooser.getSelectedFile();
        }
        else{
            System.out.println("Not Found!!");
            return;
        }
        String mainaddress = faz2.getParent();
        Scanner scanner = new Scanner(faz2);
        File output = new File(mainaddress+"\\faz4.txt");
        File faz1 = new File(mainaddress+"\\faz1.txt");
        FileOutputStream fout = new FileOutputStream(output);
        //////////////
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] arr = line.split(" ");
            String type = arr[0];
            if (type.equals("keyword") && (arr[arr.length-1].equals("int") || arr[arr.length-1].equals("float") || arr[arr.length-1].equals("double") || arr[arr.length-1].equals("boolean") || arr[arr.length-1].equals("long"))){
                String idl = scanner.nextLine();
                String[] IDarr = line.split(" ");
                String id = arr[arr.length-1];
                map.put(id , type);
            }
        }
        scanner = new Scanner(faz2);
        Scanner sc = new Scanner(faz2);
        scanner.nextLine();
        String idl="";
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (line.contains("=")){
                String[] arr1 = idl.split(" ");
                String idtype = checkType(arr1[arr1.length-1]);
                String vline = "";
                if (scanner.hasNextLine()) {
                    vline = scanner.nextLine();
                    sc.nextLine();
                }
                while (!vline.contains(";") || !vline.contains(")")){
                    String[] arr2 = vline.split(" ");
                    if (!checkType(arr2[arr2.length-1]).equals(idtype)){
                        fout.write((idtype + " , " + arr2[arr2.length-1]).getBytes());
                    }
                    if (scanner.hasNextLine()) {
                        vline = scanner.nextLine();
                        sc.nextLine();
                    }
                }
            }
            else if (sc.hasNextLine())
                idl = sc.nextLine();
        }
        System.out.println("Faz4 is done !!");
        Desktop desktop = Desktop.getDesktop();
        desktop.open(output);
    }
    static String checkType(String value){
        if (value.equals("true") || value.equals("false"))
            return "boolean";
        if (map.containsKey(value))
            return map.get(value);
        if (value.contains("."))
            return "double";
        return "int";
    }
}
