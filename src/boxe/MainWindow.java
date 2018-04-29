/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxe;

import java.util.Random;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Laura
 */
public class MainWindow extends StackPane {

    LoadingScreen loads;
    
    //Various flags
    boolean loadingComplete = false;
    boolean paused = false;
    boolean uselesspaused = false;
    
    VBox scene;
    StackPane gamePane;
    HitPane hitPane;
    PowerPane p;
    Ring ring;
    
    //damage
    Random gen = new Random(System.currentTimeMillis());
    int u = 0;
    int r = 0;
    
    Text r_u_ready;
    Text inpausa;

    /*
    * Class constructor.
    * Parameters: instances of Ring and Power Pane
    */
    MainWindow(PowerPane pp, Ring r) {

        hitPane = new HitPane();
        gamePane = new StackPane();
        StackPane tutto = new StackPane();
        
        loads = new LoadingScreen();

        mwInit(pp, r);

        gamePane.getChildren().addAll(scene, r_u_ready, inpausa, hitPane);
        tutto.getChildren().addAll(loads);

        loads.timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                tutto.getChildren().remove(loads);
                tutto.getChildren().addAll(gamePane);
                loadingComplete = true;
            }
        });

        super.getChildren().addAll(tutto);
    }

    /*
    * initializes the game scene
    */
    private void mwInit(PowerPane pp, Ring r) {
        r_u_ready = new Text();
        r_u_ready.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        r_u_ready.setTextAlignment(TextAlignment.CENTER);
        r_u_ready.setFill(Color.YELLOW);
        r_u_ready.setStroke(Color.BLACK);
        r_u_ready.setText("ARE YOU READY?\nPlease hit [SPACE]");

        inpausa = new Text();
        inpausa.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        inpausa.setTextAlignment(TextAlignment.CENTER);
        inpausa.setFill(Color.WHITE);
        inpausa.setStroke(Color.RED);
        inpausa.setText("PAUSED");
        inpausa.setStrokeWidth(3.0);
        inpausa.setOpacity(0);

        p = pp;
        ring = r;
        VBox btns = layoutBTN();
        scene = new VBox();
        //following 2 lines needed to initialize all the animations
        //it is done during the loading screen
        ring.animationpb.play();
        ring.animationpr.play();
        scene.getChildren().addAll(r, pp, btns);
    }

    /*
    * initializes the "footer" of the game screen
    */
    private VBox layoutBTN() {
        VBox hb = new VBox();
        Text ctrls = new Text();
        ctrls.setText("CONTROLS\nHIT - [space] || CLOSE - [X] || PAUSE - [P]");
        ctrls.setTextAlignment(TextAlignment.CENTER);
        Text info = new Text();
        info.setText("GREEN ZONE: maximum damage - RED ZONE: minimum damage\n"
                + "You have 2 seconds to HIT the enemy, otherwise damage will be taken.");
        info.setTextAlignment(TextAlignment.CENTER);
        ctrls.setFill(Color.RED);
        ctrls.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        info.setFill(Color.RED);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(ctrls, info);
        return hb;
    }

    /*
    * "PAUSED" appear when game is paused
    */
    void mettiInPausa() {
        if (paused || uselesspaused) {
            inpausa.setOpacity(1.0);
        } else {
            inpausa.setOpacity(0.0);
        }
    }

    /*
    * assigns damage and calls functions for calculating HP and check victory
    */
    void newTurno() {
        if (ring.finito == false) {
            if (p.matchstart) {
                gamePane.getChildren().remove(r_u_ready);
                p.tt.play();
                p.matchstart = false;
            } else if (p.tt.getStatus() == Animation.Status.RUNNING) {
                p.tt.stop();
                ring.pugno();
                if (p.controllo() == "CRITICAL"){
                    r = gen.nextInt(11);
                    u = gen.nextInt(6) + 55;
                    hitPane.segnaCritico(0);
                } else if (p.controllo() == "VERDE") {
                    u = gen.nextInt(11) + 45;
                    r = gen.nextInt(36);
                } else if (p.controllo() == "GIALLO") {
                    u = gen.nextInt(21) + 25;
                    r = gen.nextInt(16) + 35;
                } else if (p.controllo() == "ROSSO") {
                    u = gen.nextInt(26);
                    r = gen.nextInt(16) + 45;
                    if(r>=50){
                        hitPane.segnaCritico(1);
                    }
                }
                hitPane.segnaDanno(u, r);
                ring.rivale.aggiornaHP(u);
                ring.utente.aggiornaHP(r);
                System.out.println(p.controllo() + " " + u + " " + r);
                //ring.aggiornaStats();
                ring.controllaVittoria();
                ring.aggiornaStats();
            } else {
                if (p.moving == false) {
                    u = 0;
                    ring.rivale.aggiornaHP(u);
                    r = gen.nextInt(61);
                    ring.utente.aggiornaHP(r);
                    System.out.println(p.controllo() + " " + u + " " + r);
                    ring.pugnoR();
                    ring.aggiornaStats();
                    ring.controllaVittoria();
                    p.tt.play();
                    p.moving = true;
                } else {
                    p.tt.play();
                }
            }
        } else if (ring.finito == true) {
            ring.pugili.getChildren().clear();
            ring.pugili.getChildren().addAll(ring.blu, ring.rosso);
            ring.animationb.play();
            ring.animationr.play();
            ring.utente.hp = 500;
            ring.rivale.hp = 500;
            ring.aggiornaStats();
            ring.finito = false;
            ring.pugili.getChildren().remove(ring.vinto);
        }
    }

}
