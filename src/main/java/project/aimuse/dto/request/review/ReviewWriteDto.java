package project.aimuse.dto.request.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Board;

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


    public static Board ofEntity(ReviewWriteDto dto) {
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }
}
