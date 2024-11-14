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
import project.aimuse.dto.request.inquiry.InquiryUpdateDto;
import project.aimuse.dto.request.inquiry.InquiryWriteDto;
import project.aimuse.dto.response.board.ResBoardWriteDto;
import project.aimuse.dto.response.inquiry.ResInquiryDetailsDto;
import project.aimuse.dto.response.inquiry.ResInquiryListDto;
import project.aimuse.dto.response.inquiry.ResInquiryWriteDto;
import project.aimuse.entity.Inquiry;
import project.aimuse.entity.Member;
import project.aimuse.repository.InquiryRepository;
import project.aimuse.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;

    // 문의하기 목록 조회
    public Page<ResInquiryListDto> getAllInquiries(Pageable pageable) {
        Page<Inquiry> inquiries = inquiryRepository.findAllWithMember(pageable);
        List<ResInquiryListDto> list = inquiries.getContent().stream()
                .map(ResInquiryListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, inquiries.getTotalElements());
    }
    // 문의하기 검색
    public Page<ResInquiryListDto> search(SearchData searchData, Pageable pageable) {
        Page<Inquiry> result = null;
        if (!searchData.getTitle().isEmpty()) {
            result = inquiryRepository.findAllTitleContaining(searchData.getTitle(), pageable);
        } else if (!searchData.getContent().isEmpty()) {
            result = inquiryRepository.findAllContentContaining(searchData.getContent(), pageable);
        } else if (!searchData.getWriterName().isEmpty()) {
            result = inquiryRepository.findAllUsernameContaining(searchData.getWriterName(), pageable);
        }
        List<ResInquiryListDto> list = result.getContent().stream()
                .map(ResInquiryListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }
    // 문의하기 등록
    public ResInquiryWriteDto write(InquiryWriteDto inquiryDto, Member member) {
        Inquiry inquiry = InquiryWriteDto.ofEntity(inquiryDto);
        Member writeMember = memberRepository.findByUsername(member.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Username", member.getUsername())
        );
        inquiry.setMappingMember(member);
        Inquiry saveInquiry = inquiryRepository.save(inquiry);
        return ResInquiryWriteDto.fromEntity(saveInquiry);
    }
    // 문의하기 글 상세보기
    public ResInquiryDetailsDto detail(Long inquiryId) {
        Inquiry findInquiry = inquiryRepository.findByIdWithMember(inquiryId).orElseThrow(
                () -> new ResourceNotFoundException("Inquiry", "Inquiry Id", String.valueOf(inquiryId))
        );
        return ResInquiryDetailsDto.fromEntity(findInquiry);
    }

    // 문의하기 글 수정
    public ResInquiryDetailsDto update(Long inquiryId, InquiryUpdateDto inquiryDTO) {
        Inquiry updateInquiry = inquiryRepository.findByIdWithMember(inquiryId).orElseThrow(
                () -> new ResourceNotFoundException("Inquiry", "Inquiry Id", String.valueOf(inquiryId))
        );
        updateInquiry.update(inquiryDTO.getTitle(), inquiryDTO.getContent());
        return ResInquiryDetailsDto.fromEntity(updateInquiry);
    }

    // 관리자 페이지 - 문의하기 답변 작성
    public ResInquiryWriteDto writeAnswer(Long inquiryId, InquiryWriteDto inquiryDto, Member member) {
        Inquiry findInquiry = inquiryRepository.findById(inquiryId).orElseThrow(
                () -> new ResourceNotFoundException("Inquiry", "Inquiry Id", String.valueOf(inquiryId))
        );
        findInquiry.writeAnswer(inquiryDto.getAnswer());
        return ResInquiryWriteDto.fromEntity(findInquiry);
    }

    // 마이페이지 - 사용자 별 문의하기 조회
    public Page<ResInquiryListDto> getAllInquiriesByMember(Pageable pageable, Member member) {
        Page<Inquiry> inquiries = inquiryRepository.findAllByMember(member, pageable);
        List<ResInquiryListDto> list = inquiries.getContent().stream()
                .map(ResInquiryListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, inquiries.getTotalElements());
    }

    // 문의하기 삭제
    public void delete(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }
}
