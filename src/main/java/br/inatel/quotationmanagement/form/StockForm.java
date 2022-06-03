package br.inatel.quotationmanagement.form;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import br.inatel.quotationmanagement.modelo.Stock;

public class StockForm {
	
	private String stockId;
	
	private Map<LocalDate, Double> quotes = new HashMap<>();

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<LocalDate, Double> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<LocalDate, Double> quotes) {
		this.quotes = quotes;
	}

	public Stock converter() {
		
		return new Stock(stockId, quotes);
	}
	

}
