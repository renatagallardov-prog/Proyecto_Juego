package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public class CaidaAcelerada implements EstrategiaCaida {
    private float velocidad;
    private final float aceleracion;

    public CaidaAcelerada(float velocidadInicial, float aceleracion) {
        this.velocidad = velocidadInicial;
        this.aceleracion = aceleracion;
    }

    @Override
    public void mover(Rectangle area, float delta) {
        velocidad += aceleracion * delta;
        area.y -= velocidad * delta;
    }
}
