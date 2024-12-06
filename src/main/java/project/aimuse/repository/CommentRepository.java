package project.aimuse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.aimuse.entity.Comment;
import project.aimuse.entity.Member;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글 전체 조회
    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.member JOIN FETCH c.board b WHERE b.id = :boardId")
    Page<Comment> findAllWithMemberAndBoard(Pageable pageable, Long boardId);

    // 댓글 조회
    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.member m JOIN FETCH c.board b WHERE c.id = :commentId")
    Optional<Comment> findByIdWithMemberAndBoard(Long commentId);

    // 사용자 별 댓글 조회
    Page<Comment> findAllByMember(Member member, Pageable pageable);
}
