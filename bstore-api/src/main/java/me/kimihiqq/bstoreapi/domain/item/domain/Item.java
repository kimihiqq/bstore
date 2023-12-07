package me.kimihiqq.bstoreapi.domain.item.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.kimihiqq.bstoreapi.global.BaseEntity;
import me.kimihiqq.bstoreapi.global.error.ErrorCode;
import me.kimihiqq.bstoreapi.global.error.exception.OutOfStockException;

@Entity
@Table(name="items")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long productId;

	private String itemNm;

	private Long price;

	@Version
	private Long version;

	@Column(nullable = false)
	private Long stockNumber;

	@Lob
	private String itemDetail;

	public Item(Long productId, Long stockNumber){
		this.productId = productId;
		this.stockNumber = stockNumber;
	}

	public void removeStock(Long stockNumber){
		long restStock = this.stockNumber - stockNumber;
		if(restStock<0){
			throw new OutOfStockException(ErrorCode.INVALID_ITEM_QUANTITY);
		}
		this.stockNumber = restStock;
	}

	public void addStock(int stockNumber){
		this.stockNumber += stockNumber;
	}
}
