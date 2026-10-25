package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import java.util.Objects;

public final class Tarro implements Dibujable {
    private final Rectangle area;
    private final Texture textura;
    private final Sound sonidoHerido;
    private final int vidasIniciales;
    private final float velocidad;
    private final float tiempoHeridoMax;
    private int vidas;
    private int puntos;
    private float tiempoHerido;

    Tarro(Texture textura, Sound sonido, int vidas,
            float velocidad, float tiempoHeridoMax) {
        this.textura = Objects.requireNonNull(textura);
        sonidoHerido = Objects.requireNonNull(sonido);
        if (vidas < 1 || !Float.isFinite(velocidad) || velocidad <= 0
                || !Float.isFinite(tiempoHeridoMax) || tiempoHeridoMax <= 0) {
            throw new IllegalArgumentException("Tarro invalido");
        }
        vidasIniciales = vidas;
        this.velocidad = velocidad;
        this.tiempoHeridoMax = tiempoHeridoMax;
        area = new Rectangle(0, 20, Constantes.TAMANO_OBJETO,
                Constantes.TAMANO_OBJETO);
        reiniciar();
    }

    public void reiniciar() {
        area.x = (Constantes.ANCHO - area.width) / 2;
        vidas = vidasIniciales;
        puntos = 0;
        tiempoHerido = 0;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean estaHerido() {
        return tiempoHerido > 0;
    }

    public boolean estaMuerto() {
        return vidas == 0;
    }

    public boolean colisionaCon(Rectangle otra) {
        return area.overlaps(Objects.requireNonNull(otra));
    }

    public void sumarPuntos(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Puntaje invalido");
        }
        puntos += cantidad;
    }

    public void dañar() {
        if (estaHerido() || estaMuerto()) {
            return;
        }
        vidas--;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
    }

    public void actualizarEstado(float delta) {
        if (!Float.isFinite(delta) || delta < 0) {
            throw new IllegalArgumentException("Delta invalido");
        }
        tiempoHerido = Math.max(0, tiempoHerido - delta);
    }

    public void actualizarMovimiento(int direccion, float delta) {
        if (direccion < -1 || direccion > 1
                || !Float.isFinite(delta) || delta < 0) {
            throw new IllegalArgumentException("Movimiento invalido");
        }
        if (estaHerido() || estaMuerto()) {
            return;
        }
        area.x += direccion * velocidad * delta;
        area.x = MathUtils.clamp(area.x, 0, Constantes.ANCHO - area.width);
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        float desplazamiento = estaHerido() ? MathUtils.random(-5, 5) : 0;
        batch.draw(textura, area.x, area.y + desplazamiento);
    }
}
