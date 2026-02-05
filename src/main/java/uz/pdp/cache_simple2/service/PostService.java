package uz.pdp.cache_simple2.service;

import uz.pdp.cache_simple2.dto.PostCreateDTO;
import uz.pdp.cache_simple2.dto.PostUpdateDTO;
import uz.pdp.cache_simple2.entity.Post;

public interface PostService {
    Post create(PostCreateDTO postCreateDTO);

    Post get(Integer id) throws InterruptedException;

    void update(PostUpdateDTO postUpdateDTO);

    void delete(Integer id);
}
