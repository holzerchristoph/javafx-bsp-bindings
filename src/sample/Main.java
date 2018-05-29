package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private BorderPane root;
    private Pane drawPane;
    private Rectangle r1;
    private Rectangle r2;
    private DoubleProperty xPos = new SimpleDoubleProperty(100);
    private DoubleProperty yPos = new SimpleDoubleProperty(100);
    private BooleanProperty collision = new SimpleBooleanProperty(false);
    private CheckBox chCollision=new CheckBox("collision");
    private Timeline animation;

    @Override
    public void start(Stage primaryStage) throws Exception{

        root = new BorderPane();
        drawPane = new Pane();
        drawPane.setStyle("-fx-background-color:#ffcccc");

        root.setCenter(drawPane);
        root.setBottom(chCollision);

        r1 = new Rectangle(30,30);
        r1.setStroke(Color.GREEN);
        r1.setFill(Color.TRANSPARENT);

        r2 = new Rectangle(30, 30);
        r2.setStroke(Color.BLUE);
        r2.setFill(Color.TRANSPARENT);

        drawPane.getChildren().addAll(r1, r2);

        r1.setLayoutX(100);
        r1.setLayoutY(100);

        r2.xProperty().bind(xPos.subtract(15));
        r2.yProperty().bind(yPos.subtract(15));

        // bind checkbox to collision variable
        chCollision.selectedProperty().bind(collision);

        drawPane.setOnMouseMoved(e->{
            xPos.setValue(e.getSceneX());
            yPos.setValue(e.getSceneY());

            if (r1.getBoundsInParent().intersects((r2.getBoundsInParent()))){
                collision.setValue(true);
            }else{
                collision.setValue(false);
            }
        });

        animation = new Timeline(new KeyFrame(new Duration(20), new EventHandler<ActionEvent>() {
            public double offset = 1;

            @Override
            public void handle(ActionEvent event) {
                if (r1.getBoundsInParent().intersects((r2.getBoundsInParent()))){
                    collision.setValue(true);
                }else{
                    collision.setValue(false);
                }

                if ((r1.getWidth() > 100) ) {
                    offset = -1;
                }
                if (r1.getWidth() < 50){
                    offset = 1;
                }
                r1.setWidth(r1.getWidth() + offset);

            }
        }));
        animation.setAutoReverse(true);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.playFromStart();
        primaryStage.setTitle("collision");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
