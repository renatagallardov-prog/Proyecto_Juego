package puppy.code;

public class GotaBuena extends ObjetoCaida {
    private static final int PUNTOS = 10;
    private static final float VELOCIDAD = 300;

    public GotaBuena() {
        super(GestorRecursos.getInstance().getTexturaGotaBuena(), new CaidaRecta(VELOCIDAD));
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(PUNTOS);
    }

    @Override
    protected void reproducirSonido() {
        GestorRecursos.getInstance().getSonidoGota().play();
    }
}
