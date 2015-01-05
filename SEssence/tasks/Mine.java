package tasks;

import node.Task;

import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import utils.Paint;
import vars.Variables.*;

public class Mine extends Task<ClientContext> {
	
	private Paint p;

	public Mine(ClientContext ctx, Paint p) {
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
