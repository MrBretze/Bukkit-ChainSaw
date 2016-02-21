package fr.bretzel;

/**
 * Created by Axelo on 04/09/2015.
 */
public class Location {

    private int x, y, z;

    public Location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(org.bukkit.Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
