package br.com.pagueamigo.testsendpicture.rest.requestpojo;

import com.google.gson.Gson;

import br.com.pagueamigo.testsendpicture.entities.BaseEntity;


/**
 * This class represents an API error.
 */
public class ApiErrorBody extends BaseEntity {

    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;


    /**
     * Get entity from json.
     *
     * @param jsonString json object.
     * @return the entity object.
     */
    public static ApiErrorBody getFromJson(String jsonString) {
        return new Gson().fromJson(jsonString, ApiErrorBody.class);
    }

    // GETTERS AND SETTERS - BEGIN

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    // GETTERS AND SETTERS - END
}
