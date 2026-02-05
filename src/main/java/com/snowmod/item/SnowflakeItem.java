package com.snowmod.item;

import com.snowmod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SnowflakeItem extends Item {
    private static final int FREEZE_DURATION = 600; // 30 seconds (20 ticks per second)
    private static final int EFFECT_LEVEL = 255;
    private static final double FREEZE_RADIUS = 5.0;
    private static final double KNOCKBACK_STRENGTH = 5.0;

    public SnowflakeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        // List of blocks that can be transformed into snow blocks
        if (state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK) ||
                state.is(Blocks.COARSE_DIRT) || state.is(Blocks.PODZOL) ||
                state.is(Blocks.SAND) || state.is(Blocks.RED_SAND) ||
                state.is(Blocks.GRAVEL) || state.is(Blocks.STONE)) {

            if (!level.isClientSide) {
                // Transform block to snow block
                level.setBlock(pos, ModBlocks.SNOW_BLOCK.get().defaultBlockState(), 3);

                // Play sound
                level.playSound(null, pos, SoundEvents.SNOW_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);

                // Spawn particles
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.SNOWFLAKE,
                            pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                            20, 0.5, 0.5, 0.5, 0.05);
                }

                // Consume item
                context.getItemInHand().shrink(1);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        net.minecraft.world.item.ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            // Find all living entities within radius
            AABB searchBox = new AABB(
                    player.getX() - FREEZE_RADIUS, player.getY() - FREEZE_RADIUS, player.getZ() - FREEZE_RADIUS,
                    player.getX() + FREEZE_RADIUS, player.getY() + FREEZE_RADIUS, player.getZ() + FREEZE_RADIUS);

            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                    entity -> entity != player && entity.isAlive());

            if (!entities.isEmpty()) {
                for (LivingEntity entity : entities) {
                    // Apply freeze effects
                    entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, FREEZE_DURATION, EFFECT_LEVEL,
                            false, true));
                    entity.addEffect(
                            new MobEffectInstance(MobEffects.BLINDNESS, FREEZE_DURATION, EFFECT_LEVEL, false, true));

                    // Apply knockback
                    Vec3 direction = entity.position().subtract(player.position()).normalize();
                    entity.setDeltaMovement(direction.x * KNOCKBACK_STRENGTH, 0.5, direction.z * KNOCKBACK_STRENGTH);
                    entity.hurtMarked = true;

                    // Spawn particles around entity
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.SNOWFLAKE,
                                entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(),
                                30, 0.5, 0.5, 0.5, 0.1);
                    }
                }

                // Play sound
                level.playSound(null, player.blockPosition(), SoundEvents.SNOW_PLACE, SoundSource.PLAYERS, 1.0f, 0.8f);

                // Consume item
                itemStack.shrink(1);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
