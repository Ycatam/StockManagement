package br.inatel.quotationmanagement;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.inatel.quotationmanagement.dto.StockDto;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class QuotationManagementApplicationTests {

	@Autowired
	private WebTestClient webTestClient;
	

	@Test
	void givenAllStocksInDb_whenListAllStocks_thenReturnAllStocksAndStatusOk() {

		webTestClient.get()
			.uri("/stocks")
			.exchange()
			.expectStatus().isOk()
			.expectBody().returnResult();

	}

	@Test
	void givenAValidStockId_whenGetByStockId_thenReturnStockByStockIdAndStatusOk() {

		String stockId = "petr4";
		
		StockDto stockRetrive = webTestClient.get()
				.uri("/stocks/" + stockId)
				.exchange()
				.expectStatus().isOk()
				.expectBody(StockDto.class)
				.returnResult()
				.getResponseBody();
		
		Assert.assertEquals(stockRetrive.getStockId(), stockId);

	}
	
	@Test
	void givenInvalidStockId_whenGetByStockId_thenReturnStatusNotFound() {

		String stockId = "vale8";
		
		webTestClient.get()
				.uri("/stocks/" + stockId)
				.exchange()
				.expectStatus().isNotFound()
				;
	}
	
	@Test
	void givenValidStock_whenPostValidStock_thenReturntheStockBodyAndStatusOk() {
		
		StockDto stockDto = new StockDto();
		Map<LocalDate, Double> quoteMap = new HashMap<>();
		quoteMap.put(LocalDate.of(2019, Month.APRIL, 10), 10.0);
		
		stockDto.setQuotes(quoteMap);
		stockDto.setStockId("vale5");
		
		webTestClient.post()
		.uri("/stocks")
		.body(BodyInserters.fromValue(stockDto))
		.exchange()
		.expectStatus().isOk()
		;
		
	}
	
	@Test
	void givenNullStock_whenAllAtributesAreNullInAStock_thenReturnInternalServerErrorNullPK() {
		
		StockDto stockDto = new StockDto();
		
		stockDto.setQuotes(null);
		stockDto.setStockId(null);
		
		webTestClient.post()
		.uri("/stocks")
		.bodyValue(stockDto)
		.exchange()
		.expectStatus().is5xxServerError()
		;
		
	}
	
	@Test
	void givenInvalidPriceInStock_whenPostStockWithInvalidPrice_thenReturnBadRequest() {
		
		StockDto stockDto = new StockDto();
		Map<LocalDate, Double> quoteMap = new HashMap<>();
		quoteMap.put(LocalDate.parse("2019-02-03"), Double.parseDouble("-10"));
		
		stockDto.setQuotes(quoteMap);
		stockDto.setStockId("vale5");
		
		webTestClient.post()
		.uri("/stocks")
		.body(BodyInserters.fromValue(stockDto))
		.exchange()
		.expectStatus().isBadRequest()
		;
		
	}
	
	@Test
	void givenInvalidStockId_whenPostStockByInvalidSotckId_thenReturnBadRequest() {
		
		StockDto stockDto = new StockDto();
		Map<LocalDate, Double> quoteMap = new HashMap<>();
		quoteMap.put(LocalDate.parse("2019-02-03"), Double.parseDouble("10"));
		
		stockDto.setQuotes(quoteMap);
		stockDto.setStockId("vale8");
		
		webTestClient.post()
		.uri("/stocks")
		.body(BodyInserters.fromValue(stockDto))
		.exchange()
		.expectStatus().isBadRequest()
		;
		
	}

	
	@Test
	void givenAStockInDb_whenDeleteAValidStock_thenReturnStatusOk() {
		
		String stockId = "vale5";
		
		webTestClient.delete()
		.uri("/stocks/" + stockId)
		.exchange()
		.expectStatus().isOk()
		.expectBody().returnResult();
		
	}
	
	@Test
	void givenAStockInDb_whenDeleteAStockByInvalidStockId_thenReturnStatusNotFound() {
		
		String stockId = "xpto12";
		
		webTestClient.delete()
		.uri("/stocks/" + stockId)
		.exchange()
		.expectStatus().isNotFound()
		;
		
	}

}
