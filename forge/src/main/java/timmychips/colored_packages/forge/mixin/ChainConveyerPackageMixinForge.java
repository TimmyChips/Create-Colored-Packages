package timmychips.colored_packages.forge.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorPackage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.colored_packages.AllPackagePartialModels;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.util.ColoredPackagePartialUtil;

import java.util.concurrent.ExecutionException;

import static com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorPackage.physicsDataCache;

@Mixin(ChainConveyorPackage.class)
public class ChainConveyerPackageMixinForge {

    @Shadow public ItemStack item;
    @Shadow private ChainConveyorPackage.ChainConveyorPackagePhysicsData physicsData;

    @ModifyReturnValue(method = "physicsData", at = @At(value = "RETURN"))
    private ChainConveyorPackage.ChainConveyorPackagePhysicsData coloredPackages$changeModelKey(ChainConveyorPackage.ChainConveyorPackagePhysicsData original) {
//        if (original.modelKey == null) {

        if (original.modelKey == null) {
//            ColoredPackages.LOGGER.info("item id: {}", item.getDescriptionId());
            ColoredPackages.LOGGER.info("item id: {}", ForgeRegistries.ITEMS.getKey(item.getItem()));
            if (!ForgeRegistries.ITEMS.getKey(item.getItem()).getNamespace().contains("colored_packages")) {
                ColoredPackages.LOGGER.info("item shadow: {}", item);
                ResourceLocation resource = ColoredPackagePartialUtil.getPartialResourceFromTag(item); // need to pass in ItemStack to get ResourceLocation

                String splitStr = resource.getPath().split("item/")[1];
                ColoredPackages.LOGGER.info("splitStr: {}", splitStr);
                ResourceLocation key = ColoredPackages.asResource(splitStr);
//            ColoredPackages.LOGGER.info("key: {}", key);
                original.modelKey = key;

                ColoredPackages.LOGGER.info("physics data model: {}", original.modelKey);
            }
        }
        return original;
    }

//    @Inject(method = "physicsData", at = @At("HEAD"))
//    private void coloredPackages$physicsData(LevelAccessor level, CallbackInfoReturnable<ChainConveyorPackage.ChainConveyorPackagePhysicsData> cir) {
//        if (physicsData == null) {
//            try {
//                return physicsData = physicsDataCache.get(level)
//                        .get(ChainConveyorPackage.ChainConveyorPackagePhysicsData.netId, () -> new ChainConveyorPackage.ChainConveyorPackagePhysicsData(worldPosition));
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        physicsDataCache.get(level)
//                .getIfPresent(netId);
//        return physicsData;
//    }
}
