package com.ex.hero.group.service;

import com.ex.hero.common.SliceResponse;
import com.ex.hero.common.util.MemberUtils;
import com.ex.hero.group.dto.response.GroupProfileResponse;
import com.ex.hero.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReadGroupsUseCase {
    private final MemberUtils memberUtils;
    private final CommonGroupService commonGroupService;

    @Transactional(readOnly = true)
    public SliceResponse<GroupProfileResponse> execute(Pageable pageable) {
        final Member member = memberUtils.getCurrentMember();
        final Long userId = member.getUserId();

        return null;
        // return SliceResponse.of(
        //         commonGroupService
        //                 .querySliceGroupsByUserId(userId, pageable)
        //                 .map(group -> GroupProfileResponse.of(group, userId)));
    }

}
