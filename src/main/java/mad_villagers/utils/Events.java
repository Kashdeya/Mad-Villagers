package mad_villagers.utils;

import java.util.ArrayList;
import java.util.List;

import mad_villagers.configs.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Events {

	@SubscribeEvent
	public void blockBreak(BlockEvent.BreakEvent event) {
		if (!event.getWorld().isRemote && !isBlacklisted(event.getState().getBlock())) {
			EntityPlayer player;

			player = event.getPlayer();
			int chance = 1 + event.getWorld().rand.nextInt(100);

			List<EntityVillager> list = player.getEntityWorld().getEntitiesWithinAABB(EntityVillager.class, player.getEntityBoundingBox().expand((double) ConfigHandler.MAD_RANGE, (double) ConfigHandler.MAD_RANGE, (double) ConfigHandler.MAD_RANGE));

			if (!player.capabilities.isCreativeMode && chance < ConfigHandler.MAD_CHANCE && (!list.isEmpty())) {
				for (EntityVillager entity : list) {
					if (isNearVillage(entity)) {
						entity.setRevengeTarget(player);
						entity.setAttackTarget(player);
					}
				}
			}
		}
	}
	
	public boolean isNearVillage(EntityVillager entity) {
		Village village = entity.getEntityWorld().getVillageCollection().getNearestVillage(new BlockPos(entity), ConfigHandler.DISTANCE_FROM_VILLAGE);
		if (village == null)
			return false;
		else
			return true;
	}

	private static Boolean isBlacklisted(Block block) {
		List<Block> blockList = new ArrayList<Block>();
		for (int blocks = 0; blocks < ConfigHandler.BLACKLISTED_BLOCKS.length; blocks++) {
			String entry = ConfigHandler.BLACKLISTED_BLOCKS[blocks].trim();
			Block outBlock = Block.REGISTRY.getObject(new ResourceLocation(entry));
			blockList.add(outBlock);
		}
		if(blockList.contains(block))
			return true;
		return false;
	}
}
