package me.kimihiqq.bstoreapi.domain.item.facade;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.kimihiqq.bstoreapi.domain.item.application.OptimisticLockItemService;

@Service
@RequiredArgsConstructor
public class OptimisticLockItemFacade {

	private final OptimisticLockItemService optimisticLockItemService;

	public void removeStock(Long id, Long quantity) throws InterruptedException {
		while (true) {
			try {
				optimisticLockItemService.removeStock(id, quantity);

				break;
			} catch (Exception e) {
				Thread.sleep(100);
			}
		}
	}
}
