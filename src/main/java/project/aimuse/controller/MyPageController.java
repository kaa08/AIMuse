package project.aimuse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import project.aimuse.dto.request.member.MemberUpdateDto;
import project.aimuse.dto.response.board.ResBoardListDto;
import project.aimuse.dto.response.comment.ResCommentDto;
import project.aimuse.dto.response.inquiry.ResInquiryListDto;
import project.aimuse.dto.response.member.MemberResponseDto;
import project.aimuse.dto.response.music.ResMusicListDto;
import project.aimuse.dto.response.review.ResReviewListDto;
import project.aimuse.entity.Member;
import project.aimuse.service.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final InquiryService inquiryService;
    private final MemberService memberService;
    private final MusicService musicService;
    private final ReviewService reviewService;

    // 마이페이지 - 사용자가 쓴 커뮤니티 게시글 전체 조회, 페이징 처리
    @GetMapping("/board/list")
    public ResponseEntity<Page<ResBoardListDto>> boardList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Member member) {
        Page<ResBoardListDto> list = boardService.getAllBoardsByMember(pageable, member);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 마이페이지 - 사용자가 쓴 커뮤니티 댓글 전체 조회, 페이징 처리
    @GetMapping("/comment/list")
    public ResponseEntity<Page<ResCommentDto>> commentList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Member member
    ) {
        Page<ResCommentDto> list = commentService.getCommentsByMember(pageable, member);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 마이페이지 - 사용자가 쓴 문의하기 글 전체 조회, 페이징 처리
    @GetMapping("/inquiry/list")
    public ResponseEntity<Page<ResInquiryListDto>> inquiryList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Member member
    ) {
        Page<ResInquiryListDto> list = inquiryService.getAllInquiriesByMember(pageable, member);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 마이페이지 - 사용자가 쓴 리뷰 글 전체 조회, 페이징 처리
    @GetMapping("/review/list")
    public ResponseEntity<Page<ResReviewListDto>> reviewList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Member member
    ) {
        Page<ResReviewListDto> list = reviewService.getAllReviewsByMember(pageable, member);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/music/list")
    public ResponseEntity<Page<ResMusicListDto>> musicList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Member member) {
        Page<ResMusicListDto> list = musicService.getAllMusicByMember(pageable, member);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 마이페이지 - 사용자 정보 조회
    @GetMapping("/user/profile")
    public ResponseEntity<MemberResponseDto> getMember(
            @AuthenticationPrincipal Member member) {
        MemberResponseDto memberInfo = memberService.getMemberInfo(member.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(memberInfo);
    }

    // 마이페이지 - 사용자 정보 수정
    @PatchMapping("/user/profile/update")
    public ResponseEntity<MemberResponseDto> update(
            @AuthenticationPrincipal Member member,
            @RequestBody MemberUpdateDto memberUpdateDto) {
        MemberResponseDto updateMember = memberService.update(member, memberUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateMember);
    }

    // 마이페이지 - 사용자가 작성한 커뮤니티 게시글 삭제
    @DeleteMapping("/board/list/{boardId}")
    public ResponseEntity<Long> deleteBoard(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 마이페이지 - 사용자가 작성한 커뮤니티 댓글 삭제
    @DeleteMapping("/comment/list/{commentId}")
    public ResponseEntity<Long> deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 마이페이지 - 사용자가 작성한 문의글 삭제
    @DeleteMapping("/inquiry/list/{inquiryId}")
    public ResponseEntity<Long> deleteInquiry(@PathVariable Long inquiryId) {
        inquiryService.delete(inquiryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/review/list/{reviewId}")
    public ResponseEntity<Long> deleteReview(@PathVariable Long reviewId) {
        reviewService.delete(reviewId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 마이페이지 - 사용자 탈퇴
    @DeleteMapping("/user/profile/{memberId}")
    public ResponseEntity<Long> deleteMember(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 마이페이지 - 음악 기록 삭제
    @DeleteMapping("/music/{musicId}")
    public ResponseEntity<Long> deleteMusic(@PathVariable Long musicId) {
        musicService.delete(musicId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
