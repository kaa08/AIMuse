package project.aimuse.dto.response.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Review;


@Getter
@Setter
@NoArgsConstructor
public class ResReviewDetailsDto {

    private Long reviewId;
    private String title;
    private String content;
    private Integer viewCount;
    private String writerName;
    private String writerNickname;
    private String createDate;
    private String modifiedDate;


    @Builder
    public ResReviewDetailsDto(Long reviewId, String title, String content, Integer viewCount, String writerName, String writerNickname, String createDate, String modifiedDate) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

    public static ResReviewDetailsDto fromEntity(Review review) {
        return ResReviewDetailsDto.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .viewCount(review.getViewCount())
                .writerName(review.getMember().getUsername())
                .writerNickname(review.getMember().getNickname())
                .createDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .build();
    }
}
