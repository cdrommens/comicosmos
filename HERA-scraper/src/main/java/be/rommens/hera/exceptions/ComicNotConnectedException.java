package be.rommens.hera.exceptions;

/**
 * User : cederik
 * Date : 02/04/2020
 * Time : 20:50
 */
public class ComicNotConnectedException extends RuntimeException {

    public ComicNotConnectedException(String message, Throwable err) {
        super(message, err);
    }
}
