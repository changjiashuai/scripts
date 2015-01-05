package tasks;

import org.powerbot.script.rt4.ClientContext;

import utils.CtxMethods;
import utils.Paint;
import vars.Variables.*;
import node.Task;

public class BuyBank extends Task<ClientContext> {

	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);
	private Paint paint;
	
	public BuyBank(ClientContext ctx, Paint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return (ctx.inventory.select().select(Filters.randomItemsBuy).count() > 0
				|| ctx.inventory.select().id(Items.COINS).count() == 0)
				&& !ctx.objects.select().id(Objects.BANK_BOOTH).within(6.0).isEmpty();
	}

	@Override
	public boolean execute() {
		paint.setStatus("Banking");
		if(!ctx.bank.opened()) {
			m.openBank(Objects.BANK_BOOTH, Interaction.BANK);
		}
		if(ctx.bank.opened()) {
			if(ctx.inventory.select().id(Items.COINS).count() == 0)
				m.withdrawAllButOne(Items.COINS);
			if(ctx.inventory.select().select(Filters.randomItemsBuy).count() > 0)
				m.bankItems(Filters.randomItemsBuy);
		}
		return ctx.inventory.select().select(Filters.randomItemsBuy).count() == 0
				&& ctx.inventory.select().id(Items.COINS).count() > 0;
	}

}
