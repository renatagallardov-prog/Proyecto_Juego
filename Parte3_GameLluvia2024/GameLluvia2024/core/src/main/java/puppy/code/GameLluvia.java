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

public class GameLluvia extends ApplicationAdapter {
    private enum Estado {
        JUGANDO,
        FIN
    }

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private Lluvia lluvia;
    private Array<Dibujable> dibujables;
    private Array<Destruible> destruibles;
    private Estado estado;

    @Override
    public void create() {
        font = new BitmapFont();
        GestorRecursos recursos = GestorRecursos.getInstance();

        tarro = new TarroBuilder()
                .conTextura(recursos.getTexturaTarro())
                .conSonidoHerido(recursos.getSonidoHerido())
                .conVidas(3)
                .conVelocidad(400)
                .conTiempoHerido(50)
                .build();

        lluvia = new Lluvia();
        lluvia.crear();

        dibujables = new Array<Dibujable>();
        dibujables.add(tarro);
        dibujables.add(lluvia);

        destruibles = new Array<Destruible>();
        destruibles.add(lluvia);
        destruibles.add(recursos);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constantes.ANCHO, Constantes.ALTO);
        batch = new SpriteBatch();
        estado = Estado.JUGANDO;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 720, 475);

        if (estado == Estado.JUGANDO) {
            if (!tarro.estaHerido()) {
                tarro.actualizarMovimiento();
                lluvia.actualizarMovimiento(tarro);
            }
        }

        for (int i = 0; i < dibujables.size; i++) {
            dibujables.get(i).dibujar(batch);
        }

        if (estado == Estado.JUGANDO && tarro.estaMuerto() && !tarro.estaHerido()) {
            estado = Estado.FIN;
        }

        if (estado == Estado.FIN) {
            font.draw(batch, "FIN DEL JUEGO", 0, 300, Constantes.ANCHO, Align.center, false);
            font.draw(batch, "Puntaje final: " + tarro.getPuntos(), 0, 270, Constantes.ANCHO, Align.center, false);
            font.draw(batch, "Presiona ENTER para reiniciar", 0, 240, Constantes.ANCHO, Align.center, false);
        }

        batch.end();

        if (estado == Estado.FIN && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            reiniciar();
        }
    }

    private void reiniciar() {
        tarro.reiniciar();
        lluvia.reiniciar();
        estado = Estado.JUGANDO;
    }

    @Override
    public void dispose() {
        for (int i = 0; i < destruibles.size; i++) {
            destruibles.get(i).destruir();
        }
        batch.dispose();
        font.dispose();
    }
}
