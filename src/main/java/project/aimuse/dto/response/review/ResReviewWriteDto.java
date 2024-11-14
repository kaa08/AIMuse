package project.aimuse.dto.response.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Review;

@Getter
@Setter
@NoArgsConstructor
public class ResReviewWriteDto {

    private Long reviewId;
    private String title;
    private String content;
    private String writerName;
    private String writerNickname;
    private String createdDate;

    @Builder
    public ResReviewWriteDto(Long reviewId, String title, String content, String writerName, String writerNickname, String createdDate) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.createdDate = createdDate;
    }

    public static ResReviewWriteDto fromEntity(Review review) {
        return ResReviewWriteDto.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .writerName(review.getMember().getUsername())
                .writerNickname(review.getMember().getNickname())
                .createdDate(review.getCreatedDate())
                .build();
    }
}
