package com.kashdeya.mv.main;

import java.io.File;

import com.kashdeya.mv.proxy.CommonProxy;
import com.kashdeya.mv.utils.Events;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.VERSION)

public class MadVillagers {
	
	@Instance(References.MOD_ID)
    public static MadVillagers instance;
	
	@SidedProxy(clientSide=References.PROXY_CLIENT, serverSide=References.PROXY_COMMON)
	public static CommonProxy PROXY;
	
	public static final File configDir = new File("config");
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent e) {
		// Config
		Config.initMain();
		
		// Modular Events
		MinecraftForge.EVENT_BUS.register(instance);
		
		MinecraftForge.EVENT_BUS.register(new Events());
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
    	PROXY.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) 
    {}
    
}
