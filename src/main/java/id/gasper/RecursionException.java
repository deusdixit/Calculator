package id.gasper;

public class RecursionException extends Exception {

    public RecursionException() {
        super("Recursions are not supported");
    }
}
