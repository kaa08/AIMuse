package project.aimuse.dto.request.inquiry;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Inquiry;

@Getter
@Setter
@NoArgsConstructor
public class InquiryWriteDto {

    private String title;
    private String content;
    private String answer;

    @Builder
    public InquiryWriteDto(String title, String content, String answer) {
        this.title = title;
        this.content = content;
        this.answer = answer;
    }

    public static Inquiry ofEntity(InquiryWriteDto dto) {
        return Inquiry.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .answer(dto.getAnswer())
                .build();
    }
}
