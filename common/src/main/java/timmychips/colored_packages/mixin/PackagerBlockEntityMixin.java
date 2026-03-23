package timmychips.colored_packages.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.logistics.packager.PackagingRequest;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import timmychips.colored_packages.ColoredPackages;

import java.util.List;

@Mixin(PackagerBlockEntity.class)
public class PackagerBlockEntityMixin {

//    @Inject(method = "attemptToSend",
//            at = @At(value = "INVOKE",
//                target = "Lcom/simibubi/create/content/logistics/box/PackageItem;containing(Lio/github/fabricators_of_create/porting_lib/transfer/item/ItemStackHandler;)Lnet/minecraft/world/item/ItemStack;",
//                shift = At.Shift.AFTER))
//    public void coloredPackages$attemptToSend(List<PackagingRequest> queuedRequests, CallbackInfo ci,
//                                              @Local(ordinal = 2) ItemStack createdBox) {
//        ColoredPackages.LOGGER.info("createdBox value: {}", createdBox);
//    }
}
