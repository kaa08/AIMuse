package project.aimuse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.aimuse.entity.Inquiry;
import project.aimuse.entity.Member;

import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    // 문희하기 상세 조회
    @Query(value = "SELECT i FROM Inquiry i JOIN FETCH i.member WHERE i.id = :inquiryID")
    Optional<Inquiry> findByIdWithMember(Long inquiryID);

    // 문의하기 전체 조회
    @Query(value = "SELECT i FROM Inquiry i JOIN FETCH i.member")
    Page<Inquiry> findAllWithMember(Pageable pageable);

    // 문의하기 제목 검색
    @Query(value = "SELECT i FROM Inquiry i JOIN FETCH i.member WHERE i.title LIKE %:title%")
    Page<Inquiry> findAllTitleContaining(String title, Pageable pageable);

    // 문의하기 내용 검색
    @Query(value = "SELECT i FROM Inquiry i JOIN FETCH i.member WHERE i.content LIKE %:contents%")
    Page<Inquiry> findAllContentContaining(String content, Pageable pageable);

    // 문의하기 작성자 검색
    @Query(value = "SELECT i FROM Inquiry i JOIN FETCH i.member WHERE i.member.username LIKE %:username%")
    Page<Inquiry> findAllUsernameContaining(String username, Pageable pageable);

    // 사용자 별 문의하기 조회
    Page<Inquiry> findAllByMember(Member member, Pageable pageable);}
