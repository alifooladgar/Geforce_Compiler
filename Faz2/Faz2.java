import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Faz2 {
    static FileOutputStream fout;
    static int i=0;
    static String line;
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
        ////////////////////////// lexical analysis
        while (sc.hasNextLine()){
            line = sc.nextLine();
            ws();
            String others = "(){}[], \t\n;";
            StringBuilder st = new StringBuilder();
            while (others.contains(line.charAt(i)+"")){
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
                case "float":
                case "double":
                case "long":
                case "bool":
                    if (line.contains("("))
                        funcStmt();
                    else
                        decStmt();
                    break;
                default:
                    idStmt(st.toString());
            }
        }
        ////////////////////////////////
    }
    // 8
    static void ws(){
        while(line.charAt(i) == ' ')
            i++;
        while(line.charAt(i) == '\t')
            i+=8;
    }
    // 1
    static void ifStmt() throws IOException {
        fout.write("keyword :: if".getBytes());
        ws();
        if(isPunc(line.charAt(i)))
            fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
        ws();
        exprStmt();
        ws();
        if(isPunc(line.charAt(i)))
            fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
        ws();
        if (line.charAt(i)!='\n')
            if(isPunc(line.charAt(i)))
                fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
    }
    // 9
    static void elseStmt() throws IOException {
        fout.write("keyword :: else".getBytes());
        if (line.charAt(i)!='\n')
            if(isPunc(line.charAt(i)))
                fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
    }
    // 7
    static void forStmt()throws IOException{
        fout.write("keyword :: for".getBytes());
        ws();
        if(isPunc(line.charAt(i)))
            fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
        ws();
        decStmt();
        if(isPunc(line.charAt(i)))
            fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
        exprStmt();
        if(isPunc(line.charAt(i)))
            fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
        operationStmt();
        if(isPunc(line.charAt(i)))
            fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
        if(isPunc(line.charAt(i)))
            fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
    }
    // 10
    static void whileStmt()throws IOException{
        fout.write("keyword :: while".getBytes());
        if(isPunc(line.charAt(i)))
            fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
        exprStmt();
        if(isPunc(line.charAt(i)))
            fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
        if(isPunc(line.charAt(i)))
            fout.write(("punctuation :: "+line.charAt(i++)).getBytes());
    }
    // 2
    static void relOpStmt()throws IOException{
        StringBuilder st = new StringBuilder();
        st.append(line.charAt(i++));
        ws();
        if (line.charAt(i)=='=') {
            st.append("=");
            i++;
        }
        switch (st.toString()){
            case ">":
                fout.write(("relOp :: >").getBytes());
                break;
            case "<":
                fout.write(("relOp :: <").getBytes());
                break;
            case "!=":
                fout.write(("relOp :: !=").getBytes());
                break;
            case "==":
                fout.write(("relOp :: ==").getBytes());
                break;
            case ">=":
                fout.write(("relOp :: >=").getBytes());
                break;
            case "<=":
                fout.write(("relOp :: <=").getBytes());
                break;
        }
    }
    // 4
    static void TermStmt()throws IOException{
        StringBuilder st = new StringBuilder();
        String others = "(){}[], \t\n;";
        int index=i;
        while (others.contains(line.charAt(index)+"")){
            st.append(line.charAt(index));
            index++;
        }
        idStmt(st.toString());
        i=index+1;
        numberStmt();
        valueStmt();
    }
    // 5
    static void idStmt(String name)throws IOException{
        fout.write(("identifier :: "+ name).getBytes());
    }
    // 6
    static void valueStmt()throws IOException{
        if (((int)line.charAt(i))>=48 &&((int)line.charAt(i))<=57){
            numberStmt();
        }
        else{
            StringBuilder st = new StringBuilder();
            String others = "(){}[], \t\n;";
            int index=i;
            while (others.contains(line.charAt(index)+"")){
                st.append(line.charAt(index));
                index++;
            }
            if (st.toString().equals("true"))
                fout.write(("boolean :: true").getBytes());
            if (st.toString().equals("false"))
                fout.write(("boolean :: false").getBytes());
            else
                idStmt(st.toString());
            i=index+1;
        }
        ws();
        if(isOp(line.charAt(i))){

        }
    }
    // 14
    static void exprStmt()throws IOException{

    }
    // 13
    static void operationStmt()throws IOException{

    }
    // 12
    static void returnStmt()throws IOException{

    }
    // 11
    static void funcStmt()throws IOException{

    }
    // 15
    static void decStmt()throws IOException{

    }
    // 3
    static void numberStmt()throws IOException{

    }
    //
    static boolean isPunc(char in){
        String others = "(){}[]";
        return others.contains(in+"");
    }
    static boolean isOp(char in){
        String others = "+-*/";
        return others.contains(in+"");
    }
}
