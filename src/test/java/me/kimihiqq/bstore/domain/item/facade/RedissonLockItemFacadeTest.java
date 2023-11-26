package me.kimihiqq.bstore.domain.item.facade;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.kimihiqq.bstore.domain.item.domain.Item;
import me.kimihiqq.bstore.domain.item.domain.ItemRepository;

@SpringBootTest

class RedissonLockItemFacadeTest {

	@Autowired
	private RedissonLockItemFacade redissonLockItemFacade;

	@Autowired
	private ItemRepository itemRepository;

	private ExecutorService executorService;

	@BeforeEach
	public void setup() {
		Item item = new Item(1L, 100L);
		itemRepository.saveAndFlush(item);
		executorService = Executors.newFixedThreadPool(32);
	}

	@AfterEach
	public void tearDown() {
		itemRepository.deleteAll();
		executorService.shutdownNow();
	}

	@Test
	@DisplayName("동시 주문 100건 성공 테스트")
	public void concurrentOrderTest() throws InterruptedException {
		int threadCount = 100;
		CountDownLatch latch = new CountDownLatch(threadCount);
		List<Future<?>> futures = new ArrayList<>();

		for (int i = 0; i < threadCount; i++) {
			futures.add(executorService.submit(() -> {
				try {
					redissonLockItemFacade.removeStock(1L, 1L);
				} finally {
					latch.countDown();
				}
			}));
		}

		latch.await();
		executorService.shutdown();
		if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
			executorService.shutdownNow();
		}

		Item item = itemRepository.findById(1L).orElseThrow();

		assertEquals(0, item.getStockNumber());
	}
}
