package com.maveric.commentservice.exception;

public class NoCommentFound extends RuntimeException {
    public NoCommentFound(String message) {
        super(message);
    }
}
