package fr.bretzel;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;

import java.util.ArrayList;

/**
 * Created by MrBretzel on 30/08/2015.
 */
public class Saw extends JavaPlugin implements Listener {

    public static Saw saw;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Saw.saw = this;
    }

    @EventHandler
    public void onBlockBreakevent(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.LOG || event.getBlock().getType() == Material.LOG_2) {
            event.setCancelled(true);
            new SawRunable(event.getBlock().getLocation()).runTaskTimer(this, 0, 1);
            return;
        }
        event.setCancelled(true);
        event.getBlock().getWorld().generateTree(event.getBlock().getLocation(), TreeType.JUNGLE);
    }

    public static ArrayList<Location> getLocation(Location center, int radius) {
        ArrayList<Location> locations = new ArrayList<>();
        for (int x = -radius; x < radius; x++) {
            for (int y = -radius; y < radius; y++) {
                for (int z = -radius; z < radius; z++) {
                    if (distSqr(center, center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z) < radius * radius) {
                        locations.add(new Location(center.getWorld(), center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z));
                    }
                }
            }
        }
        return locations;
    }

    private static int distSqr(Location center, int x, int y, int z) {
        return ((center.getBlockX() - x) * (center.getBlockX() - x) + (center.getBlockY() - y) * (center.getBlockY() - y) + (center.getBlockZ() - z) * (center.getBlockZ() - z));
    }
}
