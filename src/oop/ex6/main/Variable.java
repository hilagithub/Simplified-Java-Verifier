package oop.ex6.main;

import oop.ex6.VarType;

public class Variable {

    private final String name;
    private final VarType type;
    private boolean assigned;
    private boolean isFinal;

    public Variable(String name, VarType type, boolean assigned,boolean isFinal){
        this.name=name;
        this.type=type;
        this.assigned=assigned;
        this.isFinal=isFinal;
    }

    public boolean isVarFinal(){return isFinal;}
    public String getName(){
        return name;
    }
    public VarType getType(){return type;}

    public boolean isAssigned(){
        return assigned;
    }

}
