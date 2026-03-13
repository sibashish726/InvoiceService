package com.example.invoice.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponse {
    private long id;
    private long amount;
    private long quantity;
    private String invoiceStatus;
    private Instant invoiceDate;
    private String productId;
    private String productDetails;
}
