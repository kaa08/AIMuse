package project.aimuse.dto.response.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import project.aimuse.entity.Member;

import java.time.LocalDate;

/**
 * -Response-
 * 사용자 정보 반환 Dto
 */

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDto {
    // 사용자 DB 인덱스 값을 굳이 사용자에게 노출시킬 필요는 없다고 생각
    private Long memberId;
    private String email;
    private String username;
    private String password;
    private String passwordCheck;
    private String name;
    private String nickname;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate birth;

    @Builder
    public MemberResponseDto(Long memberId, String email, String username, String password, String passwordCheck, String name, String nickname, LocalDate birth) {
        this.memberId = memberId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
    }

    // Entity -> DTO
    public static MemberResponseDto fromEntity(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .username(member.getUsername())
                .name(member.getName())
                .nickname(member.getNickname())
                .birth(member.getBirth())
                .build();
    }

}
