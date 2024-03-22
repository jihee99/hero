package com.ex.hero.group.service;

import com.ex.hero.common.vo.UserProfileVo;
import com.ex.hero.group.exception.AlreadyJoinedGroupException;
import com.ex.hero.group.model.Group;
import com.ex.hero.user.model.User;
import com.ex.hero.user.service.CommonUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadInviteUsersUseCase {

    private final CommonGroupService commonGroupService;
    private final CommonUserService commonUserService;

    @Transactional(readOnly = true)
    public UserProfileVo execute(Long groupId, String email) {
        return toGroupInviteUserList(groupId, email);
    }

    public UserProfileVo toGroupInviteUserList(Long groupId, String email) {
        final Group group = commonGroupService.findById(groupId);
        final User inviteUser = commonUserService.queryUserByEmail(email);
        if (group.hasGroupUserId(inviteUser.getUserId())) {
            throw AlreadyJoinedGroupException.EXCEPTION;
        }
        return inviteUser.toUserProfileVo();
    }
}
