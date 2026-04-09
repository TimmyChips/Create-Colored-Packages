package timmychips.colored_packages;

import com.mojang.serialization.Codec;
import com.simibubi.create.foundation.particle.ICustomParticleData;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.createmod.catnip.lang.Lang;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import timmychips.colored_packages.content.logistics.box.ColoredPackageParticle;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AllPackageParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ColoredPackages.MOD_ID, Registries.PARTICLE_TYPE);

//    public static final RegistrySupplier<SimpleParticleType> COLORED_PACKAGE_PARTICLE = PARTICLE_TYPES.register("colored_package", () ->
//            new SimpleParticleType(false);

//    public static final RegistrySupplier<ColoredPackageParticle.Data> COLORED_PACKAGE = PARTICLE_TYPES.register("colored_package", () -> new ColoredPackageParticle.Data(ItemStack.EMPTY));
    @Environment(EnvType.CLIENT)
    public static final RegistrySupplier<ParticleType<ItemParticleOption>> COLORED_PACKAGE =
        PARTICLE_TYPES.register("colored_package", () -> new ParticleType<>(false, ItemParticleOption.DESERIALIZER) {
            @Override
            public @NotNull Codec<ItemParticleOption> codec() {
                return ItemParticleOption.codec(COLORED_PACKAGE.get());
            }
        });

    static {
//        ParticleProviderRegistry.register(COLORED_PACKAGE, new ColoredPackageParticle.Provider());
    }

    public static void registerFactories() {
        ParticleProviderRegistry.register(COLORED_PACKAGE, new ColoredPackageParticle.Provider());
    }

    public static void register() {
        PARTICLE_TYPES.register();
    }
}