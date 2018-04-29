/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxe;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Laura
 */
public class HitPane extends Pane{
    
    Text utente = new Text();
    Text rivale = new Text();
    Text critico = new Text();
    Timeline timeline;
    Timeline crTL;
    
    /*
    * class constructor
    * contains animations for damage to appear at any turn
    */
    HitPane(){
        
        textInit(utente," ",100,Color.RED);
        textInit(rivale," ",480,Color.BLUE);
        textInit(critico,"CRITICAL!",230,Color.WHITE);
        
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(utente.yProperty(), 100),
                        new KeyValue(rivale.yProperty(), 100),
                        new KeyValue(utente.opacityProperty(), 1.0),
                        new KeyValue(rivale.opacityProperty(), 1.0)),
                new KeyFrame(new Duration(1500),
                        new KeyValue(utente.yProperty(), 0),
                        new KeyValue(rivale.yProperty(), 0),
                        new KeyValue(utente.opacityProperty(), 0),
                        new KeyValue(rivale.opacityProperty(), 0))
        );
        
        crTL = new Timeline();
        crTL.setCycleCount(1);
        crTL.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(critico.yProperty(), 100),
                        new KeyValue(critico.opacityProperty(), 1.0)),
                new KeyFrame(new Duration(1500),
                        new KeyValue(critico.yProperty(), 0),
                        new KeyValue(critico.opacityProperty(), 0))
        );
        
        super.getChildren().addAll(utente,critico,rivale);
    }
    
    /*
    * initializes writings for damage and critical
    */
    private void textInit(Text t, String s, double n, Color r){
        t.setText(s);
        t.setX(n);
        t.setY(100);
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        t.setFill(r);
        t.setOpacity(0);
    }
    
    /*
    * let damage appear
    */
    void segnaDanno(int u, int r){
        utente.setText("-"+r);
        rivale.setText("-"+u);
        timeline.play();
    }
    
    /*
    * let critical appear
    */
    void segnaCritico(int i){
        if(i==0){ //user has made critical hit
            critico.setFill(Color.BLUE);
        } else {  //cpu has made critical hit
            critico.setFill(Color.RED);
        }
        crTL.play();
    }
    
}
