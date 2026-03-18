package com.davidbargados;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements  ApplicationListener {
    private SpriteBatch batch;
    private Texture image;

    private static final int COLS = 6;
    private static final int ROWS = 5;
    float amplada;
    float altura;

    public FitViewport viewport;

    private int x,y;
    Animation<TextureRegion> animacioCaminar;
    Texture walkSheet;

    float stateTime;
    @Override
    public void create() {
        viewport = new FitViewport(8,5);
        amplada = viewport.getWorldWidth();
        altura = viewport.getWorldHeight();
        image = new Texture("libgdx.png");
        walkSheet = new Texture(Gdx.files.internal("sprite-animation4.png"));
        TextureRegion[][] tmp = TextureRegion.split(
            walkSheet,
            walkSheet.getWidth() / COLS,
            walkSheet.getHeight() / ROWS
        );


        TextureRegion[] walkFrames = new TextureRegion[COLS * ROWS];
        int idx = 0;
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                walkFrames[idx++] = tmp[i][j];
            }
        }


        animacioCaminar = new Animation<TextureRegion>(0.025f,walkFrames);

        batch = new SpriteBatch();
        stateTime = 0f;

    }



    @Override
    public void pause(){}
    public void resume(){}

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animacioCaminar.getKeyFrame(stateTime,true);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(currentFrame, 1, 1, 2, 2);
        batch.end();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        walkSheet.dispose();
    }
}
