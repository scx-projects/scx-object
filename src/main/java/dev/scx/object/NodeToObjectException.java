package dev.scx.object;

/// NodeToObjectException
///
/// @author scx567888
/// @version 0.0.1
public class NodeToObjectException extends RuntimeException {

    public NodeToObjectException(String message) {
        super(message);
    }

    public NodeToObjectException(Throwable cause) {
        super(cause);
    }

    public NodeToObjectException(String message, Throwable cause) {
        super(message, cause);
    }

}
