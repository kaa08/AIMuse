package project.aimuse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.aimuse.dto.request.music.MusicGenerateDto;
import project.aimuse.dto.request.music.MusicGenerateExpertDto;
import project.aimuse.dto.response.member.MemberResponseDto;
import project.aimuse.dto.response.music.ResMusicDto;
import project.aimuse.entity.Member;
import project.aimuse.service.MemberService;
import project.aimuse.service.MusicService;

import java.io.IOException;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
@Slf4j
public class MusicController {

    private final MusicService musicService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ResMusicDto> generateBasicMusic(
            @RequestBody MusicGenerateDto musicDTO,
            @AuthenticationPrincipal Member member
    ) {
        try {
            ResMusicDto resMusicDto = musicService.generateMusicFile2(musicDTO, member);
            return ResponseEntity.status(HttpStatus.CREATED).body(resMusicDto);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/expert")
    public ResponseEntity<ResMusicDto> generateExpertMusic(
            @RequestBody MusicGenerateExpertDto musicDTO,
            @AuthenticationPrincipal Member member
            ) {
        try {
            ResMusicDto resMusicDto = musicService.getMusicFileByChordwork(musicDTO, member);
            return ResponseEntity.status(HttpStatus.CREATED).body(resMusicDto);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/basic")
    public ResponseEntity<Resource> generateMusic(
            @RequestBody MusicGenerateDto musicDTO,
            @AuthenticationPrincipal Member member) {
        try {
            // Service를 통해 음악 파일 생성 및 저장
            Resource resource = musicService.generateMusicFile(musicDTO, member);

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/detail/{musicId}")
    public ResponseEntity<ResMusicDto> getMusicDetail(@PathVariable Long musicId) {
        ResMusicDto detail = musicService.detail(musicId);
        return ResponseEntity.status(HttpStatus.OK).body(detail);
    }

    @GetMapping("/download/{musicId}")
    public ResponseEntity<Resource> downloadMusicFile(@PathVariable Long musicId) {
        Resource fileResource = musicService.getMusicFileById(musicId);
        log.info("Downloading music file: " + fileResource.getFilename());

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }

    @GetMapping("/download/image/{musicId}")
    public ResponseEntity<Resource> downloadImageFile(@PathVariable Long musicId) {
        Resource fileResource = musicService.getImageFileById(musicId);
        log.info("Downloading image file: " + fileResource.getFilename());

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<MemberResponseDto> getMember(
            @AuthenticationPrincipal Member member) {
        MemberResponseDto memberInfo = memberService.getMemberInfo(member.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(memberInfo);
    }
}
