package psnbackend.repository;

import java.util.List;
import java.util.Optional;

import psnbackend.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<List<PostEntity>> findByUserId(String id);
    Optional<List<PostEntity>> findByUserIdOrderByCreatedAtDesc(String id);
}
