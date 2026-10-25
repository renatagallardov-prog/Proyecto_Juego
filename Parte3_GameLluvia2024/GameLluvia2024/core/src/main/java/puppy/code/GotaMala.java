package puppy.code;

public class GotaMala extends ObjetoCaida {
    private static final float VELOCIDAD_INICIAL = 220;
    private static final float ACELERACION = 120;

    public GotaMala() {
        super(GestorRecursos.getInstance().getTexturaGotaMala(), new CaidaAcelerada(VELOCIDAD_INICIAL, ACELERACION));
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.dañar();
    }
}
