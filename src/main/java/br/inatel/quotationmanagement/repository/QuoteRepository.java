package br.inatel.quotationmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.inatel.quotationmanagement.modelo.Quote;
import br.inatel.quotationmanagement.modelo.Stock;

public interface QuoteRepository extends JpaRepository<Quote, String> {

	Optional<Stock> findByStockId(String stockId);
	
//	@Query("select q.date from Quote q")
//	List<Object>listAllQuoteDates();

}
