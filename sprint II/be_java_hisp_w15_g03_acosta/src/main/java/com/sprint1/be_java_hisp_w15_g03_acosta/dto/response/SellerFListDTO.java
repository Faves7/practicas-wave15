package com.sprint1.be_java_hisp_w15_g03_acosta.dto.response;

import com.sprint1.be_java_hisp_w15_g03_acosta.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * para US:03
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerFListDTO extends PersonDTO {

    private List<PersonDTO> followers;
}
