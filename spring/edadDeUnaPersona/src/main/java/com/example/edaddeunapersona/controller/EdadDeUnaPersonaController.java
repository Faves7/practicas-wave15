package com.example.edaddeunapersona.controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/edad")
public class EdadDeUnaPersonaController {

    //Por PATH
    @GetMapping("/calcular/{dia}/{mes}/{anio}")
    public String CalcularEdadPath(@PathVariable Integer dia, @PathVariable Integer mes, @PathVariable Integer anio){
        return CalcularEdad(dia, mes, anio);
    }

    //Por Parametros
    @GetMapping("/calcular/")
    public String CalcularEdadRequest(@RequestParam Integer dia, @RequestParam Integer mes, @RequestParam Integer anio){
        return CalcularEdad(dia, mes, anio);
    }

    public String CalcularEdad(Integer dia, Integer mes, Integer anio){

        Period age = Period.between(LocalDate.of(anio, mes, dia), LocalDate.now());

        return "La edad de la persona es " + age.getYears() + " años.";
    }
}





