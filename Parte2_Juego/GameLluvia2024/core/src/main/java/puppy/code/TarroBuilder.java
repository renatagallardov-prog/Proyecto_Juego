package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import java.util.Objects;

public final class TarroBuilder {
    private Texture textura;
    private Sound sonidoHerido;
    private int vidas = 3;
    private float velocidad = 400;
    private float tiempoHerido = 0.8f;

    public TarroBuilder conTextura(Texture textura) {
        this.textura = Objects.requireNonNull(textura);
        return this;
    }

    public TarroBuilder conSonidoHerido(Sound sonido) {
        sonidoHerido = Objects.requireNonNull(sonido);
        return this;
    }

    public TarroBuilder conVidas(int vidas) {
        if (vidas < 1) {
            throw new IllegalArgumentException("Vidas invalidas");
        }
        this.vidas = vidas;
        return this;
    }

    public TarroBuilder conVelocidad(float velocidad) {
        if (!Float.isFinite(velocidad) || velocidad <= 0) {
            throw new IllegalArgumentException("Velocidad invalida");
        }
        this.velocidad = velocidad;
        return this;
    }

    public TarroBuilder conTiempoHerido(float segundos) {
        if (!Float.isFinite(segundos) || segundos <= 0) {
            throw new IllegalArgumentException("Duracion invalida");
        }
        tiempoHerido = segundos;
        return this;
    }

    public Tarro build() {
        if (textura == null || sonidoHerido == null) {
            throw new IllegalStateException("Faltan textura y sonido");
        }
        return new Tarro(textura, sonidoHerido, vidas,
                velocidad, tiempoHerido);
    }
}
