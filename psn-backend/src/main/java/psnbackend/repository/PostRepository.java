package psnbackend.repository;

import java.util.List;

import psnbackend.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByUserId(String id);
    List<PostEntity> findByUserIdOrderByCreatedAtDesc(String id);
}
