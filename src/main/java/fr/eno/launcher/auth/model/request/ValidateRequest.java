package fr.eno.launcher.auth.model.request;

/**
 * JSON Model of an validate request
 */
public class ValidateRequest {

    /**
     * The access token that you want to validate
     */
    private String accessToken;

    /**
     * Validate Request constructor
     *
     * @param accessToken
     *            The access token that you want to validate
     */
    public ValidateRequest(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Sets a new access token
     *
     * @param accessToken
     *            The new access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns the access token (Given by the constructor or the setter)
     *
     * @return The given access token
     */
    public String getAccessToken() {
        return accessToken;
    }

}
