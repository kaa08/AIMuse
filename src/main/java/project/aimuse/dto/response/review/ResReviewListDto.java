package project.aimuse.dto.response.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Review;

@Getter
@Setter
@NoArgsConstructor
public class ResReviewListDto {

    private Long reviewId;
    private String title;
    private String content;
    private String writerName;
    private String writerNickname;
    private Integer viewCount;
    private String createDate;
    private String modifiedDate;

    @Builder
    public ResReviewListDto(Long reviewId, String title, String content, String writerName, String writerNickname, Integer viewCount, String createDate, String modifiedDate) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.viewCount = viewCount;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

    public static ResReviewListDto fromEntity(Review review) {
        return ResReviewListDto.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .writerName(review.getMember().getUsername())
                .writerNickname(review.getMember().getNickname())
                .viewCount(review.getViewCount())
                .createDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .build();
    }
}
