package oop.ex6.main;

import oop.ex6.VarType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public  class Block {
    private Map<String, Variable> mapVar;

    public Block(){
        mapVar=new HashMap<>();
    }

    public boolean isVarNameFinal(String name, VarType Vtype) {
        for (String nameKey:mapVar.keySet()) {
            //name
            if(nameKey.equals(name) && Vtype==mapVar.get(nameKey).getType()  &&
                    mapVar.get(nameKey).isVarFinal() ){
                return true;
            }
        }
        return false;
    }


    public boolean isVarName(String name) {
        for (String nameKey:mapVar.keySet()) {
            //name
            if(nameKey.equals(name)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<VarType> getTypeByName(String name){
        ArrayList<VarType> arrOfTypes=new ArrayList<VarType>();
        for (String nameKey:mapVar.keySet()) {
            //name
            if(nameKey.equals(name) && !mapVar.get(nameKey).isVarFinal()){
                arrOfTypes.add(mapVar.get(nameKey).getType());
            }
        }
        return arrOfTypes;

    }


    public boolean isThereSameVarNameAndType(String name, VarType Vtype) {
        for (String nameKey:mapVar.keySet()) {
            //name
            if(nameKey.equals(name) && Vtype==mapVar.get(nameKey).getType()  &&
                    !mapVar.get(nameKey).isVarFinal() ){
                return true;
            }
        }
        return false;
    }

    public void addVarToBlock(Variable variable) {
        mapVar.put(variable.getName(), variable);
    }

    public Map<String, Variable> getMap(){
        return mapVar;
    }

}


