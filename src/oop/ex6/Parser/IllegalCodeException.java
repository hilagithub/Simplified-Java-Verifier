package oop.ex6.Parser;

public class IllegalCodeException extends Exception{
    private static String ERROR_CODE_FILE_ILLEGAL="wrong code syntax in file ";

    public IllegalCodeException() {
        super(ERROR_CODE_FILE_ILLEGAL);
    }
}
