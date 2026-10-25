package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Tarro implements Dibujable {
    private final Rectangle area;
    private final Texture textura;
    private final Sound sonidoHerido;
    private final int vidasIniciales;
    private final int velocidad;
    private final int tiempoHeridoMax;
    private int vidas;
    private int puntos;
    private boolean herido;
    private int tiempoHerido;

    Tarro(Texture textura, Sound sonidoHerido, int vidasIniciales, int velocidad, int tiempoHeridoMax) {
        this.textura = textura;
        this.sonidoHerido = sonidoHerido;
        this.vidasIniciales = vidasIniciales;
        this.velocidad = velocidad;
        this.tiempoHeridoMax = tiempoHeridoMax;
        this.area = new Rectangle(0, 20, Constantes.TAMANO_OBJETO, Constantes.TAMANO_OBJETO);
        reiniciarEstado();
    }

    private void reiniciarEstado() {
        area.x = Constantes.ANCHO / 2 - Constantes.TAMANO_OBJETO / 2;
        vidas = vidasIniciales;
        puntos = 0;
        herido = false;
        tiempoHerido = 0;
    }

    public void reiniciar() {
        reiniciarEstado();
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean estaHerido() {
        return herido;
    }

    public boolean estaMuerto() {
        return vidas <= 0;
    }

    public boolean colisionaCon(Rectangle otra) {
        return area.overlaps(otra);
    }

    public void sumarPuntos(int cantidad) {
        if (cantidad > 0) {
            puntos += cantidad;
        }
    }

    public void dañar() {
        if (vidas > 0) {
            vidas--;
        }
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
    }

    public void actualizarMovimiento() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            area.x -= velocidad * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            area.x += velocidad * Gdx.graphics.getDeltaTime();
        }
        if (area.x < 0) {
            area.x = 0;
        }
        if (area.x > Constantes.ANCHO - area.width) {
            area.x = Constantes.ANCHO - area.width;
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        if (!herido) {
            batch.draw(textura, area.x, area.y);
        } else {
            batch.draw(textura, area.x, area.y + MathUtils.random(-5, 5));
            tiempoHerido--;
            if (tiempoHerido <= 0) {
                herido = false;
            }
        }
    }
}
