package project.aimuse.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.aimuse.common.BaseTimeEntity;

@Entity
@NoArgsConstructor
@Getter
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;
    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Review(Long id, String title, String content, Integer viewCount, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = 0;
        this.member = member;
    }

    public void upViewCount() {
        this.viewCount++;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setMappingMember(Member member) {
        this.member = member;
    }


}
