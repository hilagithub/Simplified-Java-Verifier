package oop.ex6.Parser;

import oop.ex6.main.Method;
import oop.ex6.main.Variable;

import java.util.Scanner;

public abstract class Parser
{
    private static Scanner scann;


    Parser(Scanner scanner){
        scann=scanner;
    }

    //parse the line and if it is Method/Variable add it to   last Block
    public abstract void scan(String line) throws IllegalCodeException;

    public static Scanner getScanner(){
        return scann;
    }

}
