package com.ex.hero.events.service;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.dto.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteEventUseCase {
    private final EventService eventService;
    private final CommonEventService commonEventService;

    public EventResponse execute(Long eventId) {
        final Event event = commonEventService.findById(eventId);
        return EventResponse.of(eventService.deleteEvent(event));
    }

}
