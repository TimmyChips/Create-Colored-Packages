package timmychips.colored_packages.forge.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.createmod.catnip.math.VecHelper;
import timmychips.colored_packages.AllPackageParticles;

public class ClientPacketHandler {
    // This method is only called if on the client
    public static void spawnPackageParticles(ItemStack box, Vec3 location) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;

        for (int i = 0; i < 20; i++) {
            Vec3 motion = VecHelper.offsetRandomly(Vec3.ZERO, level.getRandom(), .125f);
            Vec3 pos = location.add(motion.scale(4));
            // Summon colored package particle instead
            level.addParticle(new ItemParticleOption(AllPackageParticles.COLORED_PACKAGE.get(), box), pos.x, pos.y,
                    pos.z, motion.x, motion.y, motion.z);
        }
    }
}
