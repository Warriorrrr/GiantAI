package dev.warriorrr.giantai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

/**
 * ZombieAttackTurtleEggGoal but for giants
 */
public class GiantAttackTurtleEggGoal extends RemoveBlockGoal {
    public GiantAttackTurtleEggGoal(final PathfinderMob mob, final double speed, final int range) {
        super(Blocks.TURTLE_EGG, mob, speed, range);
    }

    @Override
    public void playDestroyProgressSound(final LevelAccessor world, final @NotNull BlockPos pos) {
        world.playSound(null, pos, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + mob.random.nextFloat() * 0.2F);
    }

    @Override
    public void playBreakSound(final Level world, final @NotNull BlockPos pos) {
        world.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
    }

    @Override
    public double acceptedDistance() {
        return 1.14D;
    }
}
