package me.kimihiqq.bstorecommon.domain.coupon.application.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Future;

public record CouponCreateRequest(
	@NotNull Long memberId,
	@NotNull @Min(0) Long discountAmount,
	@NotNull @PastOrPresent LocalDate validFrom,
	@NotNull @Future LocalDate validTo,
	@NotNull @Min(1) int totalCoupons,
	@NotBlank String couponTypeId) {
}
