package com.example.invoice.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="INVOICE_ITEM")
public class Invoice {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name="INVOICE_NUMBER")
   private long id;
   
   @Column(name="INVOICE_AMOUNT")
   private long amount;
   
   @Column(name="PRODUCT_DETAILS")
   private String productDetails;
   
   @Column(name="PRODUCT_ID")
   private String productId;
   
   @Column(name="QUANTITY")
   private long quantity;
   
   @Column(name="INVOICE_STATUS")
   private String invoiceStatus;
   
   @Column(name="INVOICE_DATE")
   private Instant invoiceDate;
}
