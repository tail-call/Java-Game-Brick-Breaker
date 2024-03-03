import java.awt.*;

public class Game {
    final Player player;
    final Ball ball;
    Level level;
    boolean isPlaying = false;
    int score = 0;
    int totalBricks = 48;

    public Game(Player player, Ball ball, Level level) {
        this.player = player;
        this.ball = ball;
        this.level = level;
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

    void restart() {
        isPlaying = true;
        ball.x = 120;
        ball.y = 350;
        ball.xDirection = -1;
        ball.yDirection = -2;
        player.x = 310;
        score = 0;
        totalBricks = 21;
        level = new Level(3, 7);
    }
}
