package project.aimuse.dto.request.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request-
 * 로그인 요청 dto
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberLoginDto {

    private String username;
    private String password;

    @Builder
    public MemberLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
