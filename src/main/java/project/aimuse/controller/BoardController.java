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
import project.aimuse.dto.request.board.BoardUpdateDto;
import project.aimuse.dto.request.board.BoardWriteDto;
import project.aimuse.dto.request.board.SearchData;
import project.aimuse.dto.response.board.ResBoardDetailsDto;
import project.aimuse.dto.response.board.ResBoardListDto;
import project.aimuse.dto.response.board.ResBoardWriteDto;
import project.aimuse.entity.Member;
import project.aimuse.service.BoardService;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    //커뮤니티 게시물 목록 조회
    @GetMapping("/list")
    public ResponseEntity<Page<ResBoardListDto>> getAllBoards(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResBoardListDto> listDto = boardService.getAllBoards(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    //커뮤니티 게시물 검색
    @GetMapping("/search")
    public ResponseEntity<Page<ResBoardListDto>> search(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String writerName) {
        SearchData searchData = SearchData.createdSearchData(title, content, writerName);
        Page<ResBoardListDto> searchBoard = boardService.search(searchData, pageable);
        return  ResponseEntity.status(HttpStatus.OK).body(searchBoard);
    }

    //커뮤니티 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<ResBoardDetailsDto> details(@PathVariable Long boardId) {
        ResBoardDetailsDto dto = boardService.detail(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    //커뮤니티 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<ResBoardWriteDto> write(
            @RequestBody BoardWriteDto writeDto,
            @AuthenticationPrincipal Member member
    ) {
        Thread currentThread = Thread.currentThread();
        log.info("현재 실행 중인 쓰레드: {}", currentThread.getName());
        ResBoardWriteDto dto  = boardService.write(writeDto, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    //커뮤니티 게시글 업데이트
    @PatchMapping("/{boardId}/update")
    public ResponseEntity<ResBoardDetailsDto> update(
            @PathVariable Long boardId,
            @RequestBody BoardUpdateDto updateDto
    ) {
        ResBoardDetailsDto dto = boardService.update(boardId, updateDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Long> delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
        
}
