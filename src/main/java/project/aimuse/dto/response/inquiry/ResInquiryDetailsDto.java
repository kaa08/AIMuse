package project.aimuse.dto.response.inquiry;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Inquiry;
import project.aimuse.entity.Member;

@Getter
@Setter
@NoArgsConstructor
public class ResInquiryDetailsDto {

    private Long inquiryId;
    private String title;
    private String content;
    private String writerName;
    private String writerNickname;
    private boolean replied;
    private String answer;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public ResInquiryDetailsDto(Long inquiryId, String title, String content, String writerName, String writerNickname, boolean replied, String answer, String createdDate, String modifiedDate) {
        this.inquiryId = inquiryId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.replied = replied;
        this.answer = answer;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static ResInquiryDetailsDto fromEntity(Inquiry inquiry) {
        return ResInquiryDetailsDto.builder()
                .inquiryId(inquiry.getId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .writerName(inquiry.getMember().getUsername())
                .writerNickname(inquiry.getMember().getNickname())
                .replied(inquiry.getReplied())
                .answer(inquiry.getAnswer())
                .createdDate(inquiry.getCreatedDate())
                .modifiedDate(inquiry.getCreatedDate())
                .build();
    }
}
