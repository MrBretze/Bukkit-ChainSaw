package fr.bretzel;

import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by Mrbretzel on 30/08/2015.
 */
public class SawRunable extends BukkitRunnable {

    private Location base;

    private int radius = 8;
    private int airBlock = 0;
    private int y;



    public SawRunable(Location base) {
        this.base = base;
        this.y = base.getBlockY();
    }

    @Override
    public void run() {
        boolean isBaseLog = false;
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                Block b = base.getWorld().getBlockAt(base.getBlockX() + x, y, base.getBlockZ() + z);
                if (isLog(b.getType()) && b.getChunk().isLoaded()) {
                    isBaseLog = true;
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType().getId());
                    b.breakNaturally();
                }
            }
        }

        if (isLog(base.getBlock().getType()) && base.getBlock().getChunk().isLoaded()) {
            base.getBlock().getWorld().playEffect(base.getBlock().getLocation(), Effect.STEP_SOUND, base.getBlock().getType().getId());
            base.getBlock().breakNaturally();
        }

        if (isLeave(base.getBlock().getType()) || isBaseLog) {
            ArrayList<fr.bretzel.Location> blocks = new ArrayList<>();
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = base.getWorld().getBlockAt(base.getBlockX() + x, y, base.getBlockZ() + z);
                    if (isLeave(b.getType()) && !isPlacedByPlayer(b) && !hasLogInRadius(b.getLocation()) && b.getChunk().isLoaded()) {
                        blocks.add(new fr.bretzel.Location(b.getLocation()));
                    }
                }
            }
            new LeafDespawn(blocks, base.getWorld()).runTaskTimer(Saw.saw, 0, 1);
        }

        base.add(0.0D, 1.0D, 0.0D);
        y++;

        if (base.getWorld().getBlockAt(base.getBlockX(), y, base.getBlockZ()).getType() == Material.AIR) {
            if (this.airBlock > 3) {
                airBlock++;
                return;
            }
            this.cancel();
        }
    }

    public boolean hasLogInRadius(Location location) {
        ArrayList<Location> locations = Saw.getLocation(location, 10);
        for (Location l : locations) {
            if (l.getBlockY() <= location.getBlockY() && isLog(l.getBlock().getType())) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlacedByPlayer(Block leaves) {
        return (leaves.getData() & 4) == 4;
    }

    public static boolean isLog(Material material) {
        return material == Material.LOG || material == Material.LOG_2;
    }

    public static boolean isLeave(Material material) {
        return material == Material.LEAVES || material == Material.LEAVES_2;
    }
}
