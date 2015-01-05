package essenceminer;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import node.Task;

import org.powerbot.script.PaintListener;
import org.powerbot.script.Script;
import org.powerbot.script.PollingScript;
import org.powerbot.script.rt4.ClientContext;

import tasks.*;
import utils.Paint;

@Script.Manifest(description = "Mines essence in Varrock", 
				name = "SEssence", 
				properties = "client = 4; topic = 1239661")
@SuppressWarnings("rawtypes")
public class EssenceMiner extends PollingScript<ClientContext> implements PaintListener, KeyListener {

	private List<Task> tasks = new ArrayList<Task>();
	private Paint paint = new Paint(ctx);
	
	@Override
	public void start() {
		tasks.addAll(Arrays.asList(new BankItems(ctx, paint),
				new TraverseToBank(ctx, paint),
				new TraverseToMine(ctx, paint),
				new Mine(ctx, paint)));
	}
	
	@Override
	public void poll() {
		for(Task task : tasks) {
			if(task.activate())
				task.execute();
		}
	}

	@Override
	public void repaint(Graphics g) {
		paint.repaint(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			paint.switchPaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	
}
