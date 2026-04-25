package org.example.tfgbackend.dto.response;

public class PaymentIntentResponse {
    private String clientSecret;
    private String ephemeralKey;
    private String customerId;
    private String publishableKey;

    public PaymentIntentResponse() {}

    public PaymentIntentResponse(String clientSecret, String ephemeralKey,
                                 String customerId, String publishableKey) {
        this.clientSecret = clientSecret;
        this.ephemeralKey = ephemeralKey;
        this.customerId = customerId;
        this.publishableKey = publishableKey;
    }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }
    public String getEphemeralKey() { return ephemeralKey; }
    public void setEphemeralKey(String ephemeralKey) { this.ephemeralKey = ephemeralKey; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getPublishableKey() { return publishableKey; }
    public void setPublishableKey(String publishableKey) { this.publishableKey = publishableKey; }
}