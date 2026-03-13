package com.example.invoice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.invoice.dto.InvoiceRequest;
import com.example.invoice.dto.InvoiceResponse;
import com.example.invoice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/v1/invoice")
@Log4j2
@RequiredArgsConstructor
public class InvoiceController {
    
	private final InvoiceService invoiceService;
	
	@GetMapping("/home")
	public String home() {
    	return "This is invoice details home page";
    }
	
	@PostMapping("/saveInvoice")
	public ResponseEntity<Long> saveInvoice(@RequestBody InvoiceRequest invoiceRequest){
		long invoiceId= invoiceService.saveInvoice(invoiceRequest);
		log.info("Invoice created with id {} "+ invoiceId);
		
		return new ResponseEntity<>(invoiceId, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/getAllInvoices")
	public ResponseEntity<List<InvoiceResponse>> getAllInvoices(){
		List<InvoiceResponse> invoiceResponse= invoiceService.getAllInvoices();
		return new ResponseEntity<>(invoiceResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getInvoiceById/{invoiceId}")
	public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable long invoiceId){
		InvoiceResponse invoiceResponse = invoiceService.getInvoiceById(invoiceId);
		return new ResponseEntity<>(invoiceResponse, HttpStatus.OK);
	}
	
	@PutMapping("/updateInvoice/{invoiceId}")
	public ResponseEntity<Void> updateInvoice(@PathVariable long invoiceId,@RequestBody InvoiceRequest invoiceRequest ){
		invoiceService.updateInvoice(invoiceId,invoiceRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteInvoiceById/{invoiceId}")
	public ResponseEntity<Void> deleteInvoiceById(@PathVariable long invoiceId){
		invoiceService.deleteInvoiceById(invoiceId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/getInvoicesByStatus/{status}")
	public ResponseEntity<List<InvoiceResponse>> getInvoicesByStatus(@PathVariable String status){
		List<InvoiceResponse> invoiceResponses = invoiceService.getInvoicesByStatus(status);
		return new ResponseEntity<>(invoiceResponses, HttpStatus.OK);
	}

	@GetMapping("/getInvoicesByProductId/{productId}")
	public ResponseEntity<List<InvoiceResponse>> getInvoicesByProductId(@PathVariable String productId){
		List<InvoiceResponse> invoiceResponses = invoiceService.getInvoicesByProductId(productId);
		return new ResponseEntity<>(invoiceResponses, HttpStatus.OK);
	}

	@PatchMapping("/updateInvoiceStatus/{invoiceId}")
	public ResponseEntity<Void> updateInvoiceStatus(@PathVariable long invoiceId, @RequestParam String status){
		invoiceService.updateInvoiceStatus(invoiceId, status);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/count")
	public ResponseEntity<Long> getInvoiceCount(){
		long count = invoiceService.getInvoiceCount();
		return new ResponseEntity<>(count, HttpStatus.OK);
	}

	@DeleteMapping("/deleteAllInvoices")
	public ResponseEntity<Void> deleteAllInvoices(){
		invoiceService.deleteAllInvoices();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
