package uz.pdp.cache_simple2.service;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import uz.pdp.cache_simple2.dto.PostCreateDTO;
import uz.pdp.cache_simple2.dto.PostUpdateDTO;
import uz.pdp.cache_simple2.entity.Post;
import uz.pdp.cache_simple2.mapper.PostMapper;
import uz.pdp.cache_simple2.repository.PostRepository;

import java.util.concurrent.TimeUnit;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final CacheManager cacheManager;
    private final Cache cache;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, CacheManager cacheManager) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.cacheManager = cacheManager;
        this.cache = cacheManager.getCache("posts");
    }

    @Override
    @Transactional
    public Post create(PostCreateDTO postCreateDTO) {
        Post post = postMapper.toEntity(postCreateDTO);
        System.out.println(post);
        return postRepository.save(post);
    }

    @Override
    @SneakyThrows
    public Post get(Integer id) {
        assert cache != null;
        Post cachedPost = cache.get(id, Post.class);
        if (cachedPost != null) {
            return cachedPost;
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found: " + id));
        TimeUnit.SECONDS.sleep(1);
        cache.put(id, post);
        return post;
    }

    @Override
    public void update(PostUpdateDTO postUpdateDTO) {
        Post post = get(postUpdateDTO.getId());
        post.setTitle(postUpdateDTO.getTitle());
        post.setBody(postUpdateDTO.getBody());
        postRepository.save(post);
        assert cache != null;
        cache.put(postUpdateDTO.getId(), post);
    }

    @Override
    public void delete(Integer id) {
        postRepository.deleteById(id);
        assert cache != null;
        cache.evict(id);
    }
}
