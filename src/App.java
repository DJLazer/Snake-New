import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        final int width = 800;
        final int height = 800;
        final int tile_size = 40;

        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        SnakeGame game = new SnakeGame(width, height, tile_size);

        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
