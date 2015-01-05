package tasks;

import java.util.concurrent.Callable;

import node.Task;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Interactive;
import org.powerbot.script.rt4.Npc;

import utils.CtxMethods;
import utils.Paint;
import vars.Variables.Bounds;
import vars.Variables.Interaction;
import vars.Variables.Items;
import vars.Variables.Npcs;
import vars.Variables.Widgets;

public class SellSilk extends Task<ClientContext> {

	private Paint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);
	
	public SellSilk(ClientContext ctx, Paint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return !ctx.npcs.select().id(Npcs.TRADER_ARD).within(6.0).isEmpty()
				&& ctx.inventory.select().id(Items.SILK).count() > 0;
	}

	@Override
	public boolean execute() {
		paint.setStatus("Selling silk");
		int silkCount = ctx.inventory.select().id(Items.SILK).count();
		if(!m.attemptTextWidgetClick(Widgets.ARD_TRADER_WIDGETS, Widgets.ARD_TRADER_TEXT)) {
			Npc trader = ctx.npcs.select().id(Npcs.TRADER_ARD).each(Interactive.doSetBounds(Bounds.TRADERS)).poll();
			if(!trader.inViewport()) {
				ctx.camera.turnTo(trader);
				ctx.camera.pitch(Random.nextInt(0, 50));
			}
			trader.interact(Interaction.TALK_ALK);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.widgets.component(Widgets.ARD_TRADER_WIDGETS[0][0], Widgets.ARD_TRADER_WIDGETS[0][1]).visible();
				}
			}, 500);
			if(ctx.inventory.select().id(Items.SILK).count() == 0)
				paint.addSilk(silkCount);
			return ctx.widgets.component(Widgets.ALK_TRADER_WIDGETS[0][0], Widgets.ALK_TRADER_WIDGETS[0][1]).visible();
		} else {
			if(ctx.inventory.select().id(Items.SILK).count() == 0)
				paint.addSilk(silkCount);
			return true;
		}
	}

}
