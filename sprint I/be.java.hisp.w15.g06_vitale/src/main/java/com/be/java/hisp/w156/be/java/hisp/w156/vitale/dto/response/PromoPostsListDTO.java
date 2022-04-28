package com.be.java.hisp.w156.be.java.hisp.w156.vitale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PromoPostsListDTO {

    private Integer user_id;
    private String user_name;
    private List<ResponsePromoPostDTO> posts;
}
