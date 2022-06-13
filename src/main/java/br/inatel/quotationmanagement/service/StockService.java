package br.inatel.quotationmanagement.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.inatel.quotationmanagement.adatpter.StockManagerAdapter;
import br.inatel.quotationmanagement.dto.StockDto;
import br.inatel.quotationmanagement.dto.StockManagementDto;
import br.inatel.quotationmanagement.modelo.Stock;
import br.inatel.quotationmanagement.repository.StockRepository;

@Service
@Transactional
public class StockService {

	private StockRepository stockRepository;
	private StockManagerAdapter stockManagerAdapter;

	public StockService(StockRepository stockRepository,
			StockManagerAdapter stockManagerAdapter) {

		this.stockRepository = stockRepository;
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

	public Stock save(Stock stock) {

		if(!verifyIfExistInStockManager(stock)) {
			throw new RuntimeException("Stock não existente!");
		}
			
		Optional<Stock> opStock = stockRepository.findByStockId(stock.getStockId());
		if (opStock.isPresent()) {
			
			Stock stockAux = opStock.get();
			stock.setId(stockAux.getId());
			
		}
		
		stock = stockRepository.save(stock);
		stock.getQuotes().size();//forçando load
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

}