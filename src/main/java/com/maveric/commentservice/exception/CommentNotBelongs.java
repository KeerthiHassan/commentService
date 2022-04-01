package com.maveric.commentservice.exception;

public class CommentNotBelongs extends RuntimeException {
    public CommentNotBelongs(String message) {
        super(message);
    }
}
