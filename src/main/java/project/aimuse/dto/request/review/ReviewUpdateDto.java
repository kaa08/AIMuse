package project.aimuse.dto.request.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewUpdateDto {

    private String title;
    private String content;

    @Builder
    public ReviewUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
