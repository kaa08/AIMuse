package project.aimuse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.aimuse.common.exception.MemberException;
import project.aimuse.common.exception.ResourceNotFoundException;
import project.aimuse.dto.request.member.MemberLoginDto;
import project.aimuse.dto.request.member.MemberRegisterDto;
import project.aimuse.dto.request.member.MemberUpdateDto;
import project.aimuse.dto.response.member.MemberResponseDto;
import project.aimuse.dto.response.member.MemberTokenDto;
import project.aimuse.entity.Member;
import project.aimuse.repository.MemberRepository;
import project.aimuse.security.jwt.CustomUserDetailsService;
import project.aimuse.security.jwt.JwtTokenUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    // 사용자 - 회원가입시 이메일 중복 확인
    public HttpStatus checkIdDuplicate(String username) {
        isExistUsername(username);
        return HttpStatus.OK;
    }

    // 사용자 - 회원가입시 이메일 중복 확인
    public HttpStatus checkEmailDuplicate(String email) {
        isExistUserEmail(email);
        return HttpStatus.OK;
    }

    // 사용자 - 회원가입시 닉네임 중복 확인
    public HttpStatus checkNicknameDuplicate(String nickname) {
        isExistUserNickname(nickname);
        return HttpStatus.OK;
    }

    // 사용자 - 회원가입
    public MemberResponseDto register(MemberRegisterDto registerDto) {
        isExistUsername(registerDto.getUsername());
        isExistUserEmail(registerDto.getEmail());
        isExistUserNickname(registerDto.getNickname());
        checkPassword(registerDto.getPassword(), registerDto.getPasswordCheck());

        // 패스워드 암호화
        String encodePwd = encoder.encode(registerDto.getPassword());
        registerDto.setPassword(encodePwd);

        Member saveMember = memberRepository.save(
                MemberRegisterDto.ofEntity(registerDto));

        return MemberResponseDto.fromEntity(saveMember);
    }

    // 마이페이지 - 사용자 정보 조회
    public MemberResponseDto getMemberInfo(String username) {
        Member findMember = memberRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Username", username)
        );
        return MemberResponseDto.fromEntity(findMember);
    }

    // 사용자 - 로그인
    public MemberTokenDto login(MemberLoginDto loginDto) {
        authenticate(loginDto.getUsername(), loginDto.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());
        checkEncodePassword(loginDto.getPassword(), userDetails.getPassword());
        String token = jwtTokenUtil.generateToken(userDetails);
        return MemberTokenDto.fromEntity(userDetails, token);
    }

    // 사용자 - 로그인시 비밀번호 일치 확인
    public MemberResponseDto check(Member member, String password) {
        Member checkMember = (Member) userDetailsService.loadUserByUsername(member.getUsername());
        checkEncodePassword(password, checkMember.getPassword());
        return MemberResponseDto.fromEntity(checkMember);
    }

    // 마이페이지 - 사용자 정보 수정
    public MemberResponseDto update(Member member, MemberUpdateDto updateDto) {
        checkPassword(updateDto.getPassword(), updateDto.getPasswordCheck());
        String encodePwd = encoder.encode(updateDto.getPassword());
        Member updateMember = memberRepository.findByUsername(member.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Username", member.getUsername())
        );
        updateMember.update(encodePwd, updateDto.getEmail(), updateDto.getName(), updateDto.getNickname(), updateDto.getBirth());
        return MemberResponseDto.fromEntity(updateMember);
    }

    // 관리자 페이지 - 회원 전체 조회
    public Page<MemberResponseDto> getAllMembers(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        List<MemberResponseDto> list = members.getContent().stream()
                .map(MemberResponseDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, members.getTotalElements());
    }

    // 마이페이지, 관리자 페이지 - 회원 탈퇴
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    /**
     * 사용자 인증
     * @param username
     * @param pwd
     */
    private void authenticate(String username, String pwd) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
        } catch (DisabledException e) {
            throw new MemberException("인증되지 않은 아이디입니다.", HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            throw new MemberException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 아이디 중복 체크
     * @param username
     */
    private void isExistUsername(String username) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new MemberException("이미 사용 중인 아이디입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 이메일 중복 체크
     * @param email
     */
    private void isExistUserEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new MemberException("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 닉네임 중복 체크
     * @param nickname
     */
    private void isExistUserNickname(String nickname) {
        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new MemberException("이미 사용 중인 닉네임입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 비밀번호와 비밀번호 확인이 같은지 체크
     * @param password
     * @param passwordCheck
     */
    private void checkPassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new MemberException("패스워드 불일치", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 사용자가 입력한 비번과 DB에 저장된 비번이 같은지 체크 : 인코딩 확인
     * @param rawPassword
     * @param encodedPassword
     */
    private void checkEncodePassword(String rawPassword, String encodedPassword) {
        if (!encoder.matches(rawPassword, encodedPassword)) {
            throw new MemberException("패스워드 불일치", HttpStatus.BAD_REQUEST);
        }
    }
}
