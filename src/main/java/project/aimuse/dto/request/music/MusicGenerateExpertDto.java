package project.aimuse.dto.request.music;

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

    public MusicGenerateExpertDto(String instruments, String chordWork) {
        this.instruments = instruments;
        this.chordWork = chordWork;
    }

    public static Music ofEntity(MusicGenerateExpertDto dto, String randomFileName, String outputFilePath, String imageFilePath) {
        return Music.builder()
                .mood("x")
                .instruments(dto.getInstruments())
                .musicFast("x")
                .musicLength(60)
                .chordWork(dto.getChordWork())
                .fileName(randomFileName)
                .filePath(outputFilePath)
                .imageFilePath(imageFilePath)
                .build();
    }
}