package project.aimuse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.aimuse.dto.request.comment.CommentDto;
import project.aimuse.dto.response.comment.ResCommentDto;
import project.aimuse.entity.Member;
import project.aimuse.service.CommentService;

@RestController
@RequestMapping("/api/board/{boardId}/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    // 커뮤니티 댓글 목록 조회 페이징
    @GetMapping("/list")
    public ResponseEntity<Page<ResCommentDto>> getComments(
            @PathVariable Long boardId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<ResCommentDto> commentList = commentService.getAllComments(pageable, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(commentList);
    }

    // 커뮤니티 댓글 작성
    @PostMapping("/write")
    public ResponseEntity<ResCommentDto> write(
            @AuthenticationPrincipal Member member,
            @PathVariable Long boardId,
            @RequestBody CommentDto commentDto
    ) {
        ResCommentDto dto = commentService.write(boardId, member, commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // 커뮤니티 댓글 수정
    @PatchMapping("/{commentId}/update")
    public ResponseEntity<ResCommentDto> update(
            @PathVariable Long commentId,
            @RequestBody CommentDto commentDto
    ) {
        ResCommentDto dto = commentService.update(commentId, commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    // 커뮤니티 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResCommentDto> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
