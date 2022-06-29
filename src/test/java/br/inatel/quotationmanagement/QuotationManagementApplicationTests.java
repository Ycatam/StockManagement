package br.inatel.quotationmanagement;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.inatel.quotationmanagement.dto.StockDto;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class QuotationManagementApplicationTests {

	@Autowired
	private WebTestClient webTestClient;
	

	@Test
	void listAllStocks() {

		webTestClient.get()
			.uri("/stocks")
			.exchange()
			.expectStatus().isOk()
			.expectBody().returnResult();

	}

	@Test
	void listStockByValidStockId() {

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
	void returnNotFoundListStockByInvalidStockId() {

		String stockId = "vale8";
		
		webTestClient.get()
				.uri("/stocks/" + stockId)
				.exchange()
				.expectStatus().isNotFound()
				;
	}
	
	@Test
	void postValidStock() {
		
		StockDto stockDto = new StockDto();
		Map<String, String> quotes = new HashMap<String, String>();
		quotes.put("2019-02-03", "10");
		
		stockDto.setId("2582");
		stockDto.setQuotes(quotes);
		stockDto.setStockId("vale5");
		
		webTestClient.post()
		.uri("/stocks")
		.bodyValue(stockDto)
		;
		
	}
	
	@Test
	void postNullStock() {
		
		StockDto stockDto = new StockDto();
		
		stockDto.setId(null);
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
	void postInvalidPriceStock() {
		
		StockDto stockDto = new StockDto();
		Map<LocalDate, Double> quotes = new HashMap<>();
		quotes.put("2019-02-03", "-10");
		
		stockDto.setId("2582");
		stockDto.setQuotes(quotes);
		stockDto.setStockId("vale5");
		
		webTestClient.post()
		.uri("/stocks")
		.bodyValue(stockDto)
		.exchange()
		.expectStatus().isBadRequest()
		;
		
	}
	
	@Test
	void postInvalidStockId() {
		
		StockDto stockDto = new StockDto();
		Map<String, String> quotes = new HashMap<String, String>();
		quotes.put("2019-02-03", "10");
		
		stockDto.setId("2582");
		stockDto.setQuotes(quotes);
		stockDto.setStockId("vale8");
		
		webTestClient.post()
		.uri("/stocks")
		.bodyValue(stockDto)
		.exchange()
		.expectStatus().isBadRequest()
		;
		
	}

	
	@Test
	void deleteStock() {
		
		String stockId = "vale5";
		
		webTestClient.delete()
		.uri("/stocks/" + stockId)
		.exchange()
		.expectStatus().isOk()
		.expectBody().returnResult();
		
	}
	
	@Test
	void deleteStockByInvalidStockId() {
		
		String stockId = "xpto12";
		
		webTestClient.delete()
		.uri("/stocks/" + stockId)
		.exchange()
		.expectStatus().isNotFound()
		;
		
	}

}
