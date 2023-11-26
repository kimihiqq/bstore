package me.kimihiqq.bstore.domain.item.domain;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

	@Lock(value = LockModeType.OPTIMISTIC)
	@Query("select i from Item i where i.id = :id")
	Item findByIdWithOptimisticLock(Long id);
}
