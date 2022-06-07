package br.inatel.quotationmanagement.dto;

import java.time.LocalDate;

import br.inatel.quotationmanagement.modelo.Quote;

public class QuoteDto {
	
	private LocalDate date;
	private Double price;
	
	public QuoteDto(Quote quote) {
		
		this.date = quote.getDate();
		this.price= quote.getPrice();
		
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
