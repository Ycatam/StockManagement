package br.inatel.quotationmanagement.dto;

import java.time.LocalDate;

import br.inatel.quotationmanagement.modelo.Quote;

public class QuotesDto {
	
	private LocalDate date;
	private Double price;
	
	public QuotesDto(Quote quotes) {
		
		this.date = quotes.getDate();
		this.price= quotes.getPrice();
		
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
