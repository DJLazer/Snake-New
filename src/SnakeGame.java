import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH;
    private final int HEIGHT;
    private final int TILE_SIZE;

    // game logic
    Timer gameTimer;
    private boolean gameOver = false;
    private boolean gameStarted = false;
    ArrayDeque<Point> snake = new ArrayDeque<>();
    ArrayDeque<Direction> moves = new ArrayDeque<>();

    Direction lastDirection = null;

    Point food = new Point();

    public SnakeGame(int WIDTH, int HEIGHT, int TILE_SIZE, int SPEED) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.TILE_SIZE = TILE_SIZE;

        setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        setBackground(Color.black);

        if (Math.min(WIDTH, HEIGHT) / TILE_SIZE > 5) {
            snake.add(new Point(5, 5));
        }
        else {
            snake.add(new Point(0, 0));
        }

        addKeyListener(this);
        setFocusable(true);

        gameTimer = new Timer(SPEED, this);
    }

    public void startGame() {
        gameStarted = true;
        gameOver = false;

        requestFocus();
        snake.clear();
        if (Math.min(WIDTH, HEIGHT) / TILE_SIZE > 5) {
            snake.add(new Point(5, 5));
        }
        else {
            snake.add(new Point(0, 0));
        }

        lastDirection = null;
        moves.clear();

        placeFood();

        gameTimer.start();
    }

    private void move() {

        boolean gotFood = false;

        Point snakeHead = snake.getFirst();
        Point nextMove = new Point(snakeHead.x, snakeHead.y);
        Direction currentDirection = moves.peekFirst();
        if (currentDirection == null) {
            currentDirection = lastDirection;
        }
        else {
            lastDirection = moves.removeFirst();
        }

        if (lastDirection == null) {
            return;
        }

        switch(currentDirection) {
            case UP:
                nextMove.y -= 1;
                break;
            case DOWN:
                nextMove.y += 1;
                 break;
            case RIGHT:
                nextMove.x += 1;
                break;
            case LEFT:
                nextMove.x -= 1;
        }

        if (nextMove.x >= WIDTH / TILE_SIZE
        || nextMove.x < 0
        || nextMove.y >= HEIGHT / TILE_SIZE
        || nextMove.y < 0) {
            gameOver = true;
            return;
        }

        for (Point segment : snake) {
            if (collision(nextMove, segment) && !collision(nextMove, snake.getLast())) {
                gameOver = true;
                return;
            }
        }

        if (collision(nextMove, food)) {
            gotFood = true;
        }

        snake.addFirst(nextMove);
        if (!gotFood) {
            snake.removeLast();
        }

        if (gotFood) {
            placeFood();
        }
    }

    private void placeFood() {
        do {
            food.x = (int) (Math.random() * (double) (WIDTH / TILE_SIZE));
            food.y = (int) (Math.random() * (double) (HEIGHT / TILE_SIZE));
        } while (snake.contains(food));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // snake head
        if (!gameOver) {
            g.setColor(new Color(0, 150, 0));
        }
        else {
            g.setColor(new Color(150, 0, 0));
        }
        Point snakeHead = snake.getFirst();
        g.fillRect(snakeHead.x * TILE_SIZE, snakeHead.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // snake body
        if (!gameOver) {
            g.setColor(Color.green);
        }
        else {
            g.setColor(Color.red);
        }
        for (Point segment : snake) {
            if (segment != snake.getFirst()) {
                g.fillRect(segment.x * TILE_SIZE, segment.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // food
        if (gameStarted) {
            g.setColor(Color.red);
            g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            g.setColor(Color.green);
            g.fillRect(food.x * TILE_SIZE + ((TILE_SIZE / 2) - TILE_SIZE / 10), food.y * TILE_SIZE - TILE_SIZE / 8,
                    TILE_SIZE / 4, TILE_SIZE / 4);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            startGame();
            return;
        }

        if (!gameStarted) {
            startGame();
        }

        Direction currentDirection = moves.peekFirst();
        if (currentDirection == null) {
            currentDirection = lastDirection;
        }
        Direction newDirection = null;
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (currentDirection != Direction.DOWN) {
                    newDirection = Direction.UP;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currentDirection != Direction.LEFT) {
                    newDirection = Direction.RIGHT;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentDirection != Direction.RIGHT) {
                    newDirection = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currentDirection != Direction.UP) {
                    newDirection = Direction.DOWN;
                }
        }

        if (newDirection != null && moves.size() < 3) {
            moves.addLast(newDirection);
        }
    }

    private boolean collision(Point a, Point b) {
        return a.x == b.x && a.y == b.y;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private enum Direction {
        RIGHT,
        LEFT,
        UP,
        DOWN
    }
}
