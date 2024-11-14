package project.aimuse.dto.request.music;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Music;

@Getter
@Setter
@NoArgsConstructor
public class MusicGenerateDto {

    private String mood;
    private String instruments;
    private String musicFast;
    private Integer musicLength;

    @Builder
    public MusicGenerateDto(String mood, String instruments, String musicFast, Integer musicLength) {
        this.mood = mood;
        this.instruments = instruments;
        this.musicFast = musicFast;
        this.musicLength = musicLength;
    }

    public static Music ofEntity(MusicGenerateDto dto, String randomFileName, String outputFilePath, String imageFilePath) {
        return Music.builder()
                .mood(dto.getMood())
                .instruments(dto.getInstruments())
                .musicFast(dto.getMusicFast())
                .musicLength(dto.getMusicLength())
                .chordWork("X")
                .fileName(randomFileName)
                .filePath(outputFilePath)
                .imageFilePath(imageFilePath)
                .build();
    }

}
