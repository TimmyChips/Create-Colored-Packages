package timmychips.colored_packages.forge.mixin;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.logistics.box.PackageEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;
import timmychips.colored_packages.forge.AllPackageEntityTypesForge;
import timmychips.colored_packages.forge.content.logistics.box.RedPackageEntityForge;

@Mixin(PackageEntity.class)
public class PackageEntityMixinForge {

    // Method is called when breaking Chain Conveyor; done so it drops Colored Package entity instead
    @Inject(method = "fromItemStack", at = @At("HEAD"), cancellable = true)
    private static void coloredPackages$fromItemStack(Level world, Vec3 position, ItemStack itemstack, CallbackInfoReturnable<PackageEntity> cir) {

        // If package item has color tag (i.e. if it is a colored package item)
        if (ColoredPackageItem.hasColorTag(itemstack)) {
            RedPackageEntityForge packageEntity = AllPackageEntityTypesForge.RED_COLORED_PACKAGE_FORGE.get()
                    .create(world);
            packageEntity.setPos(position);
            packageEntity.setBox(itemstack);
            cir.setReturnValue(packageEntity);
        }
    }
}
