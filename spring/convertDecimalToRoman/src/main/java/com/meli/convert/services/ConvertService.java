package com.meli.convert.services;

import org.springframework.stereotype.Service;

@Service
public interface ConvertService {

    String convertDecimalToRoman(Integer decimal);
}
