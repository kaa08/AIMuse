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
import project.aimuse.dto.request.board.SearchData;
import project.aimuse.dto.request.inquiry.InquiryUpdateDto;
import project.aimuse.dto.request.inquiry.InquiryWriteDto;
import project.aimuse.dto.response.inquiry.ResInquiryDetailsDto;
import project.aimuse.dto.response.inquiry.ResInquiryListDto;
import project.aimuse.dto.response.inquiry.ResInquiryWriteDto;
import project.aimuse.entity.Member;
import project.aimuse.service.InquiryService;

@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
@Slf4j
public class InquiryController {

    private final InquiryService inquiryService;

    // 문의하기 목록 조회
    @GetMapping("/list")
    public ResponseEntity<Page<ResInquiryListDto>> getAllInquiry(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<ResInquiryListDto> list = inquiryService.getAllInquiries(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 문의하기 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<Page<ResInquiryListDto>> search(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String writerName) {
        SearchData searchData = SearchData.createdSearchData(title, content, writerName);
        Page<ResInquiryListDto> searchInquiry = inquiryService.search(searchData, pageable);
        return  ResponseEntity.status(HttpStatus.OK).body(searchInquiry);
    }

    // 문의하기 글 작성
    @PostMapping("/write")
    public ResponseEntity<ResInquiryWriteDto> write(
            @RequestBody InquiryWriteDto inquiryDto,
            @AuthenticationPrincipal Member member
            ) {
        Thread currentThread = Thread.currentThread();
        log.info("현재 실행 중인 스레드: {}", currentThread.getName());
        ResInquiryWriteDto saveInquiryDto = inquiryService.write(inquiryDto, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveInquiryDto);
    }

    // 문의하기 글 조회
    @GetMapping("/{inquiryId}")
    public ResponseEntity<ResInquiryDetailsDto> detail(@PathVariable Long inquiryId) {
        ResInquiryDetailsDto findInquiryDto = inquiryService.detail(inquiryId);
        return ResponseEntity.status(HttpStatus.OK).body(findInquiryDto);
    }

    // 문의하기 글 수정
    @PatchMapping("/{inquiryId}/update")
    public ResponseEntity<ResInquiryDetailsDto> update(
            @PathVariable Long inquiryId,
            @RequestBody InquiryUpdateDto inquiryDto) {
        ResInquiryDetailsDto dto = inquiryService.update(inquiryId, inquiryDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    // 문의하기 삭제
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<Long> delete(@PathVariable Long inquiryId) {
        inquiryService.delete(inquiryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
