package com.be.java.hisp.w156.be.java.hisp.w156.exception;

public class UserCantFollowHimselfException extends RuntimeException {

    public UserCantFollowHimselfException() {
        super("The user can't follow himself.");
    }
}
