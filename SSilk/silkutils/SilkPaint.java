package silkutils;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.TimeUnit;

import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import silkvars.SilkVariables.Job;
import silkvars.SilkVariables.PaintVars;
import static silkvars.SilkVariables.Job.*;

public class SilkPaint extends ClientAccessor implements PaintListener {

	private final long START_TIME = System.currentTimeMillis();
	private final int START_XP = ctx.skills.experience(PaintVars.THIEVING);
	private String status = "No job selected";
	private String hideMessage = "Press enter to hide Paint";
	private int silkCount = 0;
	private boolean showPaint = true;
	private Job job = NONE;
	
	public SilkPaint(ClientContext arg0) {
		super(arg0);
	}

	@Override
	public void repaint(Graphics arg0) {
		String timeRun = "Time Run: " + formatTime(timeRun());
		String status = "Status: " + this.status;
		String title = "SSilk";
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
			g.drawString(status, PaintVars.TEXT_X, PaintVars.TEXT_Y + PaintVars.Y_OFFSET);
			switch(job) {
				case BUY: 
					String silkBought = "Silk Bought: " + silkCount;
					String silkWorth = "Worth of Silk Bought: " + (60*silkCount);
					String boughtPerHour = "Silk Bought per Hour: " + silkPerHour();
					g.drawString(silkBought, PaintVars.TEXT_X, PaintVars.TEXT_Y + 2*PaintVars.Y_OFFSET);
					g.drawString(silkWorth, PaintVars.TEXT_X, PaintVars.TEXT_Y + 3*PaintVars.Y_OFFSET);
					g.drawString(boughtPerHour, PaintVars.TEXT_X, PaintVars.TEXT_Y + 4*PaintVars.Y_OFFSET);
					break;
				case NONE:
					g.drawString("Please select a job from the options menu", PaintVars.TEXT_X, PaintVars.TEXT_Y + 2*PaintVars.Y_OFFSET);
					break;
				case SELL:
					String silkSold = "Silk Sold: " + silkCount;
					String coinsGained = "Profit: " + (60*silkCount);
					String soldPerHour = "Silk Sold per Hour: " + silkPerHour();
					g.drawString(silkSold, PaintVars.TEXT_X, PaintVars.TEXT_Y + 2*PaintVars.Y_OFFSET);
					g.drawString(coinsGained, PaintVars.TEXT_X, PaintVars.TEXT_Y + 3*PaintVars.Y_OFFSET);
					g.drawString(soldPerHour, PaintVars.TEXT_X, PaintVars.TEXT_Y + 4*PaintVars.Y_OFFSET);
					break;
				case STEAL:
					String xpGained = "XP Gained: " + xpGained();
					String xpPerHour = "XP Per Hour: " + xpPerHour();
					String level = "Current Level: " + thievingLevel();
					String xpToLevel = "XP to Level: " + xpToLevel();
					String timeToLevel = "Time to Level: " + formatTime(timeToLevel());
					String silkStolen = "Silk stolen: " + (xpGained()/24);
					String stolenPerHour = "Silk Stolen per hour: " + (xpPerHour()/24);
					String profit = "Worth of Silk Stolen: " + ((xpGained()/6)*15);
					g.drawString(xpGained, PaintVars.TEXT_X, PaintVars.TEXT_Y + 2*PaintVars.Y_OFFSET);
					g.drawString(xpPerHour, PaintVars.TEXT_X, PaintVars.TEXT_Y + 3*PaintVars.Y_OFFSET);
					g.drawString(level, PaintVars.TEXT_X, PaintVars.TEXT_Y + 4*PaintVars.Y_OFFSET);
					g.drawString(xpToLevel, PaintVars.TEXT_X + PaintVars.X_OFFSET, PaintVars.TEXT_Y);
					g.drawString(timeToLevel, PaintVars.TEXT_X + PaintVars.X_OFFSET, PaintVars.TEXT_Y + PaintVars.Y_OFFSET);
					g.drawString(silkStolen, PaintVars.TEXT_X + PaintVars.X_OFFSET, PaintVars.TEXT_Y + 2*PaintVars.Y_OFFSET);
					g.drawString(stolenPerHour, PaintVars.TEXT_X + PaintVars.X_OFFSET, PaintVars.TEXT_Y + 3*PaintVars.Y_OFFSET);
					g.drawString(profit, PaintVars.TEXT_X + PaintVars.X_OFFSET, PaintVars.TEXT_Y + 4*PaintVars.Y_OFFSET);
					break;
			}
			g.setFont(new Font("Courier", Font.BOLD, 18));
			g.drawString(title, 400, 460);
		}
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
	
	public void incrementSilk() {
		this.silkCount++;
	}
	
	public void addSilk(int add) {
		silkCount += add;
	}
	
	public void switchPaint() {
		if(showPaint) {
			hideMessage = "Press enter to show Paint";
		} else {
			hideMessage = "Press enter to hide Paint";
		}
		showPaint = !showPaint;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int silkPerHour() {
		return (int) (silkCount*(PaintVars.MS_TO_HR / timeRun()));
	}
	
	private int thievingLevel() {
		return ctx.skills.level(PaintVars.THIEVING);
	}
	
	private int xpToLevel() {
		return PaintVars.XP[ctx.skills.level(PaintVars.THIEVING)] - ctx.skills.experience(PaintVars.THIEVING);
	}
	
	private long timeToLevel() {
		if(xpPerHour() == 0)
			return 0;
		return (long) (PaintVars.MS_TO_HR* ((double) xpToLevel() / (double) (xpPerHour()+1)));
	}
	
	private int xpGained() {
		return ctx.skills.experience(PaintVars.THIEVING) - START_XP;
	}
	
	private int xpPerHour() {
		return (int) (xpGained()*(PaintVars.MS_TO_HR / timeRun()));
	}
}
