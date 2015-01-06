package silktasks;

import silkutils.*;
import silkvars.SilkVariables.Items;
import silkvars.SilkVariables.Npcs;
import silkvars.SilkVariables.Tiles;
import node.Task;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class BuyToTrader extends Task<ClientContext> {
	
	private SilkPaint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);

	public BuyToTrader(ClientContext ctx, SilkPaint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return ctx.npcs.select().id(Npcs.TRADER_ALK).within(6.0).isEmpty()
				&& ctx.inventory.select().count() < 28
				&& ctx.inventory.id(Items.COINS).count() > 0;
	}

	@Override
	public boolean execute() {
		paint.setStatus("Traversing to Trader");
		return m.traversePath((Tile[]) Methods.reverse(Tiles.TRADER_ALK_BANK));
	}

}
