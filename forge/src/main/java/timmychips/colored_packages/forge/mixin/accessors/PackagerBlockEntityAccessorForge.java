package timmychips.colored_packages.forge.mixin.accessors;

import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PackagerBlockEntity.class)
public interface PackagerBlockEntityAccessorForge {
    @Accessor("advancements")
    AdvancementBehaviour coloredPackages$getPackagerAdvancements();
}
