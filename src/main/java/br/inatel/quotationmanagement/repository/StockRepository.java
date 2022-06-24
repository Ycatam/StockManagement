package br.inatel.quotationmanagement.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.modelo.Stock;

public interface StockRepository extends JpaRepository<Stock, String> {

	Optional<Stock> findByStockId(String stockId);
	
//	Stock findByStockIdAndQuoteDate(String stockId, LocalDate date);

}
