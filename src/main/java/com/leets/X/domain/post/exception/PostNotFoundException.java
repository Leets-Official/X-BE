package com.leets.X.domain.post.exception;

public class PostNotFoundExceptiom {
    public class PostNotFoundException extends RuntimeException {
        public PostNotFoundException(String message) {
            super(message);
        }
    }
}
