package oop.ex6.Parser;

import oop.ex6.main.Method;
import oop.ex6.main.Parameter;
import oop.ex6.main.Parsering;

import java.util.ArrayList;
import java.util.Scanner;

public class ParserCalledMethod extends Parser {
    private  String ALL_TYPES="(int|double|String|boolean|char)";


    private String PARAMETERS_METHOD="[\\s]*(final)?[\\s]*(int|char|String) ";

    private String CALL_METHOD="[\\t]*[\\s]*[a-zA-Z][\\w]*[\\s]*\\([\\s]*(([\\w\"][\\w\"]*[\\s]*)" +
            "(\\,[\\s]*[\\w\"][\\w\"]*[\\s]*)*)?[\\s]*\\)[\\s]*\\;[\\s]*";
    public ParserCalledMethod(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void scan( String line) throws IllegalCodeException {
        //check basic pattern
        if(!line.matches(CALL_METHOD)){
            throw new IllegalCodeException();
        }

        //check if name exist
        for (Method method: Parsering.getMethods()) {
            String methodName=method.getName();

            //parameters
            boolean equalNmaeMethod=ParserMethod.findMethodName(line).equals(methodName);
            if(equalNmaeMethod){
                ArrayList<Parameter> parameterArrayList=method.getParameters();
                ArrayList<String> callingParameters=splitArguments(line);
                for (int i = 0; i <parameterArrayList.size() ; i++) {
                    if(!parameterArrayList.get(i).isEqualParameter(callingParameters.get(i))){
                        throw new IllegalCodeException();
                    }
                    if(parameterArrayList.get(i).isEqualParameter(callingParameters.get(i)) &&
                            i==parameterArrayList.size()-1){
                        return ;
                    }
                }


            }

        }
        throw new IllegalCodeException();



    }


    ArrayList<String> splitArguments(String line){
        ArrayList<String> argsOut=new ArrayList<>();
        int firstIdx=line.indexOf("(")+1;
        int lastIdx=line.indexOf(")");
        String args=line.substring(firstIdx,lastIdx);
        String[] argsSplitted=args.split(",");
        for (String argStr:argsSplitted) {
            argsOut.add(argStr.replace(" ",""));
        }
        return argsOut;
    }


}
