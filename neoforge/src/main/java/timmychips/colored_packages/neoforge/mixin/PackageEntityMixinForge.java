package timmychips.colored_packages.neoforge.mixin;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.logistics.box.PackageDestroyPacket;
import com.simibubi.create.content.logistics.box.PackageEntity;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;
import timmychips.colored_packages.neoforge.AllPackageEntityTypesForge;
import timmychips.colored_packages.neoforge.content.logistics.box.ColoredPackageEntityForge;

@Mixin(PackageEntity.class)
public abstract class PackageEntityMixinForge {

    @Shadow public ItemStack box;
    @Shadow protected abstract void dropAllDeathLoot(ServerLevel level, DamageSource pDamageSource);

    // Method is called when breaking Chain Conveyor; done so it drops Colored Package entity instead
    @Inject(method = "fromItemStack", at = @At("HEAD"), cancellable = true)
    private static void coloredPackages$fromItemStack(Level world, Vec3 position, ItemStack itemstack, CallbackInfoReturnable<PackageEntity> cir) {

        // If package item has color tag (i.e. if it is a colored package item)
        if (ColoredPackageItem.hasColorTag(itemstack)) {
            ColoredPackageEntityForge packageEntity = AllPackageEntityTypesForge.COLORED_PACKAGE_ENTITY_FORGE.get()
                    .create(world);
            packageEntity.setPos(position);
            packageEntity.setBox(itemstack);
            cir.setReturnValue(packageEntity);
        }
    }

    @Inject(method = "destroy", at = @At("HEAD"), cancellable = true)
    private void coloredPackages$destroy(DamageSource source, CallbackInfo ci) {

        PackageEntity self = (PackageEntity) (Object) this;

        if (ColoredPackageItem.hasColorTag(self.box)) {

            CatnipServices.NETWORK.sendToClientsTrackingEntity(self, new PackageDestroyPacket(self.getBoundingBox().getCenter(), this.box));
            AllSoundEvents.PACKAGE_POP.playOnServer(self.level(), self.blockPosition());
            Level var3 = self.level();
            if (var3 instanceof ServerLevel serverLevel) {
                dropAllDeathLoot(serverLevel, source);
            }

            ci.cancel();
        }
    }
}
