package com.intuit.commentservice.exceptions;

/**
 * Comment Service specific generic {@link Exception}.
 */
public class CommentServiceException extends Exception {

    private static final long serialVersionUID = 2448359356216234245L;

    /**
     * Constructs a new {@link CommentServiceException}.
     *
     * @param message The message.
     */
    public CommentServiceException(final String message) {
        super(message);
    }

    /**
     * Constructs a new {@link CommentServiceException}.
     *
     * @param message The message.
     * @param cause   The cause.
     */
    public CommentServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@link CommentServiceException}.
     *
     * @param cause The cause.
     */
    public CommentServiceException(final Throwable cause) {
        super(cause);
    }
}
