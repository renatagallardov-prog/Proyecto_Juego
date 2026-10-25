package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import java.util.Objects;

public final class Lluvia implements Dibujable, Destruible {
    private static final float INTERVALO = 0.1f;
    private final Array<ObjetoCaida> objetos = new Array<>();
    private final Texture texturaBuena;
    private final Texture texturaMala;
    private final Sound sonidoGota;
    private final Music musica;
    private float tiempoAcumulado;
    private boolean destruida;

    public Lluvia(Texture buena, Texture mala, Sound sonido, Music musica) {
        texturaBuena = Objects.requireNonNull(buena);
        texturaMala = Objects.requireNonNull(mala);
        sonidoGota = Objects.requireNonNull(sonido);
        this.musica = Objects.requireNonNull(musica);
        musica.setLooping(true);
        musica.play();
        generarObjeto();
    }

    private void generarObjeto() {
        int sorteo = MathUtils.random(1, 100);
        ObjetoCaida objeto;
        if (sorteo <= 20) {
            objeto = new GotaMala(texturaMala);
        } else if (sorteo <= 28) {
            objeto = new GotaDorada(texturaBuena, sonidoGota);
        } else {
            objeto = new GotaBuena(texturaBuena, sonidoGota);
        }
        objetos.add(objeto);
    }

    public void actualizarMovimiento(Tarro tarro, float delta) {
        comprobarActiva();
        Objects.requireNonNull(tarro);
        if (!Float.isFinite(delta) || delta < 0) {
            throw new IllegalArgumentException("Delta invalido");
        }
        if (tarro.estaHerido() || tarro.estaMuerto()) return;
        tiempoAcumulado += delta;
        if (tiempoAcumulado >= INTERVALO) {
            tiempoAcumulado = 0;
            generarObjeto();
        }
        for (int i = objetos.size - 1; i >= 0; i--) {
            if (objetos.get(i).actualizar(tarro, delta)) {
                objetos.removeIndex(i);
            }
            if (tarro.estaHerido() || tarro.estaMuerto()) break;
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        comprobarActiva();
        for (ObjetoCaida objeto : objetos) objeto.dibujar(batch);
    }

    @Override
    public void destruir() {
        if (destruida) return;
        musica.stop();
        objetos.clear();
        destruida = true;
    }

    private void comprobarActiva() {
        if (destruida) throw new IllegalStateException("Lluvia destruida");
    }
}
