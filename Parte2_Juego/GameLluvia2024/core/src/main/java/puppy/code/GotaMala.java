package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public final class GotaMala extends ObjetoCaida {
    public GotaMala(Texture textura) {
        super(textura, new CaidaAcelerada(220, 120), Color.WHITE);
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.dañar();
    }
}
