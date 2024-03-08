package com.ex.hero.group.service;

import com.ex.hero.group.exception.AlreadyJoinedGroupException;
import com.ex.hero.group.model.Group;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.service.CommonMemberService;
import com.ex.hero.member.vo.MemberProfileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadInviteUsersUseCase {

    private final CommonGroupService commonGroupService;
    private final CommonMemberService commonMemberService;

    @Transactional(readOnly = true)
    public MemberProfileVo execute(Long hostId, String email) {
        return toGroupInviteUserList(hostId, email);
    }

    public MemberProfileVo toGroupInviteUserList(Long groupId, String email) {
        final Group group = commonGroupService.findById(groupId);
        final Member inviteUser = commonMemberService.queryUserByEmail(email);
        if (group.hasGroupUserId(inviteUser.getUserId())) {
            throw AlreadyJoinedGroupException.EXCEPTION;
        }
        return inviteUser.toMemberProfileVo();
    }
}
