package oop.ex6.main;

import oop.ex6.Parser.*;
import oop.ex6.VarType;

import java.util.*;

public class Parsering {
    private static List<Variable> globalVariableStack;
    private static List<Method> MethodVariableStack;
    private static boolean isGlobal=true;
    private Scanner scanner;
    private static String COMMENT="((\\/\\/|\\/\\*|\\*\\/).*|\\n?)";


    public static ParserMethod parserMethod;
    public static ParserVariable parserVariable;
    public static ParserCalledMethod parserCalledMethod;
    public static ParserBlock parserBlock;
    public static ParserCondition parserCondition;


    public Parsering(Scanner scanner){
        this.scanner=scanner;
        globalVariableStack=new ArrayList<>();
        MethodVariableStack=new ArrayList<>();
        parserMethod=new ParserMethod(scanner);
        parserVariable=new ParserVariable(scanner);
        parserCalledMethod=new ParserCalledMethod(scanner);
        parserBlock=new ParserBlock(scanner);
        parserCondition=new ParserCondition(scanner);



    }





    public void run() throws IllegalCodeException {


        while(scanner.hasNextLine()){
            String nxtLine=scanner.nextLine();

            //comment
            if(nxtLine.matches(COMMENT)){
                continue;
            }


            //define method
            if(nxtLine.contains("void") ){
                parserMethod.scan(nxtLine);

            }

            //calling a method
            else if(nxtLine.contains("(" )){
                parserCalledMethod.scan(nxtLine);
            }

            //declare global new variable
            else if(nxtLine.matches(ParserVariable.getVariableRegex())){
                setGlobal();
                parserVariable.scan(nxtLine);

            }
            else {
                throw new IllegalCodeException();
            }

        }

    }


    public static List<Method> getMethods(){
        return MethodVariableStack;
    }


    public static List<Variable> getGlobalVariabales(){
        return globalVariableStack;
    }



    public static void removeInnerBlock() throws IllegalCodeException {
        //last method
        int len =Parsering.getMethods().size();
        Method lastMethod=Parsering.getMethods().get(len-1);
        //call funcion in method class
        lastMethod.removeLastBlock();
    }

    public static  void addVariable(Variable var){
        if(Parsering.getIsGlobal()){
            globalVariableStack.add(var);
        }
        else{
            int len=MethodVariableStack.size();
            MethodVariableStack.get(len-1).addVarToBlock(var);
        }

    }

    public static Boolean isSameTypeVariableByName(String name,VarType type) throws IllegalCodeException {
        ArrayList<VarType> arrTypes=new ArrayList<>();
        for (Method method:Parsering.getMethods()) {
            name=name.replace(",","").replace(" ","");
            ArrayList<VarType> typesOfAllName=method.getTypeOfSameVarName(name);
            if(typesOfAllName!=null){
                arrTypes.addAll(method.getTypeOfSameVarName(name));
            }

        }
        for (Variable var:Parsering.getGlobalVariabales()) {
            if(var.getName().equals(name) && var.getType()==type){
                arrTypes.add(type);
            }
        }
        return arrTypes.contains(type);

    }

    public static boolean getIsGlobal(){
        return isGlobal;
    }

    public static void  addMethod(Method method){
        MethodVariableStack.add(method);
    }

    public static void  addBlock(Block block){
        int len=MethodVariableStack.size();

        MethodVariableStack.get(len-1).addBlock(block);
    }


    public static void setGlobal(){
        isGlobal=true;
    }
    public static void setLocal(){
        isGlobal=false;
    }


}
