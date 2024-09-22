package uz.pdp.cache_simple2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import uz.pdp.cache_simple2.entity.Post;
import uz.pdp.cache_simple2.repository.PostRepository;

import java.net.URL;
import java.util.Collections;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableCaching
@RequiredArgsConstructor
public class CacheSimple2 {

    public static void main(String[] args) {
        SpringApplication.run(CacheSimple2.class, args);
    }

    //    @Bean
    public ApplicationRunner applicationRunner(ObjectMapper objectMapper,
                                               PostRepository postRepository) {
        return (args) -> {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            List<Post> posts = objectMapper.readValue(url, new TypeReference<List<Post>>() {
            });
            postRepository.saveAll(posts);
        };
    }

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager concurrentMapCacheManager = new ConcurrentMapCacheManager();
        concurrentMapCacheManager.setCacheNames(Collections.singletonList("posts"));
        return concurrentMapCacheManager;
    }
}
// juda ko'p select qilinadigan malumotlarni keshlash kerak
// ko'p o'zgarmaydigan malumotlarda keshni ishlatish kerak