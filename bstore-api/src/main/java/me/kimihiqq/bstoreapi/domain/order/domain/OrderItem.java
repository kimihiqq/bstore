package me.kimihiqq.bstoreapi.domain.order.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimihiqq.bstoreapi.domain.item.domain.Item;
import me.kimihiqq.bstoreapi.global.BaseEntity;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	private int quantity;
	private Long orderPrice;

	@Builder
	public OrderItem(Order order, Item item, int quantity, Long orderPrice) {
		this.order = order;
		this.item = item;
		this.quantity = quantity;
		this.orderPrice = orderPrice;
	}

}
