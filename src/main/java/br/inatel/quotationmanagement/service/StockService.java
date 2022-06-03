package br.inatel.quotationmanagement.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.inatel.quotationmanagement.modelo.Quote;
import br.inatel.quotationmanagement.modelo.Stock;
import br.inatel.quotationmanagement.repository.QuoteRepository;
import br.inatel.quotationmanagement.repository.StockRepository;

@Service
@Transactional
public class StockService {

	private StockRepository stockRepository;
	private QuoteRepository quoteRepository;

	public StockService(StockRepository stockRepository, QuoteRepository quoteRepository) {

		this.stockRepository = stockRepository;
		this.quoteRepository = quoteRepository;
	}

	public List<Stock> list() {

		List<Stock> listAllStocks = stockRepository.findAll();
		return listAllStocks;

	}

	public Optional<Stock> findByStockId(String stockId) {
		Optional<Stock> opStock = stockRepository.findByStockId(stockId);
		return opStock;
	}

	public Stock save(Stock stock) {
		List<Quote> quotes = stock.getQuotes();
		
		stock = stockRepository.save(stock);
		
		for (Quote quote : quotes) {
			quote.setStock(stock);
			quoteRepository.save(quote);
		}
		
		return stock;
	}

	public void delete(Stock stock) {
		stockRepository.delete(stock);
		
	}

}
