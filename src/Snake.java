import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Snake extends Application {

    private Random rand = new Random();
    private Rectangle rectangle = new Rectangle();
    private Rectangle food = new Rectangle();

    enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private boolean isChanged;
    private Direction HeadDirect = Direction.DOWN;

    @Override
    public void init() throws Exception {
        System.out.println("Before beginning");
        rectangle.setHeight(10);
        rectangle.setWidth(10);
        rectangle.setFill(Color.GREEN);
        food.setHeight(10);
        food.setWidth(10);
        food.setFill(Color.RED);
        GridPane.setRowIndex(food, rand.nextInt(40));
        GridPane.setColumnIndex(food, rand.nextInt(40));
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("Snake");
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();

        GridPane grid = (GridPane) scene.lookup("#grid");
        GridPane.setRowIndex(rectangle, 0);
        GridPane.setColumnIndex(rectangle, 1);
        grid.getChildren().addAll(rectangle, food);

        Thread mainThread = new Thread(move);
        mainThread.start();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN) {
                isChanged = true;
                try {
                    HeadDirect = Direction.DOWN;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                GridPane.setRowIndex(rectangle, GridPane.getRowIndex(rectangle) + 1);
            } else if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
                isChanged = true;
                try {
                    HeadDirect = Direction.UP;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                GridPane.setRowIndex(rectangle, GridPane.getRowIndex(rectangle) - 1);
            } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
                isChanged = true;
                try {
                    HeadDirect = Direction.RIGHT;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                GridPane.setColumnIndex(rectangle, GridPane.getColumnIndex(rectangle) + 1);
            } else if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
                isChanged = true;
                try {
                    HeadDirect = Direction.LEFT;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                GridPane.setColumnIndex(rectangle, GridPane.getColumnIndex(rectangle) - 1);
            }
        });

    }

    private Task<Void> move = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            while (true) {
                switch (HeadDirect) {
                    case UP:
                        isChanged = false;
                        while (!isChanged) {
                            GridPane.setRowIndex(rectangle, GridPane.getRowIndex(rectangle) - 1);
                            Thread.sleep(100);
                        }
                        break;
                    case RIGHT:
                        isChanged = false;
                        while (!isChanged) {
                            GridPane.setColumnIndex(rectangle, GridPane.getColumnIndex(rectangle) + 1);
                            Thread.sleep(100);
                        }
                        break;
                    case LEFT:
                        isChanged = false;
                        while (!isChanged) {
                            GridPane.setColumnIndex(rectangle, GridPane.getColumnIndex(rectangle) - 1);
                            Thread.sleep(100);
                        }
                        break;
                    default:
                        isChanged = false;
                        while (!isChanged) {
                            GridPane.setRowIndex(rectangle, GridPane.getRowIndex(rectangle) + 1);
                            Thread.sleep(100);
                        }
                }
            }
        }
    };

    @Override
    public void stop() throws Exception {
        System.out.println("After");
        super.stop();
    }
}
