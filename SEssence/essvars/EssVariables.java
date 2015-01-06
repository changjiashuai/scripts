package essvars;

import java.awt.Color;

import org.powerbot.script.Filter;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Item;

public class EssVariables {
	public static class Items {
		public static final int[] PICKAXES = {1265, 1267, 1269, 1273, 1271};
	}
	
	public static class Objects {
		public static final int
			BANK_BOOTH = 11748,
			ESSENCE_MINE = 14912,
			CLOSED_DOOR = 11780;
		public static final int[] 
				PORTAL = {14913, 14914, 14915, 14916, 14917, 14918, 15638},
				DOOR_BOUNDS = {-48, 72, -224, 0, 44, 64},
				PORTAL_BOUNDS  = {-20, 44, -36, 0, -32, 32};
	}
	
	public static class Npcs {
		public static final int AUBURY = 637;
	}
	
	public static class Tiles {
		public static final Tile[] 
			MINE_TO_BANK = {
				new Tile(3253, 3398, 0), 
				new Tile(3259, 3409, 0), 
				new Tile(3260, 3423, 0), 
				new Tile(3254, 3420, 0)},
			BANK_TO_MINE = {
				new Tile(3260, 3423, 0),
				new Tile(3260, 3417, 0),
				new Tile(3259, 3412, 0),
				new Tile(3259, 3409, 0),
				new Tile(3257, 3401, 0),
				new Tile(3253, 3398, 0), 
		};
		public static final Tile 
			IN_SHOP = new Tile(3253, 3401, 0),
			DOOR_TILE = new Tile(3253, 3398, 0);
	}
	
	public static class Filters {
		public static final Filter<Item> randomItems = new Filter<Item>() {
			@Override
			public boolean accept(Item item) {
				int inventoryID = item.id();
				for(int i : Items.PICKAXES) {
					if(inventoryID == i)
						return false;
				}
				return true;
			}
		};
	}
	
	public static class Interaction {
		public static final String 
			ENTER_MINE = "Teleport",
			BANK = "Bank",
			MINE = "Mine",
			OPEN_DOOR = "Open";
		public static final String[] EXIT_MINE = {"Exit", "Use"};
	}
	
	public static class PaintVars {
		public static final Color WHITE = Color.WHITE, BLACK = Color.BLACK;
		public static final int 
			MS_TO_HR = 3600000, 
			MINING = 14,
			TEXT_X = 25,
			TEXT_Y = 375,
			Y_OFFSET = 20,
			X_OFFSET = 175,
			RECT_X = 5,
			RECT_Y = 345,
			RECT_WIDTH = 510,
			RECT_HEIGHT = 130;
		public static final int[] XP = {0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107,
            2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
            16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983,
            75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742,
            302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
            1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594,
            3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629,
            11805606, 13034431, 0
        };
	}
}
