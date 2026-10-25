package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class TarroBuilder {
    private Texture textura;
    private Sound sonidoHerido;
    private int vidas = 3;
    private int velocidad = 400;
    private int tiempoHerido = 50;

    public TarroBuilder conTextura(Texture textura) {
        this.textura = textura;
        return this;
    }

    public TarroBuilder conSonidoHerido(Sound sonidoHerido) {
        this.sonidoHerido = sonidoHerido;
        return this;
    }

    public TarroBuilder conVidas(int vidas) {
        if (vidas < 1) {
            throw new IllegalArgumentException("Las vidas deben ser al menos 1");
        }
        this.vidas = vidas;
        return this;
    }

    public TarroBuilder conVelocidad(int velocidad) {
        if (velocidad < 1) {
            throw new IllegalArgumentException("La velocidad debe ser positiva");
        }
        this.velocidad = velocidad;
        return this;
    }

    public TarroBuilder conTiempoHerido(int tiempoHerido) {
        if (tiempoHerido < 0) {
            throw new IllegalArgumentException("El tiempo herido no puede ser negativo");
        }
        this.tiempoHerido = tiempoHerido;
        return this;
    }

    public Tarro build() {
        if (textura == null || sonidoHerido == null) {
            throw new IllegalStateException("Se requieren textura y sonido de herido");
        }
        return new Tarro(textura, sonidoHerido, vidas, velocidad, tiempoHerido);
    }
}
