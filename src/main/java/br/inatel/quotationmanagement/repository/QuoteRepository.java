package br.inatel.quotationmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.quotationmanagement.modelo.Quotes;

public interface QuoteRepository extends JpaRepository<Quotes, String> {

}
