package project.aimuse.dto.request.music;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.aimuse.entity.Music;

@Getter
@Setter
@NoArgsConstructor
public class MusicGenerateExpertDto {

    private String instruments;
    private String chordWork;
    private Integer musicLength;
    private String musicFast;

    @Builder
    public MusicGenerateExpertDto(String instruments, String chordWork, Integer musicLength, String musicFast) {
        this.instruments = instruments;
        this.chordWork = chordWork;
        this.musicLength = musicLength;
        this.musicFast = musicFast;
    }

    public static Music ofEntity(MusicGenerateExpertDto dto, String randomFileName, String outputFilePath, String imageFilePath) {
        return Music.builder()
                .mood("x")
                .instruments(dto.getInstruments())
                .musicFast(dto.getMusicFast())
                .musicLength(dto.getMusicLength())
                .chordWork(dto.getChordWork())
                .fileName(randomFileName)
                .filePath(outputFilePath)
                .imageFilePath(imageFilePath)
                .build();
    }
}
