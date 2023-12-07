package me.kimihiqq.bstoreapi.domain.coupon.application;

import org.springframework.stereotype.Service;

import me.kimihiqq.bstoreapi.domain.coupon.domain.CouponIssuedMemberRepository;
import me.kimihiqq.bstoreapi.domain.coupon.domain.CouponCountRepository;
import me.kimihiqq.bstoreapi.domain.coupon.producer.CouponIssueProducer;
import me.kimihiqq.bstorecommon.domain.coupon.application.dto.request.CouponCreateRequest;

@Service
public class CouponService {

	private final CouponCountRepository couponCountRepository;

	private final CouponIssueProducer couponIssueProducer;

	private final CouponIssuedMemberRepository couponIssuedMemberRepository;

	public CouponService(CouponCountRepository couponCountRepository, CouponIssueProducer couponIssueProducer, CouponIssuedMemberRepository couponIssuedMemberRepository) {
		this.couponCountRepository = couponCountRepository;
		this.couponIssueProducer = couponIssueProducer;
		this.couponIssuedMemberRepository = couponIssuedMemberRepository;
	}

	public void issue(CouponCreateRequest request) {

		if (!isCouponApplicationAllowed(request.memberId(), request.couponTypeId(), request.totalCoupons())) {
			return;
		}

		couponIssueProducer.create(request);
	}

	private boolean isCouponApplicationAllowed(Long memberId, String couponTypeId, int totalCoupons) {
		Long issuedCount = couponIssuedMemberRepository.add(memberId, couponTypeId);
		if (issuedCount != 1) {
			return false;
		}

		Long count = couponCountRepository.increment(couponTypeId);
		return count <= totalCoupons;
	}
}
