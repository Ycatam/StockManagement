package br.inatel.quotationmanagement.modelo;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;


@Entity
public class Quote {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Data não pode ser nula")
	private LocalDate date;
	
	@ManyToOne
	private Stock stock;
	
	@NotNull
	@PositiveOrZero(message = "Somente numeros positivos ou zero")
	private Double price;
	
	public Quote() {
		
	}

	public Quote(LocalDate date, Double price, Stock stock) {
		
		this.date = date;
		this.price = price;
		this.stock = stock;
		
	}

	public Quote(LocalDate date, Double price, String stockId) {

		this.date = date;
		this.price = price;
		stockId = stock.getStockId();
	
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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(date, price, stock);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quote other = (Quote) obj;
		return Objects.equals(date, other.date) && Objects.equals(price, other.price)
				&& Objects.equals(stock, other.stock);
	}
	
}