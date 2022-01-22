package com.company.sample;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Faz3 {
    static HashMap<String , String> map;
    public static void main(String[] args) throws IOException {
        Tree tree=new Tree();
        tree.root = new Tree.Node("State0" , 0);
        map = new HashMap<>();
        /// LL1
        {
            map.put("State0 , if", "IfStmt");
            map.put("State00 , if", "State0 State00");
            map.put("IfStmt , if", "if ( ExprStmt ) { State00 }");
            map.put("ElseStmt , if", "");
            map.put("State00 , }", "''");
            map.put("State0 , else", "ElseStmt");
            map.put("State00 , else", "State0 State00");
            map.put("ElseStmt , else", "ElseStmt -> else { State00 }");
            map.put("State0 , id", "OperationStmt");
            map.put("State00 , id", "State0 State00");
            map.put("State0 , for", "ForStmt");
            map.put("State00 , for", "State0 State00");
            map.put("State0 , while", "WhileStmt");
            map.put("State00 , while", "State0 State00");
            map.put("State0 , int", "DecStmt");
            map.put("State00 , int", "State0 State00");
            map.put("State0 , float", "DecStmt");
            map.put("State00 , float", "State0 State00");
            map.put("State0 , long", "DecStmt");
            map.put("State00 , long", "State0 State00");
            map.put("State0 , bool", "DecStmt");
            map.put("State00 , bool", "State0 State00");
            map.put("State0 , double", "DecStmt");
            map.put("State00 , double", "State0 State00");
            map.put("State0 , void", "DecStmt");
            map.put("State00 , void", "State0 State00");
            map.put("State00 , return", "''");
            /////
            map.put("IdStmt , id", "id");
            map.put("ForStmt , for", "for ( DecStmt ExprStmt ; IdStmt = ValueStmt ) { State00 }");
            map.put("WhileStmt , while", "while ( ExprStmt ) { State00 }");
            map.put("TypeStmt , int", "int");
            map.put("TypeStmt , float", "float");
            map.put("TypeStmt , long", "long");
            map.put("TypeStmt , bool", "bool");
            map.put("TypeStmt , double", "double");
            map.put("TypeStmt , void", "void");
            map.put("Temp2 , if", "Temp ;");
            map.put("Temp2 , (", "( Temp3 ) { State00 return ValueStmt ; }");
            map.put("Temp2 , }", "Temp ;");
            map.put("Temp2 , else", "Temp ;");
            map.put("Temp2 , id", "Temp ;");
            map.put("Temp2 , for", "Temp ;");
            map.put("Temp2 , ;", "Temp ;");
            map.put("Temp2 , =", "Temp ;");
            map.put("Temp2 , while", "Temp ;");
            map.put("Temp2 , int", "Temp ;");
            map.put("Temp2 , float", "Temp ;");
            map.put("Temp2 , long", "Temp ;");
            map.put("Temp2 , bool", "Temp ;");
            map.put("Temp2 , double", "Temp ;");
            map.put("Temp2 , void", "Temp ;");
            map.put("Temp2 , true", "Temp ;");
            map.put("Temp2 , false", "Temp ;");
            map.put("Temp2 , number", "Temp ;");
            map.put("Temp2 , return", "Temp ;");
            map.put("Temp2 , $", "Temp ;");
            map.put("Temp , ;", "''");
            map.put("Temp , =", "= ValueStmt");
            map.put("Temp3 , )", "''");
            map.put("Temp3 , int", "TypeStmt IdStmt Temp4");
            map.put("Temp3 , float", "TypeStmt IdStmt Temp4");
            map.put("Temp3 , long", "TypeStmt IdStmt Temp4");
            map.put("Temp3 , bool", "TypeStmt IdStmt Temp4");
            map.put("Temp3 , double", "TypeStmt IdStmt Temp4");
            map.put("Temp3 , void", "TypeStmt IdStmt Temp4");
            /////Ali
            map.put("ExprStmt , id", "ValueStmt RelopStmt ValueStmt");
            map.put("ExprStmt , true", "ValueStmt RelopStmt ValueStmt");
            map.put("ExprStmt , false", "ValueStmt RelopStmt ValueStmt");
            map.put("ExprStmt , number", "ValueStmt RelopStmt ValueStmt");
            map.put("ValueStmt , id", "IdStmt ValueStmt2");
            map.put("ValueStmt , true", "true");
            map.put("ValueStmt , false", "false");
            map.put("ValueStmt , number", "NumStmt ValueStmt2");
            map.put("ValueStmt2 , )", "''");
            map.put("ValueStmt2 , ;", "''");
            map.put("ValueStmt2 , <", "''");
            map.put("ValueStmt2 , >", "''");
            map.put("ValueStmt2 , ==", "''");
            map.put("ValueStmt2 , !=", "''");
            map.put("ValueStmt2 , >=", "''");
            map.put("ValueStmt2 , <=", "''");
            map.put("ValueStmt2 , +", "OpStmt ValueStmt");
            map.put("ValueStmt2 , -", "OpStmt ValueStmt");
            map.put("ValueStmt2 , *", "OpStmt ValueStmt");
            map.put("ValueStmt2 , /", "OpStmt ValueStmt");
            map.put("ValueStmt2 , %", "OpStmt ValueStmt");
            map.put("OperationStmt , id", "IdStmt = ValueStmt ;");
            map.put("Temp4 , )", "''");
            map.put("Temp4 , ,", ", TypeStmt IdStmt Temp4");
            /////Mohammad
            map.put("NumStmt , number", "number");
            map.put("RelopStmt , <", "<");
            map.put("RelopStmt , >", ">");
            map.put("RelopStmt , ==", "==");
            map.put("RelopStmt , !=", "!=");
            map.put("RelopStmt , >=", ">=");
            map.put("RelopStmt , <=", "<=");
            map.put("OpStmt , +", "+");
            map.put("OpStmt , -", "-");
            map.put("OpStmt , ", "");
            map.put("OpStmt , /", "/");
            map.put("OpStmt , %", "%");
            map.put("DecStmt , int", "TypeStmt IdStmt Temp2");
            map.put("DecStmt , float", "TypeStmt IdStmt Temp2");
            map.put("DecStmt , long", "TypeStmt IdStmt Temp2");
            map.put("DecStmt , boolean", "TypeStmt IdStmt Temp2");
            map.put("DecStmt , double", "TypeStmt IdStmt Temp2");
            map.put("DecStmt , void", "TypeStmt IdStmt Temp2");
        }
        ////
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
        File output = new File(mainaddress+"\\faz3.txt");
        FileOutputStream fout = new FileOutputStream(output);
        //////
        Stack<Tree.Node> stack = new Stack<>();
        Queue<String> queue = new PriorityQueue<>();
        StringBuilder st = new StringBuilder();
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] arr = line.split(" ");
            String type = arr[0];
            switch (type) {
                case "identifier":
                    st.append("id").append(" ");
                    queue.add("id");
                    break;
                case "number":
                    st.append("number").append(" ");
                    queue.add("id");
                    break;
                case "operator":
                case "relOp":
                case "keyword":
                case "punctuation":
                    st.append(arr[arr.length - 1]).append(" ");
                    queue.add(arr[arr.length - 1]);
                    break;
                default:
                    System.out.println("Not Found!!! " + type);
                    break;
            }
        }
        fout.write(st.toString().getBytes());
        Tree.Node current = new Tree.Node("State0" , 0);
        stack.push(current);
        tree.root = current;
        /////////////
        while (stack.size()!=0){
            Tree.Node s = stack.pop();
            String q = queue.peek();
            if (s.value.equals(q)){
                queue.poll();
                current = current.father;
            }
            else if (map.containsKey(s.value + " , " + q)){
                String gr = map.get(s.value + " , " + q);
                String[] grArr  = gr.split(" ");
                current = s;
                for (String i : grArr) {
                    Tree.Node node = new Tree.Node(i, current.level + 1);
                    current.addChild(node);
                    stack.push(node);
                }
            }
            else{
                System.out.println("Error in Code !!!");
                break;
            }
        }
        /////////////
        tree.root.print();
        System.out.println("faz3 is done");
        Desktop desktop = Desktop.getDesktop();
        desktop.open(output);
    }
}
class Tree{
    Node root;
    static class Node{
        String value;
        int level;
        boolean isBarg = true;
        ArrayList<Node> children = new ArrayList<>();
        Node father;
        public Node(String value, int level) {
            this.value = value;
            this.level = level;
        }

        public void addChild(Node node){
            children.add(node);
            node.father = this;
            isBarg = false;
        }
        void print(){
            System.out.print(this.value + " , level = " + this.level + " : ");
            for (Node node : this.children){
                System.out.print(node.value + " , ");
            }
            System.out.println();
            for (Node node : this.children){
                node.print();
            }
        }
    }
}
