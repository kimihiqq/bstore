package me.kimihiqq.bstoreapi.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kimihiqq.bstoreapi.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;
}
