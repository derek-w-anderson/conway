import java.util.Random;

public class Cell
{
	public static Random rand = new Random();
	private boolean dead;

	public Cell() {
		dead = true;
	}
	
	public Cell(boolean random) {
		dead = random ? rand.nextBoolean() : true;
	}

	public boolean isDead() {
		return dead;
	}
	
	public boolean isAlive() {
		return !dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
