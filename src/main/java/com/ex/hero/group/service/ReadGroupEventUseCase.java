package com.ex.hero.group.service;


import com.ex.hero.group.dto.response.GroupEventProfileResponse;
import com.ex.hero.group.model.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadGroupEventUseCase{
    private final CommonGroupService commonGroupService;

    // private final CommonEventService commonEventService;

    public Page<GroupEventProfileResponse> execute(Long hostId, Pageable pageable) {
        Group group = commonGroupService.findById(hostId);
        return null;
    }
}