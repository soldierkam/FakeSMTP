package com.nilhcem.fakesmtp.core.exception;

/**
 * Thrown if the SMTP port is invalid while trying to start the server.
 * <p>
 * SMTP port can be invalid if it contains some characters, other than numbers.
 * </p>
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class InvalidPortException extends Exception {

    private static final long serialVersionUID = -3964366344520192790L;
    private final int port;

    public InvalidPortException(int port) {
        super("Invalid port " + port);
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
