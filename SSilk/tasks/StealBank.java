package tasks;

import org.powerbot.script.rt4.ClientContext;

import utils.CtxMethods;
import utils.Paint;
import vars.Variables.Filters;
import vars.Variables.Interaction;
import vars.Variables.Objects;
import node.Task;

public class StealBank extends Task<ClientContext> {

	private Paint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);
	
	public StealBank(ClientContext ctx, Paint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return !ctx.objects.select().id(Objects.BANK_BOOTH).within(6.0).isEmpty()
				&& ctx.inventory.select().count() > 0;
	}

	@Override
	public boolean execute() {
		paint.setStatus("Banking");
		if(!ctx.bank.opened()) {
			m.openBank(Objects.BANK_BOOTH, Interaction.BANK);
		}
		if(ctx.inventory.select().count() > 0) {
			m.bankItems(Filters.randomItemsSteal);
		}
		return ctx.inventory.select().count() == 0;
	}

}
