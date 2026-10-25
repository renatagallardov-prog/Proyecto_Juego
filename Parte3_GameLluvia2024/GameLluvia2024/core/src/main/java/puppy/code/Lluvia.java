package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia implements Dibujable, Destruible {
    private static final long INTERVALO_NANOS = 100000000L;
    private static final int PROBABILIDAD_MALA = 20;
    private static final int PROBABILIDAD_DORADA = 8;

    private Array<ObjetoCaida> objetos;
    private long ultimaGota;
    private Music musica;

    public void crear() {
        objetos = new Array<ObjetoCaida>();
        musica = GestorRecursos.getInstance().getMusicaLluvia();
        musica.setLooping(true);
        musica.play();
        generarObjeto();
    }

    private void generarObjeto() {
        int sorteo = MathUtils.random(1, 100);
        ObjetoCaida objeto;
        if (sorteo <= PROBABILIDAD_MALA) {
            objeto = new GotaMala();
        } else if (sorteo <= PROBABILIDAD_MALA + PROBABILIDAD_DORADA) {
            objeto = new GotaDorada();
        } else {
            objeto = new GotaBuena();
        }
        objetos.add(objeto);
        ultimaGota = TimeUtils.nanoTime();
    }

    public void actualizarMovimiento(Tarro tarro) {
        if (TimeUtils.nanoTime() - ultimaGota > INTERVALO_NANOS) {
            generarObjeto();
        }
        float delta = Gdx.graphics.getDeltaTime();
        for (int i = objetos.size - 1; i >= 0; i--) {
            if (objetos.get(i).actualizar(tarro, delta)) {
                objetos.removeIndex(i);
            }
        }
    }

    public void reiniciar() {
        objetos.clear();
        generarObjeto();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        for (int i = 0; i < objetos.size; i++) {
            objetos.get(i).dibujar(batch);
        }
    }

    @Override
    public void destruir() {
        musica.stop();
        objetos.clear();
    }
}
