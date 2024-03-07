package com.ex.hero.group.model;

import com.ex.hero.common.model.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupProfile extends BaseTimeEntity {
        // 호스트 이름
    @Column(length = 15)
    private String name;

    // 간단 소개
    private String introduce;


    // 대표자 이메일
    private String contactEmail;

    // 대표자 연락처
    @Column(length = 15)
    private String contactNumber;

    protected void updateProfile(GroupProfile groupProfile) {
        this.introduce = groupProfile.getIntroduce();
        this.contactEmail = groupProfile.getContactEmail();
        this.contactNumber = groupProfile.getContactNumber();
    }

    @Builder
    public GroupProfile(
            String name,
            String introduce,
            String contactEmail,
            String contactNumber) {
        this.name = name;
        this.introduce = introduce;
        this.contactEmail = contactEmail;
        this.contactNumber = contactNumber;
    }



}
