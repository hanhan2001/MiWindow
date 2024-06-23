package me.xiaoying.window.event;

public class IllegalEventAccessException extends RuntimeException {
    public IllegalEventAccessException(String s) {
        super(s);
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}