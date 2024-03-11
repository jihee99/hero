package com.ex.hero.ticket.controller;

import com.ex.hero.ticket.model.dto.request.CreateTicketItemRequest;
import com.ex.hero.ticket.model.dto.request.GetEventTicketItemsResponse;
import com.ex.hero.ticket.model.dto.response.TicketItemResponse;
import com.ex.hero.ticket.service.CreateTicketItemUseCase;
import com.ex.hero.ticket.service.DeleteTicketItemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "3. 티켓 상품 api(그룹용)")
@RestController
@RequestMapping("/api/v1/events/{eventId}/ticketItems")
@RequiredArgsConstructor
public class TicketItemController {

    private final CreateTicketItemUseCase createTicketItemUseCase;
    private final DeleteTicketItemUseCase deleteTicketItemUseCase;

    @Operation(summary = "이벤트에 속하는 티켓 상품을 생성합니다.")
    @PostMapping()
    public TicketItemResponse createTicketItem(
            @PathVariable Long eventId,
            @RequestBody @Valid CreateTicketItemRequest createTicketItemRequest) {
        return createTicketItemUseCase.execute(createTicketItemRequest, eventId);
    }

    @Operation(summary = "해당 티켓상품을 삭제합니다.")
    @PostMapping("/{ticketItemId}")
    public GetEventTicketItemsResponse deleteTicketItem(
            @PathVariable Long eventId, @PathVariable Long ticketItemId) {
        return deleteTicketItemUseCase.execute(eventId, ticketItemId);
    }
}
