package com.bootcamp.be_java_hisp_w15_g08.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PromoCountDTO {
    private int user_id;
    private String user_name;
    private int promo_products_count;
}
