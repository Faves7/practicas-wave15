package ar.com.mercadolibre.bootcamp.lfedele.deportes.model;

import ar.com.mercadolibre.bootcamp.lfedele.deportes.service.DeporteService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Persona {
    private String nombre;
    private String apellido;
    private Integer edad;
    private List<Deporte> deportes;

    public Persona() {
        this.deportes = new ArrayList<>();
    }

    public void addDeporte(Deporte deporte) {
        this.deportes.add(deporte);
    }
}
