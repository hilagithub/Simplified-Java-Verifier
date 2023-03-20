package oop.ex6.main;

public class InvlidNumArgseException extends Exception{

    private static String ERROR_INVALID_NUM_ARGUMENTS="invalid number of argument";

    /**default constructor*/
    public InvlidNumArgseException() {
        super(ERROR_INVALID_NUM_ARGUMENTS);

    }


}
