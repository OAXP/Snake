import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Snake extends Application {

    private final Random rand = new Random();
    private final Rectangle head = new Rectangle();
    private final Rectangle food = new Rectangle();
    private ArrayList<Rectangle> snake = new ArrayList<>();
    private int nextRowIndex;
    private int nextColIndex;
    private GridPane grid;
    private int timeToMove = 100;

    enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private boolean isChanged;
    private Direction HeadDirect = Direction.DOWN;

    @Override
    public void init() {
        System.out.println("Before beginning");
        head.setHeight(10);
        head.setWidth(10);
        head.setFill(Color.GREEN);
        food.setHeight(10);
        food.setWidth(10);
        food.setFill(Color.RED);
        GridPane.setRowIndex(food, rand.nextInt(40));
        GridPane.setColumnIndex(food, rand.nextInt(40));
        snake.add(head);

    }

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("Snake");
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();

        grid = (GridPane) scene.lookup("#grid");
        GridPane.setRowIndex(head, 0);
        GridPane.setColumnIndex(head, 1);
        grid.getChildren().addAll(head, food);

        new Thread(() -> {
            while (true) {
                switch (HeadDirect) {
                    case UP:
                        isChanged = false;
                        while (!isChanged) {
                            nextRowIndex = GridPane.getRowIndex(snake.get(0)) - 1;
                            nextColIndex = GridPane.getColumnIndex(snake.get(0));
                            verifyPos();
                            try {
                                moveAndVerify();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case RIGHT:
                        isChanged = false;
                        while (!isChanged) {
                            nextColIndex = GridPane.getColumnIndex(snake.get(0)) + 1;
                            nextRowIndex = GridPane.getRowIndex(snake.get(0));
                            verifyPos();
                            try {
                                moveAndVerify();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case LEFT:
                        isChanged = false;
                        while (!isChanged) {
                            nextColIndex = GridPane.getColumnIndex(snake.get(0)) - 1;
                            nextRowIndex = GridPane.getRowIndex(snake.get(0));
                            verifyPos();
                            try {
                                moveAndVerify();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    default:
                        isChanged = false;
                        while (!isChanged) {
                            nextRowIndex = GridPane.getRowIndex(snake.get(0)) + 1;
                            nextColIndex = GridPane.getColumnIndex(snake.get(0));
                            verifyPos();
                            try {
                                moveAndVerify();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                }
            }
        }).start();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN) {
                isChanged = true;
                try {
                    HeadDirect = Direction.DOWN;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                /*nextRowIndex = GridPane.getRowIndex(snake.get(0)) + 1;
                nextColIndex = GridPane.getColumnIndex(snake.get(0));
                try {
                    moveAndVerify();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }*/
            } else if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
                isChanged = true;
                try {
                    HeadDirect = Direction.UP;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                /*nextRowIndex = GridPane.getRowIndex(snake.get(0)) - 1;
                nextColIndex = GridPane.getColumnIndex(snake.get(0));
                try {
                    moveAndVerify();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }*/
            } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
                isChanged = true;
                try {
                    HeadDirect = Direction.RIGHT;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                /*nextColIndex = GridPane.getColumnIndex(snake.get(0)) + 1;
                nextRowIndex = GridPane.getRowIndex(snake.get(0));
                try {
                    moveAndVerify();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }*/
            } else if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
                isChanged = true;
                try {
                    HeadDirect = Direction.LEFT;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                /*nextColIndex = GridPane.getColumnIndex(snake.get(0)) - 1;
                nextRowIndex = GridPane.getRowIndex(snake.get(0));
                try {
                    moveAndVerify();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }*/
            } else if (e.getCode() == KeyCode.SHIFT) {
                timeToMove = 75;
            }
        });

        scene.setOnKeyReleased(e-> {
            if (e.getCode() == KeyCode.SHIFT){
                timeToMove = 100;
            }
        });

    }

    private void verifyPos() {
        if(nextColIndex >= 40){
            nextColIndex = 0;
        } else if(nextColIndex < 0) {
            nextColIndex = 39;
        }

        if(nextRowIndex >= 40){
            nextRowIndex = 0;
        } else if(nextRowIndex < 0){
            nextRowIndex = 39;
        }
    }

    private void moveAndVerify() throws InterruptedException {
        GridPane.setRowIndex(snake.get(snake.size() - 1), nextRowIndex);
        GridPane.setColumnIndex(snake.get(snake.size() - 1), nextColIndex);
        Rectangle tmp = snake.get(snake.size() - 1);
        snake.remove(tmp);
        snake.add(0, tmp);
        Thread.sleep(timeToMove);
        /*if((GridPane.getRowIndex(snake.get(0)).equals(GridPane.getRowIndex(food))) && (GridPane.getColumnIndex(snake.get(0)).equals(GridPane.getColumnIndex(food)))){
            eat();
        }*/
        if(nextRowIndex == GridPane.getRowIndex(food) && nextColIndex == GridPane.getColumnIndex(food)){
            eat();
        }
    }
    
    private void eat(){
        GridPane.setRowIndex(food, rand.nextInt(40));
        GridPane.setColumnIndex(food, rand.nextInt(40));
        // TODO grow la queue
        snake.add(createBlock());
        System.out.println("nb : " + snake.size());
    }

    private Rectangle createBlock(){
        Rectangle newBlock = new Rectangle();
        newBlock.setHeight(10);
        newBlock.setWidth(10);
        newBlock.setFill(Color.GREEN);
        // TODO ISSUE IS HERE grid.getChildren().add
        Platform.runLater(() -> grid.getChildren().add(newBlock));
        GridPane.setColumnIndex(newBlock, GridPane.getColumnIndex(snake.get(snake.size() - 1)));
        GridPane.setRowIndex(newBlock, GridPane.getRowIndex(snake.get(snake.size() - 1)));

        return newBlock;
    }

    @Override
    public void stop() throws Exception {
        System.out.println("After");
        super.stop();
        System.exit(0);
    }
}
