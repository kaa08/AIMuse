package project.aimuse.dto.request.inquiry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InquiryUpdateDto {

    private String title;
    private String content;

    public InquiryUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
