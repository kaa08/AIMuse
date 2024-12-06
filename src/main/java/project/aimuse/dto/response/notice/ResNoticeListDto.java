package project.aimuse.dto.response.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Notice;

@Getter
@Setter
@NoArgsConstructor
public class ResNoticeListDto {
    private Long noticeId;
    private String title;
    private String content;
    private Integer viewCount;
    private String createdDate;
    private String modifiedDate;
    private String writerName;
    private String writerNickname;

    @Builder
    public ResNoticeListDto(Long noticeId, String title, String content, Integer viewCount, String createdDate, String modifiedDate, String writerName, String writerNickname) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
    }

    public static ResNoticeListDto fromEntity(Notice notice) {
        return ResNoticeListDto.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .viewCount(notice.getViewCount())
                .createdDate(notice.getCreatedDate())
                .modifiedDate(notice.getModifiedDate())
                .writerName(notice.getMember().getUsername())
                .writerNickname(notice.getMember().getNickname())
                .build();
    }
}
