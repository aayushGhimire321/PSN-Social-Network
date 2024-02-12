package psnbackend.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post") // Define the table name for PostgreSQL
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Assuming you want an auto-incremented ID for PostgreSQL

    @Column(name = "user_id")
    private String userId;

    @Column(name = "original_user_id")
    private String originalUserId;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at")
    private Instant createdAt;

    @ElementCollection
    @CollectionTable(name = "post_love", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "love")
    private List<String> love = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "post_share", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "share")
    private List<String> share = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private List<CommentEntity> comments = new ArrayList<>();
}
