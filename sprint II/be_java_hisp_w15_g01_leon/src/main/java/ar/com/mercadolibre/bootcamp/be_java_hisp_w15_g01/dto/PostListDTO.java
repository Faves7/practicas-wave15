package ar.com.mercadolibre.bootcamp.be_java_hisp_w15_g01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostListDTO {
    private Long userId;
    private List<PostDTO> posts;
}
