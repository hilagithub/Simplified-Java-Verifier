package oop.ex6.main;

import oop.ex6.Parser.IllegalCodeException;
import oop.ex6.VarType;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class Method {


    private  String nameMethod;
    private ArrayList<Parameter> parameters;
    //variable name: type,isFinal
    private Vector<Block> innerBlocks;

    public Method(String nameMethod, ArrayList<Parameter> parameters){


        this.parameters=new ArrayList<>();
        this.parameters.addAll(parameters);

        innerBlocks=new Vector<>();
        //method  outer block
        innerBlocks.add(new Block());

        this.nameMethod = nameMethod;



    }
    public String getName(){
        return nameMethod.replace("\\t","").replace("\\s","");
    }

    public int getInnerBlockSize(){
        return innerBlocks.size();
    }


    Map<String, Variable> lastMapBlock(){
        return innerBlocks.lastElement().getMap();
    }

    boolean isVarNameFinal(String name ,VarType Vtype){

        for (String vname:lastMapBlock().keySet()) {

            if(vname.equals(name) && lastMapBlock().get(vname).isVarFinal() ){
                return true;
            }
        }
        return false;
    }

    public boolean isThereSameVarNameAndTypeInBlock(String name ,VarType Vtype){

        for (String vname:lastMapBlock().keySet()) {

            if(vname.equals(name) && lastMapBlock().get(vname).getType()==Vtype ){
                return true;
            }
        }
        return false;
    }



    public boolean isThereSameVarNameAndNotFinal(String name,VarType Vtype){
        for (Block block:innerBlocks) {

            if(block.isVarName(name) && !block.isVarNameFinal( name,  Vtype)){
                return true;
            }

        }
        return false;

    }



    public void removeLastBlock() throws IllegalCodeException {
        if(innerBlocks.size()==0){
            throw new IllegalCodeException();
        }
        innerBlocks.remove(innerBlocks.lastElement());

    }

    public ArrayList<VarType> getTypeOfSameVarName(String name) throws IllegalCodeException {
        ArrayList<VarType> typesWithSameVarName=new ArrayList<>();
        for (Block block:innerBlocks) {
            if(block.getTypeByName(name).size()!=0){
                typesWithSameVarName.addAll(block.getTypeByName(name));
            }

        }
        if(typesWithSameVarName.size()==0){
            return null;
        }

        return typesWithSameVarName;

    }



    public boolean isThereSameVarName(String name){
        for (Block block:innerBlocks) {
            if(block.isVarName(name)){
                return true;
            }

        }
        return false;

    }

    public Vector<Block> getInnerBlocks(){
        return innerBlocks;
    }

    public ArrayList<Parameter> getParameters(){
        return parameters;
    }

    void addBlock(Block block){
        innerBlocks.add(block);
    }

    void addVarToBlock(Variable variable){
        lastMapBlock().put(variable.getName(),variable);
    }

}
