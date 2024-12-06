package project.aimuse.dto.response.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Notice;

@Getter
@Setter
@NoArgsConstructor
public class ResNoticeDetailsDto {

    private Long noticeID;
    private String title;
    private String content;
    private Integer viewCount;
    private String writerName;
    private String writerNickname;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public ResNoticeDetailsDto(String modifiedDate, String createdDate, String writerName, String writerNickname, Integer viewCount, String content, String title, Long noticeID) {
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.viewCount = viewCount;
        this.content = content;
        this.title = title;
        this.noticeID = noticeID;
    }

    public static ResNoticeDetailsDto fromEntity(Notice notice) {
        return ResNoticeDetailsDto.builder()
                .noticeID(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .viewCount(notice.getViewCount())
                .writerName(notice.getMember().getUsername())
                .writerNickname(notice.getMember().getNickname())
                .createdDate(notice.getCreatedDate())
                .modifiedDate(notice.getModifiedDate())
                .build();
    }

}
