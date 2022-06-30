package br.inatel.quotationmanagement.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.inatel.quotationmanagement.adatpter.StockManagerAdapter;
import br.inatel.quotationmanagement.dto.StockDto;
import br.inatel.quotationmanagement.dto.StockManagementDto;
import br.inatel.quotationmanagement.modelo.Quote;
import br.inatel.quotationmanagement.modelo.Stock;
import br.inatel.quotationmanagement.repository.QuoteRepository;
import br.inatel.quotationmanagement.repository.StockRepository;

@Service
@Transactional
public class StockService {

	private StockRepository stockRepository;
	private QuoteRepository quoteRepository;
	private StockManagerAdapter stockManagerAdapter;

	public StockService(StockRepository stockRepository, QuoteRepository quoteRepository,
			StockManagerAdapter stockManagerAdapter) {
		this.stockRepository = stockRepository;
		this.quoteRepository = quoteRepository;
		this.stockManagerAdapter = stockManagerAdapter;
	}

	public List<StockDto> list() {

		List<Stock> listAllStocks = stockRepository.findAll();
		return StockDto.converterToDto(listAllStocks);

	}

	public StockDto findByStockId(String stockId) {
		Optional<Stock> opStock = stockRepository.findByStockId(stockId);
		
		if (opStock.isPresent()) {
			StockDto stockDto = new StockDto(opStock.get());
			return stockDto;
		}
		return null;
	}

	public Stock save(Stock stock) {
		
		List<Quote> listQuote = stock.getQuotes();

		verifyIfExistInStockManager(stock);
		
		saveStock(stock);
		
		listQuote.forEach(q -> saveQuote(q));
		
		return stock;

	}
	
	private Stock saveStock(Stock stock) {
		
		Optional<Stock> opStock = stockRepository.findByStockId(stock.getStockId());
		if (opStock.isPresent()) {

			Stock stockAux = opStock.get();
			stock.setId(stockAux.getId());

		}

		stock = stockRepository.save(stock);
		return stock;
		
	}
	
	private Quote saveQuote(Quote quote) {
		
		Optional<Quote> opQuote = quoteRepository.findByDateAndStockId(quote.getDate(), 
				quote.getStock().getId());
		
		if (opQuote.isPresent()) {
			
			Quote quoteExistent = opQuote.get();
			quoteExistent.setPrice(quote.getPrice());
			return quoteExistent;
			
		}else {
			quote = quoteRepository.save(quote);
			return quote;
		}
		
		
		
	}

	public void delete(Stock stock) {
		
		List<Quote> opQuote = quoteRepository.findAllByStockId(stock.getId());
		
			for (Quote quote : opQuote) {
				quoteRepository.delete(quote);
			}
			
			stockRepository.delete(stock);
	}

	public void verifyIfExistInStockManager(Stock stock) {

		List<StockManagementDto> listStockManager = stockManagerAdapter.listAllStockManager();

		Boolean flagValid = false;
		
		for (StockManagementDto stockManagementDto : listStockManager) {

			if (stockManagementDto.getId().equalsIgnoreCase(stock.getStockId())) {
				
				flagValid = true;
				break;
				
			}
		}
		if(!flagValid) throw new RuntimeException("Stock não existente!");
	}

}