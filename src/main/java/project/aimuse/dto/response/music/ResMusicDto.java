package project.aimuse.dto.response.music;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;
import project.aimuse.entity.Music;

@Getter
@Setter
@NoArgsConstructor
public class ResMusicDto {

    private Long musicId;
    private String mood;
    private String instruments;
    private String musicFast;
    private Integer musicLength;
    private String chordWork;

    @Builder
    public ResMusicDto(Long musicId, String mood, String instruments, String musicFast, Integer musicLength, String chordWork) {
        this.musicId = musicId;
        this.mood = mood;
        this.instruments = instruments;
        this.musicFast = musicFast;
        this.musicLength = musicLength;
        this.chordWork = chordWork;
    }

    public static ResMusicDto ofEntity(Music music) {
        return ResMusicDto.builder()
                .musicId(music.getId())
                .mood(music.getMood())
                .instruments(music.getInstruments())
                .musicFast(music.getMusicFast())
                .musicLength(music.getMusicLength())
                .chordWork(music.getChordWork())
                .build();
    }


}
