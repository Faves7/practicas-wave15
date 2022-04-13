package paquete.repositorio;

import paquete.clases.Localizador;
import paquete.repositorio.Repositorio;

import java.util.ArrayList;

public class RepositorioLocalizador implements Repositorio<Localizador> {
    private ArrayList<Localizador> localizadores;

    public RepositorioLocalizador() {
        localizadores = new ArrayList<>();
    }

    public ArrayList<Localizador> getLocalizadores() {
        return localizadores;
    }

    public void setLocalizadores(ArrayList<Localizador> localizadores) {
        this.localizadores = localizadores;
    }

    @Override
    public void agregar(Localizador item) {
        localizadores.add(item);
    }
}
