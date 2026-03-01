package com.example.invoice.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.invoice.dto.InvoiceRequest;
import com.example.invoice.dto.InvoiceResponse;
import com.example.invoice.entity.Invoice;
import com.example.invoice.exception.CustomException;
import com.example.invoice.repo.InvoiceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class InvoiceServiceImpl implements InvoiceService {

	private final InvoiceRepository invoiceRepository;
	@Override
	public long saveInvoice(InvoiceRequest invoiceRequest) {
		log.info("Creating order..");
		Invoice invoice= Invoice.builder()
				                .amount(invoiceRequest.getAmount())
				                .productDetails(invoiceRequest.getProductDetails())
				                .productId(invoiceRequest.getProductId())
				                .quantity(invoiceRequest.getQuantity())
				                .invoiceStatus("CREATED")
				                .invoiceDate(Instant.now())
				                .build();
	    invoiceRepository.save(invoice);
	    log.info("New Invoice created with id {}"+ invoice.getId());
		return invoice.getId();
	}
	@Override
	public List<InvoiceResponse> getAllInvoices() {
		log.info("Fetching all orders from db");
		List<Invoice> invoices= invoiceRepository.findAll();
		List<InvoiceResponse> invoiceResponses= invoices.stream().map(
				invoice -> {
					InvoiceResponse invoiceResponse=new InvoiceResponse();
					BeanUtils.copyProperties(invoice, invoiceResponse);
					return invoiceResponse;
				}
				).collect(Collectors.toList());
		log.info("Successfully fetched {} inoices"+ invoiceResponses.size());
		return invoiceResponses;
	}
	@Override
	public InvoiceResponse getInvoiceById(long invoiceId) {
		log.info("Invoice details with id {}"+ invoiceId);
		Invoice invoice= invoiceRepository.findById(invoiceId).orElseThrow(
				() -> new CustomException("Invoice not found with id {} "+invoiceId ,"INVOICE_NOT_FOUND",404) 
			);
		InvoiceResponse invoiceResponse= InvoiceResponse.builder()
				                                        .id(invoice.getId())
				                                        .amount(invoice.getAmount())
				                                        .invoiceDate(invoice.getInvoiceDate())
				                                        .invoiceStatus(invoice.getInvoiceStatus())
				                                        .productDetails(invoice.getProductDetails())
				                                        .productId(invoice.getProductId())
				                                        .build();
		return invoiceResponse;
	}
	@Override
	public void updateInvoice(long invoiceId, InvoiceRequest invoiceRequest) {
		log.info("updating invoice with id{} "+invoiceId );
		Invoice invoice= invoiceRepository.findById(invoiceId).orElseThrow(
				() -> new CustomException("Invoice not found with id {} "+invoiceId ,"INVOICE_NOT_FOUND",404) 
			);
		invoice.setAmount(invoiceRequest.getAmount());
		invoice.setInvoiceDate(Instant.now());
		invoice.setQuantity(invoiceRequest.getQuantity());
		
		invoiceRepository.save(invoice);
		log.info("successfully updated invoice with id{} "+invoiceId );
	}
	@Override
	public void deleteInvoiceById(long invoiceId) {
		log.info("Attempting to delete invoice with id: {}", invoiceId);

	    Invoice invoice = invoiceRepository.findById(invoiceId)
	        .orElseThrow(() -> {
	            log.error("Delete failed: Invoice not found with id {}", invoiceId);
	            return new CustomException(
	                "Invoice not found with id " + invoiceId, 
	                "INVOICE_NOT_FOUND", 
	                404
	            );
	        });

	    try {
	        invoiceRepository.delete(invoice);
	        log.info("Successfully deleted invoice with id: {}", invoiceId);
	    } catch (Exception e) {
	        log.error("Technical error occurred while deleting invoice id {}: {}", invoiceId, e.getMessage());
	        throw new CustomException("Could not delete invoice due to internal error", "INTERNAL_ERROR", 500);
	    }
	}

}
