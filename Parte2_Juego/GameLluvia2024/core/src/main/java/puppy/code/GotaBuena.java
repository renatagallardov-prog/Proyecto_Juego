package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import java.util.Objects;

public final class GotaBuena extends ObjetoCaida {
    private static final int PUNTOS = 10;
    private final Sound sonido;

    public GotaBuena(Texture textura, Sound sonido) {
        super(textura, new CaidaRecta(300), Color.WHITE);
        this.sonido = Objects.requireNonNull(sonido);
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(PUNTOS);
    }

    @Override
    protected void reproducirSonido() {
        sonido.play();
    }
}
