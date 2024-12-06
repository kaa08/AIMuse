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
import project.aimuse.dto.request.review.ReviewUpdateDto;
import project.aimuse.dto.request.review.ReviewWriteDto;
import project.aimuse.dto.response.review.ResReviewDetailsDto;
import project.aimuse.dto.response.review.ResReviewListDto;
import project.aimuse.dto.response.review.ResReviewWriteDto;
import project.aimuse.entity.Member;
import project.aimuse.service.ReviewService;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    //후기글 목록 조회
    @GetMapping("/list")
    public ResponseEntity<Page<ResReviewListDto>> getAllReviews(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResReviewListDto> listDto = reviewService.getAllReviews(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    //후기글 검색
    @GetMapping("/search")
    public ResponseEntity<Page<ResReviewListDto>> search(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String writerName) {
        SearchData searchData = SearchData.createdSearchData(title, content, writerName);
        Page<ResReviewListDto> searchBoard = reviewService.search(searchData, pageable);
        return  ResponseEntity.status(HttpStatus.OK).body(searchBoard);
    }

    //후기글 조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ResReviewDetailsDto> details(@PathVariable Long reviewId) {
        ResReviewDetailsDto dto = reviewService.detail(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    //후기글 작성
    @PostMapping("/write")
    public ResponseEntity<ResReviewWriteDto> write(
            @RequestBody ReviewWriteDto writeDto,
            @AuthenticationPrincipal Member member
    ) {
        Thread currentThread = Thread.currentThread();
        log.info("현재 실행 중인 쓰레드: {}", currentThread.getName());
        ResReviewWriteDto dto  = reviewService.write(writeDto, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    //후기글 업데이트
    @PatchMapping("/{reviewId}/update")
    public ResponseEntity<ResReviewDetailsDto> update(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateDto updateDto
    ) {
        ResReviewDetailsDto dto = reviewService.update(reviewId, updateDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    //후기글 삭제
    @DeleteMapping("/{reviewID}")
    public ResponseEntity<Long> delete(@PathVariable Long reviewID) {
        reviewService.delete(reviewID);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
