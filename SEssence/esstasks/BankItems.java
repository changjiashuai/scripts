package esstasks;

import java.util.concurrent.Callable;

import node.EssTask;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import essutils.EssPaint;
import essvars.EssVariables.*;

public class BankItems extends EssTask<ClientContext> {

	private EssPaint p;
	private int itemID;
	
	public BankItems(ClientContext ctx, EssPaint p) {
		super(ctx);
		this.p = p;
	}

	@Override
	public boolean activate() {
		return ctx.inventory.select().count() == 28
                && !ctx.objects.select().id(Objects.BANK_BOOTH).within(3.0).isEmpty()
                && !ctx.players.local().inMotion();
	}

	@Override
	public boolean execute() {
		p.setStatus("Banking");
        if(!ctx.bank.opened()) {
        	GameObject bankBooth = ctx.objects.select().id(Objects.BANK_BOOTH).nearest().poll();
        	if(!bankBooth.inViewport()) {
        		ctx.camera.turnTo(bankBooth);
        		ctx.camera.pitch(Random.nextInt(0, 50));
        	}
        	bankBooth.interact(Interaction.BANK);
        	Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.bank.opened();
				}
        	}, Random.nextInt(500, 1500));
        }
        while(ctx.inventory.select(Filters.randomItems).count() > 0) {
			if(!ctx.bank.opened())
				break;
			itemID = ctx.inventory.select().select(Filters.randomItems).poll().id();
    		ctx.bank.deposit(itemID, Bank.Amount.ALL);
    		Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.inventory.select().id(itemID).count() == 0;
				}
    		}, 100, 100);
		}
        return ctx.inventory.select().select(Filters.randomItems).count() == 0;
	}

}
