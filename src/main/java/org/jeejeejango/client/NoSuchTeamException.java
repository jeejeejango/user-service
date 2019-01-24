package org.jeejeejango.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author jeejeejango
 * @since 24/01/2019 1:48 PM
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Team is invalid")
public class NoSuchTeamException extends RuntimeException {

    public NoSuchTeamException() {
    }


    public NoSuchTeamException(String message) {
        super(message);
    }


    public NoSuchTeamException(String message, Throwable cause) {
        super(message, cause);
    }


    public NoSuchTeamException(Throwable cause) {
        super(cause);
    }


    public NoSuchTeamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
