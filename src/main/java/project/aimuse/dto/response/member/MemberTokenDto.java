package project.aimuse.dto.response.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * -Response-
 * 사용자 정보 반환 + token Dto
 */

@Getter
@Setter
@NoArgsConstructor
public class MemberTokenDto {
    private String username;

    private String token;

    @Builder
    public MemberTokenDto(String username, String token) {
        this.username = username;
        this.token = token;
    }

    // Entity -> DTO
    public static MemberTokenDto fromEntity(UserDetails member, String token) {
        return MemberTokenDto.builder()
                .username(member.getUsername())
                .token(token)
                .build();
    }
}
