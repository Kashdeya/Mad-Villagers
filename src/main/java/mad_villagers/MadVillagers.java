package mad_villagers;

import mad_villagers.configs.ConfigHandler;
import mad_villagers.proxy.CommonProxy;
import mad_villagers.utils.Events;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.CONFIG_GUI)

public class MadVillagers {

	@Instance(Reference.MOD_ID)
	public static MadVillagers INSTANCE;

	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_COMMON)
	public static CommonProxy PROXY;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// Config
		ConfigHandler.INSTANCE.loadConfig(event);
		MinecraftForge.EVENT_BUS.register(new Events());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

}
