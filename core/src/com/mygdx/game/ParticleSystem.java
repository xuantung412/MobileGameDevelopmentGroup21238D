package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ParticleSystem {
    SpriteBatch batch;
    Texture img;
    public static final int MAX_PARTICLES = 128;
    public static final float EXPLOSION_LIFETIME = 0.5f;
    public static final float SMOKE_LIFETIME = 3.0f;
    public static enum Type  { NONE, EXPLOSION, SMOKE};
    Texture spritesheet;
    TextureRegion[] explosionFrames = new TextureRegion[10];
    TextureRegion[] smokeFrames = new TextureRegion[6];
    Type[] type = new Type[128];
    Vector2[] position = new Vector2[128];
    Vector2[] velocity = new Vector2[128];
    float[] lifetime = new float[128];



    public void init(){
        spritesheet = new Texture(Gdx.files.internal("pic.jpg"));
        explosionFrames[0] = new TextureRegion(spritesheet,2,2,87,87);
        explosionFrames[1] = new TextureRegion(spritesheet,94,2,87,87);
        explosionFrames[2] = new TextureRegion(spritesheet,186,2,87,87);
        explosionFrames[3] = new TextureRegion(spritesheet,278,2,87,87);
        explosionFrames[4] = new TextureRegion(spritesheet,370,2,87,87);
        explosionFrames[5] = new TextureRegion(spritesheet,2,94,87,87);
        explosionFrames[6] = new TextureRegion(spritesheet,94,94,87,87);
        explosionFrames[7] = new TextureRegion(spritesheet,186,94,87,87);
        explosionFrames[8] = new TextureRegion(spritesheet,278,94,87,87);
        explosionFrames[9] = new TextureRegion(spritesheet,370,94,87,87);

        smokeFrames[0] = new TextureRegion(spritesheet,2,306,45,45);
        smokeFrames[1] = new TextureRegion(spritesheet,52,306,45,45);
        smokeFrames[2] = new TextureRegion(spritesheet,102,306,45,45);
        smokeFrames[3] = new TextureRegion(spritesheet,152,306,45,45);
        smokeFrames[4] = new TextureRegion(spritesheet,202,306,45,45);
        smokeFrames[5] = new TextureRegion(spritesheet,252,306,45,45);

        for(int i =0; i < this.position.length; i++) {
            this.type[i] = Type.NONE;
            this.position[i] = new Vector2();
            this.velocity[i] = new Vector2();
        }
    }

    public int spawn(Type t){
        //An early-fail stupid check
        if( t ==null) return -1;
        if(t == Type.NONE) return -1;
        //Find a free index by looping through from the beginning
        int i = -1;
        for( int free =0; free < MAX_PARTICLES; free ++){
            if(type[free] == Type.NONE){
                i = free;
                break;
            }
        }
        //Return a fail indicator if no free index was found
        if(i < 0) return -1;

        //Register the index as in-use
        type[i] = t;

        this.position[i] = new Vector2(0,0);
        this.velocity[i] = new Vector2(0,0);
        if(t == Type.EXPLOSION){
            this.lifetime[i] = EXPLOSION_LIFETIME;
        }
        else if( t == Type.SMOKE){
            this.velocity[i] = new Vector2(this.velocity[i].x + MathUtils.random(-20f, 20f),this.velocity[i].y + MathUtils.random(-20f, 20f));
            this.lifetime[i] = SMOKE_LIFETIME;
        }
        return i;
    }

    public void render(SpriteBatch batch){
        int index = 0;
        for( int i =0; i < type.length; i++) {
            if (type[i] != Type.NONE) {
                if (type[i] == Type.EXPLOSION) {
                    //float totalLifeTime = this.EXPLOSION_LIFETIME + this.SMOKE_LIFETIME;
                    index = (int) (lifetime[i] / EXPLOSION_LIFETIME * 10);//totalLifeTime * (this.explosionFrames.length + this.smokeFrames.length));

                    if (index < 0) {
                        index = 0;
                        //throw new IndexOutOfBoundsException();
                    }

                    batch.draw(explosionFrames[index], position[i].x, position[i].y);
                }
            } else {
                if (type[i] == Type.SMOKE) {
                    //float totalLifeTime = this.EXPLOSION_LIFETIME + this.SMOKE_LIFETIME;
                    index = (int) (lifetime[i] / SMOKE_LIFETIME * 6);//totalLifeTime * (this.explosionFrames.length + this.smokeFrames.length));

                    if (index < 0) {
                        index = 0;
                        //throw new IndexOutOfBoundsException();
                    }
                    batch.draw(smokeFrames[index], position[i].x, position[i].y);
                }
            }
        }
    }



    public void update(float deltaTime){
        for(int i =0; i < type.length; i++){
            if( type[i] != Type.NONE && lifetime[i] < 0){
                type[i] = Type.NONE;
            }
            else{
                position[i].mulAdd(velocity[i], deltaTime);

            }
            lifetime[i] -= deltaTime;

        }

    }

    public void create () {

    }


    public void dispose () {
        batch.dispose();
        img.dispose();
        spritesheet.dispose();
    }
}