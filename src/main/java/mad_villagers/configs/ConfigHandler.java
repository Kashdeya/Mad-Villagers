package mad_villagers.configs;

import mad_villagers.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {

	public static final ConfigHandler INSTANCE = new ConfigHandler();
	public Configuration CONFIG;
	public static int MAD_CHANCE;
	public static float MAD_RANGE;
	public static int DISTANCE_FROM_VILLAGE;
	public static String[] BLACKLISTED_BLOCKS;	
	public final String[] usedCategories = { "Mad Villagers" };

	public void loadConfig(FMLPreInitializationEvent event) {
		CONFIG = new Configuration(event.getSuggestedConfigurationFile());
		CONFIG.load();
		syncConfigs();
	}

	private void syncConfigs() {
        CONFIG.addCustomCategoryComment("Mad Villagers", "Blame Jordan Kappa");
        MAD_CHANCE = CONFIG.getInt("Mad Villager Chance", "Mad Villagers", 100, 1, 100, "Percent that Villagers will get mad if a block is broke!");
        MAD_RANGE = CONFIG.getFloat("Mad Villager Range", "Mad Villagers", 30, 1, 100, "Distance in blocks Villagers notice blocks being broken");
        MAD_CHANCE = CONFIG.getInt("Mad Villager Range From Village", "Mad Villagers", 32, 8, 128, "Distance in Blocks around The Village Villagers aggro");
        BLACKLISTED_BLOCKS = CONFIG.getStringList("Blocks broken ignored by Villagers", "Mad Villagers", new String[] { "minecraft:obsidian"}, "For Sanity");

        if (CONFIG.hasChanged())
			CONFIG.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (Reference.MOD_ID.equals(event.getModID()))
			syncConfigs();
	}
}