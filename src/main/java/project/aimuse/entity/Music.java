package project.aimuse.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.aimuse.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
public class Music extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MUSIC_ID")
    private Long id;

//    private String chordWork;

    private String mood;
    private String instruments;
    private String musicFast;
    private Integer musicLength;
    private String chordWork;


    private String fileName;
    private String filePath;
    private String imageFilePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Music(String mood, String instruments, String musicFast, Integer musicLength, String chordWork, String fileName, String filePath, String imageFilePath, Member member) {
        this.mood = mood;
        this.instruments = instruments;
        this.musicFast = musicFast;
        this.musicLength = musicLength;
        this.chordWork = chordWork;
        this.fileName = fileName;
        this.filePath = filePath;
        this.imageFilePath = imageFilePath;
        this.member = member;
    }

    public void setMappingMember(Member member) {
        this.member = member;
    }

}
