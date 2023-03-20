package oop.ex6.Parser;


import oop.ex6.VarType;
import oop.ex6.main.Method;
import oop.ex6.main.Parameter;
import oop.ex6.main.Parsering;

import java.util.ArrayList;
import java.util.Scanner;

public class ParserMethod extends Parser{
    private Scanner scan;
    private Method newNethod;
    private String nxtLine;


    private static String COMMENT="(\\/\\/|\\/\\*|\\*\\/).*";


    private static String TYPE_VARIABLES="(int|double|char|String|boolean)";
    private static String PARAMETER_LINES="([[\\s]*"+TYPE_VARIABLES+"[a-zA-Z][\\w]*[\\s]]*[\\,]?[\\s]*)*[\\s]*\\{";
    private static String DECLARE_AND_ASSIGN_METHOD="void[\\s]*[a-zA-Z][a-zA-Z_0-9]*[\\s]*"+PARAMETER_LINES;
    private static String PARAM_BOOLEAN="(true|false|[[a-zA-Z_$][a-zA-Z_$0-9]*])";

    private static String IF_BLOCK="[\\s]*if[\\s]*\\([\\s]*"+PARAM_BOOLEAN+"[\\s]*\\)[\\s]*\\{[\\s]*[\\n]?";
    private static String WHILE_BLOCK="[\\s]*while[\\s]*\\([\\s]*"+PARAM_BOOLEAN+"[\\s]*\\)[\\s]*\\{[\\s]*[\\n]?";
    private static String END_OF_BLOCK="[\\s]*\\}[\\s]*[\\n]?";




    private static String RETURN="[\\s]*return;[\\n]?";

    public ParserMethod(Scanner scanner) {
        super(scanner);
        scan=scanner;
        this.nxtLine=nxtLine;



    }

    static String findMethodName(String line){
        String str=line.replace("void ","");
        int idx=str.indexOf("(");
        String name=str.substring(0,idx).replace(" ","").replace("\t","").
                replace("\\s","");
        return name;
    }

    static ArrayList<Parameter> findParameters(String line){
        ArrayList<Parameter> parametersArray=new ArrayList<>();
        int start=line.indexOf("(");
        int end=line.indexOf(")");
        String parameters=line.substring(start+1,end);
        if(parameters.equals("")){
            return parametersArray;
        }
        String[] type_name=parameters.split(" ");
        int i=0;
        Parameter newPar;
        while (i<type_name.length) {
            if(type_name[i].equals("final")){
                VarType type=  VarType.valueOf(type_name[i+1]);
                newPar=new Parameter(type_name[i+2], type,true);
                i=i+3;
            }
            else{
                VarType type=VarType.getByName(type_name[i]);
                newPar=new Parameter(type_name[i+1], type,false);
                i=i+2;

            }
            parametersArray.add(newPar);

        }
        return parametersArray;


    }

    void checkSamName(String name) throws IllegalCodeException {
        for (Method method:Parsering.getMethods()) {
            if(method.getName().equals(name)){
                throw new IllegalCodeException();
            }
        }
    }

    public Method getMethod(){
        return newNethod;
    }

    public void scan( String nxtLine) throws IllegalCodeException {
        Parsering.setLocal();


        if(nxtLine.matches(DECLARE_AND_ASSIGN_METHOD)){
            checkSamName(findMethodName( nxtLine));
            newNethod= new Method(findMethodName( nxtLine),findParameters(nxtLine));
            Parsering.addMethod(newNethod);

            while(scan.hasNextLine() &&  !(nxtLine=scan.nextLine()).matches(END_OF_BLOCK)){

                //comment
                if(nxtLine.matches(COMMENT)){
                    continue;
                }



                //while block or if block
                else if(nxtLine.matches(WHILE_BLOCK) || nxtLine.matches(IF_BLOCK)){
                    Parsering.parserCondition.scan(nxtLine);
                    Parsering.parserBlock.scan(nxtLine);
                }

                //variable
                else if( ParserVariable.containsType(nxtLine)  || nxtLine.contains("=")){
                    ParserVariable parserVariable=new ParserVariable(scan);
                    parserVariable.scan(nxtLine);


                }
                //return
                else if(nxtLine.matches(RETURN)){
                    continue;
                }

                //calling a method
                else if(nxtLine.contains("(" )){
                    Parsering.parserCalledMethod.scan(nxtLine);
                }


            }
            Parsering.setGlobal();
            if(newNethod.getInnerBlocks().size()==0){
                throw new IllegalCodeException();
            }
            newNethod.getInnerBlocks().clear();


        }
        else{
            throw new IllegalCodeException();
        }

    }




}
