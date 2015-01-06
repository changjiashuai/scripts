package silktasks;

import silkutils.CtxMethods;
import silkutils.SilkPaint;
import silkvars.SilkVariables.Items;
import silkvars.SilkVariables.Objects;
import silkvars.SilkVariables.Tiles;
import node.Task;

import org.powerbot.script.rt4.ClientContext;

public class BuyToBank extends Task<ClientContext> {
	
	private SilkPaint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);

	public BuyToBank(ClientContext ctx, SilkPaint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return (ctx.inventory.select().count() == 28
				|| ctx.inventory.id(Items.COINS).count() == 0)
				&& ctx.objects.select().id(Objects.BANK_BOOTH).within(6.0).isEmpty();
	}

	@Override
	public boolean execute() {
		paint.setStatus("Traversing to bank");
		return m.traversePath(Tiles.TRADER_ALK_BANK);
	}

}
