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
