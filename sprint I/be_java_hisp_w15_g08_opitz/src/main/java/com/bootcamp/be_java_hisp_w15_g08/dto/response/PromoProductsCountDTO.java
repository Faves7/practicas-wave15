package com.bootcamp.be_java_hisp_w15_g08.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class PromoProductsCountDTO {
    private int user_id;
    private  String user_name;
    private  int promo_products_count;
}
