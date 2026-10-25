package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public final class CaidaAcelerada implements EstrategiaCaida {
    private float velocidad;
    private final float aceleracion;

    public CaidaAcelerada(float velocidad, float aceleracion) {
        if (!Float.isFinite(velocidad) || velocidad <= 0
                || !Float.isFinite(aceleracion) || aceleracion < 0) {
            throw new IllegalArgumentException("Caida invalida");
        }
        this.velocidad = velocidad;
        this.aceleracion = aceleracion;
    }

    @Override
    public void mover(Rectangle area, float delta) {
        area.y -= velocidad * delta + 0.5f * aceleracion * delta * delta;
        velocidad += aceleracion * delta;
    }
}
