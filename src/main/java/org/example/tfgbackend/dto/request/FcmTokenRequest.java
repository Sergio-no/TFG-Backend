package org.example.tfgbackend.dto.request;

import jakarta.validation.constraints.NotBlank;

public class FcmTokenRequest {
    @NotBlank
    private String fcmToken;

    public String getFcmToken() { return fcmToken; }
    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }
}