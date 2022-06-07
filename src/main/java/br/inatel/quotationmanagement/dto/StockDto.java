package br.inatel.quotationmanagement.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import br.inatel.quotationmanagement.modelo.Quote;
import br.inatel.quotationmanagement.modelo.Stock;

public class StockDto {
	
	private final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private String id;
	
	private String stockId;
	
	private Map<String, String> quotes = new HashMap<>();
	
	public StockDto(Stock stock) {
		
		this.id = stock.getId().toString();
		this.stockId = stock.getStockId();
		stock.getQuotes().forEach(q -> {
			String dateStr = q.getDate().format(DTF);
			String priceStr = q.getPrice().toString();
			this.quotes.put(dateStr, priceStr);
		});
		
	}
	
	public StockDto() {
		
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
	
	public Map<String, String> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<String, String> quotes) {
		this.quotes = quotes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static List<StockDto> converterToDto(List<Stock> stock){
		
		return stock.stream().map(StockDto::new).collect(Collectors.toList());
		
	}

	public Stock converterToStock(StockDto stockDto) {
		Stock stock = new Stock();
		stock.setStockId(this.stockId);
		
		List<Quote> listQuote = new ArrayList<Quote>();
		for (Entry<String, String> entry : this.quotes.entrySet()) {
			
			LocalDate date = LocalDate.parse(entry.getKey(), DTF);
			Double price = Double.parseDouble(entry.getValue());
			Quote quote = new Quote(date, price, stock);
			listQuote.add(quote);
		}
		
		stock.setQuotes(listQuote);		
		
		return stock;
	}

}
