package project.aimuse.dto.request.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import project.aimuse.common.Role;
import project.aimuse.entity.Member;

import java.time.LocalDate;

/**
 * -Request-
 * 회원 가입 요청 dto
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberRegisterDto {

    private String username;
    private String password;
    private String passwordCheck;
    private String email;
    private String name;
    private String nickname;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate birth;

    @Builder
    public MemberRegisterDto(String email, String password, String passwordCheck, String username, String name, String nickname, LocalDate birth) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.username = username;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
    }

    // DTO -> Entity
    public static Member ofEntity(MemberRegisterDto dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .birth(dto.getBirth())
                .roles(Role.USER)
                .build();
    }
}
