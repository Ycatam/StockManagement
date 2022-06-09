package br.inatel.quotationmanagement.adatpter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.quotationmanagement.dto.StockManagementDto;
import reactor.core.publisher.Flux;

@Service
public class StockManagerAdapter {

	public List<StockManagementDto> listAllStockManager() {

		List<StockManagementDto> lista = new ArrayList<>();

		Flux<StockManagementDto> fluxDto = WebClient.create("http://localhost:8080")
				.get()
				.uri("/stock")
				.retrieve()
				.bodyToFlux(StockManagementDto.class);

		fluxDto.subscribe(d -> lista.add(d));
		
		fluxDto.blockLast();

		return lista;

	}

}
