package com.maveric.commentservice.exception;

public class CommentNotFound extends RuntimeException {
    public CommentNotFound(String message) {
        super(message);
    }
}
