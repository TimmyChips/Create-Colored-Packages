package timmychips.colored_packages;

import com.mojang.serialization.Codec;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;
import timmychips.colored_packages.content.logistics.box.ColoredPackageParticle;

public class AllPackageParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ColoredPackages.MOD_ID, Registries.PARTICLE_TYPE);

    // Particle provider registry for colored package particle
    public static final RegistrySupplier<ParticleType<ItemParticleOption>> COLORED_PACKAGE =
        PARTICLE_TYPES.register("colored_package", () -> new ParticleType<>(false, ItemParticleOption.DESERIALIZER) {
            @Override
            public @NotNull Codec<ItemParticleOption> codec() {
                return ItemParticleOption.codec(COLORED_PACKAGE.get());
            }
        });

    @Environment(EnvType.CLIENT)
    // Register client particle providers
    public static void registerFactories() {
        ParticleProviderRegistry.register(COLORED_PACKAGE, new ColoredPackageParticle.Provider());
    }

    // Register particle behavior + rendering
    public static void register() {
        PARTICLE_TYPES.register();
    }
}