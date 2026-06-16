package timmychips.colored_packages.content.logistics.box;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import timmychips.colored_packages.AllPackagePartialModels;

// Item breaking particle that spawns correct colored version when breaking colored package
public class ColoredPackageParticle extends BreakingItemParticle {

    /// The particle behavior and rendering
    ColoredPackageParticle (ClientLevel arg, double d, double e, double f, double g, double h, double i, ItemStack arg2) {
        this(arg, d, e, f, arg2);
        this.xd *= (double)0.1F;
        this.yd *= (double)0.1F;
        this.zd *= (double)0.1F;
        this.xd += g;
        this.yd += h;
        this.zd += i;
    }

    /**  Main handler for particle rendering;
     *  <p>Gets the colored particle sprite from the package ItemStack's PackageColor tag
     * @param itemStack the input package stack with color tag; color tag should not be null since we set it in PackageDestroyPackageMixinForge class
     */
    protected ColoredPackageParticle(ClientLevel clientLevel, double x, double y, double z, ItemStack itemStack) {
        super(clientLevel, x, y, z, itemStack);

        // Get the particle sprite from the partial model of the package's current color
        this.setSprite(AllPackagePartialModels.coloredPartialFromColor(itemStack,
                ColoredPackageItem.getCurrentColor(itemStack)).get().getParticleIcon());
    }

    /// The particle provider
    public static class Provider implements ParticleProvider<ItemParticleOption> {

        @Override
        public @Nullable Particle createParticle(ItemParticleOption type, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ColoredPackageParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed, type.getItem());
        }
    }
}
