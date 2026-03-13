package com.example.invoice.service;

import java.time.Instant;
import java.util.List;
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
		List<InvoiceResponse> invoiceResponses = invoiceRepository.findAll().stream()
				.map(this::toInvoiceResponse)
				.collect(Collectors.toList());
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
				                                        .quantity(invoice.getQuantity())
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

	@Override
	public List<InvoiceResponse> getInvoicesByStatus(String status) {
		log.info("Fetching invoices with status: {}", status);
		return invoiceRepository.findByInvoiceStatus(status).stream()
				.map(this::toInvoiceResponse)
				.collect(Collectors.toList());
	}

	@Override
	public List<InvoiceResponse> getInvoicesByProductId(String productId) {
		log.info("Fetching invoices for productId: {}", productId);
		return invoiceRepository.findByProductId(productId).stream()
				.map(this::toInvoiceResponse)
				.collect(Collectors.toList());
	}

	@Override
	public void updateInvoiceStatus(long invoiceId, String status) {
		log.info("Updating status of invoice id {} to {}", invoiceId, status);
		Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(
				() -> new CustomException("Invoice not found with id " + invoiceId, "INVOICE_NOT_FOUND", 404));
		invoice.setInvoiceStatus(status);
		invoiceRepository.save(invoice);
		log.info("Successfully updated status of invoice id {} to {}", invoiceId, status);
	}

	@Override
	public long getInvoiceCount() {
		long count = invoiceRepository.count();
		log.info("Total invoices in db: {}", count);
		return count;
	}

	@Override
	public void deleteAllInvoices() {
		log.info("Deleting all invoices");
		invoiceRepository.deleteAll();
		log.info("Successfully deleted all invoices");
	}

	private InvoiceResponse toInvoiceResponse(Invoice invoice) {
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		BeanUtils.copyProperties(invoice, invoiceResponse);
		return invoiceResponse;
	}

}
