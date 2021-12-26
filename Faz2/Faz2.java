package com.company.sample;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Faz2 {
    static FileOutputStream fout;
    static int i=0;
    static String line;
    static int lineNumber =0;
    public static void main(String[] args) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\Admin\\Desktop"));
        int result = fileChooser.showOpenDialog(null);
        File faz1;
        if (result == JFileChooser.APPROVE_OPTION){
            faz1 = fileChooser.getSelectedFile();
        }
        else{
            System.out.println("Not Found!!");
            return;
        }
        String mainaddress = faz1.getParent();
        Scanner sc = new Scanner(faz1);
        File output = new File(mainaddress+"\\faz2.txt");
        fout = new FileOutputStream(output);
        ////////////////////////// lexical analysis (state)
        while (sc.hasNextLine()){
            line = sc.nextLine();
            lineNumber++;
            ws();
            String others = "(){}[], \t\n;";
            StringBuilder st = new StringBuilder();
            if (i<line.length()&&others.contains(line.charAt(i)+""))
                if(isPunc(line.charAt(i)))
                    fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
            if (i>=line.length()){
                i=0;
                continue;}
            while (i<line.length()&&!others.contains(line.charAt(i)+"")){
                st.append(line.charAt(i));
                i++;
            }
            switch (st.toString()){
                case "if":
                    ifStmt();
                    break;
                case "void":
                    funcStmt();
                    break;
                case "else":
                    elseStmt();
                    break;
                case "for":
                    forStmt();
                    break;
                case "while":
                    whileStmt();
                    break;
                case "return":
                    returnStmt();
                    break;
                case "int":
                    fout.write((lineNumber+"keyword :: int"+"\n").getBytes());
                    if (line.contains("("))
                        funcStmt();
                    else
                        decStmt();
                    break;
                case "float":
                    fout.write((lineNumber+"keyword :: float"+"\n").getBytes());
                    if (line.contains("("))
                        funcStmt();
                    else
                        decStmt();
                    break;
                case "double":
                    fout.write((lineNumber+"keyword :: double"+"\n").getBytes());
                    if (line.contains("("))
                        funcStmt();
                    else
                        decStmt();
                    break;
                case "long":
                    fout.write((lineNumber+"keyword :: long"+"\n").getBytes());
                    if (line.contains("("))
                        funcStmt();
                    else
                        decStmt();
                    break;
                case "bool":
                    fout.write((lineNumber+"keyword :: bool"+"\n").getBytes());
                    if (line.contains("("))
                        funcStmt();
                    else
                        decStmt();
                    break;
                default:
                    idStmt(st.toString());
            }
            i=0;
        }
        ////////////////////////////////
        System.out.println("faz2 is done");
        Desktop desktop = Desktop.getDesktop();
        desktop.open(output);
    }
    // 8
    static void ws(){
        while(i<line.length()&&line.charAt(i) == ' ')
            i++;
        while(i<line.length()&&line.charAt(i) == '\t')
            i+=8;
    }
    // 1
    static void ifStmt() throws IOException {
        fout.write((lineNumber+"keyword :: if\n").getBytes());
        ws();
        if(isPunc(line.charAt(i)))
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        exprStmt();
        ws();
        if(isPunc(line.charAt(i)))
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        if (line.charAt(i)!='\n')
            if(isPunc(line.charAt(i)))
                fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
    }
    // 9
    static void elseStmt() throws IOException {
        ws();
        fout.write((lineNumber+"keyword :: else"+"\n").getBytes());
        ws();
        if (line.charAt(i)!='\n')
            if(isPunc(line.charAt(i)))
                fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
    }
    // 7
    static void forStmt()throws IOException{
        fout.write((lineNumber+"keyword :: for"+"\n").getBytes());
        ws();
        if(i<line.length()&&isPunc(line.charAt(i)))
            fout.write((lineNumber + " " + i +" punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        decStmt();
        ws();
        if(i<line.length()&&isPunc(line.charAt(i)))
            fout.write((lineNumber + " " + i +"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        exprStmt();
        ws();
        if(i<line.length()&&isPunc(line.charAt(i)))
            fout.write((lineNumber + " " + i +"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        operationStmt();
        ws();
        if(i<line.length()&&isPunc(line.charAt(i)))
            fout.write((lineNumber + " " + i +"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        if(i<line.length()&&isPunc(line.charAt(i)))
            fout.write((lineNumber + " " + i +"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
    }
    // 10
    static void whileStmt()throws IOException{
        ws();
        fout.write((lineNumber+"keyword :: while"+"\n").getBytes());
        ws();
        if(isPunc(line.charAt(i)))
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        exprStmt();
        ws();
        if(isPunc(line.charAt(i)))
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        if(isPunc(line.charAt(i)))
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
    }
    // 2
    static void relOpStmt()throws IOException{
        ws();
        StringBuilder st = new StringBuilder();
        st.append(line.charAt(i++));
        ws();
        if (line.charAt(i)=='=') {
            st.append("=");
            i++;
            ws();
        }
        switch (st.toString()){
            case ">":
                fout.write((lineNumber + " " + i +" relOp :: >"+"\n").getBytes());
                ws();
                break;
            case "<":
                fout.write((lineNumber + " " + i +" relOp :: <"+"\n").getBytes());
                ws();
                break;
            case "!=":
                fout.write((lineNumber + " " + i  +" relOp :: !="+"\n").getBytes());
                ws();
                break;
            case "==":
                fout.write((lineNumber + " " + i +" relOp :: =="+"\n").getBytes());
                ws();
                break;
            case ">=":
                fout.write((lineNumber + " " + i +" relOp :: >="+"\n").getBytes());
                ws();
                break;
            case "<=":
                fout.write((lineNumber + " " + i  +" relOp :: <="+"\n").getBytes());
                ws();
                break;
        }
        ws();
    }
    // 4
    static void termStmt()throws IOException{
        valueStmt();
        ws();
    }
    // 5
    static void idStmt(String name)throws IOException{
        ws();
        fout.write((lineNumber + " " + i +" identifier :: "+ name+"\n").getBytes());
        ws();
    }
    static void idStmt()throws IOException{
        ws();
        if (i<line.length()&&!((((int)line.charAt(i))>=65 &&((int)line.charAt(i))<=90) || (((int)line.charAt(i))>=97 &&((int)line.charAt(i))<=122)))
            return;
        StringBuilder st = new StringBuilder();
        String others = ".,<>(){}, \t\n;-=+*-/%";
        int index=i;
        while (i<line.length()&&!others.contains(line.charAt(index)+""))
            st.append(line.charAt(index++));
        // array
        if (st.toString().contains("[")){
            StringBuilder stringBuilder = new StringBuilder();
            ws();
            while (st.toString().charAt(i)!='['){
                stringBuilder.append(st.toString().charAt(i++));
            }
            ws();
            idStmt(stringBuilder.toString());
            fout.write((lineNumber+"punctuation :: ["+"\n").getBytes());
            ws();
            stringBuilder = new StringBuilder();
            boolean flag=false;
            while (st.toString().charAt(i)!=']'){
                stringBuilder.append(st.toString().charAt(i));
                flag=true;
                ws();
            }
            ws();
            if (flag)
                fout.write((lineNumber+"number :: "+stringBuilder+"\n").getBytes());
            ws();
            fout.write((lineNumber+"punctuation :: ]"+"\n").getBytes());
            ws();
        }
        else
            fout.write((lineNumber+"identifier :: "+st.toString()+"\n").getBytes());
        i=index;
    }
    // 6
    static void valueStmt()throws IOException{
        ws();
        if (i<line.length()&&((int)line.charAt(i))>=48 &&((int)line.charAt(i))<=57){
            numberStmt();
        }
        else{
            ws();
            StringBuilder st = new StringBuilder();
            String others = "(){}, \t\n;+-*/";
            int index=i;
            ws();
            while (i<line.length()&&!others.contains(line.charAt(index)+"")){
                st.append(line.charAt(index));
                index++;
            }
            ws();
            if (st.toString().equals("true"))
                fout.write((lineNumber + " " + i +" boolean :: true"+"\n").getBytes());
            if (st.toString().equals("false"))
                fout.write((lineNumber + " " + i +" boolean :: false"+"\n").getBytes());
            else if (!others.contains(st.toString()))
                idStmt(st.toString());
            i=index;
            ws();
        }
        ws();
        while (i<line.length()&&isOp(line.charAt(i))){
            fout.write((lineNumber + " " + i +" operator :: "+line.charAt(i++)+"\n").getBytes());
            idStmt();
            numberStmt();
        }
        ws();
    }
    // 14
    static void exprStmt()throws IOException{
        ws();
        termStmt();
        ws();
        relOpStmt();
        ws();
        termStmt();
        ws();
    }
    // 13
    static void operationStmt()throws IOException{
        ws();
        idStmt();
        ws();
        fout.write((lineNumber+"operator :: ="+"\n").getBytes());
        ws();
        valueStmt();
        ws();
        fout.write((lineNumber+"punctuation :: ;"+"\n").getBytes());
        ws();
    }
    // 12
    static void returnStmt()throws IOException{
        ws();
        fout.write((lineNumber+"keyword :: return"+"\n").getBytes());
        ws();
        valueStmt();
        ws();
        fout.write((lineNumber+"punctuation :: ;"+"\n").getBytes());
        ws();
    }
    // 11
    static void funcStmt()throws IOException{
        ws();
        idStmt();
        ws();
        if (i<line.length()&&isPunc(line.charAt(i))){
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
            ws();
        }
        while(i<line.length()&&!isPunc(line.charAt(i)))
            decStmt();
        ws();
        if (i<line.length()&&isPunc(line.charAt(i))){
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
            ws();
        }
        if (i<line.length()&&isPunc(line.charAt(i))){
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
            ws();
        }
    }
    // 15
    static void decStmt()throws IOException{
        ws();
        idStmt();
        ws();
        if (i<line.length()&&line.charAt(i)=='=') {
            fout.write((lineNumber + " " + i +" operator :: "+line.charAt(i++)+"\n").getBytes());
            valueStmt();
            ws();
        }
        if (i<line.length()&&isPunc(line.charAt(i)))
            fout.write((lineNumber + " " + i +" punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
    }
    // 3
    static void numberStmt()throws IOException{
        ws();
        if (!(((int)line.charAt(i))>=48 &&((int)line.charAt(i))<=57))
            return;
        StringBuilder stringBuilder = new StringBuilder();
        if ((line.charAt(i)+line.charAt(i+1)+"").equals("0x") || (line.charAt(i)+line.charAt(i+1)+"").equals("0b"))
            stringBuilder.append('0').append(line.charAt(i++));
        ws();
        String other = "{}[] \t\n;,";
        while (i<line.length()&&!other.contains(line.charAt(i)+"")){
            stringBuilder.append(line.charAt(i++));
            ws();
        }
        fout.write((lineNumber + " " + i +" number :: "+stringBuilder+"\n").getBytes());
        ws();
    }
    //
    static boolean isPunc(char in){
        String others = "(){}[];";
        return others.contains(in+"");
    }
    static boolean isOp(char in){
        String others = "+-*/%";
        return others.contains(in+"");
    }
    static boolean isKeyword(String in){
        String keys = "int  float double  long  boolean  if  while  for else  return  void ";
        return keys.contains(in);
    }
    // 16
    static void typeStmt(){
        
    }
}
