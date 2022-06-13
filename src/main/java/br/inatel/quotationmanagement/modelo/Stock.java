package br.inatel.quotationmanagement.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private String id;

	@NotNull
	@Size(max = 10)
	private String stockId;

	@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Quote> quotes = new ArrayList<>();

	public Stock() {

	}
	

	public Stock(String stockId, List<Quote> quotes) {
		
		this.stockId = stockId;
		this.quotes = quotes;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}
	
	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
