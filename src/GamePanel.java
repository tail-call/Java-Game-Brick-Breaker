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
            public void keyTyped(KeyEvent keyEvent) {
            }

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
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer.start();
    }

    public void paint(Graphics graphics) {
        // background
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592);

        game.level.draw((Graphics2D) graphics);

        // borders
        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 0, 3, 592);

        // the scores
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("" + game.score, 590, 30);

        // the paddle
        graphics.setColor(Color.green);
        graphics.fillRect(game.player.x, 550, 100, 8);

        // the ball
        graphics.setColor(Color.yellow);
        graphics.fillOval(game.ball.x, game.ball.y, 20, 20);

        game.checkState(new GameDelegate() {
            @Override
            public void didWin(Game game) {
                graphics.setColor(Color.RED);
                graphics.setFont(new Font("serif",Font.BOLD, 30));
                graphics.drawString("You Won", 260,300);

                graphics.setColor(Color.RED);
                graphics.setFont(new Font("serif",Font.BOLD, 20));
                graphics.drawString("Press (Enter) to Restart", 230,350);
            }

            @Override
            public void didLose(Game game) {
                graphics.setColor(Color.RED);
                graphics.setFont(new Font("serif",Font.BOLD, 30));
                graphics.drawString("Game Over, Scores: " + game.score, 190,300);

                graphics.setColor(Color.RED);
                graphics.setFont(new Font("serif",Font.BOLD, 20));
                graphics.drawString("Press (Enter) to Restart", 230,350);
            }
        });

        graphics.dispose();
    }
}
