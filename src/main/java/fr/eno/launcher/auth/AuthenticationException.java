package fr.eno.launcher.auth;

import fr.eno.launcher.auth.model.AuthError;

/**
 * Authentication exceptions
 */
@SuppressWarnings("serial")
public class AuthenticationException extends Exception {

    /**
     * The given JSON model instance of the error
     */
    private AuthError model;

    /**
     * Create a new Authentication Exception
     *
     * @param model
     *            The given JSON model instance of the error
     */
    public AuthenticationException(AuthError model) {
        super(model.getErrorMessage());
        this.model = model;
    }

    /**
     * Returns the given JSON model instance of the error
     *
     * @return The error model
     */
    public AuthError getErrorModel() {
        return model;
    }
}