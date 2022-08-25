package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.*;
import com.mygdx.game.BotellaHada;
import com.mygdx.game.Cacarol;
import com.mygdx.game.Ene2;
import com.mygdx.game.Enemigo;
import com.mygdx.game.InGameMenu;
import com.mygdx.game.InyeccionVelocidad;
import com.mygdx.game.Koala;
import com.mygdx.game.Moneda;
import com.mygdx.game.Murciegalo;
import com.mygdx.game.OptionMenu;
import com.mygdx.game.PocionVida;
import java.util.ArrayList;

public class MainScreen implements Screen {
    MyGdxGame game;
    Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Koala koala;
    Murciegalo murci;
    Cacarol caca;
    EnemyListo iaEne;
    int personaje = 0;
    ArrayList<Ene2> eneSuelo;
    ArrayList<Enemigo> eneSal;
    ArrayList<Moneda> arrayMonedas;
    PocionVida poti;
    boolean vidaCog = false;
    InyeccionVelocidad inyec;
    boolean inyecCog = false;
    BotellaHada botella;
    boolean botellaCog = false;
    private Music musicaFondo;
    private Music musicaMoneda;
    private Music loseGame;
    private Music golpe;
    BitmapFont font;
    SpriteBatch batch;
    int koalx = 5;
    int koaly = 30;
    boolean sonido = true;
    String mapa;
    int vidas = 3;
    int scoreMonedas = 0;
    int nivelMapa = 1;
    boolean tocado = false;
    public MainScreen(MyGdxGame aThis, boolean soni, String mapaAct, int num) {
        game = aThis;
        this.sonido = soni;
        this.mapa = mapaAct;
        this.personaje = num;
    }
    public MainScreen (MyGdxGame aThis, boolean soni, String mapaAct, int x, int y, int vida, int score, int persona){
        game = aThis;
        this.sonido = soni;
        this.mapa = mapaAct;  
        this.koalx = x;
        this.koaly = y;
        this.vidas = vida;
        this.scoreMonedas = score;
        this.personaje = persona;
    }
    
    @Override
    public void show() {
        map = new TmxMapLoader().load(mapa);
        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();
        loseGame = Gdx.audio.newMusic(Gdx.files.internal("final.mp3"));
        musicaMoneda = Gdx.audio.newMusic(Gdx.files.internal("coin.wav"));
        if(mapa == "Nivel1.tmx"){
            musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("opening.mp3"));
        }
        if(mapa == "Nivel2.tmx"){
            musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("kalbo.mp3"));
        }
        if(mapa == "Nivel3.tmx"){
            musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("Imposible.mp3"));
        }
        if(mapa == "Nivel3.5.tmx"){
            musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("pajotes.mp3"));
        }
        golpe = Gdx.audio.newMusic(Gdx.files.internal("golpe.mp3"));
        musicaFondo.setVolume((float) 0.4);
        stage = new Stage();
        stage.getViewport().setCamera(camera);
        
        batch = new SpriteBatch();
        font = new BitmapFont();
        
        if(personaje == 0){
            koala = new Koala();
            koala.layer = (TiledMapTileLayer) map.getLayers().get("walls");
            koala.setPosition(koalx, koaly);
            stage.addActor(koala);
            iaEne = new EnemyListo(koala);
            iaEne.layer = (TiledMapTileLayer) map.getLayers().get("walls");
            stage.addActor(iaEne);
        }
        if(personaje == 1){
            murci = new Murciegalo();
            murci.layer = (TiledMapTileLayer) map.getLayers().get("walls");
            murci.setPosition(koalx, koaly);
            stage.addActor(murci);
            iaEne = new EnemyListo(murci);
            iaEne.layer = (TiledMapTileLayer) map.getLayers().get("walls");
            stage.addActor(iaEne);
        }
        if(personaje == 2){
            caca = new Cacarol();
            caca.layer = (TiledMapTileLayer) map.getLayers().get("walls");
            caca.setPosition(koalx, koaly);
            stage.addActor(caca);
            iaEne = new EnemyListo(caca);
            iaEne.layer = (TiledMapTileLayer) map.getLayers().get("walls");
            stage.addActor(iaEne);
        }
        poti = new PocionVida();
        inyec = new InyeccionVelocidad();
        botella = new BotellaHada();
        eneSuelo = new ArrayList();
        arrayMonedas = new ArrayList();
        eneSal = new ArrayList();
        if(mapa == "Nivel1.tmx"){
            generarNuevaPoti();
            generarNuevaInyec();
            generarNuevaBotella();
            generarNuevoEnemigoIA();
            this.nivelMapa = 4;
            for(int i = 0; i < this.nivelMapa * 4; i++){
                GeneradorDeMonedas();
            }
            for(int i = 0; i < this.nivelMapa; i++){
                generarNuevoEnemigo();
                generarNuevoEnemigoSuelo();
            }
        }
        if(mapa == "Nivel2.tmx"){
            generarNuevaPoti();
            generarNuevaInyec();
            generarNuevaBotella();
            generarNuevoEnemigoIA();
            this.nivelMapa = 5;
            for(int i = 0; i < this.nivelMapa * 4; i++){
                GeneradorDeMonedas();
            }
            for(int i = 0; i < this.nivelMapa; i++){
                generarNuevoEnemigo();
                generarNuevoEnemigoSuelo();
            }
        }
        if(mapa == "Nivel3.tmx"){
            generarNuevaPoti();
            generarNuevaInyec();
            generarNuevaBotella();
            generarNuevoEnemigoIA();
            this.nivelMapa = 6;
            for(int i = 0; i < this.nivelMapa * 4; i++){
                GeneradorDeMonedas();
            }
            for(int i = 0; i < this.nivelMapa; i++){
                generarNuevoEnemigo();
                generarNuevoEnemigoSuelo();
            }
        }
        if(mapa == "Nivel3.5.tmx"){
            generarNuevaPoti();
            generarNuevaInyec();
            generarNuevaBotella();
            generarNuevoEnemigoIA();
            this.nivelMapa = 4;
            for(int i = 0; i < this.nivelMapa * 4; i++){
                GeneradorDeMonedas();
            }
            for(int i = 0; i < this.nivelMapa; i++){
                generarNuevoEnemigo();
                generarNuevoEnemigoSuelo();
            }
        }
    }
    public void generarNuevoEnemigoIA(){
        if(mapa == "Nivel1.tmx"){
            iaEne.setPosition(8, 20);
        }
        if(mapa == "Nivel2.tmx"){
            iaEne.setPosition(8, 49);
        }
        if(mapa == "Nivel3.tmx"){
            iaEne.setPosition(8, 35);
        }
        if(mapa == "Nivel3.5.tmx"){
            iaEne.setPosition(8, 20);
        }
    }
    public void generarNuevoEnemigo(){
        Enemigo eneSalt = new Enemigo();
        eneSalt.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        if(mapa == "Nivel1.tmx"){
            eneSalt.setPosition(MathUtils.random(7, 100), 20);
        }
        if(mapa == "Nivel2.tmx"){
            eneSalt.setPosition(MathUtils.random(7, 120), 49);
        }
        if(mapa == "Nivel3.tmx"){
            eneSalt.setPosition(MathUtils.random(7, 120), 35);
        }
        if(mapa == "Nivel3.5.tmx"){
            eneSalt.setPosition(MathUtils.random(7, 100), 20);
        }
        stage.addActor(eneSalt);
        eneSal.add(eneSalt);
        
        
    }
    public void generarNuevaPoti(){
        poti.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        if(mapa == "Nivel1.tmx"){
            poti.setPosition(37, 8);
        }
        if(mapa == "Nivel2.tmx"){
            poti.setPosition(30,40);
        }
        if(mapa == "Nivel3.tmx"){
            poti.setPosition(37,8);
        }
        if(mapa == "Nivel3.5.tmx"){
            poti.setPosition(37, 9);
        }
        stage.addActor(poti);
        
    }
    public void generarNuevaBotella(){
        botella.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        if(mapa == "Nivel1.tmx"){
            botella.setPosition(49, 10);
        }
        if(mapa == "Nivel2.tmx"){
            botella.setPosition(30,50);
        }
        if(mapa == "Nivel3.tmx"){
            botella.setPosition(49, 10);
        }
        if(mapa == "Nivel3.5.tmx"){
            botella.setPosition(49, 9);
        }
        stage.addActor(botella);
        
    }
    public void generarNuevaInyec(){
        inyec.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        if(mapa == "Nivel1.tmx"){
            inyec.setPosition(18, 11);
        }
        if(mapa == "Nivel2.tmx"){
            poti.setPosition(30,60);
        }
        if(mapa == "Nivel3.tmx"){
            inyec.setPosition(18, 11);
        }
        if(mapa == "Nivel3.5.tmx"){
            inyec.setPosition(18, 9);
        }
        stage.addActor(inyec);
        
    }
    public void generarNuevoEnemigoSuelo(){
        Ene2 eneSu = new Ene2();
        eneSu.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        if(mapa == "Nivel1.tmx"){
            eneSu.setPosition(MathUtils.random(7, 100), 20);
        }
        if(mapa == "Nivel2.tmx"){
            eneSu.setPosition(MathUtils.random(7, 120), 49);
        }
        if(mapa == "Nivel3.tmx"){
            eneSu.setPosition(MathUtils.random(7, 100), 30);
        }
        if(mapa == "Nivel3.5.tmx"){
            eneSu.setPosition(MathUtils.random(7, 100), 20);
        }
        stage.addActor(eneSu);
        eneSuelo.add(eneSu);
        
        
    }
    private void GeneradorDeMonedas() {
        Moneda monedas= new Moneda();
            monedas.layer = (TiledMapTileLayer) map.getLayers().get("walls");
            if(mapa == "Nivel1.tmx"){
                monedas.setPosition(MathUtils.random(7, 120), MathUtils.random(6, 10));
            }
            if(mapa == "Nivel2.tmx"){
                monedas.setPosition(MathUtils.random(7, 120), MathUtils.random(20, 27));
            }
            if(mapa == "Nivel3.tmx"){
                monedas.setPosition(MathUtils.random(7, 140), MathUtils.random(7, 20));
            }
            if(mapa == "Nivel3.5.tmx"){
                monedas.setPosition(MathUtils.random(7, 120), MathUtils.random(6, 10));
            }
            stage.addActor(monedas); 
            arrayMonedas.add(monedas);
    }
    
    public void comprobarColisionPoti(float xA, float xB, PocionVida poti){
        int x = (int) xA;
        int y = (int) xB;
        int potiX = (int) poti.getX();
        int potiY = (int) poti.getY(); 
        
        if (x == potiX && y == potiY) {
            if(this.vidaCog == false){
                this.vidas++;
                poti.setVisible(false);
                this.vidaCog = true;
            }
            
        }
        
    }
    public void comprobarColisionInyec(float xA, float xB, InyeccionVelocidad in){
        int x = (int) xA;
        int y = (int) xB;
        int inX = (int) in.getX();
        int inY = (int) in.getY(); 
        
        if (x == inX && y == inY) {
            if(this.inyecCog == false){
                if(personaje == 0){
                    koala.setContador(1);
                    koala.setMAX_VELOCITY(koala.getMAX_VELOCITY() + 7);
                }
                if(personaje == 1){
                    murci.setContador(1);
                    murci.setMAX_VELOCITY(murci.getMAX_VELOCITY() + 7);
                }
                if(personaje == 2){
                    caca.setContador(1);
                    caca.setMAX_VELOCITY(caca.getMAX_VELOCITY() + 7);
                }

                in.setVisible(false);
                this.inyecCog = true;
            }
            
        }
        
    }
    public void comprobarColisionBotella(float xA, float xB, BotellaHada bot){
        int x = (int) xA;
        int y = (int) xB;
        int inX = (int) bot.getX();
        int inY = (int) bot.getY(); 
        
        if (x == inX && y == inY) {
            if(this.botellaCog == false){
                if(personaje == 0){
                    koala.setContador2(1);
                    koala.setMAX_VEL2(koala.getMAX_VEL2() + 7);
                }
                if(personaje == 1){
                    murci.setContador2(1);
                    murci.setMAX_VEL2(murci.getMAX_VEL2() + 7);
                }
                if(personaje == 2){
                    caca.setContador2(1);
                    caca.setMAX_VEL2(caca.getMAX_VEL2() + 7);
                }
                bot.setVisible(false);
                this.botellaCog = true;
            }
            
        }
        
    }
    public void comprobarColisiones(Enemigo ene, float xA, float xB){
        int eneX = (int) ene.getX();
        int eneY = (int) ene.getY(); 
        int x = (int) xA;
        int y = (int) xB;
        System.out.println("koala x" + x);
        System.out.println("koala y" + y);
        /*System.out.println("enemigo x" + eneX);
        System.out.println("enemigo y" + eneY);
        */
        
        if(this.vidas == 0){
            this.scoreMonedas = 0;
            this.arrayMonedas.clear();
            this.eneSal.clear();
            this.eneSuelo.clear();
            musicaFondo.setVolume(0);
            game.setScreen(new OptionMenu(game, sonido));
        }
        if(eneY <= 0){
            eneSal.remove(ene);
            generarNuevoEnemigo();
            
        }
        if (x == eneX && y == eneY + 1) {
                golpe.play();
                ene.setVisible(false);
                eneSal.remove(ene);
                
        }
        if (x == eneX && y == eneY) {
            this.vidas--;
            if(personaje == 0){
                koala.setX((eneX - 2));
            }
            if(personaje == 1){
                murci.setX((eneX - 2));
            }
            if(personaje == 2){
                caca.setX((eneX - 2));
            }     
        }
        if((mapa == "Nivel1.tmx") && (x == 98) && (y == 3) ){
            musicaFondo.setVolume(0);
            game.setScreen(new OptionMenu(game, sonido));
        }
        if((mapa == "Nivel2.tmx") && (x == 156) && (y == 3) ){
            musicaFondo.setVolume(0);
            game.setScreen(new OptionMenu(game, sonido));
        }
        if((mapa == "Nivel3.tmx") && (x == 157) && (y == 3) ){
            musicaFondo.setVolume(0);
            mapa = "Nivel3.5.tmx";
            game.setScreen(new MainScreen(game, sonido, mapa, this.personaje));
        }
        if((mapa == "Nivel3.5.tmx") && (x == 156) && (y == 7) ){
            musicaFondo.setVolume(0);
            game.setScreen(new OptionMenu(game, sonido));
        }
        if(y == 0){
            this.vidas --;
            this.scoreMonedas = 0;
            if(personaje == 0){
                koala.setPosition(5, 30);
            }
            if(personaje == 1){
                murci.setPosition(5, 30);
            }
            if(personaje == 2){
                caca.setPosition(5, 30);
            }
            System.out.println(eneSal.size());
            
            for(int i = arrayMonedas.size(); i < this.nivelMapa * 5; i++){
                GeneradorDeMonedas();
            }
            for(int i = eneSal.size(); i < this.nivelMapa; i++){
                generarNuevoEnemigo();
            }
            System.out.println(eneSal.size());
            if(this.vidas == 0){
                this.arrayMonedas.clear();
                this.eneSal.clear();
                this.eneSuelo.clear();
                musicaFondo.setVolume(0);
                loseGame.play();
                game.setScreen(new OptionMenu(game, sonido));
            }
        }
    }
    public void comprobarColisionesSuelo(float xA, float xB, Ene2 eneSu){
        int ene2X = (int) eneSu.getX();
        int ene2Y = (int) eneSu.getY();
        int x = (int) xA;
        int y = (int) xB;
            if(this.vidas == 0){
                this.arrayMonedas.clear();
                this.eneSal.clear();
                this.eneSuelo.clear();
                musicaFondo.setVolume(0);
                loseGame.play();
                game.setScreen(new OptionMenu(game, sonido));
            }
        if(ene2Y <= 0){
            eneSuelo.remove(eneSu);
            generarNuevoEnemigoSuelo();
            
        }
        if (x == ene2X && y == ene2Y) {
            this.vidas--;
            if(personaje == 0){
                koala.setX((ene2X - 2));
            }
            if(personaje == 1){
                murci.setX((ene2X - 2));
            }
            if(personaje == 2){
                caca.setX((ene2X - 2));
            }
                
                
        }
        if (x == ene2X && y == ene2Y + 1) {
                golpe.play();
                eneSu.setVisible(false);
                eneSuelo.remove(eneSu);
                
        }
        if(y == 0){
            this.vidas --;
            this.scoreMonedas = 0;
            if(personaje == 0){
                koala.setPosition(5, 30);
            }
            if(personaje == 1){
                murci.setPosition(5, 30);
            }
            if(personaje == 2){
                caca.setPosition(5, 30);
            }
            for(int i = eneSuelo.size(); i < this.nivelMapa; i++){
                generarNuevoEnemigoSuelo();
            }
        }
    }
    public void comprobarColisionesListo(float xA, float xB, EnemyListo eneSu){
        int ene2X = (int) eneSu.getX();
        int ene2Y = (int) eneSu.getY();
        int x = (int) xA;
        int y = (int) xB;
        if(this.vidas == 0){
                this.arrayMonedas.clear();
                this.eneSal.clear();
                this.eneSuelo.clear();
                musicaFondo.setVolume(0);
                loseGame.play();
                game.setScreen(new OptionMenu(game, sonido));
        }
        if (x == ene2X && y == ene2Y) {
            this.vidas--;
            if(personaje == 0){
                koala.setX((ene2X - 2));
            }
            if(personaje == 1){
                murci.setX((ene2X - 2));
            }
            if(personaje == 2){
                caca.setX((ene2X - 2));
            }
                
                
        }
        if (x == ene2X && y == ene2Y + 1) {
                if(tocado == false){
                    golpe.play();
                    eneSu.setVisible(false);
                    this.tocado = true;
                }
                
                
        }
        if(y == 0){
            this.vidas --;
            this.scoreMonedas = 0;
            if(personaje == 0){
                koala.setPosition(5, 30);
            }
            if(personaje == 1){
                murci.setPosition(5, 30);
            }
            if(personaje == 2){
                caca.setPosition(5, 30);
            }
        }
    }
    public void comprobarColisionesMonedas(float xA, float xB, Moneda mon){
        int x = (int) xA;
        int y = (int) xB;
        int monX = (int) mon.getX();
        int monY = (int) mon.getY();
        if((x == monX && y == monY)){
            System.out.println("Entra en recoge moneda");
            musicaMoneda.play();
            this.scoreMonedas++;
            mon.setVisible(false);
            arrayMonedas.remove(mon);
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            musicaFondo.setVolume(0);
            if(personaje == 0){
                game.setScreen(new InGameMenu(game,sonido, koala.getX(), koala.getY(), this.mapa, this.vidas, this.scoreMonedas, this.personaje));
            }
            if(personaje == 1){
                game.setScreen(new InGameMenu(game,sonido, murci.getX(), murci.getY(), this.mapa, this.vidas, this.scoreMonedas, this.personaje));
            }
            if(personaje == 2){
                game.setScreen(new InGameMenu(game,sonido, caca.getX(), caca.getY(), this.mapa, this.vidas, this.scoreMonedas, this.personaje));
            }
        }
        if (personaje == 0){
            for(int i = 0; i < this.eneSal.size(); i++){
                comprobarColisiones(this.eneSal.get(i), koala.getX(), koala.getY());
            }
            for(int i = 0; i < this.eneSuelo.size(); i++){
                comprobarColisionesSuelo(koala.getX(), koala.getY(), this.eneSuelo.get(i));
            }
            for(int i = 0; i < this.arrayMonedas.size(); i++){
                comprobarColisionesMonedas(koala.getX(), koala.getY(), this.arrayMonedas.get(i));
            }
            if (personaje == 0){
                comprobarColisionPoti(koala.getX(), koala.getY(), this.poti);
            }
            comprobarColisionesListo(koala.getX(), koala.getY(), this.iaEne);
            comprobarColisionInyec(koala.getX(), koala.getY(), this.inyec);
            comprobarColisionBotella(koala.getX(), koala.getY(), this.botella);
            
            camera.position.x = koala.getX();
            camera.position.y = koala.getY();
        }
        if (personaje == 1){
            for(int i = 0; i < this.eneSal.size(); i++){
                comprobarColisiones(this.eneSal.get(i), murci.getX(), murci.getY());
            }
            for(int i = 0; i < this.eneSuelo.size(); i++){
                comprobarColisionesSuelo(murci.getX(), murci.getY(), this.eneSuelo.get(i));
            }
            for(int i = 0; i < this.arrayMonedas.size(); i++){
                comprobarColisionesMonedas(murci.getX(), murci.getY(), this.arrayMonedas.get(i));
            }
            if (personaje == 0){
                comprobarColisionPoti(murci.getX(), murci.getY(), this.poti);
            }
            comprobarColisionesListo(murci.getX(), murci.getY(), this.iaEne);
            comprobarColisionInyec(murci.getX(), murci.getY(), this.inyec);
            comprobarColisionBotella(murci.getX(), murci.getY(), this.botella);
            
            camera.position.x = murci.getX();
            camera.position.y = murci.getY();
        }
        if (personaje == 2){
            for(int i = 0; i < this.eneSal.size(); i++){
                comprobarColisiones(this.eneSal.get(i), caca.getX(), caca.getY());
            }
            for(int i = 0; i < this.eneSuelo.size(); i++){
                comprobarColisionesSuelo(caca.getX(), caca.getY(), this.eneSuelo.get(i));
            }
            for(int i = 0; i < this.arrayMonedas.size(); i++){
                comprobarColisionesMonedas(caca.getX(), caca.getY(), this.arrayMonedas.get(i));
            }
            if (personaje == 0){
                comprobarColisionPoti(caca.getX(), caca.getY(), this.poti);
            }
            comprobarColisionesListo(caca.getX(), caca.getY(), this.iaEne);
            comprobarColisionInyec(caca.getX(), caca.getY(), this.inyec);
            comprobarColisionBotella(caca.getX(), caca.getY(), this.botella);
            
            camera.position.x = caca.getX();
            camera.position.y = caca.getY();
        }
        camera.update();
        renderer.setView(camera);
        renderer.render();
        if(this.sonido == true){
            musicaFondo.setLooping(true);
            musicaFondo.play();
        }
        stage.act(delta);
        stage.draw();
        batch.begin();
        font.draw(batch, "Vidas: " + this.vidas , 0 , 455);
        font.draw(batch, "Score: " + this.scoreMonedas, 0 , 475);
        if(mapa == "Nivel1.tmx"){
            font.draw(batch, "Pantalla: 1", 0 , 435);
        }
        if(mapa == "Nivel2.tmx"){
            font.draw(batch, "Pantalla: 2", 0 , 435);
        }
        if(mapa == "Nivel3.tmx"){
            font.draw(batch, "Pantalla: 3", 0 , 435);
        }
        if(mapa == "Nivel3.5.tmx"){
            font.draw(batch, "Pantalla: 3.5", 0 , 435);
        }
        batch.end();
        
    }

    @Override
    public void dispose() {
        
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, 20 * width / height, 20);
    }

    @Override
    public void resume() {
    }

	
}
