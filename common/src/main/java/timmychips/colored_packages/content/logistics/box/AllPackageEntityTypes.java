package timmychips.colored_packages.content.logistics.box;

import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageRenderer;
import com.simibubi.create.content.logistics.box.PackageVisual;
import com.simibubi.create.foundation.data.CreateEntityBuilder;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.DeferredRegister;
import net.createmod.catnip.lang.Lang;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.red.RedPackageEntity;
import timmychips.colored_packages.content.logistics.box.red.RedPackageRenderer;
import timmychips.colored_packages.content.logistics.box.red.RedPackageVisual;

public class AllPackageEntityTypes {

//    private static final DeferredRegister<EntityType<?>> COLORED_PACKAGE_ENTITIES = DeferredRegister.create(ColoredPackages.MOD_ID, Registries.ENTITY_TYPE);
//    private static final Registrate ENT = Registrate.create(ColoredPackages.MOD_ID);


    public static EntityEntry<RedPackageEntity> RED_COLORED_PACKAGE;

//    public static EntityEntry<RedPackageEntity> RED_COLORED_PACKAGE_1 = ColoredPackages.REGISTRATE.entity("red_package_1", RedPackageEntity::new, MobCategory.MISC)
//            .renderer(() -> RedPackageRenderer::new);
    
    @ExpectPlatform
    public static void registerPlatform() {}

    static {
        registerPlatform();
    }

    public static void register() {}
}
