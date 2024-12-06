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
import project.aimuse.dto.request.review.ReviewUpdateDto;
import project.aimuse.dto.request.review.ReviewWriteDto;
import project.aimuse.dto.response.review.ResReviewDetailsDto;
import project.aimuse.dto.response.review.ResReviewListDto;
import project.aimuse.dto.response.review.ResReviewWriteDto;
import project.aimuse.entity.Member;
import project.aimuse.entity.Review;
import project.aimuse.repository.MemberRepository;
import project.aimuse.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    // 커뮤니티 - 커뮤니티 게시글 목록 조회
    public Page<ResReviewListDto> getAllReviews(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllWithMember(pageable);
        List<ResReviewListDto> list = reviews.getContent().stream()
                .map(ResReviewListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, reviews.getTotalElements());
    }

    // 커뮤니티 - 커뮤니티 게시글 검색

    public Page<ResReviewListDto> search(SearchData searchData, Pageable pageable) {
        Page<Review> result = null;
        if (!searchData.getTitle().isEmpty()) {
            result = reviewRepository.findAllTitleContaining(searchData.getTitle(), pageable);
        } else if(!searchData.getContent().isEmpty()) {
            result = reviewRepository.findAllContentContaining(searchData.getContent(), pageable);
        } else if(!searchData.getWriterName().isEmpty()) {
            result = reviewRepository.findAllUsernameContaining(searchData.getWriterName(), pageable);
        }
        List<ResReviewListDto> list = result.getContent().stream()
                .map(ResReviewListDto::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    // 커뮤니티 - 커뮤니티 게시글 등록
    public ResReviewWriteDto write(ReviewWriteDto dto, Member member) {
        Review review = ReviewWriteDto.ofEntity(dto);
        Member writeMember = memberRepository.findByUsername(member.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Username", member.getUsername())
        );
        review.upViewCount();
        review.setMappingMember(member);
        reviewRepository.save(review);
        return ResReviewWriteDto.fromEntity(review);
    }

    // 커뮤니티 - 커뮤니티 게시글 조회
    public ResReviewDetailsDto detail(Long reviewId) {
        Review findReview = reviewRepository.findByIdWithMember(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review", "Review ID", String.valueOf(reviewId))
        );
        findReview.upViewCount();
        return ResReviewDetailsDto.fromEntity(findReview);
    }
    // 커뮤니티 - 게시글 수정
    public ResReviewDetailsDto update(Long reviewId, ReviewUpdateDto dto) {
        Review updateReview = reviewRepository.findByIdWithMember(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review", "Review ID", String.valueOf(reviewId))
        );
        updateReview.update(dto.getTitle(), dto.getContent());
        return ResReviewDetailsDto.fromEntity(updateReview);
    }
    // 커뮤니티 - 게시글 삭제
    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
    // 마이페이지 - 사용자별 커뮤니티 게시글 조회
    public Page<ResReviewListDto> getAllReviewsByMember(Pageable pageable, Member member) {
        Page<Review> reviews = reviewRepository.findAllByMember(pageable, member);
        List<ResReviewListDto> list = reviews.getContent().stream()
                .map(ResReviewListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, reviews.getTotalElements());
    }
}
