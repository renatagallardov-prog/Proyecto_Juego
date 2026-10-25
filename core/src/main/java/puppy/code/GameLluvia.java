package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public final class GameLluvia extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private Lluvia lluvia;
    private final Array<Dibujable> dibujables = new Array<>();
    private final Array<Destruible> destruibles = new Array<>();

    @Override
    public void create() {
        GestorRecursos recursos = GestorRecursos.getInstance();
        tarro = new TarroBuilder()
                .conTextura(recursos.getTexturaTarro())
                .conSonidoHerido(recursos.getSonidoHerido())
                .conVidas(3).conVelocidad(400)
                .conTiempoHerido(0.8f).build();
        lluvia = new Lluvia(recursos.getTexturaGotaBuena(),
                recursos.getTexturaGotaMala(), recursos.getSonidoGota(),
                recursos.getMusicaLluvia());
        lluvia.crear();
        dibujables.add(tarro);
        dibujables.add(lluvia);
        destruibles.add(lluvia);
        destruibles.add(recursos);
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constantes.ANCHO, Constantes.ALTO);
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        tarro.actualizarEstado(delta);
        if (!tarro.estaHerido() && !tarro.estaMuerto()) {
            int direccion = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) direccion--;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) direccion++;
            tarro.actualizarMovimiento(direccion, delta);
            lluvia.actualizarMovimiento(tarro);
        }
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Dibujable objeto : dibujables) objeto.dibujar(batch);
        font.draw(batch, "Puntaje: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas: " + tarro.getVidas(), 720, 475);
        batch.end();
    }

    @Override
    public void dispose() {
        for (Destruible objeto : destruibles) objeto.destruir();
        batch.dispose();
        font.dispose();
    }
}
