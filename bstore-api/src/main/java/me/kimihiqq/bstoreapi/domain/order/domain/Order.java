package me.kimihiqq.bstoreapi.domain.order.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimihiqq.bstoreapi.domain.member.domain.Member;
import me.kimihiqq.bstoreapi.domain.coupon.domain.Coupon;
import me.kimihiqq.bstoreapi.global.BaseEntity;
import me.kimihiqq.bstoreapi.global.error.ErrorCode;
import me.kimihiqq.bstoreapi.global.error.exception.BusinessException;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	private LocalDateTime orderDate;

	private Long totalAmount;

	@Builder
	public Order(Member member, Coupon coupon, List<OrderItem> orderItems, LocalDateTime orderDate) {
		this.member = member;
		this.coupon = coupon;
		this.orderDate = orderDate;
	}

	public void addOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public void applyCoupon(Coupon coupon) {
		LocalDate today = LocalDate.now();

		if (today.isAfter(coupon.getValidFrom()) && today.isBefore(coupon.getValidTo())) {

			this.totalAmount = Math.max(0, this.totalAmount - coupon.getDiscountAmount());
		} else {

		}
			throw new BusinessException(ErrorCode.INVALID_COUPON);
		}
}
