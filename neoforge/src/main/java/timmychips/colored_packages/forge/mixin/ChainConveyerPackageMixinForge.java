package timmychips.colored_packages.forge.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorPackage;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import timmychips.colored_packages.content.logistics.box.util.ColoredPackagePartialUtil;

@Mixin(ChainConveyorPackage.class)
public class ChainConveyerPackageMixinForge {

    @Shadow public ItemStack item;

    // Modifies the return value of a Chain Conveyor's package modelKey to use the proper colored package's resource key
    @ModifyReturnValue(method = "physicsData", at = @At(value = "RETURN"))
    private ChainConveyorPackage.ChainConveyorPackagePhysicsData coloredPackages$changeModelKey(ChainConveyorPackage.ChainConveyorPackagePhysicsData original) {

        if (original.modelKey == null) {
             // Change model key to colored package key if possible
             original.modelKey = ColoredPackagePartialUtil.getResourceKeyFromTagColor(item);
        }
        return original;
    }
}
