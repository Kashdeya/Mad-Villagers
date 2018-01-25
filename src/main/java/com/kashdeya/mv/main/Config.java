package com.kashdeya.mv.main;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
	
	public static int MAD_CHANCE;
	
	public static Configuration config;
	public static final File configDir = new File("config");	
	
	public static void initMain()
	{
		
		File f = new File(configDir, "Mad Villagers.cfg");
        config = new Configuration(f);
        config.load();
		
		// AI
        config.addCustomCategoryComment("Mad Villagers", "Blame Jordan Kappa");
        MAD_CHANCE = config.getInt("Mad Villager Chance", "Mad Villagers", 100, 1, 100, "Percent that Villagers will get mad if a block is broke!");
        
		if (config.hasChanged() == true){
        config.save();
        }
	}
	
}
