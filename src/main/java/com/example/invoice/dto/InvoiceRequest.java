package com.example.invoice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceRequest {
	   
	   private long amount;
	   private String productDetails;
	   private String productId;
	   private long quantity;
}
