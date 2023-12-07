package me.kimihiqq.bstoreapi.domain.coupon.domain;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CouponIssuedMemberRepository {

	private final RedisTemplate<String, String> redisTemplate;

	public CouponIssuedMemberRepository(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public Long add(Long memberId, String couponTypeId) {
		return redisTemplate.opsForSet().add("issued_member", memberId.toString(), couponTypeId);
	}
}
