package com.ex.hero.cart.model.mapper;

import com.ex.hero.cart.model.Cart;
import com.ex.hero.cart.model.CartItem;
import com.ex.hero.cart.model.dto.request.AddCartItemRequest;
import com.ex.hero.cart.model.dto.request.AddCartRequest;
import com.ex.hero.cart.model.dto.response.CartItemResponse;
import com.ex.hero.cart.model.dto.response.CartResponse;
import com.ex.hero.cart.service.CommonCartService;
import com.ex.hero.common.annotation.Mapper;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.service.CommonTicketItemService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Mapper
@RequiredArgsConstructor
public class CartMapper {

    private final CommonTicketItemService commonTicketItemService;
    private final CommonEventService commonEventService;
    private final CommonCartService commonCartService;

    public CartResponse toCartResponse(Long cartId){
        Cart cart = commonCartService.queryCart(cartId);
        return getCartResponse(cart);
    }

    public CartResponse toCartResponse(Cart cart) {
        return getCartResponse(cart);
    }

    private CartResponse getCartResponse(Cart cart) {
        List<CartItem> newCartLineItems = cart.getCartItems();
        TicketItem ticketItem = commonTicketItemService.queryTicketItem(cart.getItemId());
        Event event = commonEventService.findById(ticketItem.getEventId());
        List<CartItemResponse> cartItemResponses = getCartItemResponses(newCartLineItems, ticketItem.getName());

        return CartResponse.of(cartItemResponses, cart, ticketItem, event);
    }

    private List<CartItemResponse> getCartItemResponses(
            List<CartItem> newCartLineItems, String name) {
        return newCartLineItems.stream()
                .map(cartLineItem -> getCartItemResponse(cartLineItem, name))
                .toList();
    }

    private CartItemResponse getCartItemResponse(CartItem cartItem, String itemName) {
        return CartItemResponse.of(cartItem, generateCartName(itemName, cartItem.getQuantity()));
    }

    private String generateCartName(String itemName, Long quantity) {
        return String.format("%s %d매", itemName, quantity);
    }

    public Cart toEntity(AddCartRequest addCartRequest, Long currentUserId) {
        List<AddCartItemRequest> addCartItmes = addCartRequest.getItems();
        String itemName = getItemName(addCartItmes);
        List<CartItem> cartLineItems =
                addCartItmes.stream()
                        .map(
                                addCartItemRequest ->
                                        CartItem.builder()
                                                .item(getTicketItem(addCartItemRequest))
                                                .quantity(addCartItemRequest.getQuantity())
                                                .build())
                        .toList();
        return Cart.of(cartLineItems, itemName, currentUserId);
    }

    private String getItemName(List<AddCartItemRequest> addCartItemRequests){
        Long itemId = addCartItemRequests.stream().map(AddCartItemRequest::getItemId).findFirst().orElseThrow();
        return commonTicketItemService.queryTicketItem(itemId).getName();
    }

    private TicketItem getTicketItem(AddCartItemRequest addCartItemRequest) {
        return commonTicketItemService.queryTicketItem(addCartItemRequest.getItemId());
    }


}
