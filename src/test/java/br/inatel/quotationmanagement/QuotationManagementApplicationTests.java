package br.inatel.quotationmanagement;

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

		String stockId = "stock";
		
		StockDto stockRetrive = webTestClient.get()
				.uri("/stocks/" + stockId)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(StockDto.class)
				.returnResult()
				.getResponseBody();
		
		Assert.assertEquals(stockRetrive.getStockId(), stockId);

	}
	
	@Test
	void postStock() {
		
		StockDto stockDto = new StockDto();
		
		webTestClient.post()
		.uri("/stocks")
		.bodyValue(stockDto)
		.exchange()
		.expectStatus().isOk()
		;
		
	}
	
//	@Test
//	void deleteStock() {
//		
//		webTestClient.delete()
//		.uri("/stocks/" + stockId)
//		.exchange()
//		.expectStatus().isOk()
//		.expectBody().returnResult();
//		
//	}

}
