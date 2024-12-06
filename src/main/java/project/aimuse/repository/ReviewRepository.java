package project.aimuse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.aimuse.entity.Member;
import project.aimuse.entity.Review;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 게시글 상세 조회, @BatchSize : Comments와 Files는 필요할 때 in 절로 가져옴
    @Query(value = "SELECT r FROM Review r JOIN FETCH r.member WHERE r.id = :reviewId")
    Optional<Review> findByIdWithMember(Long reviewId);

    // 게시글 전체 조회
    @Query(value = "SELECT r FROM Review r JOIN FETCH r.member")
    Page<Review> findAllWithMember(Pageable pageable);

    // 게시글 제목 검색
    @Query(value = "SELECT r FROM Review r JOIN FETCH r.member WHERE r.title LIKE %:title%")
    Page<Review> findAllTitleContaining(String title, Pageable pageable);

    // 게시글 내용 검색
    @Query(value = "SELECT r FROM Review r JOIN FETCH r.member WHERE r.content LIKE %:content%")
    Page<Review> findAllContentContaining(String content, Pageable pageable);

    // 게시글 작성자 검색
    @Query(value = "SELECT r FROM Review r JOIN FETCH r.member WHERE r.member.username LIKE %:username%")
    Page<Review> findAllUsernameContaining(String username, Pageable pageable);

    // 사용자 별 게시글 조회
    Page<Review> findAllByMember(Pageable pageable, Member member);
}
