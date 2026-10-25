package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class CaidaZigzag implements EstrategiaCaida {
    private final float velocidadVertical;
    private final float velocidadHorizontal;
    private int direccion;

    public CaidaZigzag(float velocidadVertical, float velocidadHorizontal) {
        this.velocidadVertical = velocidadVertical;
        this.velocidadHorizontal = velocidadHorizontal;
        this.direccion = MathUtils.randomSign();
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
