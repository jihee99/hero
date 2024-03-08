package com.ex.hero.ticket.model;

import java.time.LocalDateTime;

import com.ex.hero.member.model.AccountInfo;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import com.ex.hero.common.vo.Money;
import com.ex.hero.member.model.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tickets")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	@Comment("티켓 아이디")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id")
	@Comment("회원 아이디(Long)")
	private Member member;

	@Enumerated(EnumType.STRING)
	@Comment("결제 타입 (무료|유료)")
	private TicketPayType payType;

	private String name;
	private String description;
	private Money price;
	@Comment("재고")
	private Long quantity;
	@Comment("공급량")
	private Long supplyCount;
	@Comment("구매 제한 수량")
	private Long purchaseLimit;
	@Enumerated(EnumType.STRING)
	private TicketType type;
	@Embedded
	private AccountInfo accountInfo;
	@Comment("판매 가능 여부")
	private Boolean isSellable;
	@Comment("판매 가능 시간")
	private LocalDateTime saleStartAt;
	@Comment("판매 종료 시간")
	private LocalDateTime saleEndAt;
	@Enumerated(EnumType.STRING)
	@ColumnDefault(value = "'VALID'")
	@Comment("티켓 상태")
	private TicketItemStatus ticketItemStatus = TicketItemStatus.VALID;





	/* 선착순 티켓인지 확인하는 메서드 */
	public Boolean isFCFS() {
		return this.type.isFCFS();
	}

	/* 티켓 수량이 감소되었는지 확인하는 메서드 */
	public Boolean isQuantityReduced() {
		return !this.quantity.equals(this.supplyCount);
	}

	public void reduceQuantity(Long quantity) {
		if (this.quantity < 0) {
			// throw TicketItemQuantityException.EXCEPTION;
		}
		validEnoughQuantity(quantity);
		this.quantity = this.quantity - quantity;
	}

	public void validEnoughQuantity(Long quantity) {
		if (this.quantity < quantity) {
			// throw TicketItemQuantityLackException.EXCEPTION;
		}
	}

	public void validPurchaseLimit(Long quantity) {
		if (isPurchaseLimitExceed(quantity)) {
			// throw TicketPurchaseLimitException.EXCEPTION;
		}
	}

	public Boolean isPurchaseLimitExceed(Long quantity) {
		return this.purchaseLimit < quantity;
	}


	public Boolean isSold() {
		return quantity < supplyCount;
	}

	public Boolean isQuantityLeft() {
		return quantity > 0;
	}

}
