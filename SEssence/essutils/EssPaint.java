package essutils;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.TimeUnit;

import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import essvars.EssVariables.PaintVars;

public class EssPaint extends ClientAccessor implements PaintListener {

	private final long START_TIME = System.currentTimeMillis();
	private final int START_XP = ctx.skills.experience(PaintVars.MINING);
	private String status = "";
	private String hideMessage = "Press enter to hide Paint";
	private boolean showPaint = true;
	
	public EssPaint(ClientContext arg0) {
		super(arg0);
	}

	@Override
	public void repaint(Graphics arg0) {
		String timeRun = "Time Run: " + formatTime(timeRun());
		String xpGained = "XP Gained: " + xpGained();
		String xpPerHour = "XP Per Hour: " + xpPerHour();
		String essenceMined = "Essence Mined: " + essenceMined();
		String status = "Status: " + this.status;
		String level = "Current Level: " + miningLevel();
		String xpToLevel = "XP to Level: " + xpToLevel();
		String timeToLevel = "Time to Level: " + formatTime(timeToLevel());
		String title = "SEssence";
		Graphics2D g = (Graphics2D) arg0;
		g.setColor(PaintVars.BLACK);
		g.drawLine(ctx.input.getLocation().x - 20, ctx.input.getLocation().y, 
				ctx.input.getLocation().x + 20, ctx.input.getLocation().y);
		g.drawLine(ctx.input.getLocation().x, ctx.input.getLocation().y - 20, 
				ctx.input.getLocation().x, ctx.input.getLocation().y + 20);
		g.drawRect(ctx.input.getLocation().x - 10, ctx.input.getLocation().y - 10, 20, 20);
		g.setColor(PaintVars.WHITE);
		g.drawString(hideMessage, 350, 325);
		if(this.showPaint) {
			g.fill3DRect(PaintVars.RECT_X, PaintVars.RECT_Y, PaintVars.RECT_WIDTH, PaintVars.RECT_HEIGHT, true);
			g.setColor(PaintVars.BLACK);
			g.drawString(timeRun, PaintVars.TEXT_X, PaintVars.TEXT_Y);
			g.drawString(xpGained, PaintVars.TEXT_X, PaintVars.TEXT_Y + PaintVars.Y_OFFSET);
			g.drawString(xpPerHour, PaintVars.TEXT_X, PaintVars.TEXT_Y + 2*PaintVars.Y_OFFSET);
			g.drawString(essenceMined, PaintVars.TEXT_X, PaintVars.TEXT_Y + 3*PaintVars.Y_OFFSET);
			g.drawString(status, PaintVars.TEXT_X, PaintVars.TEXT_Y + 4*PaintVars.Y_OFFSET);
			g.drawString(level, PaintVars.TEXT_X + PaintVars.X_OFFSET, PaintVars.TEXT_Y);
			g.drawString(xpToLevel, PaintVars.TEXT_X + PaintVars.X_OFFSET, PaintVars.TEXT_Y + PaintVars.Y_OFFSET);
			g.drawString(timeToLevel, PaintVars.TEXT_X + PaintVars.X_OFFSET, PaintVars.TEXT_Y + 2*PaintVars.Y_OFFSET);
			g.setFont(new Font("Courier", Font.BOLD, 18));
			g.drawString(title, 400, 460);
		}
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	private String formatTime(long time) {
        String format = String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                        .toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                        .toMinutes(time)));
        return format;
    }
	
	private long timeRun() {
		return System.currentTimeMillis() - START_TIME;
	}
	
	private int miningLevel() {
		return ctx.skills.level(PaintVars.MINING);
	}
	
	private int xpToLevel() {
		return PaintVars.XP[ctx.skills.level(PaintVars.MINING)] - ctx.skills.experience(PaintVars.MINING);
	}
	
	private long timeToLevel() {
		if(xpPerHour() == 0)
			return 0;
		return (long) (PaintVars.MS_TO_HR* ((double) xpToLevel() / (double) (xpPerHour()+1)));
	}
	
	private int xpGained() {
		return ctx.skills.experience(PaintVars.MINING) - START_XP;
	}
	
	private int xpPerHour() {
		return (int) (xpGained()*(PaintVars.MS_TO_HR / timeRun()));
	}
	
	private int essenceMined() {
		return xpGained()/5;
	}
	
	public void switchPaint() {
		if(showPaint) {
			hideMessage = "Press enter to show Paint";
		} else {
			hideMessage = "Press enter to hide Paint";
		}
		showPaint = !showPaint;
	}
	
}
