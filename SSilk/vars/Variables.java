package vars;

import java.awt.Color;

import org.powerbot.script.Filter;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Item;

public class Variables {
	public enum Job {
		STEAL, BUY, SELL, NONE;
	}
	public static class Npcs {
		public static final int 
		TRADER_ALK = 525, 
		TRADER_ARD = 1043;
	}
	public static class Objects {
		public static final int 
		BANK_BOOTH = 11744, 
		SILK_STALL = 11729;
	}
	public static class Items {
		public static final int 
		COINS = 995, 
		SILK = 950;
	}
	public static class Filters {
		public static final Filter<Item> randomItemsBuy = new Filter<Item>() {
			@Override
			public boolean accept(Item item) {
				int inventoryID = item.id();
				if(inventoryID == Items.COINS)
					return false;
				return true;
			}
		};
		public static final Filter<Item> randomItemsSell = new Filter<Item>() {
			@Override
			public boolean accept(Item item) {
				int inventoryID = item.id();
				if(inventoryID == Items.SILK)
					return false;
				return true;
			}
		};
		public static final Filter<Item> randomItemsSteal = new Filter<Item>() {
			@Override
			public boolean accept(Item item) {
				return true;
			}
		};
	}
	public static class Interaction {
		public static String
		BANK = "Bank", 
		TALK_ALK = "", 
		TALK_ARD = "Talk-to",
		STEAL = "Steal-from";
	}
	public static class Tiles {
		public static final Tile 
		STEAL_TILE = new Tile(2663, 3316, 0),
		SAFE_TILE = new Tile(2666, 3323, 0);
		public static final Tile[] 
		TRADER_ALK_BANK = {
			new Tile(3301, 3204, 0), new Tile(3300, 3201, 0),
			new Tile(3298, 3197, 0), new Tile(3294, 3192, 0),
			new Tile(3292, 3188, 0), new Tile(3287, 3183, 0),
			new Tile(3284, 3180, 0), new Tile(3280, 3179, 0),
			new Tile(3277, 3175, 0), new Tile(3275, 3170, 0),
			new Tile(3272, 3167, 0), new Tile(3269, 3167, 0)
		},
		TRADER_ARD_BANK = {
			new Tile(2655, 3301, 0), new Tile(2652, 3305, 0),
			new Tile(2648, 3305, 0), new Tile(2644, 3302, 0),
			new Tile(2644, 3298, 0), new Tile(2645, 3294, 0),
			new Tile(2644, 3291, 0), new Tile(2644, 3287, 0),
			new Tile(2644, 3284, 0), new Tile(2647, 3284, 0),
			new Tile(2652, 3284, 0), new Tile(2655, 3283, 0) 
		},
		STALL_ARD_BANK = {
			new Tile(2663, 3316, 0), new Tile(2660, 3313, 0),
			new Tile(2658, 3309, 0), new Tile(2653, 3306, 0),
			new Tile(2648, 3305, 0), new Tile(2644, 3302, 0),
			new Tile(2644, 3298, 0), new Tile(2645, 3294, 0),
			new Tile(2644, 3291, 0), new Tile(2644, 3287, 0),
			new Tile(2644, 3284, 0), new Tile(2647, 3284, 0),
			new Tile(2652, 3284, 0), new Tile(2655, 3283, 0)
		};
	}
	public static class Widgets {
		public static final int[][] 
		ALK_TRADER_WIDGETS = {
			{241, 3},{228, 1},{64, 3},{228, 2},{519, 2}
		},
		ARD_TRADER_WIDGETS = {
			{65, 4}, {242, 4}, {232, 3}, {64, 3}, {230, 2}
		};
		public static final String[] 
		ALK_TRADER_TEXT = {
			"Click here to continue", "How much are they?", "Okay, that sounds good."
		},
		ARD_TRADER_TEXT = {
			"Click here to continue", "120 coins.", "I'll give it to you for 60."
		};
	}
	public static class Bounds {
		public static final int[] 
		TRADERS = {-32, 24, -156, 0, -12, 12},
		STALL = {-64, 92, -152, -92, -32, 32};
	}
	public static class PaintVars {
		public static final Color 
		WHITE = Color.WHITE, 
		BLACK = Color.BLACK;
		public static final int
		THIEVING = 17,
		MS_TO_HR = 3600000,
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
