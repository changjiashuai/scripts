package ssilk;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;

import node.Task;

import org.powerbot.script.BotMenuListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import tasks.*;
import utils.Paint;
import static vars.Variables.Job.*;

@Script.Manifest(description = "Buys, sells, steals silk", 
								name = "SSilk", 
								properties = "client = 4; topic = 1239663")
@SuppressWarnings("rawtypes")
public class SSilk extends PollingScript<ClientContext> implements PaintListener, KeyListener, BotMenuListener {

	Paint paint = new Paint(ctx);
	private List<Task> tasks = new ArrayList<Task>();
	
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
	public void menuCanceled(MenuEvent arg0) {}

	@Override
	public void menuDeselected(MenuEvent arg0) {}

	@Override
	public void menuSelected(MenuEvent e) {
		final JMenu menu = (JMenu) e.getSource();
		final JMenuItem steal = new JMenuItem("Steal silk");
		final JMenuItem buy = new JMenuItem("Buy silk");
		final JMenuItem sell = new JMenuItem("Sell silk");
		steal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tasks.removeAll(tasks);
				tasks.addAll(Arrays.asList(new StealBank(ctx, paint), new StealSilk(ctx, paint),
						new StealToBank(ctx, paint), new StealToStall(ctx, paint)));
				paint.setJob(STEAL);
			}
		});
		buy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tasks.removeAll(tasks);
				tasks.addAll(Arrays.asList(new BuySilk(ctx, paint), new BuyBank(ctx, paint), 
						new BuyToBank(ctx, paint), new BuyToTrader(ctx, paint)));
				paint.setJob(BUY);
			}
		});
		sell.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tasks.removeAll(tasks);
				tasks.addAll(Arrays.asList(new SellSilk(ctx, paint), new SellBank(ctx, paint),
						new SellToBank(ctx, paint), new SellToTrader(ctx, paint)));
				paint.setJob(SELL);
			}
		});
		menu.add(steal);
		menu.add(sell);
		menu.add(buy);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			paint.switchPaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

}
