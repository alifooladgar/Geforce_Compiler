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
            String others = "(){}, \t\n;";
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
                    funcStmt("void");
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
                    if (line.contains("("))
                        funcStmt("int");
                    else
                        decStmt("int");
                    break;
                case "float":
                    if (line.contains("("))
                        funcStmt("float");
                    else
                        decStmt("float");
                    break;
                case "double":
                    if (line.contains("("))
                        funcStmt("double");
                    else
                        decStmt("double");
                    break;
                case "long":
                    if (line.contains("("))
                        funcStmt("long");
                    else
                        decStmt("long");
                    break;
                case "bool":
                    if (line.contains("("))
                        funcStmt("bool");
                    else
                        decStmt("bool");
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
        if(i<line.length()&&isPunc(line.charAt(i)))
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        if (i<line.length()&&line.charAt(i)!='\n')
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
        if (i>=line.length())
            return;
        st.append(line.charAt(i++));
        ws();
        if (i<line.length()&&line.charAt(i)=='=') {
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
    // 5
    static void idStmt(String name)throws IOException{
        if (name.isEmpty())
            return;
//        if (name.toString().contains("[")){
//            StringBuilder stringBuilder = new StringBuilder();
//            int index =0;
//            ws();
//            while (i<line.length()&&name.charAt(index)!='['){
//                stringBuilder.append(name.charAt(index++));
//            }
//            ws();
//            idStmt(stringBuilder.toString());
//            fout.write((lineNumber+"punctuation :: "+name.charAt(index++)+"\n").getBytes());
//            ws();
//            stringBuilder = new StringBuilder();
//            boolean flag=false;
//            while (name.toString().charAt(index)!=']'){
//                stringBuilder.append(name.charAt(index++));
//                flag=true;
//                ws();
//            }
//            ws();
//            if (flag)
//                fout.write((lineNumber+"number :: "+stringBuilder+"\n").getBytes());
//            ws();
//            fout.write((lineNumber+"punctuation :: "+name.charAt(index)+"\n").getBytes());
//            ws();
//        }
        ws();
        fout.write((lineNumber + " " + i +" identifier :: "+ name+"\n").getBytes());
        ws();
        if (i<line.length()&&isOp(line.charAt(i)))
            fout.write((lineNumber+"operator :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        valueStmt();
        ws();
        if (i<line.length()&&isPunc(line.charAt(i)))
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
    }
    static void idStmt()throws IOException{
        ws();
        if (i<line.length()&&!((((int)line.charAt(i))>=65 &&((int)line.charAt(i))<=90) || (((int)line.charAt(i))>=97 &&((int)line.charAt(i))<=122)))
            return;
        StringBuilder st = new StringBuilder();
        String others = "., < >(){}, \t\n;-=+*-/%";
        //int index=i;
        while (i<line.length()&&!others.contains(line.charAt(i)+""))
            st.append(line.charAt(i++));
        // array
//        if (st.toString().contains("[")){
//            StringBuilder stringBuilder = new StringBuilder();
//            int index =0;
//            ws();
//            while (i<line.length()&&st.toString().charAt(index)!='['){
//                stringBuilder.append(st.toString().charAt(index++));
//            }
//            ws();
//            idStmt(stringBuilder.toString());
//            fout.write((lineNumber+"punctuation :: "+st.toString().charAt(index++)+"\n").getBytes());
//            ws();
//            stringBuilder = new StringBuilder();
//            boolean flag=false;
//            while (st.toString().charAt(index)!=']'){
//                stringBuilder.append(st.toString().charAt(index++));
//                flag=true;
//                ws();
//            }
//            ws();
//            if (flag)
//                fout.write((lineNumber+"number :: "+stringBuilder+"\n").getBytes());
//            ws();
//            fout.write((lineNumber+"punctuation :: "+st.toString().charAt(index)+"\n").getBytes());
//            ws();
//        }
        //else
            fout.write((lineNumber+"identifier :: "+st+"\n").getBytes());
        //i=index;
        ws();
        // a = 5 +3;
        if (i<line.length()&&isOp(line.charAt(i)))
            fout.write((lineNumber+"operator :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        valueStmt();
        ws();
        if (i<line.length()&&isPunc(line.charAt(i)))
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
        ws();
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
            String others = "(){}<>, \t\n;+-*/=";
            //int index=i;
            ws();
            while (i<line.length()&&!others.contains(line.charAt(i)+"")){
                st.append(line.charAt(i));
                i++;
            }
            ws();
            if (st.toString().equals("true"))
                fout.write((lineNumber + " " + i +" boolean :: true"+"\n").getBytes());
            if (st.toString().equals("false"))
                fout.write((lineNumber + " " + i +" boolean :: false"+"\n").getBytes());
            else if (!others.contains(st.toString()))
                idStmt(st.toString());
            //i=index;
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
        valueStmt();
        ws();
        String relop = "<= >= == != < >";
        if (i<line.length()&&!relop.contains(line.charAt(i)+""))
            return;
        relOpStmt();
        ws();
        valueStmt();
        ws();
    }
    // 13
    static void operationStmt()throws IOException{
        ws();
        idStmt();
        ws();
        if (i<line.length()&&isOp(line.charAt(i)))
            fout.write((lineNumber+"operator :: "+line.charAt(i++)+"\n").getBytes());
        ws();
        valueStmt();
        ws();
        if (i<line.length()&&isPunc(line.charAt(i)))
            fout.write((lineNumber+"punctuation :: "+line.charAt(i++)+"\n").getBytes());
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
    static void funcStmt(String type)throws IOException{
        fout.write((lineNumber+"keyword :: "+ type+"\n").getBytes());
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
        typeStmt();
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
    static void decStmt(String type)throws IOException{
        ws();
        fout.write((lineNumber+"keyword :: "+ type+"\n").getBytes());
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
        if (i<line.length()&&!(((int)line.charAt(i))>=48 &&((int)line.charAt(i))<=57))
            return;
        StringBuilder stringBuilder = new StringBuilder();
//        if (i+1<line.length()&&(line.charAt(i)+line.charAt(i+1)+"").equals("0x") || (line.charAt(i)+line.charAt(i+1)+"").equals("0b")){
//            stringBuilder.append('0').append(line.charAt(++i));
//            i++;
//        }
        ws();
        String other = "{}[]() \t\n;,+-*/=%";
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
        String others = "+-*/%=";
        return others.contains(in+"");
    }
    static boolean isKeyword(String in){
        String keys = "int  float double  long  boolean  if  while  for else  return  void ";
        return keys.contains(in);
    }
    // 16
    static void typeStmt() throws IOException {
        StringBuilder st = new StringBuilder();
        String other =" \n\t.;+-*/%";
        while(!other.contains(line.charAt(i)+""))
            st.append(line.charAt(i++));
        if (isKeyword(st.toString()))
            fout.write((lineNumber+"keyword :: "+ st+"\n").getBytes());
        else idStmt(st.toString());
    }
}
