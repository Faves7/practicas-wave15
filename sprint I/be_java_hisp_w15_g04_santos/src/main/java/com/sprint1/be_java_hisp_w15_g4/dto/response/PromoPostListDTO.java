package com.sprint1.be_java_hisp_w15_g4.dto.response;

import com.sprint1.be_java_hisp_w15_g4.dto.request.PostPromoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PromoPostListDTO {

    private int user_id;
    private List<PostPromoDTO> posts;
}
