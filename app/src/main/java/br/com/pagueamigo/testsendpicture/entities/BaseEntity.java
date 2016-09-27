package br.com.pagueamigo.testsendpicture.entities;

import com.google.gson.Gson;

/**
 * The base entity.
 */
public class BaseEntity {

    /**
     * Convert the entity to PaymentDetailsActivity json representation.
     *
     * @return the string o PaymentDetailsActivity json representation.
     */
    public String toJsonString() {
        return new Gson().toJson(this);
    }

}
