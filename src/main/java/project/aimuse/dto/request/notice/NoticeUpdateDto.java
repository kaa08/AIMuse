package project.aimuse.dto.request.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeUpdateDto {

    private String title;
    private String content;

    public NoticeUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
