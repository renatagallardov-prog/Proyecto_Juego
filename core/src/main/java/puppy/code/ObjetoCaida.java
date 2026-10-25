package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import java.util.Objects;

public abstract class ObjetoCaida implements Dibujable {
    private final Rectangle area;
    private final Texture textura;
    private final Color color;
    private EstrategiaCaida estrategia;

    protected ObjetoCaida(Texture textura, EstrategiaCaida estrategia,
            Color color) {
        this.textura = Objects.requireNonNull(textura);
        this.color = new Color(Objects.requireNonNull(color));
        cambiarEstrategia(estrategia);
        area = new Rectangle(
                MathUtils.random(0, Constantes.ANCHO
                        - Constantes.TAMANO_OBJETO),
                Constantes.ALTO, Constantes.TAMANO_OBJETO,
                Constantes.TAMANO_OBJETO);
    }

    public final void cambiarEstrategia(EstrategiaCaida nueva) {
        estrategia = Objects.requireNonNull(nueva);
    }

    public final boolean actualizar(Tarro tarro, float delta) {
        Objects.requireNonNull(tarro);
        if (!Float.isFinite(delta) || delta < 0) {
            throw new IllegalArgumentException("Delta invalido");
        }
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

    private void mover(float delta) {
        estrategia.mover(area, delta);
    }

    private boolean haCaidoAlSuelo() {
        return area.y + area.height < 0;
    }

    protected abstract void aplicarEfecto(Tarro tarro);

    protected void reproducirSonido() {
    }

    @Override
    public final void dibujar(SpriteBatch batch) {
        Color anterior = batch.getColor();
        float r = anterior.r, g = anterior.g;
        float b = anterior.b, a = anterior.a;
        batch.setColor(color);
        batch.draw(textura, area.x, area.y);
        batch.setColor(r, g, b, a);
    }
}
