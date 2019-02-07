
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * TODO Put here a description of what this class does.
 *
 * @author eyequ.
 *         Created Dec 29, 2018.
 */
public class Sprite {
	private Image image;
	protected double X;
	protected double Y;
	private double velocityX;
	private double velocityY;
	protected double width;
	protected double height;
	
	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 */
	public Sprite()
    {
        this.X = 0;
        this.Y = 0; 
        this.velocityX = 0;
        this.velocityY = 0;
        this.width = 0;
        this.height = 0;
    }
	
	 /**
	 * TODO Put here a description of what this method does.
	 *
	 * @param img
	 */
	public void setImage(Image img)
	    {
	        this.image = img;
	        this.width = img.getWidth();
	        this.height = img.getHeight();
	    }
	

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param filename
	 */
	public void setImage(String filename)
	    {
	        Image img = new Image(filename);
	        setImage(img);
	    }
	
	
	 /**
	 * TODO Put here a description of what this method does.
	 *
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y)
	    {
	        this.X = x;
	        this.Y = y;
	    }
	
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param gc
	 */
	public void render(GraphicsContext gc)
    {
        gc.drawImage(this.image, this.X, this.Y );
    }
	
	 /**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public Rectangle2D getBoundary()
	    {
		
	        return new Rectangle2D(this.X, this.Y, this.width,this.height);
	    }
	
	 /**
	 * TODO Put here a description of what this method does.
	 *
	 * @param s
	 * @return
	 */
	public boolean intersects(Sprite s)
	    {		
	       return s.getBoundary().intersects(this.getBoundary());
	    }
	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param x
	 * @param y
	 */
	public void move(double x, double y) {
		this.X += x;
		this.Y += y;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public double getX() {
		return this.X;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public double getY() {
		return this.Y;
	}
	


}
