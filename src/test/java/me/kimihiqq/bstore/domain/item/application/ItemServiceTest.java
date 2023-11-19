package me.kimihiqq.bstore.domain.item.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.kimihiqq.bstore.domain.item.domain.Item;
import me.kimihiqq.bstore.domain.item.domain.ItemRepository;

@SpringBootTest
class ItemServiceTest {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemRepository itemRepository;

	@BeforeEach
	public void insert() {
		Item item = new Item(1L, 100L);

		itemRepository.saveAndFlush(item);
	}

	@AfterEach
	public void delete() {
		itemRepository.deleteAll();
	}

	@Test
	@DisplayName("동시에 100명 주문")
	public void concurrentOrderTest() throws InterruptedException {
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					itemService.decrease(1L, 1L);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Item item = itemRepository.findById(1L).orElseThrow();

		assertEquals(0, item.getStockNumber());
	}
}
