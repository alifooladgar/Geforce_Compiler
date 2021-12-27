package com.company.sample;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
public class Faz1 {
    static HashMap<String , String> map = new HashMap<>();
    public static void main(String[] args) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\Admin\\Desktop"));
        int result = fileChooser.showOpenDialog(null);
        File cfile;
        if (result == JFileChooser.APPROVE_OPTION){
            cfile = fileChooser.getSelectedFile();
        }
        else{
            System.out.println("Not Found!!");
            return;
        }
        String mainaddress = cfile.getParent();
        Scanner sc = new Scanner(cfile);
        File output = new File(mainaddress+"\\faz1.txt");
        FileOutputStream fout = new FileOutputStream(output);
        /////////////////// pre process
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.contains("//")) {
                int index = line.indexOf("//");
                if (index==0)
                    continue;
                line = line.substring(0, index - 1);
            }
            if (line.length() == 0)
                continue;
            String[] arr = line.split(" ");
            if (arr[0].equals("#include")) {
                String address = arr[1].substring(1 , arr[1].length()-1);
                File lib = new File(mainaddress+"\\" + address);
                Scanner scanner = new Scanner(lib);
                while (scanner.hasNextLine()) {
                    String libline = scanner.nextLine();
                    if (libline.contains("//")) {
                        int index = libline.indexOf("//");
                        libline = libline.substring(0, index - 1);
                    }
                    if (libline.length() == 0)
                        continue;
                    String[] liblinearr = libline.split(" ");
                    switch (liblinearr[0]) {
                        case "#define":
                            define(libline);
                            break;
                        case "#undef":
                            undef(libline);
                            break;
                        case "#ifndef":
                            define(scanner.nextLine());
                            scanner.nextLine();
                            break;
                        default:
                            for (String i : liblinearr) {
                                if(i.contains(";"))
                                    i = i.replace(";","");
                                if (map.containsKey(i)) {
                                    libline = libline.replace(i, map.get(i));
                                }
                            }
                            // ++
                            if (libline.contains("++")){
                                int pindex = libline.indexOf("++");
                                StringBuilder id = new StringBuilder();
                                String other = " \n\t+*/;.,";
                                pindex--;
                                while (pindex>=0&&!other.contains(libline.charAt(pindex)+""))
                                    id  .append(libline.charAt(pindex--));
                                libline = libline.replace(id+"++" , id + " =" + id + "+1");
                            }
                            /// --
                            if (libline.contains("--")){
                                int pindex = libline.indexOf("--");
                                StringBuilder id = new StringBuilder();
                                String other = " \n\t+*/;.,";
                                pindex--;
                                while (pindex>=0&&!other.contains(libline.charAt(pindex)+""))
                                    id.append(libline.charAt(pindex--));
                                libline = libline.replace(id+"--" , id + " =" + id+ "-1");
                            }
                            fout.write((libline+"\n").getBytes());
                            break;
                    }
                }
            }
            switch (arr[0]) {
                case "#define":
                    define(line);
                    break;
                case "#include":
                    break;
                case "#undef":
                    undef(line);
                    break;
                case "#ifndef":
                    define(sc.nextLine());
                    sc.nextLine();
                    break;
                default:
                    for (String i : arr) {
                        if(i.contains(";"))
                            i = i.replace(";","");
                        if (map.containsKey(i)) {
                            line = line.replace(i, map.get(i));
                        }
                    }
                    boolean flag = false;
                    if (line.charAt(line.length()-1)== '{' && line.length()>1){
                        line = line.substring(0 , line.length()-1);
                        flag = true;
                    }
                    // ++
                    if (line.contains("++")){
                        int pindex = line.indexOf("++");
                        StringBuilder id = new StringBuilder();
                        String other = " \n\t+*/;.,";
                        pindex--;
                        while (pindex>=0&&!other.contains(line.charAt(pindex)+""))
                            id  .append(line.charAt(pindex--));
                        line = line.replace(id+"++" , id + " =" + id + "+1");
                    }
                    /// --
                    if (line.contains("--")){
                        int pindex = line.indexOf("--");
                        StringBuilder id = new StringBuilder();
                        String other = " \n\t+*/;.,";
                        pindex--;
                        while (pindex>=0&&!other.contains(line.charAt(pindex)+""))
                            id  .append(line.charAt(pindex--));
                        line = line.replace(id+"--" , id + " =" + id+ "-1");
                    }
                    fout.write((line+"\n").getBytes());
                    if (flag)
                        fout.write(("{" + "\n").getBytes());
                    break;
            }
        }
        //////////////////////////
        System.out.println("faz1 is done");
        Desktop desktop = Desktop.getDesktop();
        desktop.open(output);
    }
    static void define(String line){
        String[] arr = line.split(" ");
        if (map.containsKey(arr[1]))
            return;
        StringBuilder st = new StringBuilder();
        if (line.length() < 3)
            return;
        for(int i=2; i<arr.length;i++) {
            if (i!= arr.length-1)
                st.append(arr[i]).append(" ");
            else
                st.append(arr[i]);
        }
        map.put(arr[1], st.toString());
    }
    static void undef(String line){
        String[] arr = line.split(" ");
        if (line.length() < 3)
            return;
        map.remove(arr[1]);
    }
}
