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
public class Inquiry extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INQUIRY_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    private String answer;
    private Boolean replied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Inquiry(Long id, String title, String content, String answer, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.answer = answer;
        this.replied = false;
        this.member = member;
    }

    public void update(String tilte, String content) {
        this.title = tilte;
        this.content = content;
    }

    public void writeAnswer(String answer) {
        this.replied = true;
        this.answer = answer;
    }

    public void setMappingMember(Member member) {
        this.member = member;
    }
}
