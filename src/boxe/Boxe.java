/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxe;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Laura
 */
public class Boxe extends Application {

    @Override
    public void start(Stage primaryStage) {

        PowerPane pp = new PowerPane();
        Ring r = new Ring();
        MainWindow root = new MainWindow(pp, r);

        Scene scene = new Scene(root, 600, 425);

        primaryStage.setTitle("Boxing Champion");
        primaryStage.setScene(scene);
        primaryStage.show();

        EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (root.loadingComplete) {
                    if (keyEvent.getCode() == KeyCode.SPACE) {
                        if (!root.paused && !root.uselesspaused) {
                            root.newTurno();
                        } else {
                            System.out.println("il gioco è in pausa");
                        }
                    }
                    if (keyEvent.getCode() == KeyCode.X) {
                        System.exit(0);
                    }
                    if (keyEvent.getCode() == KeyCode.P) { //pauses the game
                        if (root.p.matchstart) {
                            if (!root.uselesspaused) {
                                root.uselesspaused = true;
                                root.mettiInPausa();
                                System.out.println("paused " + root.paused + ", useless " + root.uselesspaused);
                            } else if (root.uselesspaused) {
                                root.uselesspaused = false;
                                root.mettiInPausa();
                                System.out.println("paused " + root.paused + ", useless " + root.uselesspaused);
                            }
                        } else {
                            if (root.p.tt.getStatus() == Animation.Status.RUNNING) {
                                root.p.tt.pause();
                                root.paused = true;
                                root.uselesspaused = false;
                                root.mettiInPausa();
                                System.out.println("paused " + root.paused + ", useless " + root.uselesspaused);
                            } else if (!root.paused && !root.uselesspaused) {
                                root.uselesspaused = true;
                                root.mettiInPausa();
                                System.out.println("paused " + root.paused + ", useless " + root.uselesspaused);
                            } else if (root.paused) {
                                root.p.tt.play();
                                root.paused = false;
                                root.uselesspaused = false;
                                root.mettiInPausa();
                                System.out.println("paused " + root.paused + ", useless " + root.uselesspaused);
                            } else if (root.uselesspaused) {
                                root.uselesspaused = false;
                                root.mettiInPausa();
                                System.out.println("paused " + root.paused + ", useless " + root.uselesspaused);
                            }
                        }
                    }
                }
            }
        };

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
