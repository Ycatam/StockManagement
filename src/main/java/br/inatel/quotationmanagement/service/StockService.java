package br.inatel.quotationmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.inatel.quotationmanagement.adatpter.StockManagerAdapter;
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

	public List<Stock> list() {

		List<Stock> listAllStocks = stockRepository.findAll();
		return listAllStocks;

	}

	public Optional<Stock> findByStockId(String stockId) {
		Optional<Stock> opStock = stockRepository.findByStockId(stockId);
		return opStock;
	}

	public Stock save(Stock stock) {

		List<Quote> quotes = new ArrayList<Quote>();

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

	public Boolean comparingTwoStockIds(Stock stock) {

		List<StockManagementDto> listStockManager = stockManagerAdapter.listAllStockManager();
		
		System.out.println(listStockManager.toString());
		
		for (StockManagementDto stockManagementDto : listStockManager) {
			
			
			if (stockManagementDto.getId().equalsIgnoreCase(stock.getStockId())){
				
				return true;
				
			}
			
		}

		return false;
		
	}

}