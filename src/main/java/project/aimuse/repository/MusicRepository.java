package project.aimuse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.aimuse.entity.Member;
import project.aimuse.entity.Music;

public interface MusicRepository extends JpaRepository<Music, Long> {

    Page<Music> findAllByMember(Member member, Pageable pageable);
}
