package com.be.java.hisp.w156.be.java.hisp.w156.vitale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PromoPostsCountDTO {

    private Integer user_id;
    private String user_name;
    private Integer promo_products_count;
}
