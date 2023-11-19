package me.kimihiqq.bstore.global.error;

public class OutOfStockException extends RuntimeException{

	public OutOfStockException(String message) {
		super(message);
	}

}
