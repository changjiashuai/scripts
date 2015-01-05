package utils;

import java.util.EnumSet;
import java.util.concurrent.Callable;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.Path;
import org.powerbot.script.rt4.TilePath;

public class CtxMethods <C extends ClientContext> extends ClientAccessor<C> {
	
	public CtxMethods(C ctx) {
		super(ctx);
	}

	public boolean attemptTextWidgetClick(final int[][] WIDGETS, final String[] WIDGET_TEXT) {
		boolean widgetVisible = false;
		for(int i = 0; i < WIDGETS.length; i++) {
			final int X = i;
			if(ctx.widgets.component(WIDGETS[X][0], WIDGETS[X][1]).visible()) {
				for(int j = 0; j < WIDGET_TEXT.length; j++) {
					if(ctx.widgets.component(WIDGETS[X][0], WIDGETS[X][1]).text().contains(WIDGET_TEXT[j])) {
						widgetVisible = true;
						ctx.widgets.component(WIDGETS[X][0], WIDGETS[X][1]).click();
						Condition.wait(new Callable<Boolean>() {
							@Override
							public Boolean call() throws Exception {
								return true;
							}
						}, 500);
						break;
					}
				}
			}
		}
		return widgetVisible;
	}
	
	public boolean traversePath(final Tile[] path) {
		final EnumSet<Path.TraversalOption> e = EnumSet.of(Path.TraversalOption.HANDLE_RUN, Path.TraversalOption.SPACE_ACTIONS);
		final TilePath t = ctx.movement.newTilePath(path);
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				if(ctx.movement.destination().distanceTo(ctx.players.local()) < 4
						|| !ctx.players.local().inMotion())
					t.traverse(e);
				return path[path.length-1].distanceTo(ctx.players.local()) < 2;
			}
		}, 200, 200);
		return path[path.length-1].distanceTo(ctx.players.local()) < 2
				&& !ctx.players.local().inMotion();
	}
	
	public boolean bankItems(Filter<Item> filter) {
		while(ctx.inventory.select().select(filter).count() > 0) {
			if(!ctx.bank.opened())
				break;
			final int ITEM_ID = ctx.inventory.select().select(filter).poll().id();
    		ctx.bank.deposit(ITEM_ID, Bank.Amount.ALL);
    		Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.inventory.select().id(ITEM_ID).count() == 0;
				}
    		}, 300, 10);
		}
		return ctx.inventory.select().select(filter).count() == 0;
	}
	
	public boolean withdrawAllButOne(final int ITEM_ID) {
		ctx.bank.withdraw(ITEM_ID, Bank.Amount.ALL_BUT_ONE);
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.inventory.select().id(ITEM_ID).count() > 0;
			}
		}, 100, 30);
		return ctx.inventory.select().id(ITEM_ID).count() > 0;
	}
	
	public boolean openBank(final int BOOTH_ID, final String TEXT) {
		GameObject booth = ctx.objects.id(BOOTH_ID).nearest().poll();
		if(!booth.inViewport()) {
			ctx.camera.turnTo(booth);
			ctx.camera.pitch(Random.nextInt(0, 50));
		}
		booth.interact(TEXT);
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.bank.opened();
			}
		}, 500);
		return ctx.bank.opened();
	}
}
