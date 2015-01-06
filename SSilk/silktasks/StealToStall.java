package silktasks;

import org.powerbot.script.rt4.ClientContext;

import silkutils.CtxMethods;
import silkutils.Methods;
import silkutils.SilkPaint;
import silkvars.SilkVariables.Tiles;
import node.Task;

public class StealToStall extends Task<ClientContext> {

	private SilkPaint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);
	
	public StealToStall(ClientContext ctx, SilkPaint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return Tiles.STEAL_TILE.distanceTo(ctx.players.local()) > 5
				&& ctx.inventory.select().count() < 28;
	}

	@Override
	public boolean execute() {
		paint.setStatus("Traversing to Stall");
		return m.traversePath(Methods.reverse(Tiles.STALL_ARD_BANK));
	}

}
