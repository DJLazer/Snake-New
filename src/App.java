import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {

        int width, height, tile_size, speed;
        width = 800;
        height = 800;
        tile_size = 40;
        speed = 135;

        if (args.length == 4) {
            try {
                if (!args[0].equalsIgnoreCase("def")) {
                    width = Integer.parseInt(args[0]);
                }
                if (!args[1].equalsIgnoreCase("def")) {
                    height = Integer.parseInt(args[1]);
                }
                if (!args[2].equalsIgnoreCase("def")) {
                    tile_size = Integer.parseInt(args[2]);
                }
                if (!args[3].equalsIgnoreCase("def")) {
                    speed = Integer.parseInt(args[3]);
                }


            } catch (NumberFormatException e) {
                System.out.println("<Width> <Height> <tile_size> <speed>");
                return;
            }
        }

        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        SnakeGame game = new SnakeGame(width, height, tile_size, speed);

        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
