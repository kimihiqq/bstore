package me.kimihiqq.bstoreapi.domain.coupon.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.kimihiqq.bstoreapi.domain.coupon.domain.CouponRepository;
import me.kimihiqq.bstorecommon.domain.coupon.application.dto.request.CouponCreateRequest;

@SpringBootTest
class CouponServiceTest {

	@Autowired
	private CouponService couponService;

	@Autowired
	private CouponRepository couponRepository;

	private final String testCouponType = "testCouponType";

	@AfterEach
	void cleanUp() {
		couponRepository.deleteAllByCouponTypeId(testCouponType);
	}

	@Test
	@DisplayName("1인1매쿠폰발급성공테스트")
	public void getCouponSuccessTest() throws InterruptedException {
		int threadCount = 1000;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 1; i <= threadCount; i++) {
			long memberId = i;

			executorService.submit(() -> {
				try {
					CouponCreateRequest request = new CouponCreateRequest(memberId, 5000L, LocalDate.now(), LocalDate.now().plusDays(30), 100, testCouponType);
					couponService.issue(request);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		Thread.sleep(10000);

		long count = couponRepository.countByCouponTypeId(testCouponType);
		assertThat(count).isEqualTo(100);
	}
}
