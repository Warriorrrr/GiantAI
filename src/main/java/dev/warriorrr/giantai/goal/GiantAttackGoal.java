package dev.warriorrr.giantai.goal;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Giant;

/**
 * {@link net.minecraft.world.entity.ai.goal.ZombieAttackGoal} but for giants
 */
public class GiantAttackGoal extends MeleeAttackGoal {
    private final Giant giant;
    private int raiseArmTicks;

    public GiantAttackGoal(Giant giant, double speed, boolean pauseWhenMobIdle) {
        super(giant, speed, pauseWhenMobIdle);
        this.giant = giant;
    }

    @Override
    public void start() {
        super.start();
        this.raiseArmTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.giant.setAggressive(false);
    }

    @Override
    public void tick() {
        super.tick();
        ++this.raiseArmTicks;
        if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
            this.giant.setAggressive(true);
        } else {
            this.giant.setAggressive(false);
        }
    }
}
