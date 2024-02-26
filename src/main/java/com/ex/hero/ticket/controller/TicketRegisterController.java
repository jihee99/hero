package com.ex.hero.ticket.controller;

import javax.xml.transform.Result;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ex.hero.common.dto.ApiResponse;
import com.ex.hero.common.dto.ResultMap;
import com.ex.hero.ticket.dto.request.CreateTicketItemRequest;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.service.TicketItemRegisterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "판매자 티켓 등록 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seller/ticket")
public class TicketRegisterController {

	private final TicketItemRegisterService ticketItemRegisterService;

	@Operation(summary = "티켓 발급")
	@PostMapping("/register")
	public ResultMap createTicketItem(@RequestBody CreateTicketItemRequest ticketItemRequest) {

		ResultMap result = new ResultMap();
		try {
			result.setSuccess();

		} catch (Exception e){
			log.error(e.getMessage());
		}
		return result;
	}
}
