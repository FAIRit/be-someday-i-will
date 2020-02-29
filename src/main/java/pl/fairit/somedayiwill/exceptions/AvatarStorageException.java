package pl.fairit.somedayiwill.exceptions;

public class AvatarStorageException extends RuntimeException {
    public AvatarStorageException() {
        super();
    }

    public AvatarStorageException(String message) {
        super(message);
    }

    public AvatarStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvatarStorageException(Throwable cause) {
        super(cause);
    }

    protected AvatarStorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
