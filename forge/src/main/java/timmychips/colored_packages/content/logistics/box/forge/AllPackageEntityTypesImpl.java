package timmychips.colored_packages.content.logistics.box.forge;

import com.simibubi.create.foundation.data.CreateEntityBuilder;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.createmod.catnip.lang.Lang;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.red.RedPackageVisual;
import timmychips.colored_packages.forge.content.logistics.box.RedPackageEntityForge;
import timmychips.colored_packages.forge.content.logistics.box.RedPackageRendererForge;

// Arch @ExpectPlatform Forge inject
public class AllPackageEntityTypesImpl {

    public static EntityEntry<RedPackageEntityForge> RED_COLORED_PACKAGE_FORGE;

    public static void registerPlatform() {

        RED_COLORED_PACKAGE_FORGE = register("red_package", RedPackageEntityForge::new, () -> RedPackageRendererForge::new,
                MobCategory.MISC, 10, 3, true, false, RedPackageEntityForge::build)
                .visual(() -> RedPackageVisual::new, true)
                .register();
    }

    // Forge Entity Builder version; Copied from Create's AllEntityTypes register() method
    private static <T extends Entity> CreateEntityBuilder<T, ?> register(String name, EntityType.EntityFactory<T> factory,
                                                                         NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer,
                                                                         MobCategory group, int range, int updateFrequency, boolean sendVelocity, boolean immuneToFire,
                                                                         NonNullConsumer<EntityType.Builder<T>> propertyBuilder) {
        String id = Lang.asId(name);
        return (CreateEntityBuilder<T, ?>) ColoredPackages.REGISTRATE
                .entity(id, factory, group)
                .properties(b -> b.clientTrackingRange(range)
                        .updateInterval(updateFrequency)
                        .setShouldReceiveVelocityUpdates(sendVelocity))
                .properties(propertyBuilder)
                .properties(b -> {
                    if (immuneToFire)
                        b.fireImmune();
                })
                .renderer(renderer);
    }
}
