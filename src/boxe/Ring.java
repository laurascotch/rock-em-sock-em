/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxe;

import boxe.images.ImageResources;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mylib.SpriteAnimation;

/**
 *
 * @author Laura
 */
public class Ring extends HBox {

    Pugile utente;
    Pugile rivale;
    Text hpU;
    Text hpR;
    HBox stats;
    HBox pugili;
    StackPane scena;
    boolean finito = false;
    Text vinto;
    ImageView rosso;
    ImageView blu;
    Animation animationb;
    Animation animationr;
    ImageView pugnoblu;
    Animation animationpb;
    ImageView pugnorosso;
    Animation animationpr;
    ImageView vincerosso;
    ImageView perderosso;
    ImageView vinceblu;
    ImageView perdeblu;
    Rectangle bluHP;
    Rectangle rossoHP;
    HBox graficaStats;

    Ring() {
        Image ring = new Image(ImageResources.class.getResourceAsStream("ring.jpg"));
        ImageView ringbg = new ImageView(ring);

        inizializzaImmagini();
        
        hpU = new Text();
        hpR = new Text();
        utente = new Pugile();
        rivale = new Pugile();
        hpU.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        hpU.setFill(Color.CORNFLOWERBLUE);
        hpU.setStroke(Color.NAVY);
        hpR.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        hpR.setFill(Color.RED);
        hpR.setStroke(Color.DARKRED);
        hpU.setText("Player - HP: " + utente.getHP() + " ");
        hpR.setText("HP: " + rivale.getHP() + "  -  CPU");
        
        bluHP = new Rectangle(250,15);
        bluHP.setFill(Color.BLUE);
        Rectangle bgbluHP = new Rectangle(250,15);
        bgbluHP.setFill(Color.LIGHTBLUE);
        rossoHP = new Rectangle(250,15);
        rossoHP.setFill(Color.RED);
        Rectangle bgrossoHP = new Rectangle(250,15);
        bgrossoHP.setFill(Color.PINK);
        
        HBox bggrafica = new HBox();
        bggrafica.setSpacing(100);
        bggrafica.setAlignment(Pos.TOP_CENTER);
        bggrafica.getChildren().addAll(bgbluHP,bgrossoHP);
        graficaStats = new HBox();
        graficaStats.setSpacing(100);
        graficaStats.setAlignment(Pos.TOP_CENTER);
        graficaStats.getChildren().addAll(bluHP,rossoHP);
        StackPane grafica = new StackPane();
        grafica.getChildren().addAll(bggrafica,graficaStats);
        VBox completeStats = new VBox();
        stats = new HBox();
        stats.setSpacing(100);
        stats.setAlignment(Pos.TOP_CENTER);
        stats.getChildren().addAll(hpU, hpR);
        completeStats.getChildren().addAll(stats,grafica);
        pugili = new HBox();
        pugili.setAlignment(Pos.CENTER);
        pugili.setAlignment(Pos.BOTTOM_CENTER);
        pugili.getChildren().addAll(blu, rosso);
        scena = new StackPane();
        scena.getChildren().addAll(ringbg, pugili, completeStats);
        super.getChildren().addAll(scena);
    }
    
    private void inizializzaImmagini(){
        Image vb = new Image(ImageResources.class.getResourceAsStream("bluvince.png"));
        vinceblu = new ImageView(vb);
        Image vr = new Image(ImageResources.class.getResourceAsStream("rossovince.png"));
        vincerosso = new ImageView(vr);
        Image peb = new Image(ImageResources.class.getResourceAsStream("bluperde.png"));
        perdeblu = new ImageView(peb);
        Image per = new Image(ImageResources.class.getResourceAsStream("rossoperde.png"));
        perderosso = new ImageView(per);

        Image pb = new Image(ImageResources.class.getResourceAsStream("pugnoblu.png"));
        pugnoblu = new ImageView(pb);
        animationpb = new SpriteAnimation(pugnoblu, Duration.millis(500), 5, 5, 1, 0, 175, 175);

        Image pr = new Image(ImageResources.class.getResourceAsStream("pugnorosso.png"));
        pugnorosso = new ImageView(pr);
        animationpr = new SpriteAnimation(pugnorosso, Duration.millis(500), 5, 5, 1, 0, 175, 175);
        animationpr.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (finito == false) {
                    pugili.getChildren().clear();
                    pugili.getChildren().addAll(blu, rosso);
                    animationb.play();
                    animationr.play();
                }
            }
        });
        
        Image b = new Image(ImageResources.class.getResourceAsStream("blu.png"));
        blu = new ImageView(b);
        animationb = new SpriteAnimation(blu, Duration.millis(1000), 6, 6, 1, 0, 175, 175);
        animationb.setCycleCount(Animation.INDEFINITE);
        animationb.play();

        Image r = new Image(ImageResources.class.getResourceAsStream("rosso.png"));
        rosso = new ImageView(r);
        animationr = new SpriteAnimation(rosso, Duration.millis(1000), 6, 6, 1, 0, 175, 175);
        animationr.setCycleCount(Animation.INDEFINITE);
        animationr.play();
    }

    /*
    *
    * Function that updates the stats (both user and cpu)
    *
    */
    void aggiornaStats() {
        stats.getChildren().clear();
        hpU.setText("Player - HP: " + utente.getHP() + " ");
        hpR.setText("HP: " + rivale.getHP() + "  -  CPU");
        stats.getChildren().addAll(hpU, hpR);
        graficaStats.getChildren().clear();
        bluHP.setWidth(utente.getHP()/2);
        rossoHP.setWidth(rivale.getHP()/2);
        graficaStats.getChildren().addAll(bluHP,rossoHP);
    }

    /*
    *
    * This function is used to check whether the user or the cpu is able to continue the match
    *
    */
    void controllaVittoria() {
        if (utente.hp > 0 && rivale.hp <= 0) {
            rivale.hp = 0;
            dichiaraVittoria();
            System.out.println("HAI VINTO");
        } else if (utente.hp <= 0 && rivale.hp > 0) {
            utente.hp = 0;
            dichiaraSconfitta();
            System.out.println("K.O.");
        } else if (utente.hp <= 0 && rivale.hp <= 0) {
            if (utente.hp > rivale.hp) {
                utente.hp = 1;
                rivale.hp = 0;
                dichiaraVittoria();
                System.out.println("HAI VINTO");
            } else {
                rivale.hp = 1;
                utente.hp = 0;
                dichiaraSconfitta();
                System.out.println("K.O.");
            }
        }
    }

    /*
    *
    * This function is used to declare user's victory
    *
    */
    void dichiaraVittoria() {
        finito = true;
        vinto = new Text();
        vinto.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        vinto.setFill(Color.GOLD);
        vinto.setStroke(Color.SILVER);
        vinto.setText("VITTORIA");
        //scena.getChildren().add(vinto);
        animationb.stop();
        animationr.stop();
        pugili.getChildren().clear();
        pugili.getChildren().addAll(vinto,vinceblu, perderosso);
    }

    /*
    *
    * This function is used to declare user's K.O.
    *
    */
    void dichiaraSconfitta() {
        finito = true;
        vinto = new Text();
        vinto.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        vinto.setFill(Color.BLACK);
        vinto.setStroke(Color.WHITE);
        vinto.setText("K.O.");
        //scena.getChildren().add(vinto);
        animationb.stop();
        animationr.stop();
        pugili.getChildren().clear();
        pugili.getChildren().addAll(perdeblu,vincerosso,vinto);
    }

    /*
    *
    *  This function is used when both the player and the CPU are able to hit
    *
    */
    void pugno() {
        pugili.getChildren().remove(blu);
        pugili.getChildren().remove(rosso);
        animationb.stop();
        animationr.stop();
        animationpb.play();
        animationpr.play();
        pugili.getChildren().addAll(pugnoblu, pugnorosso);
    }
    
    /*
    *
    * This function is used when only the CPU is able to hit
    *
    */
    void pugnoR(){
        pugili.getChildren().remove(blu);
        pugili.getChildren().remove(rosso);
        animationr.stop();
        animationpr.play();
        pugili.getChildren().addAll(blu, pugnorosso);
    }

}
