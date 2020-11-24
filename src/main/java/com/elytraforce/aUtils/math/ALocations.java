package com.elytraforce.aUtils.math;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;

@SuppressWarnings("unused")
public class ALocations {

    public static Location getFixedLocation(Location var0, BlockFace var1) {
        for (int var2 = 0; var2 < 250 && var0.getBlock().isEmpty(); ++var2) {
            var0.add(0.0D, var1 == BlockFace.UP ? 1.0D : -1.0D, 0.0D);
        }

        return var0.add(0.0D, var1 == BlockFace.UP ? -1.0D : 1.0D, 0.0D);
    }

    public static ArrayList<Location> getCircleAt(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = 6.283185307179586D / amount;
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

    public static ArrayList<Location> getCircleReverse(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = 6.283185307179586D / amount;
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = center.getX() - radius * Math.cos(angle);
            double z = center.getZ() - radius * Math.sin(angle);
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

    public static Location get(Location paramLocation, double paramDouble1, double paramDouble2, double paramDouble3) {
        return new Location(paramLocation.getWorld(), paramLocation.getX() + paramDouble1,
                paramLocation.getY() + paramDouble2, paramLocation.getZ() + paramDouble3);
    }

    public static List<Block> getCube(Location paramLocation, int paramInt) {
        ArrayList<Block> localArrayList = new ArrayList<>();
        int i = paramLocation.getBlockX() - paramInt / 2;
        int j = paramLocation.getBlockY() - paramInt / 2;
        int k = paramLocation.getBlockZ() - paramInt / 2;
        for (int m = i; m < i + paramInt; m++) {
            for (int n = j; n < j + paramInt; n++) {
                for (int i1 = k; i1 < k + paramInt; i1++) {
                    localArrayList.add(Objects.requireNonNull(paramLocation.getWorld()).getBlockAt(m, n, i1));
                }
            }
        }
        return localArrayList;
    }

    public static Entity[] getNearbyEntities(Location l, int radius) {
        int chunkRadius = radius < 16 ? 1 : (radius - radius % 16) / 16;
        HashSet<Entity> radiusEntities = new HashSet<>();
        for (int chX = -chunkRadius; chX <= chunkRadius; chX++) {
            for (int chZ = -chunkRadius; chZ <= chunkRadius; chZ++) {
                int x = (int) l.getX();
                int y = (int) l.getY();
                int z = (int) l.getZ();
                Entity[] arrayOfEntity;
                int j = (arrayOfEntity = new Location(l.getWorld(), x + chX * 16, y, z + chZ * 16).getChunk()
                        .getEntities()).length;
                for (int i = 0; i < j; i++) {
                    Entity e = arrayOfEntity[i];
                    if ((e.getLocation().distance(l) <= radius) && (e.getLocation().getBlock() != l.getBlock())) {
                        radiusEntities.add(e);
                    }
                }
            }
        }
        return (Entity[]) radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }

    public static Location getLocation(Location paramLocation, double paramDouble1, double paramDouble2,
                                       double paramDouble3) {
        return new Location(paramLocation.getWorld(), paramLocation.getX() + paramDouble1,
                paramLocation.getY() + paramDouble2, paramLocation.getZ() + paramDouble3);
    }

    public static List<Location> getCircleBlocks(Location paramLocation, int paramInt) {
        ArrayList<Location> localArrayList = new ArrayList<>();

        int i = paramLocation.getBlockX() - paramInt / 2;
        int j = paramLocation.getBlockY() - paramInt / 2;
        int k = paramLocation.getBlockZ() - paramInt / 2;
        for (int m = i; m < i + paramInt; m++) {
            for (int n = j; n < j + paramInt; n++) {
                for (int i1 = k; i1 < k + paramInt; i1++) {
                    localArrayList.add(paramLocation.getWorld().getBlockAt(m, n, i1).getLocation());
                }
            }
        }
        return localArrayList;
    }

    public static List<Player> getNearbyPlayers(Location paramLocation, double paramDouble) {
        ArrayList<Player> localArrayList = new ArrayList<>();
        for (Player localPlayer : Bukkit.getOnlinePlayers()) {
            if ((localPlayer.getWorld() == paramLocation.getWorld())
                    && (localPlayer.getLocation().distance(paramLocation) <= paramDouble)) {
                localArrayList.add(localPlayer);
            }
        }
        return localArrayList;
    }

    public static List<LivingEntity> getNearbyLivingEntities(Location paramLocation, double paramDouble) {
        ArrayList<LivingEntity> localArrayList = new ArrayList<>();
        for (LivingEntity entities : paramLocation.getWorld().getLivingEntities()) {
            if ((entities.getWorld() == paramLocation.getWorld())
                    && (entities.getLocation().distance(paramLocation) <= paramDouble)) {
                localArrayList.add(entities);
            }
        }
        return localArrayList;
    }

    public static List<Location> getCircle(Location paramLocation, double paramDouble, int paramInt) {
        ArrayList<Location> localArrayList = new ArrayList<>();
        double d1 = 6.283185307179586D / paramInt;
        for (int i = 0; i < paramInt; i++) {
            double d2 = i * d1;

            double d3 = paramLocation.getX() + paramDouble * Math.cos(d2);
            double d4 = paramLocation.getZ() + paramDouble * Math.sin(d2);

            localArrayList.add(new Location(paramLocation.getWorld(), d3, paramLocation.getY(), d4));
        }
        return localArrayList;
    }

    public static List<Location> getSphere(Location loc, int r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx + r; x++) {
            for (int z = cz - r; z <= cz + r; z++) {
                for (int y = sphere ? cy - r : cy; y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if ((dist < r * r) && ((!hollow) || (dist >= (r - 1) * (r - 1)))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public static Location getTargetBlock(Player paramPlayer, int paramInt) {
        Location localLocation = paramPlayer.getEyeLocation();
        Vector localVector = localLocation.getDirection().normalize();

        Block localBlock = null;
        for (int i = 0; i <= paramInt; i++) {
            localLocation.add(localVector);
            localBlock = localLocation.getBlock();
            if (localBlock.getType() != Material.AIR) {
                return localLocation;
            }
        }
        return localLocation;
    }

    public static Player getTarget(Player player, int distanceX, int distanceY, int distanceZ, int finalDistance) {
        List<Entity> n = player.getNearbyEntities(distanceX, distanceY, distanceZ);
        ArrayList<Player> nearPlayers = new ArrayList<>();
        for (Entity e : n) {
            if ((e instanceof Player)) {
                nearPlayers.add((Player) e);
            }
        }
        Player target = null;
        BlockIterator bItr = new BlockIterator(player, finalDistance);
        while (bItr.hasNext()) {
            Block block = bItr.next();
            int bx = block.getX();
            int by = block.getY();
            int bz = block.getZ();
            for (Player e : nearPlayers) {
                Location loc = e.getLocation();
                double ex = loc.getX();
                double ey = loc.getY();
                double ez = loc.getZ();
                if ((bx - 0.75D <= ex) && (ex <= bx + 1.75D) && (bz - 0.75D <= ez) && (ez <= bz + 1.75D)
                        && (by - 1 <= ey) && (ey <= by + 2.5D)) {
                    target = e;
                    break;
                }
            }
        }
        if (target != null && target.getGameMode() == GameMode.SPECTATOR)
            target = null;
        return target;
    }

    public static LivingEntity getTarget(Player player) {
        List<LivingEntity> n = getNearbyLivingEntities(player.getLocation(), 12);
        ArrayList<LivingEntity> nearPlayers = new ArrayList<>();
        for (Entity e : n) {
            if ((e instanceof LivingEntity) && (!(e instanceof Player))) {
                nearPlayers.add((LivingEntity) e);
            }
        }
        LivingEntity target = null;
        BlockIterator bItr = new BlockIterator(player, 30);
        while (bItr.hasNext()) {
            Block block = bItr.next();
            int bx = block.getX();
            int by = block.getY();
            int bz = block.getZ();
            for (LivingEntity e : nearPlayers) {
                Location loc = e.getLocation();
                double ex = loc.getX();
                double ey = loc.getY();
                double ez = loc.getZ();
                if ((bx - 0.75D <= ex) && (ex <= bx + 1.75D) && (bz - 0.75D <= ez) && (ez <= bz + 1.75D)
                        && (by - 1 <= ey) && (ey <= by + 2.5D)) {
                    target = e;
                    break;
                }
            }
        }
        return target;
    }

    public static Player getPlayerTarget(Player player) {
        List<Player> n = getNearbyPlayers(player.getLocation(), 12);
        ArrayList<Player> nearPlayers = new ArrayList<>();
        for (Entity e : n) {
            if (!(e instanceof Player)) {
                nearPlayers.add((Player) e);
            }
        }
        Player target = null;
        BlockIterator bItr = new BlockIterator(player, 30);
        while (bItr.hasNext()) {
            Block block = bItr.next();
            int bx = block.getX();
            int by = block.getY();
            int bz = block.getZ();
            for (Player e : nearPlayers) {
                Location loc = e.getLocation();
                double ex = loc.getX();
                double ey = loc.getY();
                double ez = loc.getZ();
                if ((bx - 0.75D <= ex) && (ex <= bx + 1.75D) && (bz - 0.75D <= ez) && (ez <= bz + 1.75D)
                        && (by - 1 <= ey) && (ey <= by + 2.5D)) {
                    target = e;
                    break;
                }
            }
        }
        return target;
    }

    public static List<Block> getBlocksInRadius(Location location, int radius, boolean hollow) {
        List<Block> blocks = new ArrayList<>();

        int bX = location.getBlockX();
        int bY = location.getBlockY();
        int bZ = location.getBlockZ();
        for (int x = bX - radius; x <= bX + radius; x++) {
            for (int y = bY - radius; y <= bY + radius; y++) {
                for (int z = bZ - radius; z <= bZ + radius; z++) {
                    double distance = (bX - x) * (bX - x) + (bY - y) * (bY - y) + (bZ - z) * (bZ - z);
                    if ((distance < radius * radius) && ((!hollow) || (distance >= (radius - 1) * (radius - 1)))) {
                        Location l = new Location(location.getWorld(), x, y, z);
                        blocks.add(l.getBlock());
                    }
                }
            }
        }
        return blocks;
    }

    public static HashMap<Block, Double> getInRadius(Location loc, double dR) {
        return getInRadius(loc, dR, 9999);
    }

    public static HashMap<Block, Double> getInRadius(Location loc, double dR, double maxHeight) {
        HashMap<Block, Double> blockList = new HashMap<Block, Double>();
        int iR = (int) dR + 1;

        for (int x = -iR; x <= iR; x++)
            for (int z = -iR; z <= iR; z++)
                for (int y = -iR; y <= iR; y++) {
                    if (Math.abs(y) > maxHeight)
                        continue;

                    Block curBlock = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int) (loc.getY() + y),
                            (int) (loc.getZ() + z));

                    double offset = AMath.offset(loc, curBlock.getLocation().add(0.5, 0.5, 0.5));


                    if (offset <= dR)
                        blockList.put(curBlock, 1 - (offset / dR));
                }

        return blockList;
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static Location getGround(Location l) {
        Location locBelow = l.clone().subtract(0, 1, 0);
        while (locBelow.getBlock().getType() == Material.AIR) {
            locBelow.subtract(0, 1, 0);
        }
        return locBelow;
    }

    public static boolean checkEmptyArea(Location corner1, Location corner2) {
        if (corner1.getWorld() != corner2.getWorld()) {
            return false;
        }
        World world = corner1.getWorld();
        for (int x = corner1.getBlockX(); x <= corner2.getBlockX(); x++) {
            for (int y = corner1.getBlockY(); y <= corner2.getBlockY(); y++) {
                for (int z = corner1.getBlockZ(); z <= corner2.getBlockZ(); z++) {
                    Location location = new Location(world, x, y, z);
                    Block block = location.getBlock();
                    if (block.getType() != Material.AIR) {
                        return false;
                    }
                    Entity[] arrayOfEntity;
                    int j = (arrayOfEntity = getNearbyEntities(location, 2)).length;
                    for (int i = 0; i < j; i++) {
                        Entity entity = arrayOfEntity[i];
                        if (((entity instanceof ItemFrame)) || ((entity instanceof Painting))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean isInArea(Player p, Location l, double distance) {
        return p.getLocation().distance(l) <= distance;

    }

    //TODO: serialize location to string, and back!

    public static ArrayList<Location> getLinesDistancedPoints(Location startingPoint, Location endingPoint,
                                                              double distanceBetweenParticles) {
        return getLinesLimitedPoints(startingPoint, endingPoint,
                (int) Math.ceil(startingPoint.distance(endingPoint) / distanceBetweenParticles));
    }

    public static ArrayList<Location> getLinesLimitedPoints(Location startingPoint, Location endingPoint,
                                                            int amountOfPoints) {
        startingPoint = startingPoint.clone();
        Vector vector = endingPoint.toVector().subtract(startingPoint.toVector());
        vector.normalize();
        vector.multiply(startingPoint.distance(endingPoint) / (amountOfPoints + 1D));

        ArrayList<Location> locs = new ArrayList<>();
        for (int i = 0; i < amountOfPoints; i++) {
            locs.add(startingPoint.add(vector).clone());
        }
        return locs;
    }
}