package me.kimihiqq.bstoreapi.domain.item.application;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.kimihiqq.bstoreapi.domain.item.domain.Item;
import me.kimihiqq.bstoreapi.domain.item.domain.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	// @Transactional(propagation = Propagation.REQUIRES_NEW)
	public synchronized void removeStock(Long id, Long quantity) {
		Item item = itemRepository.findById(id).orElseThrow();

		item.removeStock(quantity);

		itemRepository.saveAndFlush(item);
	}
}
