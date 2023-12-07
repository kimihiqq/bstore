package me.kimihiqq.bstoreconsumer.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kimihiqq.bstoreconsumer.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class OutOfStockException extends RuntimeException{

	private final ErrorCode errorCode;
}
