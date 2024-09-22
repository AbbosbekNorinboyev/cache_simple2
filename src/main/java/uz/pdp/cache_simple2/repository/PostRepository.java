package uz.pdp.cache_simple2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.cache_simple2.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}