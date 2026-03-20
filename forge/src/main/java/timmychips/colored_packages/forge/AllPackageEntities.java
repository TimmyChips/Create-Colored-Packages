package timmychips.colored_packages.forge;

import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageRenderer;
import com.simibubi.create.content.logistics.box.PackageVisual;
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

public class AllPackageEntities {
//    public static final EntityEntry<PackageEntity> PACKAGE;

    private static <T extends Entity> CreateEntityBuilder<T, ?> register(String name, EntityType.EntityFactory<T> factory, NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer, MobCategory group, int range, int updateFrequency, boolean sendVelocity, boolean immuneToFire, NonNullConsumer<EntityType.Builder<T>> propertyBuilder) {
        String id = Lang.asId(name);
        return (CreateEntityBuilder) ColoredPackages.REGISTRATE.entity(id, factory, group).properties((b) -> b.setTrackingRange(range).setUpdateInterval(updateFrequency).setShouldReceiveVelocityUpdates(sendVelocity)).properties(propertyBuilder).properties((b) -> {
            if (immuneToFire) {
                b.fireImmune();
            }

        }).renderer(renderer);
    }

    static {
//        PACKAGE = register("red", PackageEntity::new, () -> PackageRenderer::new, MobCategory.MISC, 10, 3, true, false, PackageEntity::build).visual(() -> PackageVisual::new, true).register();
    }

    public static void register() {}
}
