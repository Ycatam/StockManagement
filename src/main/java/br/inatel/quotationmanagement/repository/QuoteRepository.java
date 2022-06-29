package br.inatel.quotationmanagement.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.modelo.Quote;

public interface QuoteRepository extends JpaRepository<Quote, String> {

	Optional<Quote> findByDateAndStockId(LocalDate date, String stockId);

	List<Quote> findAllByStockId(String id);
	
}
