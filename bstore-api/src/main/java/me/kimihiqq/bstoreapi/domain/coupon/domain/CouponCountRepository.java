package me.kimihiqq.bstoreapi.domain.coupon.domain;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CouponCountRepository {

	private final RedisTemplate<String, String> redisTemplate;

	public CouponCountRepository(RedisTemplate<String, String> redisTemplate){
		this.redisTemplate = redisTemplate;
	}

	public Long increment(String couponTypeId) {
		String key = "coupon_count_" + couponTypeId;
		return redisTemplate.opsForValue().increment(key);
	}
}
