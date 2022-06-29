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

	public Optional<Stock> findByStockId(String stockId) {
		Optional<Stock> opStock = stockRepository.findByStockId(stockId);
		opStock.get().getQuotes().size();
		return opStock;
	}

	public Stock save(Stock stock, List<Quote> listQuote) {

		if (!verifyIfExistInStockManager(stock)) {
			throw new RuntimeException("Stock não existente!");
		}

		Optional<Stock> opStock = stockRepository.findByStockId(stock.getStockId());
		if (opStock.isPresent()) {

			Stock stockAux = opStock.get();
			stock.setId(stockAux.getId());

		}

		stock = stockRepository.save(stock);
		
		for (Quote quote : listQuote) {
			
			quote.setStock(stock);
			
//			Optional<Quote> opQuote = quoteRepository.findByDateAndStockId(quote.getDate(), stock.getStockId());
			
			List<Quote> opQuote = quoteRepository.findAllByStockId(stock.getId());
			
			if (opQuote.isEmpty()) {
				quoteRepository.save(quote);
			}
			
			else {
				
				Boolean exists = false;
				
				for (Quote quoteAux : opQuote) {
					
					if (quote.getDate().equals(quoteAux.getDate())) {
						//throw new RuntimeException("Data já cadastrada: " + quote.getDate() + " para o stock: " + stock.getStockId());
						exists = true;
						break;
					}
					
				}
				
				if (!exists) {
					quoteRepository.save(quote);
				}
				
			}
			
		}
		
		stock.getQuotes().size();// forçando load
		return stock;

	}

	public void delete(Stock stock) {
		stockRepository.delete(stock);

	}

	public Boolean verifyIfExistInStockManager(Stock stock) {

		List<StockManagementDto> listStockManager = stockManagerAdapter.listAllStockManager();

		for (StockManagementDto stockManagementDto : listStockManager) {

			if (stockManagementDto.getId().equalsIgnoreCase(stock.getStockId())) {

				return true;

			}

		}

		return false;

	}

//	public Boolean verifyIfQuoteDateExists(Stock stock) {
//
//		List<Quote> listQuote = quoteRepository.findAll();
//		List<Quote> quotesList = stock.getQuotes();
//
//		for (Quote quote : listQuote) {
//
//			for (Quote quote2 : quotesList) {
//
//				if (quote.getDate().equals(quote2.getDate())) {
//
//					return true;
//				}
//
//			}
//		}
//		return false;
//	}

}