import java.awt.*;

public class Game {
    final Player player;
    final Ball ball;
    boolean isPlaying = false;
    int score = 0;
    int totalBricks = 48;

    public Game(Player player, Ball ball) {
        this.player = player;
        this.ball = ball;
    }

    void update(GameDelegate delegate) {
        if (totalBricks <= 0) {
            isPlaying = false;
            ball.xDirection = 0;
            ball.yDirection = 0;
            delegate.didWin(this);
        }

        if (ball.y > 570) {
            isPlaying = false;
            ball.xDirection = 0;
            ball.yDirection = 0;
            delegate.didLose(this);
        }
    }
}
