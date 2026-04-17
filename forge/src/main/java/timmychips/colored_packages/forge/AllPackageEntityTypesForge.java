package timmychips.colored_packages.forge;

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
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageVisual;
import timmychips.colored_packages.forge.content.logistics.box.RedPackageEntityForge;
import timmychips.colored_packages.forge.content.logistics.box.RedPackageRendererForge;

public class AllPackageEntityTypesForge {

    public static final EntityEntry<RedPackageEntityForge> RED_COLORED_PACKAGE_FORGE = register("red_package", RedPackageEntityForge::new, () -> RedPackageRendererForge::new,
            MobCategory.MISC, 10, 3, true, false, RedPackageEntityForge::build)
            .visual(() -> ColoredPackageVisual::new, true)
            .register();

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(RED_COLORED_PACKAGE_FORGE.get(), RedPackageEntityForge.createPackageAttributes()
                .build());
    }

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

    public static void register() {}
}
