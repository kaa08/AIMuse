package project.aimuse.dto.response.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Board;

@Getter
@Setter
@NoArgsConstructor
public class ResBoardListDto {

    private Long boardId;
    private String title;
    private String content;
    private String writerName;
    private String writerNickname;
    private Integer viewCount;
    private String createDate;
    private String modifiedDate;

    @Builder
    public ResBoardListDto(Long boardId, String title, String content, String writerName, String writerNickname, Integer viewCount, String createDate, String modifiedDate) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.viewCount = viewCount;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

    public static ResBoardListDto fromEntity(Board board) {
        return ResBoardListDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerName(board.getMember().getUsername())
                .writerNickname(board.getMember().getNickname())
                .viewCount(board.getViewCount())
                .createDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();
    }
}
