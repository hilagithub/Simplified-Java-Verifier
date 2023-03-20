package oop.ex6.main;

import oop.ex6.Parser.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Sjavac {

    private static String SUFFIX_FILE=".sjava";


    private static String ERROR_INVALID_FILE_SJAVA="invalid file format-not sjava file as required";


    private static final int LEGAL_CODE=0;
    private static final int ILLEGAL_CODE=1;
    private static final int IO_ERROR=2;
    private static final int NUM_ARGS=1;

    public static void main(String[] args) {

        try{
            //invalid num arguments
            if(args.length!=NUM_ARGS){
                System.out.println(args.length+" "+args[0]);
               throw new InvlidNumArgseException();
            }
            String fileName=args[NUM_ARGS-1];
            if(!fileName.endsWith(SUFFIX_FILE)){
                throw new IOException(ERROR_INVALID_FILE_SJAVA);
            }
            Scanner scan=new Scanner(new File(fileName));
            Parsering startParsing=new Parsering(scan);
            startParsing.run();




        }

        catch (InvlidNumArgseException e){ //invalid number of args
            System.err.println(e.getMessage());
            return;
        }
        catch (IOException e) { //file name is invalid
             System.err.println(e.getMessage());
             System.out.println(IO_ERROR);
             return;
        }
        catch (IllegalCodeException e) { // invalid code of file
            System.err.println(e.getMessage());
            System.out.println(ILLEGAL_CODE);
            return;
        }
        //legal code
        System.out.println(LEGAL_CODE);

    }

}
