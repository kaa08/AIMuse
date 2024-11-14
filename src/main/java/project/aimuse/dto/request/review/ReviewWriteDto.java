package project.aimuse.dto.request.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Review;

@Getter
@Setter
@NoArgsConstructor
public class ReviewWriteDto {
    private String title;
    private String content;

    @Builder
    public ReviewWriteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public static Review ofEntity(ReviewWriteDto dto) {
        return Review.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }
}
