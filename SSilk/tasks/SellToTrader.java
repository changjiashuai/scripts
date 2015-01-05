package tasks;

import node.Task;

import org.powerbot.script.rt4.ClientContext;

import utils.CtxMethods;
import utils.Methods;
import utils.Paint;
import vars.Variables.Items;
import vars.Variables.Npcs;
import vars.Variables.Tiles;

public class SellToTrader extends Task<ClientContext> {

	private Paint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);
	
	public SellToTrader(ClientContext ctx, Paint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return ctx.npcs.select().id(Npcs.TRADER_ARD).within(6.0).isEmpty()
				&& ctx.inventory.select().id(Items.SILK).count() > 0;
	}

	@Override
	public boolean execute() {
		paint.setStatus("Traversing to Trader");
		return m.traversePath(Methods.reverse(Tiles.TRADER_ARD_BANK));
	}

}
