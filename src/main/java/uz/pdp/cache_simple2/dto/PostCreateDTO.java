package uz.pdp.cache_simple2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateDTO {
    private Integer userId;
    private String title;
    private String body;
}