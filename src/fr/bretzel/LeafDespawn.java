package fr.bretzel;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by MrBretzel on 04/09/2015.
 */
public class LeafDespawn extends BukkitRunnable {

    private ArrayList<Location> blocks = new ArrayList<>();
    private Random random = new Random();
    private World world;

    public LeafDespawn(ArrayList<Location> blocks, World world) {
        this.blocks = blocks;
        this.world = world;
    }

    @Override
    public void run() {
        if (blocks.size() > 0) {
            int i = random.nextInt(blocks.size());
            Location location = blocks.get(i);
            Block b = world.getBlockAt(location.getX(), location.getY(), location.getZ());
            b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType().getId());
            b.breakNaturally();
            blocks.remove(i);
        } else {
            blocks.clear();
            this.cancel();
        }
    }
}
