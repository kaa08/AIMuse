package project.aimuse.dto.response.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Notice;

@Getter
@Setter
@NoArgsConstructor
public class ResNoticeWriteDto {

    private Long noticeId;
    private String title;
    private String content;
    private String writerName;
    private String writerNickname;
    private String createdDate;

    @Builder
    public ResNoticeWriteDto(Long noticeId, String title, String content, String writerName, String writerNickname, String createdDate) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.createdDate = createdDate;
    }

    public static ResNoticeWriteDto fromEntity(Notice notice) {
        return ResNoticeWriteDto.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writerName(notice.getMember().getUsername())
                .writerNickname(notice.getMember().getNickname())
                .createdDate(notice.getCreatedDate())
                .build();
    }

}
