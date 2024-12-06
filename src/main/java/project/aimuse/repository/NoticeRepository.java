package project.aimuse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.aimuse.entity.Notice;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n JOIN FETCH n.member WHERE n.id = :noticeId")
    Optional<Notice> findByIdWithMember(Long noticeId);

    @Query("SELECT n FROM Notice n JOIN FETCH n.member")
    Page<Notice> findAllWithMember(Pageable pageable);

    @Query("SELECT n FROM Notice n JOIN FETCH n.member WHERE n.title LIKE %:title")
    Page<Notice> findAllTitleContaining(String title, Pageable pageable);

    @Query("SELECT n FROM Notice n JOIN FETCH n.member WHERE n.content Like %:content")
    Page<Notice> findAllContentContaining(String content, Pageable pageable);

    @Query("SELECT n FROM Notice n JOIN FETCH n.member WHERE n.member.username Like %:username")
    Page<Notice> findAllUsernameContaining(String username, Pageable pageable);
}
