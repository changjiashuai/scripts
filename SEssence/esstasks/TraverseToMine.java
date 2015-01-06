package esstasks;

import java.util.concurrent.Callable;

import node.EssTask;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Interactive;
import org.powerbot.script.rt4.Npc;
import org.powerbot.script.rt4.TilePath;

import essutils.EssPaint;
import essvars.EssVariables.*;

public class TraverseToMine extends EssTask<ClientContext> {

	private TilePath toShop;
	private EssPaint p;

	public TraverseToMine(ClientContext ctx, EssPaint p) {
		super(ctx);
		this.p = p;
	}
	
	@Override
	public boolean activate() {
		return ctx.inventory.select().count() < 28
				&& ctx.objects.select().id(Objects.ESSENCE_MINE).within(6.0).isEmpty();
	}

	@Override
	public boolean execute() {
		p.setStatus("Heading to Mine");
		if(ctx.npcs.select().id(Npcs.AUBURY).within(8.0).isEmpty()
				&& ctx.movement.reachable(ctx.players.local(), Tiles.MINE_TO_BANK[0])) {
			toShop = ctx.movement.newTilePath(Tiles.BANK_TO_MINE);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					if(!ctx.players.local().inMotion())
						toShop.traverse();
					return !ctx.npcs.select().id(Npcs.AUBURY).isEmpty()
							&& !ctx.players.local().inMotion();
				}
			}, Random.nextInt(100, 300), 100);
		}
		if(!ctx.movement.reachable(ctx.players.local(), Tiles.IN_SHOP)
				&& ctx.objects.select().at(Tiles.DOOR_TILE).equals(
						ctx.objects.id(Objects.CLOSED_DOOR))) {
			GameObject closedDoor = ctx.objects.select().at(Tiles.DOOR_TILE)
					.each(Interactive.doSetBounds(Objects.DOOR_BOUNDS)).poll();
			if(!closedDoor.inViewport()) {
				ctx.camera.turnTo(closedDoor);
				ctx.camera.pitch(Random.nextInt(0, 50));
			}
			closedDoor.interact(Interaction.OPEN_DOOR);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.objects.select().at(Tiles.DOOR_TILE).equals(Objects.CLOSED_DOOR);
				}
			}, Random.nextInt(500, 1500));
		}
		if(!ctx.npcs.select().id(Npcs.AUBURY).within(8.0).isEmpty()
				&& ctx.movement.reachable(ctx.players.local(), Tiles.IN_SHOP)) {
			Npc aubury = ctx.npcs.select().id(Npcs.AUBURY).poll();
			if(!aubury.inViewport()) {
				ctx.camera.turnTo(aubury);
				ctx.camera.pitch(Random.nextInt(0, 50));
			}
			aubury.interact(Interaction.ENTER_MINE);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.npcs.select().id(Npcs.AUBURY).isEmpty();
				}
			}, Random.nextInt(500, 1500));
		}
		if(!ctx.objects.select().id(Objects.ESSENCE_MINE).isEmpty()) {
			ctx.movement.step(ctx.objects.select().id(Objects.ESSENCE_MINE).nearest().poll());
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.objects.select().id(Objects.ESSENCE_MINE).within(6.0).isEmpty();
				}
			});
		}
		return !ctx.objects.select().id(Objects.ESSENCE_MINE).within(6.0).isEmpty();
	}

}
