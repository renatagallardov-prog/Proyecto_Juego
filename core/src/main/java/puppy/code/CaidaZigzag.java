package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public final class CaidaZigzag implements EstrategiaCaida {
    private final float velocidadVertical;
    private final float velocidadHorizontal;
    private int direccion;

    public CaidaZigzag(float vertical, float horizontal) {
        if (!Float.isFinite(vertical) || vertical <= 0
                || !Float.isFinite(horizontal) || horizontal <= 0) {
            throw new IllegalArgumentException("Velocidad invalida");
        }
        velocidadVertical = vertical;
        velocidadHorizontal = horizontal;
        direccion = MathUtils.randomSign();
    }

    @Override
    public void mover(Rectangle area, float delta) {
        area.y -= velocidadVertical * delta;
        area.x += direccion * velocidadHorizontal * delta;
        if (area.x < 0) {
            area.x = 0;
            direccion = 1;
        } else if (area.x > Constantes.ANCHO - area.width) {
            area.x = Constantes.ANCHO - area.width;
            direccion = -1;
        }
    }
}
