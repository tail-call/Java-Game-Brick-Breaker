import java.awt.*;

public class Painter {
    Graphics2D graphics;

    public Painter(Graphics2D graphics) {
        this.graphics = graphics;
    }

    void paintBackground() {
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592);
    }

    void paintLevel(Level level) {
        Pair<Integer, Integer> brickDimensions = level.getBrickDimensions();
        int brickWidth = brickDimensions.first;
        int brickHeight = brickDimensions.second;

        for (int i = 0; i < level.bricks.length; i++) {
            for (int j = 0; j < level.bricks[i].length; j++) {
                if (level.bricks[i][j] > 0) {
                    graphics.setColor(Color.white);
                    graphics.fillRect(
                        j * brickWidth + 80,
                        i * brickHeight + 50,
                        brickWidth,
                        brickHeight
                    );

                    // this is just to show separate brick, game can still run without it
                    graphics.setStroke(new BasicStroke(3));
                    graphics.setColor(Color.black);
                    graphics.drawRect(
                        j * brickWidth + 80,
                        i * brickHeight + 50,
                        brickWidth,
                        brickHeight
                    );
                }
            }
        }
    }

    void paintBorders() {
        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 0, 3, 592);
    }

    void paintScore(int score) {
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("" + score, 590, 30);
    }

    void paintPlayer(Player player) {
        graphics.setColor(Color.green);
        graphics.fillRect(player.x, 550, 100, 8);
    }

    void paintBall(Ball ball) {
        graphics.setColor(Color.yellow);
        graphics.fillOval(ball.x, ball.y, 20, 20);
    }

    void paintFailureScreen(Game game) {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("serif",Font.BOLD, 30));
        graphics.drawString("Game Over, Scores: " + game.score, 190,300);

        graphics.setColor(Color.RED);
        graphics.setFont(new Font("serif",Font.BOLD, 20));
        graphics.drawString("Press (Enter) to Restart", 230,350);
    }

    void paintVictoryScreen() {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("serif",Font.BOLD, 30));
        graphics.drawString("You Won", 260,300);

        graphics.setColor(Color.RED);
        graphics.setFont(new Font("serif",Font.BOLD, 20));
        graphics.drawString("Press (Enter) to Restart", 230,350);
    }
}
