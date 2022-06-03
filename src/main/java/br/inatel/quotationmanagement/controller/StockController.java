package br.inatel.quotationmanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
	public List<StockDto> list(){
		
		List<Stock> listAllStocks = stockService.list();
		return StockDto.converter(listAllStocks);
	
	}
	
	@GetMapping("/{stockId}")
	public ResponseEntity<StockDto> listByStockId(@PathVariable String stockId){
		
		Optional<Stock> opStock = stockService.findByStockId(stockId);
		if (opStock.isPresent()) {
			
			StockDto stockDto = new StockDto(opStock.get());
			return ResponseEntity.ok(stockDto);

		}

		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@CacheEvict(value = "stockCache", allEntries = true)
	public ResponseEntity<StockDto> register(@RequestBody @Valid StockDto stockDto, UriComponentsBuilder uriBuilder){
		
		Stock stock = stockDto.converter();
		stock = stockService.save(stock);
		//quote = quoteRepository.save(quote);
		
		URI uri = uriBuilder.path("/stocks/{stockId}").buildAndExpand(stock.getStockId()).toUri();
		
		return ResponseEntity.created(uri).body(new StockDto(stock));
		
	}
	
	@DeleteMapping("/{stockId}")
	@CacheEvict(value = "stockCache", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable String stockId){
		
		Optional <Stock> optional = stockService.findByStockId(stockId);
		
		if (optional.isPresent()) {

			Stock stock = optional.get();
			stockService.delete(stock);
			return ResponseEntity.ok().build();

		}

		return ResponseEntity.notFound().build();
		
	}

}
