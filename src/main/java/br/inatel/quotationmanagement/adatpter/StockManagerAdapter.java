package br.inatel.quotationmanagement.adatpter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import br.inatel.quotationmanagement.dto.StockManagementDto;
import br.inatel.quotationmanagement.form.NotificationForm;
import reactor.core.publisher.Flux;

@Service
public class StockManagerAdapter {

	private static final String URL_DOCKER = "http://localhost:8080";

	@Cacheable(value = "cacheManager")
	public List<StockManagementDto> listAllStockManager() {

		List<StockManagementDto> lista = new ArrayList<>();
		try {

			Flux<StockManagementDto> fluxDto = WebClient.create(URL_DOCKER)
					.get()
					.uri("/stock")
					.retrieve()
					.bodyToFlux(StockManagementDto.class);

			fluxDto.subscribe(d -> lista.add(d));
			
			fluxDto.blockLast();

		} catch (WebClientException e) {
			e.printStackTrace();
		}

		return lista;

	}
	
	@CacheEvict(value = "cacheManager", allEntries = true)
	public ResponseEntity<StockManagementDto> registerNewStock(StockManagementDto stockManagementDto) {
		
		StockManagementDto stockRegistered = new StockManagementDto();
		
		stockRegistered.setId(stockManagementDto.getId());
		stockRegistered.setDescription(stockManagementDto.getDescription());
		
		ResponseEntity<Void> responseEntity = 
				WebClient.create(URL_DOCKER)
				.post()
				.uri("/stock")
				.bodyValue(stockRegistered)
				.retrieve()
				.toBodilessEntity()
				.block();
		
		HttpStatus status = responseEntity.getStatusCode();
		return ResponseEntity.status(status).body(stockRegistered);
		
	}
	
	@PostConstruct //executa metodo assim que inicializar projeto
	public void registerAtProjectStartup() {
		
		NotificationForm nf = new NotificationForm();
		nf.setHost("localhost");
		nf.setPort(8081);
		
		try {
			WebClient.create(URL_DOCKER)
			.post()
			.uri("/notification")
			.bodyValue(nf)
			.retrieve()
			.toBodilessEntity()
			.block();
		} catch (WebClientException e) {
			e.printStackTrace();
		}
		
	}
	
}
