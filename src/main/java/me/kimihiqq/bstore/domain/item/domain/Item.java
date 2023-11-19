package me.kimihiqq.bstore.domain.item.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.kimihiqq.bstore.global.BaseEntity;
import me.kimihiqq.bstore.global.error.OutOfStockException;

@Entity
@Table(name="item")
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
			throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stockNumber + ")");
		}
		this.stockNumber = restStock;
	}

	public void addStock(int stockNumber){
		this.stockNumber += stockNumber;
	}
}
