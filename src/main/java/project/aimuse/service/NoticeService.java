package project.aimuse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.aimuse.common.exception.ResourceNotFoundException;
import project.aimuse.dto.request.board.SearchData;
import project.aimuse.dto.request.notice.NoticeUpdateDto;
import project.aimuse.dto.request.notice.NoticeWriteDto;
import project.aimuse.dto.response.notice.ResNoticeDetailsDto;
import project.aimuse.dto.response.notice.ResNoticeListDto;
import project.aimuse.dto.response.notice.ResNoticeWriteDto;
import project.aimuse.entity.Member;
import project.aimuse.entity.Notice;
import project.aimuse.repository.MemberRepository;
import project.aimuse.repository.NoticeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    // 공지사항 - 공지사항 전체 조회
    public Page<ResNoticeListDto> getAllNotices(Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllWithMember(pageable);
        List<ResNoticeListDto> list = notices.getContent().stream()
                .map(ResNoticeListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, notices.getTotalElements());
    }

    // 공지사항 - 공지사항 검색
    public Page<ResNoticeListDto> search(SearchData searchData, Pageable pageable) {
        Page<Notice> result = null;
        if (!searchData.getTitle().isEmpty()) {
            result = noticeRepository.findAllTitleContaining(searchData.getTitle(), pageable);
        } else if (!searchData.getContent().isEmpty()) {
            result = noticeRepository.findAllContentContaining(searchData.getContent(), pageable);
        } else if (!searchData.getWriterName().isEmpty()) {
            result = noticeRepository.findAllUsernameContaining(searchData.getWriterName(), pageable);
        }
        List<ResNoticeListDto> list = result.getContent().stream()
                .map(ResNoticeListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    //관리자 페이지 - 공지사항 작성
    public ResNoticeWriteDto write(NoticeWriteDto noticeDto, Member member) {

        Notice notice = NoticeWriteDto.ofEntity(noticeDto);
        Member findMember = memberRepository.findByUsername(member.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Username", member.getUsername())
        );
        notice.setMappingMember(findMember);
        Notice saveNotice = noticeRepository.save(notice);
        return ResNoticeWriteDto.fromEntity(saveNotice);
    }

    // 공지사항 - 공지사항 조회
    public ResNoticeDetailsDto detail(Long noticeId) {
        Notice findNotice = noticeRepository.findByIdWithMember(noticeId).orElseThrow(
                () -> new ResourceNotFoundException("Notice", "Notice Id", String.valueOf(noticeId))
        );
        // 조회수 증가
        findNotice.upViewCount();
        return ResNoticeDetailsDto.fromEntity(findNotice);
    }

    // 공지사항 - 공지사항 수정
    public ResNoticeDetailsDto update(Long noticeId, NoticeUpdateDto noticeDTO) {
        Notice updateNotice =noticeRepository.findByIdWithMember(noticeId).orElseThrow(
                () -> new ResourceNotFoundException("Notice", "Notice Id", String.valueOf(noticeId))
        );
        updateNotice.update(noticeDTO.getTitle(), noticeDTO.getContent());
        return ResNoticeDetailsDto.fromEntity(updateNotice);
    }

    // 공지사항 - 공지사항 삭제
    public void delete(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

}
