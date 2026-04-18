package timmychips.colored_packages.neoforge.mixin.accessors;

import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Debug(export = true)
@Mixin(PackagerBlockEntity.class)
public interface PackagerBEAdvancementAccessorForge {
    @Accessor("advancements")
    AdvancementBehaviour coloredPackages$getPackagerAdvancements();
}
