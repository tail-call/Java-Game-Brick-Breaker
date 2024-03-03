import java.awt.*;

public class Painter {
    Graphics graphics;

    public Painter(Graphics graphics) {
        this.graphics = graphics;
    }

    void paintBackground() {
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592);
    }

    void paintLevel(Level level) {
        level.draw((Graphics2D) graphics);
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
