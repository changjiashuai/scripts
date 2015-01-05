package tasks;

import node.Task;

import org.powerbot.script.rt4.ClientContext;

import utils.CtxMethods;
import utils.Paint;
import vars.Variables.Items;
import vars.Variables.Objects;
import vars.Variables.Tiles;

public class SellToBank extends Task<ClientContext> {

	private Paint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);
	
	public SellToBank(ClientContext ctx, Paint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return ctx.objects.select().id(Objects.BANK_BOOTH).within(6.0).isEmpty()
				&& ctx.inventory.select().id(Items.SILK).count() == 0;
	}

	@Override
	public boolean execute() {
		paint.setStatus("Traversing to Bank");
		return m.traversePath(Tiles.TRADER_ARD_BANK);
	}

}
