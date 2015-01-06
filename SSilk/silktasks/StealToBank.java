package silktasks;

import org.powerbot.script.rt4.ClientContext;

import silkutils.CtxMethods;
import silkutils.SilkPaint;
import silkvars.SilkVariables.Objects;
import silkvars.SilkVariables.Tiles;
import node.Task;

public class StealToBank extends Task<ClientContext> {

	private SilkPaint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);
	
	public StealToBank(ClientContext ctx, SilkPaint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return ctx.objects.select().id(Objects.BANK_BOOTH).within(6.0).isEmpty()
				&& ctx.inventory.select().count() == 28;
	}

	@Override
	public boolean execute() {
		paint.setStatus("Traversing to Bank");
		return m.traversePath(Tiles.STALL_ARD_BANK);
	}

}
