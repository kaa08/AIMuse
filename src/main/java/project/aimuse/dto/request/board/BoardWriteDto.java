package project.aimuse.dto.request.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Board;

@Getter
@Setter
@NoArgsConstructor
public class BoardWriteDto {
    private String title;
    private String content;

    @Builder
    public BoardWriteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public static Board ofEntity(BoardWriteDto dto) {
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }
}
