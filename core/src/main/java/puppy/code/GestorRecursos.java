package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public final class GestorRecursos implements Destruible {
    private static GestorRecursos instancia;
    private final Texture texturaTarro;
    private final Texture texturaGotaBuena;
    private final Texture texturaGotaMala;
    private final Sound sonidoGota;
    private final Sound sonidoHerido;
    private final Music musicaLluvia;
    private boolean destruido;

    private GestorRecursos() {
        texturaTarro = new Texture(Gdx.files.internal("bucket.png"));
        texturaGotaBuena = new Texture(Gdx.files.internal("drop.png"));
        texturaGotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        sonidoGota = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        musicaLluvia = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
    }

    public static synchronized GestorRecursos getInstance() {
        if (instancia == null) {
            instancia = new GestorRecursos();
        }
        return instancia;
    }

    public Texture getTexturaTarro() {
        return texturaTarro;
    }

    public Texture getTexturaGotaBuena() {
        return texturaGotaBuena;
    }

    public Texture getTexturaGotaMala() {
        return texturaGotaMala;
    }

    public Sound getSonidoGota() {
        return sonidoGota;
    }

    public Sound getSonidoHerido() {
        return sonidoHerido;
    }

    public Music getMusicaLluvia() {
        return musicaLluvia;
    }

    @Override
    public void destruir() {
        if (destruido) {
            return;
        }
        musicaLluvia.stop();
        texturaTarro.dispose();
        texturaGotaBuena.dispose();
        texturaGotaMala.dispose();
        sonidoGota.dispose();
        sonidoHerido.dispose();
        musicaLluvia.dispose();
        destruido = true;
        instancia = null;
    }
}
