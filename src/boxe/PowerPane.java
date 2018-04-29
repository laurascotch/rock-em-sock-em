/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxe;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * this class creates an instance of the power bar
 * @author Laura
 */
public class PowerPane extends StackPane {

    Rectangle red;
    Rectangle yellow;
    Rectangle green;
    Rectangle indicatore;
    TranslateTransition tt;
    boolean moving = true;
    boolean matchstart = true;

    PowerPane() {
        StackPane s = sbarra();
        indicatore = new Rectangle(6, 40);
        indicatore.setFill(Color.WHITE);
        indicatore.setStroke(Color.BLACK);

        tt = new TranslateTransition(Duration.millis(334/*500*/), indicatore);
        tt.setFromX(-250);
        tt.setToX(250);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);

        tt.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                moving = false;
            }
        });

        //tt.play();

        super.getChildren().addAll(s, indicatore);
    }

    /*
    * this is the bar
    */
    StackPane sbarra() {
        StackPane s = new StackPane();
        red = new Rectangle(500, 25);
        red.setFill(Color.RED);
        yellow = new Rectangle(250, 25);
        yellow.setFill(Color.YELLOW);
        green = new Rectangle(50, 25);
        green.setFill(Color.GREEN);
        s.setAlignment(Pos.CENTER);
        s.getChildren().addAll(red, yellow, green);
        return s;
    }

    /*
    * this checks where the user stops the slider
    */
    String controllo() {
        String s = "";
        if (indicatore.getTranslateX() >= green.getX() - 10 && indicatore.getTranslateX() <= green.getX() + 10){
            s = "CRITICAL";
        } else if (indicatore.getTranslateX() >= green.getX() - 25 && indicatore.getTranslateX() <= green.getX() + 25) {
            s = "VERDE";
        } else if (indicatore.getTranslateX() >= yellow.getX() - 125 && indicatore.getTranslateX() <= yellow.getX() + 125) {
            s = "GIALLO";
        } else {
            s = "ROSSO";
        }
        return s;
    }

}
