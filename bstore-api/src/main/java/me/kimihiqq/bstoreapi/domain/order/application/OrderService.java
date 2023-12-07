package me.kimihiqq.bstoreapi.domain.order.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kimihiqq.bstoreapi.domain.coupon.domain.Coupon;
import me.kimihiqq.bstoreapi.domain.coupon.domain.CouponRepository;
import me.kimihiqq.bstoreapi.domain.item.domain.Item;
import me.kimihiqq.bstoreapi.domain.item.domain.ItemRepository;
import me.kimihiqq.bstoreapi.domain.item.facade.RedissonLockItemFacade;
import me.kimihiqq.bstoreapi.domain.member.domain.Member;
import me.kimihiqq.bstoreapi.domain.member.domain.MemberRepository;
import me.kimihiqq.bstoreapi.domain.order.application.dto.request.OrderItemCreateRequest;
import me.kimihiqq.bstoreapi.domain.order.domain.Order;
import me.kimihiqq.bstoreapi.domain.order.domain.OrderItem;
import me.kimihiqq.bstoreapi.domain.order.domain.OrderRepository;
import me.kimihiqq.bstoreapi.global.error.ErrorCode;
import me.kimihiqq.bstoreapi.global.error.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository;
	private final RedissonLockItemFacade redissonLockItemFacade;
	private final CouponRepository couponRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public Long createOrder(Long memberId, List<OrderItemCreateRequest> orderItemDtos, Long couponId) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
		Coupon coupon = couponId != null ? couponRepository.findById(couponId)
			.orElse(null) : null;

		Order order = Order.builder()
			.member(member)
			.coupon(coupon)
			.orderDate(LocalDateTime.now())
			.build();

		List<OrderItem> orderItems = createOrderItems(order, orderItemDtos);

		order.addOrderItems(orderItems);

		if (coupon != null) {

			order.applyCoupon(coupon);
		}

		orderRepository.save(order);

		return order.getId();
	}

	private List<OrderItem> createOrderItems(Order order, List<OrderItemCreateRequest> orderItemCreateRequest) {
		return orderItemCreateRequest.stream()
			.map(productReq -> createOrderItem(order, productReq))
			.toList();
	}

	private OrderItem createOrderItem(Order order, OrderItemCreateRequest request) {
		long itemId = request.itemId();
		int quantity = request.quantity();

		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));

		redissonLockItemFacade.removeStock(item.getId(), (long) request.quantity());

		return OrderItem.builder()
			.item(item)
			.order(order)
			.quantity(quantity)
			.orderPrice(item.getPrice() * quantity)
			.build();

	}
}
