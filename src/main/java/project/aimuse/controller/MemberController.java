package project.aimuse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.aimuse.dto.request.member.MemberLoginDto;
import project.aimuse.dto.request.member.MemberRegisterDto;
import project.aimuse.dto.request.member.MemberUpdateDto;
import project.aimuse.dto.response.member.MemberResponseDto;
import project.aimuse.dto.response.member.MemberTokenDto;
import project.aimuse.entity.Member;
import project.aimuse.service.MemberService;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 사용자 - 회원가입시 사용자 아이디 중복 확인
    @GetMapping("/checkId")
    public ResponseEntity<?> checkIdDuplicate(@RequestParam String username) {
        memberService.checkIdDuplicate(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 사용자 - 회원가입시 사용자 이메일 중복 확인
    @GetMapping("/checkEmail")
    public ResponseEntity<?> checkEmailDuplicate(@RequestParam String email) {
        memberService.checkEmailDuplicate(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 사용자 - 회원가입시 사용자 닉네임 중복 확인
    @GetMapping("/checkNickname")
    public ResponseEntity<?> checkNicknameDuplicate(@RequestParam String nickname) {
        memberService.checkNicknameDuplicate(nickname);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 사용자 - 회원 가입
    @PostMapping("/register")
    public ResponseEntity<MemberResponseDto> register(@RequestBody MemberRegisterDto memberRegisterDTO) {
        MemberResponseDto successMember = memberService.register(memberRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(successMember);
    }

    // 사용자 - 로그인
    @PostMapping("/login")
    public ResponseEntity<MemberTokenDto> login(@RequestBody MemberLoginDto memberLoginDTO) {
        MemberTokenDto loginDTO = memberService.login(memberLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).header(loginDTO.getToken()).body(loginDTO);
    }

    // 사용자 - 로그인 시 비밀번호 일치확인
    @PostMapping("/checkPwd")
    public ResponseEntity<MemberResponseDto> check(
            @AuthenticationPrincipal Member member,
            @RequestBody Map<String, String> request) {
        String password = request.get("password");
        MemberResponseDto memberInfo = memberService.check(member, password);
        return ResponseEntity.status(HttpStatus.OK).body(memberInfo);
    }

    // 사용자 - 사용자 정보 수정
    @PutMapping("/update")
    public ResponseEntity<MemberResponseDto> update(
            @AuthenticationPrincipal Member member,
            @RequestBody MemberUpdateDto memberUpdateDTO) {
        MemberResponseDto memberUpdate = memberService.update(member, memberUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(memberUpdate);
    }
}
