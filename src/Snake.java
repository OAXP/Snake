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


public class Snake extends Application {

    private Random rand = new Random();
    private Rectangle head = new Rectangle();
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
        head.setHeight(10);
        head.setWidth(10);
        head.setFill(Color.GREEN);
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
        GridPane.setRowIndex(head, 0);
        GridPane.setColumnIndex(head, 1);
        grid.getChildren().addAll(head, food);

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
                GridPane.setRowIndex(head, GridPane.getRowIndex(head) + 1);
            } else if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
                isChanged = true;
                try {
                    HeadDirect = Direction.UP;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                GridPane.setRowIndex(head, GridPane.getRowIndex(head) - 1);
            } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
                isChanged = true;
                try {
                    HeadDirect = Direction.RIGHT;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                GridPane.setColumnIndex(head, GridPane.getColumnIndex(head) + 1);
            } else if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
                isChanged = true;
                try {
                    HeadDirect = Direction.LEFT;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                GridPane.setColumnIndex(head, GridPane.getColumnIndex(head) - 1);
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
                            GridPane.setRowIndex(head, GridPane.getRowIndex(head) - 1);
                            Thread.sleep(100);
                            if((GridPane.getRowIndex(head).equals(GridPane.getRowIndex(food))) && (GridPane.getColumnIndex(head).equals(GridPane.getColumnIndex(food)))){
                                eat();
                            }
                        }
                        break;
                    case RIGHT:
                        isChanged = false;
                        while (!isChanged) {
                            GridPane.setColumnIndex(head, GridPane.getColumnIndex(head) + 1);
                            Thread.sleep(100);
                            if((GridPane.getRowIndex(head).equals(GridPane.getRowIndex(food))) && (GridPane.getColumnIndex(head).equals(GridPane.getColumnIndex(food)))){
                                eat();
                            }
                        }
                        break;
                    case LEFT:
                        isChanged = false;
                        while (!isChanged) {
                            GridPane.setColumnIndex(head, GridPane.getColumnIndex(head) - 1);
                            Thread.sleep(100);
                            if((GridPane.getRowIndex(head).equals(GridPane.getRowIndex(food))) && (GridPane.getColumnIndex(head).equals(GridPane.getColumnIndex(food)))){
                                eat();
                            }
                        }
                        break;
                    default:
                        isChanged = false;
                        while (!isChanged) {
                            GridPane.setRowIndex(head, GridPane.getRowIndex(head) + 1);
                            Thread.sleep(100);
                            if((GridPane.getRowIndex(head).equals(GridPane.getRowIndex(food))) && (GridPane.getColumnIndex(head).equals(GridPane.getColumnIndex(food)))){
                                eat();
                            }
                        }
                }
            }
        }
    };
    
    private void eat(){
        GridPane.setRowIndex(food, rand.nextInt(40));
        GridPane.setColumnIndex(food, rand.nextInt(40));
        // TODO grow la queue
    }

    @Override
    public void stop() throws Exception {
        System.out.println("After");
        super.stop();
    }
}
