package dev.warriorrr.giantai;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import dev.warriorrr.giantai.goal.GiantAttackGoal;
import dev.warriorrr.giantai.goal.GiantAttackTurtleEggGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftGiant;
import org.bukkit.entity.Giant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class GiantAI extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(final EntityAddToWorldEvent event) {
        if (!(event.getEntity() instanceof Giant giant))
            return;

        registerGoals(((CraftGiant) giant).getHandle());
    }

    void registerGoals(final @NotNull net.minecraft.world.entity.monster.Giant giant) {
        giant.goalSelector.addGoal(8, new LookAtPlayerGoal(giant, Player.class, 8.0F));
        giant.goalSelector.addGoal(8, new RandomLookAroundGoal(giant));

        giant.goalSelector.addGoal(2, new GiantAttackGoal(giant, 1.0D, false));
        giant.goalSelector.addGoal(6, new MoveThroughVillageGoal(giant, 1.0D, true, 4, () -> false));
        giant.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(giant, 1.0D));

        giant.targetSelector.addGoal(1, new HurtByTargetGoal(giant).setAlertOthers(ZombifiedPiglin.class));
        giant.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, Player.class, true));

        giant.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(giant, IronGolem.class, true));
        giant.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(giant, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));

        if (giant.level().spigotConfig.zombieAggressiveTowardsVillager)
            giant.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(giant, AbstractVillager.class, false));

        if (giant.level().paperConfig().entities.behavior.zombiesTargetTurtleEggs)
            giant.goalSelector.addGoal(4, new GiantAttackTurtleEggGoal(giant, 1.0D, 3));
    }
}
