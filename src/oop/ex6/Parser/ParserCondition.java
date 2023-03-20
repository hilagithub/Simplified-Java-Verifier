package oop.ex6.Parser;

import java.util.Scanner;

public class ParserCondition extends Parser {

    private static String TRUE="[\\s]*true[\\s]*";
    private static String FALSE="[\\s]*false[\\s]*";
    private static final String BOOLEAN_PHRASE="("+TRUE+"|"+FALSE+"|[\\s]*[a-zA-z][\\w]*)";
    private static String CONDITION="[\\s]*"+BOOLEAN_PHRASE+"[\\s]*[([\\s]*\\|\\|[\\s]*|[\\s]*\\&\\&[\\s]*)[\\s]*"
            +BOOLEAN_PHRASE+"[\\s]*]*";


    public ParserCondition(Scanner scann) {
        super(scann);
    }

    @Override
    public void scan(String line) throws IllegalCodeException {

        int firstIndx=line.indexOf("(")+1;
        int last=line.indexOf(")");
        String conditionStr=line.substring(firstIndx,last);
        if(!conditionStr.matches(CONDITION)){
            throw new IllegalCodeException();
        }

    }



}
