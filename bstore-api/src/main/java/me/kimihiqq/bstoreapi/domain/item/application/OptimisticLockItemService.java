package me.kimihiqq.bstoreapi.domain.item.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kimihiqq.bstoreapi.domain.item.domain.Item;
import me.kimihiqq.bstoreapi.domain.item.domain.ItemRepository;

@Service
@RequiredArgsConstructor
public class OptimisticLockItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public void removeStock(Long id, Long quantity) {
		Item item = itemRepository.findByIdWithOptimisticLock(id);
		item.removeStock(quantity);

		itemRepository.saveAndFlush(item);
	}
}
