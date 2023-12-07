package me.kimihiqq.bstoreapi.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	//서버
	INTERNAL_SERVER_ERROR("S001", "예기치 못한 오류가 발생했습니다."),
	UNABLE_TO_HANDLE_ERROR("S002", "처리할 수 없는 데이터입니다."),
	THREAD_INTERRUPTED("S003", "스레드가 중단되었습니다."),


	//공용
	INVALID_INPUT_VALUE("C001", "잘못된 값을 입력하셨습니다."),
	UNAUTHORIZED_REQUEST("C002", "해당 요청을 수행할 권한이 없습니다."),
	DEFAULT_IMAGE_ALREADY_SET("C003", "기본 이미지로 설정된 이미지는 삭제할 수 없습니다."),
	INVALID_LIST_SORT_TYPE("C004", "유효하지 않은 정렬 조건입니다."),
	REQUIRE_QUERY_PARAM("C005", "URL에 추가적인 요청 조건이 필요합니다."),
	LOCK_ACQUISITION_FAILED("L001", "락 획득에 실패했습니다."),

	//상품
	INVALID_ITEM_QUANTITY("I001", "상품 재고가 부족합니다"),
	;

	private final String code;
	private final String message;
}
