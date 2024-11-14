package project.aimuse.dto.request.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * -Request-
 * 회원 정보 변경 요청 dto
 */

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateDto {
    private String password;
    private String passwordCheck;
    private String email;
    private String name;
    private String nickname;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate birth;

    @Builder
    public MemberUpdateDto(String password, String passwordCheck, String email, String name, String nickname, LocalDate birth) {
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
    }
}
