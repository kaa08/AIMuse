package project.aimuse.dto.request.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Notice;

@Getter
@Setter
@NoArgsConstructor
public class NoticeWriteDto {

    private String title;
    private String content;

    @Builder
    public NoticeWriteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Notice ofEntity(NoticeWriteDto dto) {
        return Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }
}
