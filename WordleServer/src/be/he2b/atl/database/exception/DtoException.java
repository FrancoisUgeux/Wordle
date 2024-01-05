package be.he2b.atl.database.exception;

/**
 * François Ugeux
 */
public class DtoException extends Exception {

    /**
     * Creates a new instance of <code>DtoException</code> without detail message.
     */
    public DtoException() {
    }


    /**
     * Constructs an instance of <code>DtoException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DtoException(String msg) {
        super(msg);
    }
}
