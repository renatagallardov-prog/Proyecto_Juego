package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.function.Supplier;

public final class GameLluvia extends ApplicationAdapter {
    private enum Estado {
        JUGANDO, FIN
    }

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private Lluvia lluvia;
    private final Array<Dibujable> dibujables = new Array<>();
    private final Array<Destruible> destruibles = new Array<>();
    private Estado estado;
    private String modoMovimiento = "por tipo";

    @Override
    public void create() {
        GestorRecursos recursos = GestorRecursos.getInstance();
        tarro = new TarroBuilder()
                .conTextura(recursos.getTexturaTarro())
                .conSonidoHerido(recursos.getSonidoHerido())
                .conVidas(3).conVelocidad(400)
                .conTiempoHerido(0.8f).build();
        FabricaGotasPonderada fabrica = new FabricaGotasPonderada()
                .registrar(72, () -> new GotaBuena(
                        recursos.getTexturaGotaBuena(),
                        recursos.getSonidoGota()))
                .registrar(20, () -> new GotaMala(
                        recursos.getTexturaGotaMala()))
                .registrar(8, () -> new GotaDorada(
                        recursos.getTexturaGotaBuena(),
                        recursos.getSonidoGota()));
        lluvia = new Lluvia(fabrica, recursos.getMusicaLluvia());
        dibujables.add(tarro);
        dibujables.add(lluvia);
        destruibles.add(lluvia);
        destruibles.add(recursos);
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constantes.ANCHO, Constantes.ALTO);
        batch = new SpriteBatch();
        estado = Estado.JUGANDO;
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        if (estado == Estado.JUGANDO) {
            tarro.actualizarEstado(delta);
            procesarCambioEstrategia();
            if (!tarro.estaHerido() && !tarro.estaMuerto()) {
                int direccion = 0;
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) direccion--;
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) direccion++;
                tarro.actualizarMovimiento(direccion, delta);
                lluvia.actualizarMovimiento(tarro, delta);
            }
            if (tarro.estaMuerto() && !tarro.estaHerido()) {
                estado = Estado.FIN;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            reiniciar();
        }
        dibujarEscena();
    }

    private void procesarCambioEstrategia() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            seleccionarMovimiento("recta", () -> new CaidaRecta(300));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            seleccionarMovimiento("acelerada",
                    () -> new CaidaAcelerada(220, 120));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            seleccionarMovimiento("zigzag", () -> new CaidaZigzag(350, 150));
        }
    }

    private void seleccionarMovimiento(String nombre,
            Supplier<EstrategiaCaida> fabrica) {
        lluvia.cambiarEstrategia(fabrica);
        modoMovimiento = nombre;
    }

    private void dibujarEscena() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Dibujable objeto : dibujables) objeto.dibujar(batch);
        font.draw(batch, "Puntaje: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas: " + tarro.getVidas(), 720, 475);
        font.draw(batch, "Movimiento: " + modoMovimiento, 5, 450);
        font.draw(batch, "1 Recta   2 Acelerada   3 Zigzag", 5, 425);
        if (estado == Estado.FIN) {
            font.draw(batch, "FIN DEL JUEGO", 0, 300,
                    Constantes.ANCHO, Align.center, false);
            font.draw(batch, "Puntaje final: " + tarro.getPuntos(),
                    0, 270, Constantes.ANCHO, Align.center, false);
            font.draw(batch, "Presiona ENTER para reiniciar", 0, 240,
                    Constantes.ANCHO, Align.center, false);
        }
        batch.end();
    }

    private void reiniciar() {
        tarro.reiniciar();
        lluvia.reiniciar();
        modoMovimiento = "por tipo";
        estado = Estado.JUGANDO;
    }

    @Override
    public void dispose() {
        for (Destruible objeto : destruibles) objeto.destruir();
        batch.dispose();
        font.dispose();
    }
}
