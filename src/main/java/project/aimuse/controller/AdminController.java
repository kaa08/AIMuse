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
import org.springframework.web.bind.annotation.*;
import project.aimuse.dto.request.inquiry.InquiryWriteDto;
import project.aimuse.dto.request.notice.NoticeUpdateDto;
import project.aimuse.dto.request.notice.NoticeWriteDto;
import project.aimuse.dto.response.board.ResBoardListDto;
import project.aimuse.dto.response.inquiry.ResInquiryListDto;
import project.aimuse.dto.response.inquiry.ResInquiryWriteDto;
import project.aimuse.dto.response.member.MemberResponseDto;
import project.aimuse.dto.response.notice.ResNoticeDetailsDto;
import project.aimuse.dto.response.notice.ResNoticeListDto;
import project.aimuse.dto.response.notice.ResNoticeWriteDto;
import project.aimuse.entity.Member;
import project.aimuse.service.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final InquiryService inquiryService;
    private final NoticeService noticeService;
    private final CommentService commentService;

    // 관리자 페이지 - 전체 사용자 조회
    @GetMapping("/user/list")
    public ResponseEntity<Page<MemberResponseDto>> getAllMembers(Pageable pageable) {
        Page<MemberResponseDto> members = memberService.getAllMembers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(members);
    }

    // 관리자 페이지 - 사용자 삭제
    @DeleteMapping("/user/list/{id}")
    public ResponseEntity<MemberResponseDto> deleteMember(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 관리자 페이지 - 커뮤니티 게시판 조회
    @GetMapping("/board/list")
    public ResponseEntity<Page<ResBoardListDto>> boardList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResBoardListDto> list = boardService.getAllBoards(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 관리자 페이지 - 커뮤니티 게시판 글 삭제
    @DeleteMapping("/board/list/{id}")
    public ResponseEntity<ResBoardListDto> deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 관리자 페이지 - 문의사항 게시판 조회
    @GetMapping("/inquiry/list")
    public ResponseEntity<Page<ResInquiryListDto>> inquiryList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResInquiryListDto> list = inquiryService.getAllInquiries(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 관리자 페이지 - 문의사항 답변
    @PatchMapping("/inquiry/{id}/answer")
    public ResponseEntity<ResInquiryWriteDto> writeAnswer(
            @RequestBody InquiryWriteDto inquiryDto,
            @PathVariable Long id,
            @AuthenticationPrincipal Member member) {
        Thread currentThread = Thread.currentThread();
        log.info("현재 실행중인 스레드: {}", currentThread.getName());
        ResInquiryWriteDto saveInquiryDto = inquiryService.writeAnswer(id, inquiryDto, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveInquiryDto);
    }

    // 관리자 페이지 - 공지사항 게시판 조회
    @GetMapping("/notice/list")
    public ResponseEntity<Page<ResNoticeListDto>> noticeList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResNoticeListDto> list = noticeService.getAllNotices(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 관리자 페이지 - 공지사항 게시판 글 쓰기
    @PostMapping("/notice/write")
    public ResponseEntity<ResNoticeWriteDto> write(
            @RequestBody NoticeWriteDto noticeDto,
            @AuthenticationPrincipal Member member) {
        Thread currentThread = Thread.currentThread();
        log.info("현재 실행 중인 스레드: " + currentThread.getName());
        ResNoticeWriteDto saveNoticeDto = noticeService.write(noticeDto, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveNoticeDto);
    }

    // 관리자 페이지 - 공지사항 게시판 글 수정
    @PatchMapping("/notice/{noticeId}/update")
    public ResponseEntity<ResNoticeDetailsDto> update(
            @PathVariable Long noticeId,
            @RequestBody NoticeUpdateDto noticeDto) {
        ResNoticeDetailsDto updateNoticeDto = noticeService.update(noticeId, noticeDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateNoticeDto);
    }

    // 관리자 페이지 - 공지사항 게시판 글 삭제
    @DeleteMapping("/notice/delete/{id}")
    public ResponseEntity<ResNoticeListDto> deleteNotice(@PathVariable Long id) {
        noticeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}