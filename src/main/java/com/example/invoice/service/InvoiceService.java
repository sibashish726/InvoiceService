package com.example.invoice.service;

import java.util.List;
import com.example.invoice.dto.InvoiceRequest;
import com.example.invoice.dto.InvoiceResponse;

public interface InvoiceService {
	long saveInvoice(InvoiceRequest invoiceRequest);
	List<InvoiceResponse> getAllInvoices();
	InvoiceResponse getInvoiceById(long invoiceId);
	void updateInvoice(long invoiceId, InvoiceRequest invoiceRequest);
	void deleteInvoiceById(long invoiceId);
	List<InvoiceResponse> getInvoicesByStatus(String status);
	List<InvoiceResponse> getInvoicesByProductId(String productId);
	void updateInvoiceStatus(long invoiceId, String status);
	long getInvoiceCount();
	void deleteAllInvoices();
}
