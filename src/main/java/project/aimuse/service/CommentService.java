package project.aimuse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.aimuse.common.exception.ResourceNotFoundException;
import project.aimuse.dto.request.comment.CommentDto;
import project.aimuse.dto.response.comment.ResCommentDto;
import project.aimuse.entity.Board;
import project.aimuse.entity.Comment;
import project.aimuse.entity.Member;
import project.aimuse.repository.BoardRepository;
import project.aimuse.repository.CommentRepository;
import project.aimuse.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    //커뮤니티 - 커뮤니티 댓글 목록 조회
    public Page<ResCommentDto> getAllComments(Pageable pageable, Long boardId) {
        Page<Comment> comments = commentRepository.findAllWithMemberAndBoard(pageable, boardId);
        List<ResCommentDto> commentList = comments.getContent().stream()
                .map(ResCommentDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(commentList, pageable, comments.getTotalElements());
    }

    //커뮤니티 댓글 작성
    public ResCommentDto write(Long boardId, Member member, CommentDto commentDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResourceNotFoundException("Board", "Board Id", String.valueOf(boardId))
        );
        memberRepository.findByUsername(member.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Username", member.getUsername())
        );
        Comment comment = CommentDto.ofEntity(commentDto);
        comment.setMappingBoard(board);
        comment.setMappingMember(member);

        Comment saveComment = commentRepository.save(comment);
        return ResCommentDto.fromEntity(saveComment);
    }

    // 커뮤니티 댓글 수정
    public ResCommentDto update(Long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findByIdWithMemberAndBoard(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "Comment Id", String.valueOf(commentId))
        );
        comment.update(commentDto.getContent());
        return ResCommentDto.fromEntity(comment);
    }

    // 마이페이지 - 사용자 별 커뮤니티 댓글 조회
    public Page<ResCommentDto> getCommentsByMember(Pageable pageable, Member member) {
        Page<Comment> comments = commentRepository.findAllByMember(member, pageable);
        List<ResCommentDto> list = comments.getContent().stream()
                .map(ResCommentDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, comments.getTotalElements());
    }

    // 커뮤니티 댓글 삭제
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
