package me.kimihiqq.bstoreconsumer.domain.coupon.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.kimihiqq.bstoreconsumer.domain.member.domain.Member;

@Entity
@Table(name="coupons")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private Long discountAmount;
	private LocalDate validFrom;
	private LocalDate validTo;
	private String couponTypeId;

	public Coupon(Member member, Long discountAmount, LocalDate validFrom, LocalDate validTo, String couponTypeId) {
		this.member = member;
		this.discountAmount = discountAmount;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.couponTypeId = couponTypeId;
	}
}
