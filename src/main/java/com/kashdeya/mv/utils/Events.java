package com.kashdeya.mv.utils;

import java.util.List;

import com.kashdeya.mv.main.Config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Events {
	
	@SubscribeEvent
	public void blockBreak(BlockEvent.BreakEvent event)
	{
			EntityPlayer player;
			
			player = event.getPlayer();
			int chance = event.getWorld().rand.nextInt(Config.MAD_CHANCE);
			
			List<EntityVillager> list = player.getEntityWorld().getEntitiesWithinAABB(EntityVillager.class, player.getEntityBoundingBox().expand(30.0D, 30.0D, 30.0D));
			
			if ((!player.capabilities.isCreativeMode) && (chance == 0) && (!list.isEmpty())) {
				for (EntityVillager entity : list)
				{
					entity.setRevengeTarget(player);
					entity.setAttackTarget(player);
				}
			}
		}
	
}
