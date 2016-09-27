package br.com.pagueamigo.testsendpicture.entities;

import com.google.gson.Gson;

/**
 * Class to represent PaymentDetailsActivity payment placeholder.
 */
public class Transaction extends BaseEntity {

    public String friend;
    public String imagePath;
    public String message;
    public String subject;
    public String value;
    public String date;
    public String status;
    public long rememberTimestamp = 0;
    public String createdTime;
    public String updatedTime;
    public String type;


    /**
     * Get entity from json.
     *
     * @param jsonString json object.
     * @return the entity object.
     */
    public static Transaction getFromJson(String jsonString) {
        return new Gson().fromJson(jsonString, Transaction.class);
    }

    public double getValue() {
        return Double.parseDouble(value.replace("BRL", "").replace(",", ".").trim());
    }

}
