package project.aimuse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.aimuse.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 사용자 조회
    Optional<Member> findByEmail(String email);

    // 아이디로 사용자 조회
    Optional<Member> findByUsername(String username);

    // 닉네임으로 사용자 조회
    Optional<Member> findByNickname(String nickname);

}
