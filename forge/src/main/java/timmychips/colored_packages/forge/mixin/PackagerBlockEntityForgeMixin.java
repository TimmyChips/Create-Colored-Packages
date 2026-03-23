package timmychips.colored_packages.forge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
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
public class PackagerBlockEntityForgeMixin {
//    @Inject(method = "attemptToSend",
//            at = @At(value = "INVOKE",
//                    target = "Lcom/simibubi/create/content/logistics/box/PackageItem;clearAddress(Lnet/minecraft/world/item/ItemStack;)V",
//                    shift = At.Shift.AFTER))
//    public void coloredPackages$attemptToSend(List<PackagingRequest> queuedRequests, CallbackInfo ci,
//                                              @Local(ordinal = 2) ItemStack createdBox) {
//        ColoredPackages.LOGGER.info("TEST!!");
//        ColoredPackages.LOGGER.info("createdBox value: {}", createdBox);
//    }

    @Inject(method = "attemptToSend",
            at = @At(value = "HEAD"))
    public void coloredPackages$attemptToSend(List<PackagingRequest> queuedRequests, CallbackInfo ci) {
//        ColoredPackages.LOGGER.info("TEST!!");
    }

    @Inject(method = "tick()V", at = @At(value = "HEAD"))
    public void coloredPackages$tick(CallbackInfo ci) {
//        ColoredPackages.LOGGER.info("test tick!");
    }
}
