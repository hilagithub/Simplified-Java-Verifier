package oop.ex6.main;

import oop.ex6.Parser.IllegalCodeException;
import oop.ex6.Parser.ParserVariable;
import oop.ex6.VarType;

public class Parameter {


    private final String name;
    private final VarType vType;
    private final boolean isFinal;

    public Parameter(String name, VarType vType, boolean isFinal){
        this.name=name;
        this.vType=vType;
        this.isFinal=isFinal;
    }




    public boolean isEqualParameter(String p) throws IllegalCodeException {
        return  ParserVariable.typeMachAssignment(vType,p) ;
    }

    public String getName(){
        return name;
    }

    VarType getType(){
        return vType;
    }

    boolean isFinalVar(){ return isFinal;}
}
