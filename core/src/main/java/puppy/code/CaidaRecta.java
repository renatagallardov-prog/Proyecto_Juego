package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public final class CaidaRecta implements EstrategiaCaida {
    private final float velocidad;

    public CaidaRecta(float velocidad) {
        if (!Float.isFinite(velocidad) || velocidad <= 0) {
            throw new IllegalArgumentException("Velocidad invalida");
        }
        this.velocidad = velocidad;
    }

    @Override
    public void mover(Rectangle area, float delta) {
        area.y -= velocidad * delta;
    }
}
