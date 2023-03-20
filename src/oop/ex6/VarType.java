package oop.ex6;

import javax.print.DocFlavor;

public enum VarType {
    INT("int"),DOUBLE("double"),STRING("String"),BOOLEAN("boolean"),CHAR("char");

    private final String stringName;

    VarType(String string) {
        this.stringName=string;
    }

    public static VarType getByName(String typeStr){
        switch (typeStr){
            case "int":
                return INT;
            case "double":
                return DOUBLE;
            case "String":
                return STRING;
            case "boolean":
                return BOOLEAN;
            case "char":
                return CHAR;
        }
        return INT;
    }

    VarType getByEnum(){
        return this ;
    }
}
