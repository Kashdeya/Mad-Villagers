package mad_villagers.utils;

import java.util.ArrayList;
import java.util.List;

import mad_villagers.configs.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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

	@SubscribeEvent
	public void onEntityJoinedWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityVillager) {
			EntityVillager entity = (EntityVillager) event.getEntity();
			World world = entity.getEntityWorld();
			if (!world.isRemote)
				setUpAI(entity);
		}
	}

	public void setUpAI(EntityCreature entity) {
		entity.tasks.addTask(1, new AIVillagerAttack(entity, 1.25D, ConfigHandler.LONG_TERM_MEMORY));
		entity.targetTasks.addTask(1, new EntityAIHurtByTarget(entity, ConfigHandler.CALL_FOR_HELP));
	}

	static class AIVillagerAttack extends EntityAIAttackMelee {
		public AIVillagerAttack(EntityCreature entity, double moveSpeed, boolean longMemory) {
			super(entity, moveSpeed, longMemory);
		}

		@Override
		protected void checkAndPerformAttack(EntityLivingBase entity, double distanceIn) {
			double reach = this.getAttackReachSqr(entity);
			if (distanceIn <= reach && this.attackTick <= 0) {
				attackTick = 20;
				attacker.swingArm(EnumHand.MAIN_HAND);

				// This overrides the vanilla method with out needing to fuck about with entities
					attackEntityAsMob(attacker, entity); // Use our damage value attack below
			}
		}

		public boolean attackEntityAsMob(EntityCreature entityAttacker, Entity entityIn) {
			return entityIn.attackEntityFrom(DamageSource.causeMobDamage(entityAttacker), ConfigHandler.ATTACK_DAMAGE);
		}
	}
}
