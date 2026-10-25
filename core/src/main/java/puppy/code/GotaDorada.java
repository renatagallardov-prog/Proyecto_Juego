package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import java.util.Objects;

public final class GotaDorada extends ObjetoCaida {
    private static final int PUNTOS = 50;
    private final Sound sonido;

    public GotaDorada(Texture textura, Sound sonido) {
        super(textura, new CaidaZigzag(350, 150), Color.GOLD);
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
