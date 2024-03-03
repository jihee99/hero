package com.ex.hero.host.model;

import com.ex.hero.common.model.BaseTimeEntity;
import com.ex.hero.host.dto.request.UpdateHostRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HostProfile extends BaseTimeEntity {
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

    protected void updateProfile(HostProfile hostProfile) {
        this.introduce = hostProfile.getIntroduce();
        this.contactEmail = hostProfile.getContactEmail();
        this.contactNumber = hostProfile.getContactNumber();
    }

    @Builder
    public HostProfile(
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
