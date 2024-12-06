package project.aimuse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.aimuse.common.exception.ResourceNotFoundException;
import project.aimuse.dto.request.board.BoardUpdateDto;
import project.aimuse.dto.request.board.BoardWriteDto;
import project.aimuse.dto.request.board.SearchData;
import project.aimuse.dto.response.board.ResBoardDetailsDto;
import project.aimuse.dto.response.board.ResBoardListDto;
import project.aimuse.dto.response.board.ResBoardWriteDto;
import project.aimuse.entity.Board;
import project.aimuse.entity.Member;
import project.aimuse.repository.BoardRepository;
import project.aimuse.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 커뮤니티 - 커뮤니티 게시글 목록 조회
    public Page<ResBoardListDto> getAllBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findAllWithMemberAndComments(pageable);
        List<ResBoardListDto> list = boards.getContent().stream()
                .map(ResBoardListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, boards.getTotalElements());
    }

    // 커뮤니티 - 커뮤니티 게시글 검색

    public Page<ResBoardListDto> search(SearchData searchData, Pageable pageable) {
        Page<Board> result = null;
        if (!searchData.getTitle().isEmpty()) {
            result = boardRepository.findAllTitleContaining(searchData.getTitle(), pageable);
        } else if(!searchData.getContent().isEmpty()) {
            result = boardRepository.findAllContentContaining(searchData.getContent(), pageable);
        } else if(!searchData.getWriterName().isEmpty()) {
            result = boardRepository.findAllUsernameContaining(searchData.getWriterName(), pageable);
        }
        List<ResBoardListDto> list = result.getContent().stream()
                .map(ResBoardListDto::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    // 커뮤니티 - 커뮤니티 게시글 등록
    public ResBoardWriteDto write(BoardWriteDto dto, Member member) {
        Board board = BoardWriteDto.ofEntity(dto);
        Member writeMember = memberRepository.findByUsername(member.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Username", member.getUsername())
        );
        board.upViewCount();
        board.setMappingMember(member);
        boardRepository.save(board);
        return ResBoardWriteDto.fromEntity(board);
    }

    // 커뮤니티 - 커뮤니티 게시글 조회
    public ResBoardDetailsDto detail(Long boardId) {
        Board findBoard = boardRepository.findByIdWithMemberAndComments(boardId).orElseThrow(
                () -> new ResourceNotFoundException("Board", "Board ID", String.valueOf(boardId))
        );
        findBoard.upViewCount();
        return ResBoardDetailsDto.fromEntity(findBoard);
    }
    // 커뮤니티 - 게시글 수정
    public ResBoardDetailsDto update(Long boardId, BoardUpdateDto dto) {
        Board updateBoard = boardRepository.findByIdWithMemberAndComments(boardId).orElseThrow(
                () -> new ResourceNotFoundException("Board", "Board ID", String.valueOf(boardId))
        );
        updateBoard.update(dto.getTitle(), dto.getContent());
        return ResBoardDetailsDto.fromEntity(updateBoard);
    }
    // 커뮤니티 - 게시글 삭제
    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }
    // 마이페이지 - 사용자별 커뮤니티 게시글 조회
    public Page<ResBoardListDto> getAllBoardsByMember(Pageable pageable, Member member) {
        Page<Board> boards = boardRepository.findAllByMember(pageable, member);
        List<ResBoardListDto> list = boards.getContent().stream()
                .map(ResBoardListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, boards.getTotalElements());
    }
}
