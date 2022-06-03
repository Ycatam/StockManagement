package br.inatel.quotationmanagement.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.inatel.quotationmanagement.modelo.Stock;

public class StockDto {
	
	private String id;
	
	private String stockId;
	
	private Map<LocalDate, Double> quotes = new HashMap<>();
	
	public StockDto(Stock stock) {
		
		this.id = stock.getId().toString();
		this.stockId = stock.getStockId();
		stock.getQuotes().forEach(q -> this.quotes.put(q.getDate(), q.getPrice()));
		//stock.getQuotes().forEach((k,v) -> quotes.put(k, v));
		
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
	
	public static List<StockDto> converter(List<Stock> stock){
		
		return stock.stream().map(StockDto::new).collect(Collectors.toList());
		
	}
	
//	private static Page<StockDto> converter(Page<Stock> stock){
//		
//		return stock.map(StockDto::new);
//		
//	}

	
}
