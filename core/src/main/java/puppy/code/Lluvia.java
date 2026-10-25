package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.Objects;

public final class Lluvia implements Dibujable, Destruible {
    private final Array<Rectangle> posiciones = new Array<>();
    private final Array<Integer> tipos = new Array<>();
    private final Texture texturaBuena;
    private final Texture texturaMala;
    private final Sound sonidoGota;
    private final Music musica;
    private float tiempoAcumulado;

    public Lluvia(Texture buena, Texture mala, Sound sonido, Music musica) {
        texturaBuena = Objects.requireNonNull(buena);
        texturaMala = Objects.requireNonNull(mala);
        sonidoGota = Objects.requireNonNull(sonido);
        this.musica = Objects.requireNonNull(musica);
    }

    public void crear() {
        musica.setLooping(true);
        musica.play();
        generarGota();
    }

    private void generarGota() {
        posiciones.add(new Rectangle(
                MathUtils.random(0, Constantes.ANCHO - Constantes.TAMANO_OBJETO),
                Constantes.ALTO, Constantes.TAMANO_OBJETO,
                Constantes.TAMANO_OBJETO));
        tipos.add(MathUtils.random(1, 100) <= 20 ? 1 : 2);
    }

    public void actualizarMovimiento(Tarro tarro) {
        if (tarro.estaHerido() || tarro.estaMuerto()) return;
        float delta = Gdx.graphics.getDeltaTime();
        tiempoAcumulado += delta;
        if (tiempoAcumulado >= 0.1f) {
            tiempoAcumulado = 0;
            generarGota();
        }
        for (int i = posiciones.size - 1; i >= 0; i--) {
            Rectangle gota = posiciones.get(i);
            gota.y -= 300 * delta;
            boolean retirar = gota.y + gota.height < 0;
            if (!retirar && tarro.colisionaCon(gota)) {
                if (tipos.get(i) == 1) {
                    tarro.dañar();
                } else {
                    tarro.sumarPuntos(10);
                    sonidoGota.play();
                }
                retirar = true;
            }
            if (retirar) {
                posiciones.removeIndex(i);
                tipos.removeIndex(i);
            }
            if (tarro.estaHerido() || tarro.estaMuerto()) break;
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        for (int i = 0; i < posiciones.size; i++) {
            Rectangle gota = posiciones.get(i);
            Texture textura = tipos.get(i) == 1 ? texturaMala : texturaBuena;
            batch.draw(textura, gota.x, gota.y);
        }
    }

    @Override
    public void destruir() {
        musica.stop();
        posiciones.clear();
        tipos.clear();
    }
}
