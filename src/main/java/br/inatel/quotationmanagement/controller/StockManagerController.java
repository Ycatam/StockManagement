package br.inatel.quotationmanagement.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockcache")
public class StockManagerController {
	
	@DeleteMapping()
	@CacheEvict(value = "cacheManager", allEntries = true)
	public ResponseEntity<?> deleteCacheManager(){
		
		return ResponseEntity.noContent().build();
		
	}

}
