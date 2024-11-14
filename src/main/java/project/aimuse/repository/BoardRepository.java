package project.aimuse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.aimuse.entity.Board;
import project.aimuse.entity.Member;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 게시글 상세 조회, @BatchSize : Comments와 Files는 필요할 때 in 절로 가져옴
    @Query(value = "SELECT b FROM Board b JOIN FETCH b.member WHERE b.id = :boardId")
    Optional<Board> findByIdWithMemberAndComments(Long boardId);

    // 게시글 전체 조회
    @Query(value = "SELECT b FROM Board b JOIN FETCH b.member")
    Page<Board> findAllWithMemberAndComments(Pageable pageable);

    // 게시글 제목 검색
    @Query(value = "SELECT b FROM Board b JOIN FETCH b.member WHERE b.title LIKE %:title%")
    Page<Board> findAllTitleContaining(String title, Pageable pageable);

    // 게시글 내용 검색
    @Query(value = "SELECT b FROM Board b JOIN FETCH b.member WHERE b.content LIKE %:content%")
    Page<Board> findAllContentContaining(String content, Pageable pageable);

    // 게시글 작성자 검색
    @Query(value = "SELECT b FROM Board b JOIN FETCH b.member WHERE b.member.username LIKE %:username%")
    Page<Board> findAllUsernameContaining(String username, Pageable pageable);

    // 사용자 별 게시글 조회
    Page<Board> findAllByMember(Pageable pageable, Member member);
}
