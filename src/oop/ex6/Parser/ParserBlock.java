package oop.ex6.Parser;

import oop.ex6.main.Block;
import oop.ex6.main.Method;
import oop.ex6.main.Parsering;
import oop.ex6.main.Variable;

import java.util.Scanner;

public class ParserBlock extends Parser{
    private ParserBlock parserBlock;
    private Scanner scanner;
    private ParserVariable parserVariable;


    private static String PARAM_BOOLEAN="(true|false|[[a-zA-Z_$][a-zA-Z_$0-9]*])";

    private static String IF_BLOCK="[\\s]*if[\\s]*\\([\\s]*"+PARAM_BOOLEAN+"[\\s]*\\)[\\s]*\\{[\\s]*[\\n]?";
    private static String WHILE_BLOCK="[\\s]*while[\\s]*\\([\\s]*"+PARAM_BOOLEAN+"[\\s]*\\)[\\s]*\\{[\\s]*[\\n]?";
    private static String END_OF_BLOCK="[\\s]*\\}[\\s]*[\\n]?";

    public ParserBlock(Scanner scann){
        super(scann);
        scanner=scann;
    }

    @Override
    public void scan( String line) throws IllegalCodeException {

        Block block=new Block();
        Parsering.addBlock(block);

        //then, continue to next line
        while(Parser.getScanner().hasNextLine() &&  !(line=Parser.getScanner().nextLine()).matches(END_OF_BLOCK)){

            //if or while block
            if(line.matches(IF_BLOCK) || line.matches(WHILE_BLOCK)){
                Block innerBlock=new Block();
                Parsering.addBlock(innerBlock);
                Parsering.parserBlock.scan(line);

            }
            //variable
            else if(line.contains("=") || ParserVariable.containsType(line)){
                Parsering.parserVariable.scan(line);
            }

            //calling a method
            else if(line.contains("(" )){
                Parsering.parserCalledMethod.scan(line);
            }

            //end of line- } -
            else if(line.matches(END_OF_BLOCK)){
                Parsering.removeInnerBlock();

            }
            //else- do no match any pattern
            else{
                throw new IllegalCodeException();
            }

        }

    }
}
