import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JButton;

public class GameOfLife
{
	private Grid grid;
	private JFrame frame;
	private AbstractButton button;
	private boolean running = false;
	
	public GameOfLife(int rows, int cols, boolean populate) {
		grid = new Grid(rows, cols, populate);
		frame = createWindow("Conway's Game Of Life");
		frame.setVisible(true);
		try {
			execute();
		} catch (InterruptedException e) {}
	}
	
	private JFrame createWindow(String title) {
		JFrame frame = new JFrame(title);
		frame.setLayout(new FlowLayout());
		
		int gridWidth = grid.getRows() * grid.getCellSize();
		int gridHeight = grid.getColumns() * grid.getCellSize();
		grid.setPreferredSize(new Dimension(gridWidth, gridHeight));
		frame.getContentPane().add(grid);
		
		button = new JButton("Start");
		button.addMouseListener(new RunListener());
		frame.getContentPane().add(button);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(gridWidth, gridHeight + 65);
		return frame;
	}

	private void execute() throws InterruptedException {
		do {
			if (running) {
				grid.updateCells();
				Thread.sleep(200);
			}
		} while (true);
	}
	
	private class RunListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {
			button.setText(running ? "Start" : "Stop");
			running = !running;
		}
	}
	
	public static void main(String[] args) {
		boolean populate = true;
		new GameOfLife(50, 40, populate);
	}
}
