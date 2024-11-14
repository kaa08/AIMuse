package project.aimuse.dto.response.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Comment;

@Getter
@Setter
@NoArgsConstructor
public class ResCommentDto {

    //commentId를 반환해야하나?
    private String content;
    private String writerName;
    private String writerNickname;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public ResCommentDto(String content, String writerName, String writerNickname, String createdDate, String modifiedDate) {
        this.content = content;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static ResCommentDto fromEntity(Comment comment) {
        return ResCommentDto.builder()
                .content(comment.getContent())
                .writerName(comment.getMember().getUsername())
                .writerNickname(comment.getMember().getNickname())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .build();
    }
}
