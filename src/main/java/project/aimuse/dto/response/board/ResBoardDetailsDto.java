package project.aimuse.dto.response.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.dto.response.comment.ResCommentDto;
import project.aimuse.entity.Board;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ResBoardDetailsDto {

    private Long boardId;
    private String title;
    private String content;
    private Integer viewCount;
    private String writerName;
    private String writerNickname;
    private String createDate;
    private String modifiedDate;

    //comments
    private List<ResCommentDto> comments;

    @Builder
    public ResBoardDetailsDto(Long boardId, String title, String content, Integer viewCount, String writerName, String writerNickname, String createDate, String modifiedDate, List<ResCommentDto> comments) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.writerName = writerName;
        this.writerNickname = writerNickname;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.comments = comments;
    }

    public static ResBoardDetailsDto fromEntity(Board board) {
        return ResBoardDetailsDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .viewCount(board.getViewCount())
                .writerName(board.getMember().getUsername())
                .writerNickname(board.getMember().getNickname())
                .createDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .comments(board.getComments().stream()
                        .map(ResCommentDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
