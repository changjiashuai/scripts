package silktasks;

import org.powerbot.script.rt4.ClientContext;

import node.Task;
import silkutils.CtxMethods;
import silkutils.SilkPaint;
import silkvars.SilkVariables.*;

public class SellBank extends Task<ClientContext> {

	private SilkPaint paint;
	private CtxMethods<ClientContext> m = new CtxMethods<ClientContext>(ctx);
	
	public SellBank(ClientContext ctx, SilkPaint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return ctx.inventory.count() != 28
				&& !ctx.objects.select().id(Objects.BANK_BOOTH).within(6.0).isEmpty();
	}

	@Override
	public boolean execute() {
		paint.setStatus("Banking");
		if(!ctx.bank.opened()) {
			m.openBank(Objects.BANK_BOOTH, Interaction.BANK);
		}
		if(ctx.bank.opened()) {
			if(ctx.inventory.select().select(Filters.randomItemsSell).count() > 0)
				m.bankItems(Filters.randomItemsSell);
			if(ctx.inventory.select().count() < 28)
				m.withdrawAllButOne(Items.SILK);
		}
		return ctx.inventory.count() == 28;
	}

}
