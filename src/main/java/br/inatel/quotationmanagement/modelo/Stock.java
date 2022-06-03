package br.inatel.quotationmanagement.modelo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Stock {

	// Random number based UUID (IETF RFC 4122 version 4)
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;

	@NotNull
	@Size(max = 10)
	private String stockId;

	@OneToMany(mappedBy = "stock")
	private List<Quotes> quotes = new ArrayList<>();
	//private Map<LocalDate, Double> quotes = new HashMap<>();

	public Stock() {

	}

	public Stock(UUID id, String stockId, List<Quotes> quotes) {
		
		this.id = id;
		this.stockId = stockId;
		this.quotes = quotes;
		
	}

//	public Stock(String stockId, Map<LocalDate, Double> quotes) {
//
//		this.stockId = stockId;
//		this.quotes = quotes;
//
//	}

	public Stock(String stockId, Map<LocalDate, Double> quotes) {
		
		this.stockId = stockId;
		
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public List<Quotes> getQuotes() {
		return quotes;
	}
	
//	public Map<LocalDate, Double> getQuotes() {
//		return quotes;
//	}

//	public void setQuotes(Map<LocalDate, Double> quotes) {
//		this.quotes = quotes;
//	}
	
	public void setQuotes(List<Quotes> quotes) {
		this.quotes = quotes;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, quotes, stockId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return Objects.equals(id, other.id) && Objects.equals(quotes, other.quotes)
				&& Objects.equals(stockId, other.stockId);
	}

}
