package me.kimihiqq.bstore.domain.item.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import me.kimihiqq.bstore.domain.item.domain.Item;
import me.kimihiqq.bstore.domain.item.domain.ItemRepository;

@Service
public class ItemService {

	private final ItemRepository itemRepository;

	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	// @Transactional(propagation = Propagation.REQUIRES_NEW)
	public synchronized void decrease(Long id, Long quantity) {
		Item item = itemRepository.findById(id).orElseThrow();

		item.removeStock(quantity);

		itemRepository.saveAndFlush(item);
	}
}
