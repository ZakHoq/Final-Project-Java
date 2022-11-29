import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class App extends Application {
    
    private Pane root = new Pane();

    private double t = 0;

    private Sprite player = new Sprite(600, 650, 40, 40, "player", Color.BLUE);
    
    private Parent createContent() {
        Image BG = new Image("/Icons/Background.png");
        BackgroundImage IMG = new BackgroundImage(BG, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(IMG);
        root.setBackground(background);
        root.getChildren().add(player);
      
       
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        nextLevel();
        nextLevel2();
        nextLevel3();
        nextLevel4();
        nextLevel5();
      

        return root;
    }

    private void nextLevel() {
        for (int i = 0; i < 12; i++) {
            Sprite s = new Sprite(90 + i*100, 100, 30, 30, "enemy", Color.RED);
            
            root.getChildren().add(s);
        }
     
    }
    private void nextLevel2(){
        for (int i = 0; i < 12; i++) {
            Sprite s = new Sprite(145 + i*100, 200, 30, 30, "enemy", Color.GREEN);

            root.getChildren().add(s);
        }
     
    }
    private void nextLevel3(){
        for (int i = 0; i < 12; i++) {
            Sprite s = new Sprite(90 + i*100, 300, 30, 30, "enemy", Color.PURPLE);

            root.getChildren().add(s);
        }
    }

    private void nextLevel4(){
        for (int i = 0; i < 12; i++) {
            Sprite s = new Sprite(145 + i*100, 400, 30, 30, "enemy", Color.ORANGE);

            root.getChildren().add(s);
        }
    }
    
    private void nextLevel5(){
        for (int i = 0; i < 12; i++) {
            Sprite s = new Sprite(90 + i*100, 500, 30, 30, "enemy", Color.YELLOW);

            root.getChildren().add(s);
        }
    }

  

    

    

    private List<Sprite> sprites() {
        return root.getChildren().stream().map(n -> (Sprite)n).collect(Collectors.toList());
    }

    private void update() {
        t += 0.016;

        sprites().forEach(s -> {
            switch (s.type) {

                case "enemybullet":
                    s.moveDown();

                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true;
                        s.dead = true;
                    }
                    break;

                case "playerbullet":
                    s.moveUp();

                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                        }
                    });

                    break;

                case "enemy":

                    if (t > 2) {
                        if (Math.random() < 0.3) {
                            shoot(s);
                        }
                    }

                    break;
            }
        });

        root.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });

        if (t > 2) {
            t = 0;
        }
    }

    private void shoot(Sprite who) {
        Sprite s = new Sprite((int) who.getTranslateX() + 20, (int) who.getTranslateY(), 5, 20, who.type + "bullet", Color.BROWN);
        
        root.getChildren().add(s);
    }


    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());
              

        
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    player.moveLeft(20);
                    break;
                case D:
                    player.moveRight(20);
                    break;
                case SPACE:
                    shoot(player);
                    break;
            }
        });

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
    private static class Sprite extends Rectangle {
        boolean dead = false;
        final String type;
    
        Sprite(int x, int y, int w, int h, String type, Color color) {
            super(w, h, color);
    
            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
    

            if(type == "enemy"){
                if(color == Color.RED){
                    Image EnemyPNG = new Image("/Icons/Enemy1.png");
                setFill(new ImagePattern(EnemyPNG));
                }
                if(color == Color.GREEN){
                    Image EnemyPNG = new Image("/Icons/Enemy2.png");
                setFill(new ImagePattern(EnemyPNG));
                }    
                if(color == Color.PURPLE){
                    Image EnemyPNG = new Image("/Icons/Enemy3.png");
                setFill(new ImagePattern(EnemyPNG));
                }
                if(color == Color.ORANGE){
                    Image EnemyPNG = new Image("/Icons/Enemy4.png");
                setFill(new ImagePattern(EnemyPNG));
                }
                if(color == Color.YELLOW){
                    Image EnemyPNG = new Image("/Icons/Enemy5.png");
                setFill(new ImagePattern(EnemyPNG));
                }
            }
            if(type == "player"){
                Image PlayerPNG = new Image("/Icons/PlayerShip.png");
                setFill(new ImagePattern(PlayerPNG));
            }
            
        }
    
        void moveLeft(int x) {
            setTranslateX(getTranslateX() - x);
        }
    
        void moveRight(int x) {
            setTranslateX(getTranslateX() + x);
        }
    
        void moveUp() {
            setTranslateY(getTranslateY() - 5);
        }
    
        void moveDown() {
            setTranslateY(getTranslateY() + 5);
        }
    }
    

    
    public static void main(String[] args) {
        launch(args);
    }
}