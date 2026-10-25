package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import java.util.Objects;
import java.util.function.Supplier;

public final class Lluvia implements Dibujable, Destruible {
    private static final float INTERVALO = 0.1f;
    private final Array<ObjetoCaida> objetos = new Array<>();
    private final FabricaGotas fabricaGotas;
    private final Music musica;
    private Supplier<EstrategiaCaida> fabricaMovimiento;
    private float tiempoAcumulado;
    private boolean destruida;

    public Lluvia(FabricaGotas fabrica, Music musica) {
        fabricaGotas = Objects.requireNonNull(fabrica);
        this.musica = Objects.requireNonNull(musica);
        musica.setLooping(true);
        musica.play();
        generarObjeto();
    }

    private void generarObjeto() {
        ObjetoCaida objeto = Objects.requireNonNull(fabricaGotas.crear());
        if (fabricaMovimiento != null) {
            objeto.cambiarEstrategia(fabricaMovimiento.get());
        }
        objetos.add(objeto);
    }

    public void cambiarEstrategia(Supplier<EstrategiaCaida> fabrica) {
        comprobarActiva();
        Objects.requireNonNull(fabrica);
        for (ObjetoCaida objeto : objetos) {
            objeto.cambiarEstrategia(fabrica.get());
        }
        fabricaMovimiento = fabrica;
    }

    public void actualizarMovimiento(Tarro tarro, float delta) {
        comprobarActiva();
        Objects.requireNonNull(tarro);
        if (!Float.isFinite(delta) || delta < 0) {
            throw new IllegalArgumentException("Delta invalido");
        }
        if (tarro.estaHerido() || tarro.estaMuerto()) {
            return;
        }
        tiempoAcumulado += delta;
        if (tiempoAcumulado >= INTERVALO) {
            tiempoAcumulado = 0;
            generarObjeto();
        }
        for (int i = objetos.size - 1; i >= 0; i--) {
            if (objetos.get(i).actualizar(tarro, delta)) {
                objetos.removeIndex(i);
            }
            if (tarro.estaHerido() || tarro.estaMuerto()) {
                break;
            }
        }
    }

    public void reiniciar() {
        comprobarActiva();
        objetos.clear();
        fabricaMovimiento = null;
        tiempoAcumulado = 0;
        generarObjeto();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        comprobarActiva();
        for (ObjetoCaida objeto : objetos) {
            objeto.dibujar(batch);
        }
    }

    @Override
    public void destruir() {
        if (destruida) {
            return;
        }
        musica.stop();
        objetos.clear();
        destruida = true;
    }

    private void comprobarActiva() {
        if (destruida) {
            throw new IllegalStateException("Lluvia destruida");
        }
    }
}
