package com.ex.hero.events.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventDetail {
    // 포스터 이미지도 고민중
//    @Embedded private ImageVo posterImage;

    @Column(columnDefinition = "TEXT")
    private String content;

    protected Boolean isUpdated() {
        return this.content != null;
    }

    @Builder
    public EventDetail(
//            String posterImageKey,
            String content) {
//        this.posterImage = ImageVo.valueOf(posterImageKey);
        this.content = content;
    }
}
