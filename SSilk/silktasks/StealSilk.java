package silktasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Interactive;

import silkutils.SilkPaint;
import silkvars.SilkVariables.Bounds;
import silkvars.SilkVariables.Interaction;
import silkvars.SilkVariables.Items;
import silkvars.SilkVariables.Objects;
import silkvars.SilkVariables.Tiles;
import node.Task;

public class StealSilk extends Task<ClientContext> {

	private SilkPaint paint;
	
	public StealSilk(ClientContext ctx, SilkPaint paint) {
		super(ctx);
		this.paint = paint;
	}

	@Override
	public boolean activate() {
		return Tiles.STEAL_TILE.distanceTo(ctx.players.local()) < 6
				&& ctx.inventory.select().count() < 28;
	}

	@Override
	public boolean execute() {
		paint.setStatus("Stealing silk");
		int silkCount = ctx.inventory.select().id(Items.SILK).count();
		if(ctx.players.local().inCombat()) {
			ctx.movement.step(Tiles.SAFE_TILE);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.players.local().tile().equals(Tiles.SAFE_TILE);
				}
			}, 100, 50);
			ctx.movement.step(Tiles.STEAL_TILE);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.players.local().tile().equals(Tiles.STEAL_TILE);
				}
			}, 100, 50);
		}
		if(!ctx.players.local().tile().equals(Tiles.STEAL_TILE)
				&& !ctx.players.local().inCombat()) {
			ctx.movement.step(Tiles.STEAL_TILE );
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.players.local().tile().equals(Tiles.STEAL_TILE)
							|| ctx.players.local().inCombat();
				}
			});
		}
		if(ctx.players.local().tile().equals(Tiles.STEAL_TILE) && 
				!ctx.objects.select().id(Objects.SILK_STALL).within(6.0).isEmpty()
				&& !ctx.players.local().inCombat()) {
			GameObject stall = ctx.objects.select().id(Objects.SILK_STALL).each(Interactive.doSetBounds(Bounds.STALL)).nearest().poll();
			if(!stall.inViewport()) {
				ctx.camera.turnTo(stall);
				ctx.camera.pitch(true);
			}
			stall.interact(Interaction.STEAL);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.objects.select().id(Objects.SILK_STALL).within(6.0).isEmpty()
							|| ctx.players.local().inCombat();
				}
			}, 100);
		}
		if(ctx.players.local().tile().equals(Tiles.STEAL_TILE) && 
				ctx.objects.select().id(Objects.SILK_STALL).within(6.0).isEmpty()
				&& !ctx.players.local().inCombat()) {
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.objects.select().id(Objects.SILK_STALL).within(6.0).isEmpty()
							|| ctx.players.local().inCombat();
				}
			}, 100, 100);
		}
		if(ctx.inventory.select().id(Items.SILK).count() == silkCount + 1) {
			paint.incrementSilk();
			return true;
		}
		return false;
	}

}
