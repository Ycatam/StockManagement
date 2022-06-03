package br.inatel.quotationmanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
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
import br.inatel.quotationmanagement.form.StockForm;
import br.inatel.quotationmanagement.modelo.Stock;
import br.inatel.quotationmanagement.repository.QuoteRepository;
import br.inatel.quotationmanagement.repository.StockRepository;

@RestController
@RequestMapping("/stocks")
public class StockController {
	
	private final StockRepository stockRepository;
	private final QuoteRepository quoteRepository;
	
	public StockController(StockRepository stockRepository, QuoteRepository quoteRepository) {

		this.stockRepository = stockRepository;
		this.quoteRepository = quoteRepository;
	}



	@GetMapping
	@Transactional
	@Cacheable(value = "stockCache")
	public List<StockDto> list(){
		
		List<Stock> listAllStocks = stockRepository.findAll();
		return StockDto.converter(listAllStocks);
	
	}
	
	@GetMapping("/{stockId}")
	@Transactional
	public ResponseEntity<StockDto> listByStockId(@PathVariable String stockId){
		
		Optional<Stock> stock = stockRepository.findByStockId(stockId);
		if (stock.isPresent()) {

			return ResponseEntity.ok(new StockDto(stock.get()));

		}

		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "stockCache", allEntries = true)
	public ResponseEntity<StockDto> register(@RequestBody @Valid StockForm form, UriComponentsBuilder uriBuilder){
		
		Stock stock = form.converter();
		stock = stockRepository.save(stock);
		//quote = quoteRepository.save(quote);
		
		URI uri = uriBuilder.path("/stocks/{stockId}").buildAndExpand(stock.getStockId()).toUri();
		
		return ResponseEntity.created(uri).body(new StockDto(stock));
		
	}
	
	@DeleteMapping("/{stockId}")
	@Transactional
	@CacheEvict(value = "stockCache", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable String stockId){
		
		Optional <Stock> optional = stockRepository.findByStockId(stockId);
		
		if (optional.isPresent()) {

			stockRepository.deleteById(stockId);
			return ResponseEntity.ok().build();

		}

		return ResponseEntity.notFound().build();
		
	}

}
