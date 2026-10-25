package puppy.code;

public class GotaDorada extends ObjetoCaida {
    private static final int PUNTOS = 50;
    private static final float VELOCIDAD_VERTICAL = 350;
    private static final float VELOCIDAD_HORIZONTAL = 150;

    public GotaDorada() {
        super(GestorRecursos.getInstance().getTexturaGotaDorada(), new CaidaZigzag(VELOCIDAD_VERTICAL, VELOCIDAD_HORIZONTAL));
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
