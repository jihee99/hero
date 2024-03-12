package com.ex.hero.ticket.controller;

import com.ex.hero.ticket.model.dto.request.CreateTicketItemRequest;
import com.ex.hero.ticket.model.dto.request.GetEventTicketItemsResponse;
import com.ex.hero.ticket.model.dto.response.TicketItemResponse;
import com.ex.hero.ticket.service.CreateTicketItemUseCase;
import com.ex.hero.ticket.service.DeleteTicketItemUseCase;
import com.ex.hero.ticket.service.GetEventTicketItemsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "4. 티켓 상품 관리 API (그룹용)")
@RestController
@RequestMapping("/api/v1/events/{eventId}/ticketItems")
@RequiredArgsConstructor
public class TicketItemController {

    private final GetEventTicketItemsUseCase getEventTicketItemsUseCase;

    @Operation(summary = "해당 이벤트의 티켓상품을 모두 조회합니다.")
    @GetMapping
    public GetEventTicketItemsResponse getEventTicketItems(@PathVariable Long eventId) {
        return getEventTicketItemsUseCase.execute(eventId);
    }

}
