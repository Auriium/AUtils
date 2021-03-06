package com.elytraforce.aUtils.spigot.particles;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.Callable;

@SuppressWarnings("unused")
public class SpigotParticle {

    public Particle particle;

    public Location location;

    public Callable<Location> locationCaller;
    public int count;
    public double offsetx, offsety, offsetz;
    public double extra;

    public Vector rotation;

    public Object data;

    /**
     * Make a new instance of particle display.
     * The position of each particle will be randomized positively and negatively by the offset parameters on each axis.
     *
     * @param particle the particle to spawn.
     * @param location the location to spawn the particle at.
     * @param count    the count of particles to spawn.
     * @param offsetx  the x offset.
     * @param offsety  the y offset.
     * @param offsetz  the z offset.
     * @param extra    in most cases extra is the speed of the particles.
     */
    public SpigotParticle(Particle particle, Callable<Location> locationCaller, Location location, int count,
                          double offsetx, double offsety, double offsetz, double extra) {
        this.particle = particle;
        this.location = location;
        this.locationCaller = locationCaller;
        this.count = count;
        this.offsetx = offsetx;
        this.offsety = offsety;
        this.offsetz = offsetz;
        this.extra = extra;
    }

    public SpigotParticle(Particle particle, Location location, int count, double offsetx, double offsety, double offsetz) {
        this(particle, null, location, count, offsetx, offsety, offsetz, 0);
    }

    public SpigotParticle(Particle particle, Location location, int count) {
        this(particle, location, count, 0, 0, 0);
    }

    public SpigotParticle(Particle particle, Location location) {
        this(particle, location, 0);
    }

    /**
     * Builds a simple ParticleDisplay object with cross-version
     * compatible {@link org.bukkit.Particle.DustOptions} properties.
     *
     * @param location the location of the display.
     * @param size     the size of the dust.
     * @return a redstone colored dust.
     * @see #simple(Location, Particle)
     * @since 1.0.0
     */

    public static SpigotParticle colored(Location location, int r, int g, int b, float size) {
        SpigotParticle dust = new SpigotParticle(Particle.REDSTONE, null, location, 1, 0, 0, 0, 0);
        dust.data = new float[]{r, g, b, size};
        return dust;
    }

    /**
     * Builds a simple ParticleDisplay object with cross-version
     * compatible {@link org.bukkit.Particle.DustOptions} properties.
     *
     * @param location the location of the display.
     * @param color    the color of the particle.
     * @param size     the size of the dust.
     * @return a redstone colored dust.
     * @see #colored(Location, int, int, int, float)
     * @since 3.0.0
     */

    public static SpigotParticle colored(Location location, Color color, float size) {
        return colored(location, color.getRed(), color.getGreen(), color.getBlue(), size);
    }

    /**
     * Builds a simple ParticleDisplay object.
     * An invocation of this method yields exactly the same result as the expression:
     * <p>
     * <blockquote>
     * new ParticleDisplay(particle, location, 1, 0, 0, 0, 0);
     * </blockquote>
     *
     * @param location the location of the display.
     * @param particle the particle of the display.
     * @return a simple ParticleDisplay.
     * @since 1.0.0
     */

    public static SpigotParticle simple(Location location, Particle particle) {
        Objects.requireNonNull(particle, "Cannot build ParticleDisplay with null particle");
        return new SpigotParticle(particle, null, location, 1, 0, 0, 0, 0);
    }

    /**
     * A quick access method to display a simple particle.
     * An invocation of this method yields the same result as the expression:
     * <p>
     * <blockquote>
     * ParticleDisplay.simple(location, particle).spawn();
     * </blockquote>
     *
     * @param location the location of the particle.
     * @param particle the particle to show.
     * @return a simple ParticleDisplay.
     * @since 1.0.0
     */

    public static SpigotParticle display(Location location, Particle particle) {
        Objects.requireNonNull(location, "Cannot display particle in null location");
        SpigotParticle display = simple(location, particle);
        display.spawn();
        return display;
    }

    /**
     * Builds particle settings from a configuration section.
     *
     * @param location the location for this particle settings.
     * @param config   the config section for the settings.
     * @return a parsed ParticleDisplay.
     * @since 1.0.0
     */


    /**
     * Rotates the given xyz with the given rotation radians and
     * adds the to the specified location.
     *
     * @param location the location to add the rotated axis.
     * @param rotation the xyz rotation radians.
     * @return a cloned rotated location.
     * @since 3.0.0
     */

    public static Location rotate( Location location, double x, double y, double z,  Vector rotation) {
        if (rotation == null) return cloneLocation(location).add(x, y, z);

        Vector rotate = new Vector(x, y, z);
        SpigotParticles.rotateAround(rotate, rotation.getX(), rotation.getY(), rotation.getZ());
        return cloneLocation(location).add(rotate);
    }

    /**
     * We don't want to use {@link Location#clone()} since it doens't copy to constructor and Javas clone method
     * is known to be inefficient and broken.
     *
     * @since 3.0.3
     */

    private static Location cloneLocation( Location location) {
        return new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    /**
     * Changes the particle count of the particle settings.
     *
     * @param count the particle count.
     * @return the same particle display.
     * @since 3.0.0
     */

    public SpigotParticle withCount(int count) {
        this.count = count;
        return this;
    }

    /**
     * In most cases extra is the speed of the particles.
     *
     * @param extra the extra number.
     * @return the same particle display.
     * @since 3.0.1
     */

    public SpigotParticle withExtra(double extra) {
        this.extra = extra;
        return this;
    }

    /**
     * Adds color properties to the particle settings.
     *
     * @param color the RGB color of the particle.
     * @param size  the size of the particle.
     * @return a colored particle.
     * @see #colored(Location, Color, float)
     * @since 3.0.0
     */

    public SpigotParticle withColor(Color color, float size) {
        this.data = new float[]{color.getRed(), color.getGreen(), color.getBlue(), size};
        return this;
    }

    /**
     * Saves an instance of an entity to track the location from.
     *
     * @param entity the entity to track the location from.
     * @return the location tracker entity.
     * @since 3.1.0
     */

    public SpigotParticle withEntity(Entity entity) {
        return withLocationCaller(entity::getLocation);
    }

    /**
     * Sets a caller for location changes.
     *
     * @param locationCaller the caller to call to get the new location.
     * @return the same particle settings with the caller added.
     * @since 3.1.0
     */

    public SpigotParticle withLocationCaller(Callable<Location> locationCaller) {
        this.locationCaller = locationCaller;
        return this;
    }

    /**
     * Gets the location of an entity if specified or the constant location.
     *
     * @return the location of the particle.
     * @since 3.1.0
     */

    public Location getLocation() {
        try {
            return locationCaller == null ? location : locationCaller.call();
        } catch (Exception e) {
            e.printStackTrace();
            return location;
        }
    }

    /**
     * Adjusts the rotation settings to face the entitys direction.
     * Only some of the shapes support this method.
     *
     * @param entity the entity to face.
     * @return the same particle display.
     * @see #rotate(Vector)
     * @since 3.0.0
     */

    public SpigotParticle faceEntity(Entity entity) {
        Objects.requireNonNull(entity, "Cannot face null entity");
        Location loc = entity.getLocation();
        this.rotation = new Vector(Math.toRadians(loc.getPitch() + 90), Math.toRadians(-loc.getYaw()), 0);
        return this;
    }

    /**
     * Clones the location of this particle display and adds xyz.
     *
     * @param x the x to add to the location.
     * @param y the y to add to the location.
     * @param z the z to add to the location.
     * @return the cloned location.
     * @see #clone()
     * @since 1.0.0
     */

    public Location cloneLocation(double x, double y, double z) {
        return location == null ? null : cloneLocation(location).add(x, y, z);
    }

    /**
     * Clones this particle settings and adds xyz to its location.
     *
     * @param x the x to add.
     * @param y the y to add.
     * @param z the z to add.
     * @return the cloned ParticleDisplay.
     * @see #clone()
     * @since 1.0.0
     */

    public SpigotParticle cloneWithLocation(double x, double y, double z) {
        SpigotParticle display = clone();
        if (location == null) return display;
        display.location.add(x, y, z);
        return display;
    }

    /**
     * Clones this particle settings.
     *
     * @return the cloned ParticleDisplay.
     * @see #cloneWithLocation(double, double, double)
     * @see #cloneLocation(double, double, double)
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override

    public SpigotParticle clone() {
        SpigotParticle display = new SpigotParticle(particle, locationCaller, (location == null ? null : cloneLocation(location)), count, offsetx, offsety, offsetz, extra);
        if (rotation != null) display.rotation = new Vector(rotation.getX(), rotation.getY(), rotation.getZ());
        display.data = data;
        return display;
    }

    /**
     * Rotates the particle position based on this vector.
     *
     * @param vector the vector to rotate from. The xyz values of this vectors must be radians.
     * @see #rotate(double, double, double)
     * @since 1.0.0
     */

    public SpigotParticle rotate(Vector vector) {
        Objects.requireNonNull(vector, "Cannot rotate ParticleDisplay with null vector");
        if (rotation == null) rotation = vector;
        else rotation.add(vector);
        return this;
    }

    /**
     * Rotates the particle position based on the xyz radians.
     * Rotations are only supported for some shapes in {@link SpigotParticles}.
     * Rotating some of them can result in weird shapes.
     *
     * @see #rotate(Vector)
     * @since 3.0.0
     */

    public SpigotParticle rotate(double x, double y, double z) {
        return rotate(new Vector(x, y, z));
    }

    /**
     * Set the xyz offset of the particle settings.
     *
     * @since 1.1.0
     */

    public SpigotParticle offset(double x, double y, double z) {
        offsetx = x;
        offsety = y;
        offsetz = z;
        return this;
    }

    /**
     * When a particle is set to be directional it'll only
     * spawn one particle and the xyz offset values are used for
     * the direction of the particle.
     * <p>
     * Colored particles in 1.12 and below don't support this.
     *
     * @return the same particle display.
     * @see #isDirectional()
     * @since 1.1.0
     */

    public SpigotParticle directional() {
        count = 0;
        return this;
    }

    /**
     * Check if this particle setting is a directional particle.
     *
     * @return true if the particle is directional, otherwise false.
     * @see #directional()
     * @since 2.1.0
     */
    public boolean isDirectional() {
        return count == 0;
    }

    /**
     * Spawns the particle at the current location.
     *
     * @since 2.0.1
     */
    public void spawn() {
        spawn(getLocation());
    }

    /**
     * Adds xyz of the given vector to the cloned location before
     * spawning particles.
     *
     * @param location the xyz to add.
     * @since 1.0.0
     */

    public Location spawn( Vector location) {
        Objects.requireNonNull(location, "Cannot add xyz of null vector to ParticleDisplay");
        return spawn(location.getX(), location.getY(), location.getZ());
    }

    /**
     * Adds xyz to the cloned loaction before spawning particle.
     *
     * @since 1.0.0
     */

    public Location spawn(double x, double y, double z) {
        return spawn(rotate(getLocation(), x, y, z, rotation));
    }

    /**
     * Displays the particle in the specified location.
     * This method does not support rotations if used directly.
     *
     * @param loc the location to display the particle at.
     * @see #spawn(double, double, double)
     * @since 2.1.0
     */

    public Location spawn( Location loc) {
        if (data != null) {
            if (data instanceof float[]) {
                float[] datas = (float[]) data;
                Objects.requireNonNull(loc.getWorld()).spawnParticle(particle, loc, count, (int) datas[0], (int) datas[1], (int) datas[2], datas[3]);
            }
        } else {
            Objects.requireNonNull(loc.getWorld()).spawnParticle(particle, loc, count, offsetx, offsety, offsetz, extra);
        }
        return loc;
    }
}
