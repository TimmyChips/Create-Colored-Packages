package timmychips.colored_packages.neoforge.content.logistics.box;

import com.simibubi.create.AllPackets;
import com.simibubi.create.content.logistics.box.PackageDestroyPacket;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecs;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.net.base.BasePacketPayload;
import net.createmod.catnip.net.base.ClientboundPacketPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import timmychips.colored_packages.AllPackageParticles;

public record ColoredPackageDestroyPacket(Vec3 location, ItemStack box) implements ClientboundPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, PackageDestroyPacket> STREAM_CODEC = StreamCodec.composite(
            CatnipStreamCodecs.VEC3, PackageDestroyPacket::location,
            ItemStack.STREAM_CODEC, PackageDestroyPacket::box,
            PackageDestroyPacket::new
    );

    @Override
    public BasePacketPayload.PacketTypeProvider getTypeProvider() {
        return AllPackets.PACKAGE_DESTROYED;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle(LocalPlayer player) {
        ClientLevel level = Minecraft.getInstance().level;
        Vec3 motion = VecHelper.offsetRandomly(Vec3.ZERO, level.getRandom(), .125f);
        Vec3 pos = location.add(motion.scale(4));
        // Summon colored package break particle
        level.addParticle(new ItemParticleOption(AllPackageParticles.COLORED_PACKAGE.get(), box), pos.x, pos.y,
                pos.z, motion.x, motion.y, motion.z);
    }
}
