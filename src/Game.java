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
}
