package me.kimihiqq.bstoreconsumer.domain.coupon.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import me.kimihiqq.bstorecommon.domain.coupon.application.dto.request.CouponCreateRequest;
import me.kimihiqq.bstoreconsumer.domain.coupon.domain.Coupon;
import me.kimihiqq.bstoreconsumer.domain.coupon.domain.CouponRepository;
import me.kimihiqq.bstoreconsumer.domain.member.domain.Member;
import me.kimihiqq.bstoreconsumer.domain.member.domain.MemberRepository;

@Component
public class CouponIssuedConsumer {

	private final CouponRepository couponRepository;
	private final MemberRepository memberRepository;

	public CouponIssuedConsumer(CouponRepository couponRepository, MemberRepository memberRepository) {
		this.couponRepository = couponRepository;
		this.memberRepository = memberRepository;
	}

	@KafkaListener(topics = "coupon_issue", groupId = "group_1")
	public void listener(CouponCreateRequest request) {
		Member member = memberRepository.findById(request.memberId())
			.orElseThrow(() -> new RuntimeException("Member not found"));

		Coupon coupon = new Coupon(member, request.discountAmount(),
			request.validFrom(), request.validTo() , request.couponTypeId());
		couponRepository.save(coupon);
	}
}
