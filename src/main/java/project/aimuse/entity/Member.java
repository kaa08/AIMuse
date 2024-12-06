package project.aimuse.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.aimuse.common.BaseTimeEntity;
import project.aimuse.common.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    // 이메일로 로그인함
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // 아이디
    @Column(nullable = false)
    private String username;

    // 이름
    @Column(nullable = false)
    private String name;

    // 닉네임
    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role roles;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate birth;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inquiry> inquiries = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Music> musics = new ArrayList<>();

    // 생성자 Builder
    @Builder
    public Member(String email, String password, String username, String name, String nickname, Role roles, LocalDate birth) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.name = name;
        this.nickname = nickname;
        this.roles = roles;
        this.birth = birth;
    }

    //== update ==//
    public void update(String password, String email, String name, String nickname, LocalDate birth) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
    }

    //========== Jwt UserDetails Implements ==========//

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority("ROLE_" + this.roles.name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
