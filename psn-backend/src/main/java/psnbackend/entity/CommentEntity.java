package psnbackend.entity;

import java.time.Instant;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment") // Define the table name for PostgreSQL
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Assuming you want an auto-incremented ID for PostgreSQL

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_fullname")
    private String userFullname;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private Instant createdAt;
}
