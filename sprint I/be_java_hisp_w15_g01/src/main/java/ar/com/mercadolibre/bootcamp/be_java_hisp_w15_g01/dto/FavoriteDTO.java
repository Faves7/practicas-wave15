package ar.com.mercadolibre.bootcamp.be_java_hisp_w15_g01.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDTO {
    private Long userId;
    private Long productId;
}
