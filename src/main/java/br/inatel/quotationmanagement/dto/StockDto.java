package br.inatel.quotationmanagement.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.inatel.quotationmanagement.modelo.Quote;
import br.inatel.quotationmanagement.modelo.Stock;

public class StockDto {
	
	private String id;
	
	private String stockId;
	
	private Map<LocalDate, Double> quotes = new HashMap<>();
	
	
	public StockDto() {
		
	}
	
	public StockDto(Stock stock) {
		
		this.id = stock.getId().toString();
		this.stockId = stock.getStockId();
		stock.getQuotes().forEach(q -> {
			this.quotes.put(q.getDate(), q.getPrice());
		});
		
	}
	
	public Stock converterToStock() {
		Stock stock = new Stock();
		if (this.id != null && !this.id.trim().isEmpty()) {
			stock.setId(this.id);
		}
		stock.setStockId(this.stockId);
		
		this.quotes.entrySet().stream()
			.map(e -> new Quote(e.getKey(), e.getValue(), stock))
			.forEach(q -> stock.addQuote(q));
		
		return stock;
	}
	
	public static List<StockDto> converterToDto(List<Stock> stock){
		
		return stock.stream().map(StockDto::new).collect(Collectors.toList());
		
	}
	
	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<LocalDate, Double> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<LocalDate, Double> quotes) {
		this.quotes = quotes;
	}
	
}
