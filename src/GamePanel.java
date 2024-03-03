import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel {
    private final Game game;
    private final Timer timer;

    public GamePanel() {
        game = new Game(new Player(), new Ball());
        timer = new Timer(8, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                timer.start();
                game.update();
                repaint();
            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        game.moveRight();
                        break;
                    case KeyEvent.VK_LEFT:
                        game.moveLeft();
                        break;
                    case KeyEvent.VK_ENTER:
                        if (!game.isPlaying) {
                            game.restart();
                            repaint();
                        }
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent keyEvent) { }

            @Override
            public void keyReleased(KeyEvent keyEvent) { }
        });

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer.start();
    }

    public void paint(Graphics graphics) {
        Painter painter = new Painter(graphics);

        painter.paintBackground();
        painter.paintLevel(game.level);
        painter.paintBorders();
        painter.paintScore(game.score);
        painter.paintPlayer(game.player);
        painter.paintBall(game.ball);

        game.checkState(new GameDelegate() {
            @Override
            public void didWin(Game game) {
                painter.paintVictoryScreen();
            }

            @Override
            public void didLose(Game game) {
                painter.paintFailureScreen(game);
            }
        });

        graphics.dispose();
    }
}
