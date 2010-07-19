import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Grid extends JComponent
{
	private static final int cellSize = 10;
	private int rows, cols;
	private Cell[][] cells;

	public Grid(int rows, int cols, boolean populate) {
		this.rows = rows;
		this.cols = cols;
		
		cells = new Cell[rows][cols];
		for (int x = 0; x < rows; x++)
			for (int y = 0; y < cols; y++)
				cells[x][y] = new Cell(populate);
		
		addMouseListener(new GridListener());
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return cols;
	}

	public int getCellSize() {
		return cellSize;
	}

	public void updateCells() {
		Cell[][] nextGen = new Cell[rows][cols];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				nextGen[x][y] = getNextGenCell(x, y);
			}
		}
		cells = nextGen;
		repaint();
	}
	
	private Cell getNextGenCell(int x, int y) {
		Cell current = cells[x][y];
		Cell next = new Cell();
		int liveNeighbors = getNumLiveNeighbors(x, y);
		
		if (current.isAlive() && (liveNeighbors == 2 || liveNeighbors == 3))
			next.setDead(false);
		else if (current.isDead() && liveNeighbors == 3)
			next.setDead(false);
		
		return next;
	}
	
	private int getNumLiveNeighbors(int x, int y) {
		int living = 0;
		living += isAlive(x-1, y-1) ? 1 : 0;
		living += isAlive(x,   y-1) ? 1 : 0;
		living += isAlive(x+1, y-1) ? 1 : 0;
		living += isAlive(x-1, y)   ? 1 : 0;
		living += isAlive(x+1, y)   ? 1 : 0;
		living += isAlive(x-1, y+1) ? 1 : 0;
		living += isAlive(x,   y+1) ? 1 : 0;
		living += isAlive(x+1, y+1) ? 1 : 0;
		return living;
	}
	
	private boolean isAlive(int x, int y) {
		try {
			return cells[x][y].isAlive();
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	@Override
	public void paint(Graphics g) {
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				g.setColor(cells[x][y].isDead() ? Color.WHITE : Color.BLACK);
				g.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
			}
		}
	}
	
	private class GridListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {
			int x = e.getX() / cellSize;
			int y = e.getY() / cellSize;
			cells[x][y].setDead(!cells[x][y].isDead());
			repaint();
		}
	}
}