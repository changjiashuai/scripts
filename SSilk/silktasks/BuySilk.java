package silktasks;

import java.util.concurrent.Callable;

import silkutils.CtxMethods;
import silkutils.SilkPaint;
import silkvars.SilkVariables.*;
import node.Task;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Interactive;
import org.powerbot.script.rt4.Npc;

public class BuySilk extends Task<ClientContext> {
	
	private SilkPaint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);
	
	public BuySilk(ClientContext ctx, SilkPaint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return !ctx.npcs.select().id(Npcs.TRADER_ALK).within(6.0).isEmpty()
				&& ctx.inventory.select().count() < 28
				&& ctx.inventory.id(Items.COINS).count() > 0;
	}

	@Override
	public boolean execute() {
		paint.setStatus("Buying silk");
		int silkCount = ctx.inventory.select().id(Items.SILK).count();
		if(!m.attemptTextWidgetClick(Widgets.ALK_TRADER_WIDGETS, Widgets.ALK_TRADER_TEXT)) {
			Npc trader = ctx.npcs.select().id(Npcs.TRADER_ALK).each(Interactive.doSetBounds(Bounds.TRADERS)).poll();
			if(!trader.inViewport()) {
				ctx.camera.turnTo(trader);
				ctx.camera.pitch(Random.nextInt(0, 50));
			}
			trader.interact(Interaction.TALK_ALK);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.widgets.component(Widgets.ALK_TRADER_WIDGETS[0][0], Widgets.ALK_TRADER_WIDGETS[0][1]).visible();
				}
			}, 500);
			if(ctx.inventory.select().count() == silkCount + 1)
				paint.incrementSilk();
			return ctx.widgets.component(Widgets.ALK_TRADER_WIDGETS[0][0], Widgets.ALK_TRADER_WIDGETS[0][1]).visible();
		} else {
			if(ctx.inventory.select().count() == silkCount + 1)
				paint.incrementSilk();
			return true;
		}
	}

}
