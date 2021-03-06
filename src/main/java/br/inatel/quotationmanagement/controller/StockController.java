package br.inatel.quotationmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.inatel.quotationmanagement.dto.StockDto;
import br.inatel.quotationmanagement.modelo.Stock;
import br.inatel.quotationmanagement.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

	private StockService stockService;

	public StockController(StockService stockService) {
		this.stockService = stockService;
	}

	@GetMapping
	@Cacheable(value = "stockCache")
	public List<StockDto> list() {
		 
		return stockService.list();

	}

	@GetMapping("/{stockId}")
	public ResponseEntity<StockDto> listByStockId(@PathVariable String stockId) {

		StockDto stockDto = stockService.findByStockId(stockId);
		if (stockDto != null) {
			
			return ResponseEntity.ok(stockDto);
		}
		
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@CacheEvict(value = "stockCache", allEntries = true)
	public ResponseEntity<StockDto> register(@RequestBody @Valid StockDto stockDto) {

		Stock stock = stockDto.converterToStock();
		
		try {
			stock = stockService.save(stock);
			return ResponseEntity.ok(new StockDto(stock));
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@DeleteMapping("/{stockId}")
	@CacheEvict(value = "stockCache", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable String stockId) {

		StockDto optional = stockService.findByStockId(stockId);

		if (optional != null) {

			Stock stock = optional.converterToStock();
			stockService.delete(stock);
			return ResponseEntity.ok().build();

		}

		return ResponseEntity.notFound().build();

	}

}
