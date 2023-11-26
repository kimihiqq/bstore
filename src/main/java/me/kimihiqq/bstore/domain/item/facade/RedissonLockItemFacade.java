package me.kimihiqq.bstore.domain.item.facade;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.bstore.domain.item.application.ItemService;
import me.kimihiqq.bstore.global.error.ErrorCode;
import me.kimihiqq.bstore.global.error.exception.BusinessException;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedissonLockItemFacade {

	private final RedissonClient redissonClient;

	private final ItemService stockService;

	public void removeStock(Long key, Long quantity) {
		RLock lock = redissonClient.getLock(key.toString());
		boolean isLocked = false;

		try {
			isLocked = lock.tryLock(10, 1, TimeUnit.SECONDS);

			if (!isLocked) {
				log.info("Can't get lock for key: {}", key);
				throw new BusinessException(ErrorCode.LOCK_ACQUISITION_FAILED);
			}

			stockService.removeStock(key, quantity);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new BusinessException(ErrorCode.THREAD_INTERRUPTED);
		} finally {
			if (isLocked) {
				lock.unlock();
			}
		}
	}
}
