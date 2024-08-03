package de.plocki.blenma.easy.entityai;

import de.plocki.blenma.BlockEntityManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class StupidCow implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if(event.getEntityType() == EntityType.COW) {
            if(event.getEntity().getName().equalsIgnoreCase("nowdinnerbone")) {
                startCircularMovement((Cow) event.getEntity());
                event.getEntity().setCustomNameVisible(false);
            }
        }
    }

    private void startCircularMovement(Cow cow) {
        new BukkitRunnable() {
            private double angle = 0;
            private final Location center = cow.getLocation().clone();

            @Override
            public void run() {
                if (cow.isDead()) {
                    this.cancel();
                    return;
                }

                angle += 0.1;
                double radius = 5.0;
                double x = center.getX() + radius * Math.cos(angle);
                double z = center.getZ() + radius * Math.sin(angle);
                Location targetLocation = new Location(center.getWorld(), x, center.getY(), z);

                cow.getPathfinder().moveTo(targetLocation);

                cow.setRotation((float) Math.toDegrees(angle), cow.getLocation().getPitch());

                if(new Random().nextInt(6) == 2) {
                    if(cow.getName().startsWith("now")) {
                        cow.customName(Component.text("Dinnerbone"));
                        cow.setCustomNameVisible(false);
                    } else {
                        cow.customName(Component.text("nowdinnerbone"));
                        cow.setCustomNameVisible(false);
                    }
                }
            }
        }.runTaskTimer(BlockEntityManager.getPlugin(BlockEntityManager.class), 0L, 1L);
    }

}
