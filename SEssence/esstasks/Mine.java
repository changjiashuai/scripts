package esstasks;

import node.EssTask;

import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import essutils.EssPaint;
import essvars.EssVariables.*;

public class Mine extends EssTask<ClientContext> {
	
	private EssPaint p;

	public Mine(ClientContext ctx, EssPaint p) {
		super(ctx);
		this.p = p;
	}

	@Override
	public boolean activate() {
		return ctx.players.local().animation() == -1
				&& !ctx.objects.select().id(Objects.ESSENCE_MINE).within(6.0).isEmpty()
				&& ctx.inventory.select().count() != 28;
	}

	@Override
	public boolean execute() {
		p.setStatus("Mining Essence");
		GameObject essence = ctx.objects.select().id(Objects.ESSENCE_MINE).nearest().poll();
		if(ctx.movement.distance(ctx.players.local(), essence) > 3)
			ctx.movement.step(essence);
		if(!essence.inViewport())
			ctx.camera.turnTo(essence);
		ctx.camera.pitch(Random.nextInt(0, 20));
		essence.interact("Mine");
		return ctx.players.local().animation() != -1;
	}

}
