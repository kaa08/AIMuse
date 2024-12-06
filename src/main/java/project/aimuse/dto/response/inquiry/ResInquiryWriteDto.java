package project.aimuse.dto.response.inquiry;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Inquiry;

@Getter
@Setter
@NoArgsConstructor
public class ResInquiryWriteDto {

    private Long inquiryId;
    private String title;
    private String content;
    private String answer;
    private String writerName;
    private String writerNickname;
    private String createdDate;

    @Builder
    public ResInquiryWriteDto(Long inquiryId, String title, String content, String answer, String writerName, String writerNickname, String createdDate) {
        this.inquiryId = inquiryId;
        this.title = title;
        this.content = content;
        this.answer = answer;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.createdDate = createdDate;
    }

    public static ResInquiryWriteDto fromEntity(Inquiry inquiry) {
        return ResInquiryWriteDto.builder()
                .inquiryId(inquiry.getId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .answer(inquiry.getAnswer())
                .writerName(inquiry.getMember().getUsername())
                .writerNickname(inquiry.getMember().getNickname())
                .createdDate(inquiry.getCreatedDate())
                .build();
    }
}
