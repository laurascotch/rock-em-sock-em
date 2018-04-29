/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxe;

import boxe.images.ImageResources;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Laura
 */
public class LoadingScreen extends Pane {

    ImageView loadingscr;
    Timeline timeline;
    Rectangle rectTL;
    Text perc;
    int percInt;

    LoadingScreen() {

        Image loadscr = new Image(ImageResources.class.getResourceAsStream("loading.jpg"));
        loadingscr = new ImageView(loadscr);

        //double w = rectTL.getWidth();
        perc = new Text("0%");
        perc.setY(357);
        perc.setX(300);
        perc.setTextOrigin(VPos.CENTER);
        perc.setFill(Color.WHITE);
        perc.setStroke(Color.WHITE);
        //perc.setText( (w*100/600) + "%" );

        rectTL = new Rectangle(600, 15);
        rectTL.setX(0);
        rectTL.setY(350);
        rectTL.setFill(Color.CORAL);
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(rectTL.widthProperty(), 0)),
                new KeyFrame(new Duration(1000),
                        new KeyValue(rectTL.widthProperty(), 150)),
                new KeyFrame(new Duration(2000),
                        new KeyValue(rectTL.widthProperty(), 600),
                        new KeyValue(rectTL.opacityProperty(), 1),
                        new KeyValue(perc.opacityProperty(), 1),
                        new KeyValue(loadingscr.opacityProperty(), 1)),
                new KeyFrame(new Duration(3000),
                        new KeyValue(rectTL.opacityProperty(), 0),
                        new KeyValue(perc.opacityProperty(), 0),
                        new KeyValue(loadingscr.opacityProperty(), 0))
        );

        rectTL.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                perc.setText( (int)((rectTL.getWidth()*100/600)) + "%" );
            }
        });

        timeline.play();

        super.getChildren().addAll(loadingscr, rectTL, perc);
    }

}
