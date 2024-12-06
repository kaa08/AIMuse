package project.aimuse.dto.response.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Board;
import project.aimuse.entity.Review;

@Getter
@Setter
@NoArgsConstructor
public class ResBoardWriteDto {

    private Long boardId;
    private String title;
    private String content;
    private String writerName;
    private String writerNickname;
    private String createdDate;

    @Builder
    public ResBoardWriteDto(Long boardId, String title, String content, String writerName, String writerNickname, String createdDate) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.createdDate = createdDate;
    }

    public static ResBoardWriteDto fromEntity(Board board) {
        return ResBoardWriteDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerName(board.getMember().getUsername())
                .writerNickname(board.getMember().getNickname())
                .createdDate(board.getCreatedDate())
                .build();
    }
}
