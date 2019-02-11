import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * TODO Put here a description of what this class does.
 * Handles game logic and GUI
 * @author eyequ. Created Dec 29, 2018.
 */
public class DefendGUI extends Application {

	AnimationTimer gameLoop;
	Random rand;
	Sprite ball, pinkEnemy;
	ArrayList<String> input;
	ArrayList<Platform> platformList;
	ArrayList<Sprite> enemies;
	ArrayList<Sprite> enemiesUp;
	ArrayList<Sprite> enemiesDown;
	ArrayList<Sprite> blueEnemies;
	Platform current, newest;
	PlatformRight horizontalPlatform;
	double totalWidth, totalHeight, enemyX, enemyY, enemyUpX, enemyUpY, enemyDownX, enemyDownY, blueX, blueY,
		   peX, peY;
	String lastInput;
	int score;
	boolean hitEnemy, flag, gameOver;

	
	@Override
	public void start(Stage stage) {
		// TODO Auto-generated method stub.

		stage.setTitle("Defend Game");
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);

		Canvas canvas = new Canvas(500, 500);
		
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
		loadGame(gc);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				
				if(code.equals("SPACE") && gameOver == true) {
					gc.clearRect(0, 0, 500, 500);
					loadGame(gc);
					gameLoop.start();
				}
				
				else {
				if (!input.contains(code))
					input.add(code);
				}			
			}
		});
		
			
		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {

				
					if (DefendGUI.this.current.direction.equals("UP")) {
						DefendGUI.this.totalHeight += 1;
						DefendGUI.this.totalWidth -= 1;

					}

					else if (DefendGUI.this.current.direction.equals("RIGHT"))
						DefendGUI.this.totalWidth -= 1;

					else if (DefendGUI.this.current.direction.equals("DOWN")) {
						DefendGUI.this.totalHeight -= 1;
						DefendGUI.this.totalWidth -= 1;
					}

					if (input.contains("RIGHT")) {
						PlatformRight rightPlatform = new PlatformRight();
						rightPlatform.setImage("images/platform_forward.png");
						rightPlatform.setPosition(DefendGUI.this.totalWidth, DefendGUI.this.totalHeight);
						platformList.add(rightPlatform);
						input.remove("RIGHT");
						DefendGUI.this.totalWidth += horizontalPlatform.getWidth();
						DefendGUI.this.newest = rightPlatform;
						DefendGUI.this.lastInput = "RIGHT";
					}

					else if (input.contains("DOWN") && !DefendGUI.this.lastInput.equals("UP")) {
						PlatformDown downPlatform = new PlatformDown();
						downPlatform.setImage("images/platform_vertical.png");
						downPlatform.setPosition(DefendGUI.this.totalWidth, DefendGUI.this.totalHeight);
						platformList.add(downPlatform);
						input.remove("DOWN");
						DefendGUI.this.totalHeight += downPlatform.getHeight();
						DefendGUI.this.newest = downPlatform;
						DefendGUI.this.lastInput = "DOWN";
					}

					else if (input.contains("UP") && !DefendGUI.this.lastInput.equals("DOWN")) {
						PlatformUp upPlatform = new PlatformUp();
						upPlatform.setImage("images/platform_vertical.png");
						upPlatform.setPosition(DefendGUI.this.totalWidth, DefendGUI.this.totalHeight - 91);
						platformList.add(upPlatform);
						input.remove("UP");
						DefendGUI.this.totalHeight -= upPlatform.getHeight();
						DefendGUI.this.newest = upPlatform;
						DefendGUI.this.lastInput = "UP";

					}

					gc.clearRect(0, 0, 500, 500);
					
					DefendGUI.this.score++;

					ball.render(gc);

					String scoreText = "Score: " + DefendGUI.this.score;
					gc.fillText(scoreText, 10, 15);
					gc.strokeText(scoreText, 10, 15);
					
					pinkEnemy.move(2.5, 0);
					pinkEnemy.render(gc);
					
					if(ball.intersects(pinkEnemy))
						hitEnemy = true;
					
					if(pinkEnemy.X > 515 || DefendGUI.this.newest.intersects(pinkEnemy)) {
						peX = -100 - (100 + (350 - 100) * rand.nextDouble());
					    peY = (ball.Y - 70) + ((ball.Y + 70) - (ball.Y - 70)) * rand.nextDouble();
						pinkEnemy.setPosition(peX, peY);					
					}

					for (Sprite enemy : blueEnemies) {
						enemy.move(-2.5, 0);
						enemy.render(gc);

						if (enemy.X < -15) {
							DefendGUI.this.blueX = 500 + (200 + (12000 - 200) * rand.nextDouble());
							DefendGUI.this.blueY = (ball.Y - 60) + ((ball.Y + 60) - (ball.Y - 60)) * rand.nextDouble();
							enemy.setPosition(DefendGUI.this.enemyX, DefendGUI.this.enemyY);
						}

						if (ball.intersects(enemy))
							DefendGUI.this.hitEnemy = true;

					}

					for (Sprite enemy : enemies) {
						enemy.move(-2, 0);
						enemy.render(gc);

						if (enemy.X < -15 || DefendGUI.this.newest.intersects(enemy)) {
							DefendGUI.this.enemyX = 500 + (20 + (700 - 20) * rand.nextDouble());
							DefendGUI.this.enemyY = 1 + (500 - 1) * rand.nextDouble();
							enemy.setPosition(DefendGUI.this.enemyX, DefendGUI.this.enemyY);
						}

						if (ball.intersects(enemy))
							DefendGUI.this.hitEnemy = true;

					}

					for (Sprite enemy : enemiesUp) {
						enemy.move(0, 1);
						enemy.render(gc);

						if (enemy.Y > 515 || DefendGUI.this.newest.intersects(enemy)) {
							DefendGUI.this.enemyUpX = 1 + (500 - 1) * rand.nextDouble();
							DefendGUI.this.enemyUpY = 0 - (20 + (700 - 20) * rand.nextDouble());
							enemy.setPosition(DefendGUI.this.enemyUpX, DefendGUI.this.enemyUpY);
						}

						if (ball.intersects(enemy))
							DefendGUI.this.hitEnemy = true;

					}

					for (Sprite enemy : enemiesDown) {
						enemy.move(0, -1);
						enemy.render(gc);

						if (enemy.Y < -15 || DefendGUI.this.newest.intersects(enemy)) {
							DefendGUI.this.enemyDownX = 1 + (500 - 1) * rand.nextDouble();
							DefendGUI.this.enemyDownY = 500 + (20 + (700 - 20) * rand.nextDouble());
							enemy.setPosition(DefendGUI.this.enemyDownX, DefendGUI.this.enemyDownY);
						}

						if (ball.intersects(enemy))
							DefendGUI.this.hitEnemy = true;
					}

					for (int i = 0; i < platformList.size(); i++) {

						if (ball.intersects(platformList.get(i))) {
							DefendGUI.this.current = platformList.get(i);
							if (DefendGUI.this.current == platformList.get(platformList.size() - 1))
								DefendGUI.this.flag = true;
						}

					}

					if (DefendGUI.this.current.direction.equals("UP")) {
						for (Platform platform : platformList) {
							platform.move(-1, 1);
							platform.render(gc);
						}

						if (DefendGUI.this.flag == true && ((ball.Y - 18) == DefendGUI.this.totalHeight || (ball.Y - 18) == DefendGUI.this.totalHeight + 1)) {
							ball.move(-1, 1);
							DefendGUI.this.flag = false;
						}

						else
							ball.move(-1, -1);

					}

					else if (DefendGUI.this.current.direction.equals("DOWN")) {
						for (Platform platform : platformList) {
							platform.move(-1, -1);
							platform.render(gc);
						}

						if (DefendGUI.this.flag == true && ((ball.Y + ball.height) == DefendGUI.this.totalHeight || (ball.Y + ball.height) == DefendGUI.this.totalHeight + 1)) {
							ball.move(-1, -1);
							DefendGUI.this.flag = false;
						}

						else
							ball.move(-1, 1);

					}

					else if (DefendGUI.this.current.direction.equals("RIGHT")) {
						for (Platform platform : platformList) {
							platform.move(-1, 0);
							platform.render(gc);
						}

						if (DefendGUI.this.flag == true && (ball.X + ball.getWidth() == DefendGUI.this.totalWidth || ball.X + ball.getWidth() == DefendGUI.this.totalWidth + 1)) {
							ball.move(-1, 0);
							DefendGUI.this.flag = false;
						}

						else
							ball.move(1, 0);
					}

					if (DefendGUI.this.hitEnemy == true || ball.X + ball.getWidth() < 0) {
						gameOver = true;
						stop();
						gc.clearRect(0, 0, 500, 500);			
						gc.setFill(Color.LIGHTGREY);
						gc.fillRect(0,0, 500, 500);
						Font theFont = Font.font("Helvetica", FontWeight.BOLD, 20);
						gc.setFont(theFont);
						gc.setFill(Color.RED);
						gc.fillText("Game Over", 200, 100);
						gc.strokeText("Game Over", 200, 100);

						gc.setFill(Color.BLACK);
						gc.fillText("Score: " + DefendGUI.this.score, 203, 145);
						gc.strokeText("Score: " + DefendGUI.this.score, 203, 145);
						theFont = Font.font("Helvetica", FontWeight.BOLD, 15);
						gc.setFont(theFont);
						gc.setFill(Color.LIGHTGREEN);
						gc.fillText("press spacebar to restart", 165, 220);
						gc.strokeText("press spacebar to restart", 165, 220);					
					}
				}
			

		};
		gameLoop.start();
		
		
		stage.show();
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 * @param gc 
	 *
	 */
	public void loadGame(GraphicsContext gc) {
		hitEnemy = false;
		flag = true;
		gameOver = false;	
		score = 0;
		
		Font theFont = Font.font("Helvetica", FontWeight.BOLD, 15);
		gc.setFont(theFont);
		gc.setFill(Color.BLUE);
		
		input = new ArrayList<>();
		lastInput = "";

		ball = new Sprite();
		ball.setImage("images/ball_image.png");
		ball.setPosition(30, 100);

		horizontalPlatform = new PlatformRight();
		horizontalPlatform.setImage("images/platform_forward.png");
		horizontalPlatform.setPosition(0, 121);
		DefendGUI.this.current = horizontalPlatform;
		DefendGUI.this.newest = horizontalPlatform;

		platformList = new ArrayList<>();
		platformList.add(horizontalPlatform);

		enemies = new ArrayList<>();
		enemiesUp = new ArrayList<>();
		enemiesDown = new ArrayList<>();
		blueEnemies = new ArrayList<>();

		rand = new Random();

		for (int i = 0; i < 8; i++) {
			Sprite enemy = new Sprite();
			enemy.setImage("images/enemy_vert.png");
			DefendGUI.this.enemyDownX = 1 + (500 - 1) * rand.nextDouble();
			DefendGUI.this.enemyDownY = 500 + (20 + (700 - 20) * rand.nextDouble());
			enemy.setPosition(DefendGUI.this.enemyDownX, DefendGUI.this.enemyDownY);
			enemiesDown.add(enemy);
		}

		for (int i = 0; i < 8; i++) {
			Sprite enemy = new Sprite();
			enemy.setImage("images/enemy_vert.png");
			DefendGUI.this.enemyUpX = 1 + (500 - 1) * rand.nextDouble();
			DefendGUI.this.enemyUpY = 0 - (20 + (700 - 20) * rand.nextDouble());
			enemy.setPosition(DefendGUI.this.enemyUpX, DefendGUI.this.enemyUpY);
			enemiesUp.add(enemy);
		}

		for (int i = 0; i < 15; i++) {
			Sprite enemy = new Sprite();
			enemy.setImage("images/enemy.png");
			DefendGUI.this.enemyX = 500 + (20 + (700 - 20) * rand.nextDouble());
			DefendGUI.this.enemyY = 1 + (500 - 1) * rand.nextDouble();
			enemy.setPosition(DefendGUI.this.enemyX, DefendGUI.this.enemyY); 
			enemies.add(enemy);
		}

		for (int i = 0; i < 6; i++) {
			Sprite enemy = new Sprite();
			enemy.setImage("images/blue_ball.png");
			DefendGUI.this.blueX = 200 + (200 + (12000 - 200) * rand.nextDouble());
			DefendGUI.this.blueY = (ball.Y - 60) + ((ball.Y + 60) - (ball.Y - 60)) * rand.nextDouble();
			enemy.setPosition(DefendGUI.this.blueX, DefendGUI.this.blueY);
			blueEnemies.add(enemy);
		}
		
		pinkEnemy = new Sprite();
		pinkEnemy.setImage("images/triangle.png");
		peX = -100 - (500 + (10000 - 500) * rand.nextDouble());
	    peY = (ball.Y - 70) + ((ball.Y + 70) - (ball.Y - 70)) * rand.nextDouble();
		pinkEnemy.setPosition(peX, peY);

		DefendGUI.this.totalWidth = horizontalPlatform.getWidth();
		DefendGUI.this.totalHeight = 121;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

}
