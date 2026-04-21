package org.example.tfgbackend.dto.request;

import jakarta.validation.constraints.NotNull;

public class PaymentIntentRequest {
    @NotNull
    private Long facturaId;

    public Long getFacturaId() { return facturaId; }
    public void setFacturaId(Long facturaId) { this.facturaId = facturaId; }
}
