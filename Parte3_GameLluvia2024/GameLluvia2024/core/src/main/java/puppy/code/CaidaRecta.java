package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public class CaidaRecta implements EstrategiaCaida {
    private final float velocidad;

    public CaidaRecta(float velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public void mover(Rectangle area, float delta) {
        area.y -= velocidad * delta;
    }
}
