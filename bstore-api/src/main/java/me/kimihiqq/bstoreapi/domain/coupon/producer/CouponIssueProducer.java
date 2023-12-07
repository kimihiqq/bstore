package me.kimihiqq.bstoreapi.domain.coupon.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.kimihiqq.bstorecommon.domain.coupon.application.dto.request.CouponCreateRequest;

@Component
public class CouponIssueProducer {

	private final KafkaTemplate<String, CouponCreateRequest> kafkaTemplate;

	public CouponIssueProducer(KafkaTemplate<String, CouponCreateRequest> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void create(CouponCreateRequest couponCreateRequest) {
		kafkaTemplate.send("coupon_issue", couponCreateRequest);
	}
}
