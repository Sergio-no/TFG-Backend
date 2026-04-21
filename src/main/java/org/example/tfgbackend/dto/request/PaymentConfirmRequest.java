package org.example.tfgbackend.dto.request;

import jakarta.validation.constraints.NotNull;

public class PaymentConfirmRequest {
    @NotNull
    private Long facturaId;

    private String paymentIntentId;

    public Long getFacturaId() { return facturaId; }
    public void setFacturaId(Long facturaId) { this.facturaId = facturaId; }
    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }
}