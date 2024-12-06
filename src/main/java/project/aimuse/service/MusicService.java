package project.aimuse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.aimuse.common.exception.ResourceNotFoundException;
import project.aimuse.dto.request.music.MusicGenerateDto;
import project.aimuse.dto.request.music.MusicGenerateExpertDto;
import project.aimuse.dto.response.music.ResMusicDto;
import project.aimuse.dto.response.music.ResMusicListDto;
import project.aimuse.entity.Member;
import project.aimuse.entity.Music;
import project.aimuse.repository.MusicRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MusicService {

    private final MusicRepository musicRepository;

    // 음악 파일 생성 - 초보자용
    public Resource generateMusicFile(MusicGenerateDto musicDTO, Member member) throws Exception {
        String originMusicFileName = "Music-Transformer-Composition.wav";
        String originImageFileName = "Music-Transformer-Composition.png";
        String MUSIC_PATH = "/home/t24207/AI/Music_Transformer/output";

        String musicToken = switch (musicDTO.getMusicLength()) {
            case 15 -> "300";
            case 30 -> "600";
            case 45 -> "900";
            default -> "1200";
        };


        // 음악 파일과 이미지 파일의 경로
        String outputMusicFilePath = MUSIC_PATH + File.separator + originMusicFileName;
        String outputImageFilePath = MUSIC_PATH + File.separator + originImageFileName;

        log.info("outputMusicFilePath: {}", outputMusicFilePath);
        log.info("outputImageFilePath: {}", outputImageFilePath);

        // 디렉토리 생성
        Path path = Paths.get(MUSIC_PATH);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }

        // 파이썬 스크립트 명령어 생성
        List<String> commands = new ArrayList<>();
        commands.add("python");
        commands.add("/home/t24207/AI/Music_Transformer/Music_Transformer.py");
        commands.add(musicDTO.getInstruments());
        commands.add(musicToken);

        log.info("{}", commands);

        // 파이썬 프로세스 실행
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output = new BufferedReader(new InputStreamReader(process.getInputStream()))
                .lines().collect(Collectors.joining("\n"));
        log.info("Python Script Output: {}", output);

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("파일 생성 실패, 종료 코드: " + exitCode);
        }

        // 파일 생성 확인
        File musicFile = new File(outputMusicFilePath);
        File imageFile = new File(outputImageFilePath);
        if (!musicFile.exists() || !imageFile.exists()) {
            throw new RuntimeException("파일 생성 실패");
        }

        // UUID로 새 파일 이름 생성
        String newFileName = UUID.randomUUID().toString();
        String newMusicFilePath = MUSIC_PATH + File.separator + newFileName + ".wav";
        String newImageFilePath = MUSIC_PATH + File.separator + newFileName + ".png";

        File newMusicFile = new File(newMusicFilePath);
        File newImageFile = new File(newImageFilePath);

        if (!musicFile.renameTo(newMusicFile) || !imageFile.renameTo(newImageFile)) {
            throw new RuntimeException("파일 이름 변경 실패");
        }

        // MusicEntity 저장
        Music music = MusicGenerateDto.ofEntity(musicDTO, newFileName + ".wav", newMusicFilePath, newImageFilePath);
        music.setMappingMember(member);
        musicRepository.save(music);

        return new FileSystemResource(newMusicFile);
    }

    // 음악 파일 생성 - 초보자용
    public ResMusicDto generateMusicFile2(MusicGenerateDto musicDTO, Member member) throws Exception {
        String originMusicFileName = "Music-Transformer-Composition.wav";
        String originImageFileName = "Music-Transformer-Composition.png";
        String MUSIC_PATH = "/home/t24207/AI/Music_Transformer/output";

        String musicToken = switch (musicDTO.getMusicLength()) {
            case 15 -> "300";
            case 30 -> "600";
            case 45 -> "900";
            default -> "1200";
        };


        // 음악 파일과 이미지 파일의 경로
        String outputMusicFilePath = MUSIC_PATH + File.separator + originMusicFileName;
        String outputImageFilePath = MUSIC_PATH + File.separator + originImageFileName;

        log.info("outputMusicFilePath: {}", outputMusicFilePath);
        log.info("outputImageFilePath: {}", outputImageFilePath);

        // 디렉토리 생성
        Path path = Paths.get(MUSIC_PATH);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }

        // 파이썬 스크립트 명령어 생성
        List<String> commands = new ArrayList<>();
        commands.add("python");
        commands.add("/home/t24207/AI/Music_Transformer/Music_Transformer.py");
        commands.add(musicDTO.getInstruments());
        commands.add(musicToken);

        log.info("{}", commands);

        // 파이썬 프로세스 실행
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output = new BufferedReader(new InputStreamReader(process.getInputStream()))
                .lines().collect(Collectors.joining("\n"));
        log.info("Python Script Output: {}", output);

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("파일 생성 실패, 종료 코드: " + exitCode);
        }

        // 파일 생성 확인
        File musicFile = new File(outputMusicFilePath);
        File imageFile = new File(outputImageFilePath);
        if (!musicFile.exists() || !imageFile.exists()) {
            throw new RuntimeException("파일 생성 실패");
        }

        // UUID로 새 파일 이름 생성
        String newFileName = UUID.randomUUID().toString();
        String newMusicFilePath = MUSIC_PATH + File.separator + newFileName + ".wav";
        String newImageFilePath = MUSIC_PATH + File.separator + newFileName + ".png";

        File newMusicFile = new File(newMusicFilePath);
        File newImageFile = new File(newImageFilePath);

        if (!musicFile.renameTo(newMusicFile) || !imageFile.renameTo(newImageFile)) {
            throw new RuntimeException("파일 이름 변경 실패");
        }

        // MusicEntity 저장
        Music music = MusicGenerateDto.ofEntity(musicDTO, newFileName + ".wav", newMusicFilePath, newImageFilePath);
        music.setMappingMember(member);
        musicRepository.save(music);

        return ResMusicDto.ofEntity(music);
    }

    // 음악 파일 다운로드
    public Resource getMusicFileById(Long musicId) {
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new RuntimeException("해당 파일을 찾을 수 없습니다."));

        File file = new File(music.getFilePath());
        if (!file.exists()) {
            throw new RuntimeException("파일이 존재하지 않습니다.");
        }

        return new FileSystemResource(file);
    }

    public ResMusicDto detail(Long musicId) {
        Music music = musicRepository.findById(musicId).orElseThrow(
                () -> new ResourceNotFoundException("Music", "Music Id", String.valueOf(musicId))
        );
        return ResMusicDto.ofEntity(music);
    }

    // 음악 이미지 다운로드
    public Resource getImageFileById(Long musicId) {
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new RuntimeException("해당 파일을 찾을 수 없습니다."));

        File file = new File(music.getImageFilePath());
        if (!file.exists()) {
            throw new RuntimeException("파일이 존재하지 않습니다.");
        }

        return new FileSystemResource(file);
    }

    //마이페이지 - 과거 기록 조회
    public Page<ResMusicListDto> getAllMusicByMember(Pageable pageable, Member member) {
        Page<Music> musics = musicRepository.findAllByMember(member, pageable);
        List<ResMusicListDto> list = musics.getContent().stream()
                .map(ResMusicListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, musics.getTotalElements());
    }

    // 과거기록 삭제
    public void delete(Long musicId) {
        musicRepository.deleteById(musicId);
    }

    public ResMusicDto getMusicFileByChordwork(MusicGenerateExpertDto dto, Member member) throws IOException, InterruptedException {
        // 기본 디렉토리와 파일 경로 설정
        String sourceDirectory = "/home/t24207/AI/Music_Transformer/output2";
        String targetDirectory = "/home/t24207/AI/Music_Transformer/output";

        // chordwork에 따른 원본 음악 및 이미지 파일 경로 설정
        String sourceMusicFilePath;
        String sourceImageFilePath;

        if (dto.getChordWork().contains("Gm")) {
            sourceMusicFilePath = sourceDirectory + "/Music-Transformer-Composition_Gm.wav";
            sourceImageFilePath = sourceDirectory + "/Music-Transformer-Composition_Gm.png";
        } else if (dto.getChordWork().contains("Cm")) {
            sourceMusicFilePath = sourceDirectory + "/Music-Transformer-Composition_Cm.wav";
            sourceImageFilePath = sourceDirectory + "/Music-Transformer-Composition_Cm.png";
        } else if (dto.getChordWork().contains("G")) {
            sourceMusicFilePath = sourceDirectory + "/Music-Transformer-Composition_G.wav";
            sourceImageFilePath = sourceDirectory + "/Music-Transformer-Composition_G.png";
        } else {
            // 랜덤 선택
            String[] musicFiles = {
                    sourceDirectory + "/Music-Transformer-Composition_Gm.wav",
                    sourceDirectory + "/Music-Transformer-Composition_Cm.wav",
                    sourceDirectory + "/Music-Transformer-Composition_G.wav"
            };
            String[] imageFiles = {
                    sourceDirectory + "/Music-Transformer-Composition_Gm.png",
                    sourceDirectory + "/Music-Transformer-Composition_Cm.png",
                    sourceDirectory + "/Music-Transformer-Composition_G.png"
            };

            Random random = new Random();
            int randomIndex = random.nextInt(musicFiles.length);

            sourceMusicFilePath = musicFiles[randomIndex];
            sourceImageFilePath = imageFiles[randomIndex];
        }
        // UUID를 사용하여 새 파일 이름 생성
        String randomFileName = UUID.randomUUID().toString();
        String newMusicFilePath = targetDirectory + "/" + randomFileName + ".wav";
        String newImageFilePath = targetDirectory + "/" + randomFileName + ".png";

        // 음악 파일 및 이미지 파일 복사
        Files.copy(Paths.get(sourceMusicFilePath), Paths.get(newMusicFilePath), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Paths.get(sourceImageFilePath), Paths.get(newImageFilePath), StandardCopyOption.REPLACE_EXISTING);

        // Music 엔터티 생성 및 저장
        Music music = MusicGenerateExpertDto.ofEntity(dto, randomFileName + ".wav", newMusicFilePath, newImageFilePath);
        music.setMappingMember(member);
        musicRepository.save(music);

        Thread.sleep(10000);
        // 새 파일 이름과 경로를 포함한 DTO 반환
        return ResMusicDto.ofEntity(music);
    }

    public Page<ResMusicListDto> getAllMusics(Pageable pageable) {
        Page<Music> musics = musicRepository.findAllWithMember(pageable);
        List<ResMusicListDto> list = musics.getContent().stream()
                .map(ResMusicListDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, musics.getTotalElements());
    }
}


