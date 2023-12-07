package me.kimihiqq.bstoreapi.domain.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kimihiqq.bstoreapi.domain.coupon.domain.Coupon;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
