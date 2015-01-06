package esstasks;

import java.util.concurrent.Callable;

import node.EssTask;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Interactive;
import org.powerbot.script.rt4.TilePath;

import essutils.EssPaint;
import essvars.EssVariables.*;

public class TraverseToBank extends EssTask<ClientContext> {
	
	private TilePath toBank;
	private GameObject portal;
	private EssPaint p;

	public TraverseToBank(ClientContext ctx, EssPaint p) {
		super(ctx);
		this.p = p;
	}

	@Override
	public boolean activate() {
		return ctx.inventory.select().count() == 28
				&& ctx.objects.select().id(Objects.BANK_BOOTH).within(6.0).isEmpty();
	}

	@Override
	public boolean execute() {
		p.setStatus("Heading to Bank");
		if(!ctx.objects.select().id(Objects.PORTAL).isEmpty()) {
			portal = ctx.objects.select().id(Objects.PORTAL)
					.each(Interactive.doSetBounds(Objects.PORTAL_BOUNDS)).nearest().poll();
			if(portal.tile().distanceTo(ctx.players.local()) > 3) {
				ctx.movement.step(portal);
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return portal.tile().distanceTo(ctx.players.local()) < 3;
					}
				}, Random.nextInt(1000,  1500));
			}
			ctx.camera.pitch(true);
			portal.click();
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.npcs.select().id(Npcs.AUBURY).isEmpty();
				}
			}, Random.nextInt(500, 1500));
		}
		if(!ctx.movement.reachable(ctx.players.local(), Tiles.MINE_TO_BANK[0])
				&& !ctx.objects.select().id(Objects.CLOSED_DOOR).within(6.0).isEmpty()) {
			GameObject closedDoor = ctx.objects.select().at(Tiles.DOOR_TILE)
					.each(Interactive.doSetBounds(Objects.DOOR_BOUNDS)).poll();
			if(!closedDoor.inViewport()) {
				ctx.camera.turnTo(closedDoor);
				ctx.camera.pitch(Random.nextInt(25, 50));
			}
			closedDoor.interact(Interaction.OPEN_DOOR);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.movement.reachable(ctx.players.local(), Tiles.MINE_TO_BANK[0]);
				}
			}, Random.nextInt(2000, 3000));
		}
		if(ctx.movement.reachable(ctx.players.local(), Tiles.MINE_TO_BANK[0])
				&& ctx.objects.select().id(Objects.BANK_BOOTH).within(6.0).isEmpty()) {
			toBank = ctx.movement.newTilePath(Tiles.MINE_TO_BANK);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					if(!ctx.players.local().inMotion())
						toBank.traverse();
					return !ctx.objects.select().id(Objects.BANK_BOOTH).within(6.0).isEmpty()
							&& !ctx.players.local().inMotion();
				}
			}, 300, 100);
		}
		return !ctx.objects.select().id(Objects.BANK_BOOTH).within(6.0).isEmpty();
	}

}
