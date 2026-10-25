package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoCaida implements Dibujable {
    private final Rectangle area;
    private final Texture textura;
    private final EstrategiaCaida estrategia;

    protected ObjetoCaida(Texture textura, EstrategiaCaida estrategia) {
        this.textura = textura;
        this.estrategia = estrategia;
        this.area = new Rectangle(
                MathUtils.random(0, Constantes.ANCHO - Constantes.TAMANO_OBJETO),
                Constantes.ALTO,
                Constantes.TAMANO_OBJETO,
                Constantes.TAMANO_OBJETO);
    }

    public final boolean actualizar(Tarro tarro, float delta) {
        mover(delta);
        if (haCaidoAlSuelo()) {
            return true;
        }
        if (tarro.colisionaCon(area)) {
            aplicarEfecto(tarro);
            reproducirSonido();
            return true;
        }
        return false;
    }

    protected void mover(float delta) {
        estrategia.mover(area, delta);
    }

    protected boolean haCaidoAlSuelo() {
        return area.y + area.height < 0;
    }

    protected abstract void aplicarEfecto(Tarro tarro);

    protected void reproducirSonido() {
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }
}
