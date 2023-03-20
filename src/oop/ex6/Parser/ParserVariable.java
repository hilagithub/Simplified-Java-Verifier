package oop.ex6.Parser;

import oop.ex6.VarType;
import oop.ex6.main.*;

import java.lang.reflect.Array;
import java.util.*;

public class ParserVariable extends Parser{
    private static final String ASSIGNMENT_ONLY = "";

    private static String COMMENT="(\\/\\/|\\/\\*|\\*\\/).*";



    //INT
    private static String COMMA=";";
    private static String DECLARE_AND_ASSIGN_INT="[\\s]*[a-zA-Z_$][a-zA-Z_$0-9]*[\\s]*[=][\\s]*[(-|+)][\\s]*[0-9]+";
    private static String DECLARE_ONLY_INT="[\\s]*[a-zA-Z_$][a-zA-Z_$0-9]*";

    private static String DEFINE_INT_VARIABLE_REGEX="final? int[\\s]+"+"[("+DECLARE_ONLY_INT+"|"
            +DECLARE_AND_ASSIGN_INT+")[\\s]*\\,]*"+
            "("+DECLARE_ONLY_INT+"|"+DECLARE_AND_ASSIGN_INT+")[\\s]*;[\\n]?";
    private static String INT_REGEX="[\\s]*[\\-]?[\\s]*[0-9]*[\\s]*";



    //DOUBLE
    private static String DECLARE_AND_ASSIGN_DOUBLE="[\\s]*[a-zA-Z_$][a-zA-Z_$0-9]*[\\s]*[=][\\s]*[0-9]*[.]{0,1}[0-9]*";
    private static String DECLARE_ONLY_DOUBLE="[\\s]*[a-zA-Z_$][a-zA-Z_$0-9]*";
    private static String DEFINE_DOUBLE_VARIABLE_REGEX="final? double[\\s]+"+"[("+DECLARE_ONLY_DOUBLE+"|"+
            DECLARE_AND_ASSIGN_DOUBLE+"),]*"+
            "("+DECLARE_ONLY_DOUBLE+"|"+DECLARE_AND_ASSIGN_DOUBLE+")[\\s]*;\\n";
    private static String ALL_TYPES="(int|double|String|boolean|char)";
    private static String DOUBLE_REGEX="[\\s]*[0-9]*[.]{0,1}[0-9]*";


    //STRING
    private static String FINAL="(\\bfinal\\b)?";
    private static String DECLARE_AND_ASSIGN_STRING="[\\s]*[a-zA-Z_$][a-zA-Z_$0-9]*[\\s]*[=][\\s]*[\"][^0-9]*[\"]";
    private static String DECLARE_ONLY_STRING="[\\s]*[a-zA-Z_][\\w]*";
    private static String DEFINE_STRING_VARIABLE_REGEX="[\\s]*"+FINAL+"[\\s]*"+ALL_TYPES+"[\\s]*[("+DECLARE_ONLY_STRING+"|"
        +DECLARE_AND_ASSIGN_STRING+")[\\s]*,]"
        +"("+DECLARE_ONLY_STRING+"|"+DECLARE_AND_ASSIGN_STRING+")[\\s]*;\\n?";
    private static String STRING_REGEX="[\\s]*[\"][\\s]*[\\w]*[\\s]*[\"][\\s]*";


    //BOOLEAN
    private static String DECLARE_AND_ASSIGN_BOOLEAN="[\\s]*[a-zA-Z_$][a-zA-Z_$0-9]*[\\s]*[=][\\s]*(true|false|[0-9]*[.]{0,1}[0-9]*)";
    private static String DECLARE_ONLY_BOOLEAN="[\\s]*[a-zA-Z_$][a-zA-Z_$0-9]*";
    private static String DEFINE_BOOLEAN_VARIABLE_REGEX="final? boolean[\\s]+"+"[("+DECLARE_ONLY_BOOLEAN+"|"+
            DECLARE_AND_ASSIGN_BOOLEAN+"),]*"+
            "("+DECLARE_ONLY_BOOLEAN+"|"+DECLARE_AND_ASSIGN_BOOLEAN+")[\\s]*;\\n";
    private static String TRUE="true";
    private static String FALSE="false";
    private static String BOOLEAN_REGEX="[\\s]*(true|false|[[0-9]*[.]?[0-9]*])[\\s]*";
    //private static String BOOLEAN_REGEX="[\\s]*("+TRUE+"|"+FALSE+"|[[0-9]*[.]?[0-9]*])[\\s]*";



    //CHAR
    private static String DECLARE_AND_ASSIGN_CHAR="[\\s]*[a-zA-Z_$][a-zA-Z_$0-9]*[\\s]*[=][\\s]*['][^\\\\'\"\\,0-9][']";
    private static String DECLARE_ONLY_CHAR="[\\s]*[a-zA-Z_$][a-zA-Z_$0-9]*";
    private static String DEFINE_CHAR_VARIABLE_REGEX="final? char[\\s]+"+"[("+DECLARE_ONLY_CHAR+"|"+
            DECLARE_AND_ASSIGN_CHAR+"),]*"+
            "("+DECLARE_ONLY_CHAR+"|"+DECLARE_AND_ASSIGN_CHAR+")[\\s]*;\\n";

    private static String val="[^,=;-]+";

    private static String ASSIGNMENT="([\\s]*[\\=][\\s]*[\\-]?[\\s]*"+val+")?";

    private static String CHAR_REGEX="[\\s]*[\\'][^\\'\"\\,0-9\\=][\\']";

    ///VARIABLES
    private static String VARIABLE_CHAR="[\\s]*"+FINAL+"[\\s]*"+ALL_TYPES+"[\\s]*("+
            DECLARE_ONLY_STRING+ASSIGNMENT+"[\\s]*[\\,])?"
            +DECLARE_ONLY_STRING+ASSIGNMENT+"[\\s]*[\\;]\\n?";




    public static String getVariableRegex(){
        return VARIABLE_CHAR;
    }

    void isSameVarName(String nameVar) throws IllegalCodeException {
        if(Parsering.getIsGlobal()){
            for(var globalVar:Parsering.getGlobalVariabales()){

                if(nameVar.equals(globalVar.getName())){
                    throw new IllegalCodeException();
                }
            }
        }
        else{
            int size_methods=Parsering.getMethods().size();
            Method method=Parsering.getMethods().get(size_methods-1);
            if(method.isThereSameVarName(nameVar)){
                throw new IllegalCodeException();
            }
            //if parameterthe same
            for (Parameter parameter: method.getParameters()) {
                if(nameVar.equals(parameter.getName())){
                    throw new IllegalCodeException();
                }
            }
        }
    }


    public ParserVariable(Scanner scanner){
        super(scanner);
    }

    boolean machesToRegex(String line){
        System.out.println(line.matches(VARIABLE_CHAR));
        System.out.println(line);
        String fd=" -5;";
        System.out.println(fd.matches("[\\s]*[\\-]?[\\s]*[^,=;-]+\\;\\n?"));
        return line.matches(VARIABLE_CHAR);
    }




    /**
     * if var  only assigned we check that is already declared and not final->(a=5)
     * */
    boolean assignOnly(String nxtLine) throws IllegalCodeException {
        String lastName = "";
        if(!nxtLine.contains(ALL_TYPES) && nxtLine.contains("=")){
            String[] nameAndValue=nxtLine.replace(",","").split("=");
            for (int i = 0; i <nameAndValue.length ; i++) {

                if(i%2==0){
                    lastName=nameAndValue[i].replace(" ","");

                }
                else{
                    String value=nameAndValue[i];


                    List<Variable> lstVar=Parsering.getGlobalVariabales();
                    for (Variable var:lstVar) {
                        //if variables have same name and var declared as final-error!
                        //(the assignment  has the same type because of the regex check)
                        if(lastName.equals(var.getName()) && !var.isVarFinal() ){
                            return true;
                        }

                    }
                    //same thing-on methods
                    List<Method> methodList=Parsering.getMethods();
                    for (Method method:methodList) {
                        for (Block block:method.getInnerBlocks()) {
                            ArrayList<VarType> arrTypes=block.getTypeByName(lastName);
                            for (VarType vt:arrTypes) {
                                if(typeMachAssignment(vt,value)){
                                    return true;
                                }


                            }

                        }


                    }

                }

            }

        }
        throw new IllegalCodeException();


    }

    public static boolean typeMachAssignment(VarType type,String assignmentToCheck) throws IllegalCodeException {

        //if this is a **name** of variable with same type- return true
        if(Parsering.isSameTypeVariableByName(assignmentToCheck.replace(" ",""),type)){
            return true;
        }

        switch (type){
            case INT:
                return assignmentToCheck.matches(INT_REGEX);
            case DOUBLE:
                return assignmentToCheck.matches(DOUBLE_REGEX);
            case STRING:
                return assignmentToCheck.matches(STRING_REGEX);

            case BOOLEAN:
                return assignmentToCheck.matches(BOOLEAN_REGEX);

            case CHAR:
                return assignmentToCheck.matches(CHAR_REGEX);

        }

        return false;

    }


    public static  boolean containsType(String line){
        return line.contains("int") || line.contains("double") || line.contains("String")
                || line.contains("boolean") || line.contains("char");
    }


    ArrayList<Variable> splitScanVariables(String nextLine) throws IllegalCodeException {

        ArrayList<Variable> allVariables = new ArrayList<>();
        VarType lastType=VarType.INT;
        boolean onlyAssign=false,isLastFinal=false,assigned=false;
        String nameVar="",strAssignment="";
        if(!containsType(nextLine) && !nextLine.contains("=") ){
             throw new IllegalCodeException();
        }

        String[] varsStr= nextLine.split(",");
        //create single variable
        for(String str:varsStr){
            String[] varData;
            if(str.contains("=")){
                varData=str.split("=")[0].split(" ");
                strAssignment=str.split("=")[1].replace("\"","").replace(";","");
                assigned=true;

            }
            else{
                varData=str.split(" ");
                assigned=false;
            }
            //final/name of variable /assignment
            for (String substr:varData) {

                //is variable final
                if(substr.contains("final")  ){
                    if(!varData[0].contains("final")){
                        throw new IllegalCodeException();
                    }
                    isLastFinal=true;
                }
                //the type of variable
                else if(containsType(substr.replace(" ",""))){
                    String typeName=substr.replace(" ","");
                    lastType=VarType.getByName(typeName);
                }
                // variable only - without assignment
                else {
                    nameVar=substr.replace(" ","").replace(";","")
                            .replace(",","");
                    //assigned=false;
                }

            }
            //if final and not assigned or assignment is invalid
            if((isLastFinal && !assigned) ||(assigned && !typeMachAssignment(lastType,strAssignment) )){
                throw new IllegalCodeException();
            }
            isSameVarName(nameVar);
            Variable newVar=new Variable(nameVar,lastType,assigned,isLastFinal);
            allVariables.add(newVar);


        }
        return allVariables;


    }




    @Override
    public void scan(String nextLine) throws IllegalCodeException {

        //comment
        if(nextLine.matches(COMMENT)){
            return;
        }

        if(!containsType(nextLine) ){
            assignOnly(nextLine);
            return;
        }


        ArrayList<Variable> allVariablesToAdd=splitScanVariables(nextLine);
        for (Variable var:allVariablesToAdd) {
            Parsering.addVariable(var);
        }

    }
}
