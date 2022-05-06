package com.example.be_java_hisp_w15_g07_ravelli.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class NewPostDTO {

    @NotNull(message = "El id no puede estar vacio")
    @Positive(message = "El id debe ser mayor a cero")
    private Integer userId;

    @JsonFormat (pattern = "dd-MM-yyyy")
    @NotNull(message = "La fecha no puede estar vacia")
    private LocalDate date;

    @NotNull(message = "El campo no puede estar vacio")
    @Valid
    private ProductDTO detail;

    @NotNull(message = "El campo no puede estar vacio")
    private Integer category;

    @NotNull(message = "El campo no puede estar vacio")
    @Max(value = 10000000, message = "El precio maximo por producto es de 10.000.000")
    private Double price;
}