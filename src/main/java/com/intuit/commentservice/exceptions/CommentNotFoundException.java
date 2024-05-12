package com.intuit.commentservice.exceptions;

public class CommentNotFoundException extends CommentServiceException {

    private static final long serialVersionUID = 3936424068454854308L;

    /**
     * Constructs a new {@link CommentNotFoundException}.
     *
     * @param message The message.
     */
    public CommentNotFoundException(final String message) {
        super(message);
    }
}
