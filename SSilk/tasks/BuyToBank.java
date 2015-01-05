package tasks;

import utils.CtxMethods;
import utils.Paint;
import node.Task;

import org.powerbot.script.rt4.ClientContext;

import vars.Variables.Items;
import vars.Variables.Objects;
import vars.Variables.Tiles;

public class BuyToBank extends Task<ClientContext> {
	
	private Paint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);

	public BuyToBank(ClientContext ctx, Paint paint) {
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
