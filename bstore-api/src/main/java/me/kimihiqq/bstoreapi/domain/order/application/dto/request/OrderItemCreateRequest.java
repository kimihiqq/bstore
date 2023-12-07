package me.kimihiqq.bstoreapi.domain.order.application.dto.request;

public record OrderItemCreateRequest(Long itemId, int quantity) {

	public static OrderItemCreateRequest of(Long itemId, int quantity) {
		return new OrderItemCreateRequest(itemId, quantity);
	}
}
