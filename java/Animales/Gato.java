package Animales;

public class Gato extends Animal implements Carnivoro {
    @Override
    void emitirSonido() {
        System.out.println("Miau");
    }

    @Override
    public void comerCarne() {
        System.out.println("Come carne");
    }

    @Override
    boolean esComestible() {
        return false;
    }
}
