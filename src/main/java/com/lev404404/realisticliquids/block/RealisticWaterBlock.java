package com.lev404404.realisticliquids.block;

import net.minecraft.block.FluidBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;

public class RealisticWaterBlock extends FluidBlock {
    public RealisticWaterBlock(AbstractBlock.Settings settings) {
        super(() -> Blocks.WATER.getFluidState(Blocks.WATER.getDefaultState()).getFluid(), settings);
    }

    @Override
    public ActionResult onUse(net.minecraft.block.BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && player.getStackInHand(hand).isEmpty()) {
            player.getInventory().offerOrDrop(new net.minecraft.item.ItemStack(Items.WATER_BUCKET));
            world.breakBlock(pos, false);
            
            for (int i = 0; i < 20; i++) {
                world.addParticle(ParticleTypes.SPLASH, 
                    pos.getX() + 0.5 + (Math.random() - 0.5) * 0.8,
                    pos.getY() + 0.5 + (Math.random() - 0.5) * 0.8,
                    pos.getZ() + 0.5 + (Math.random() - 0.5) * 0.8,
                    (Math.random() - 0.5) * 0.4,
                    Math.random() * 0.4,
                    (Math.random() - 0.5) * 0.4
                );
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
