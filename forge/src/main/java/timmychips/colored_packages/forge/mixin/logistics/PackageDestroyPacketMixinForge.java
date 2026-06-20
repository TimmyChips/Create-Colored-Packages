package timmychips.colored_packages.forge.mixin.logistics;

import com.simibubi.create.content.logistics.box.PackageDestroyPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;
import timmychips.colored_packages.forge.client.ClientPacketHandler;

@Mixin(PackageDestroyPacket.class)
public class PackageDestroyPacketMixinForge {

    @Shadow protected Vec3 location;
    @Shadow private ItemStack box;

    // Re-add tag color to private shadowed (this.box) ItemStack after the init/constructor nulls all tags
    @Inject(method = "<init>(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/item/ItemStack;)V", at = @At("RETURN"))
    public void coloredPackages$init(Vec3 location, ItemStack box, CallbackInfo ci) {

        // Only perform if original input box ItemStack has PackageColor tag
        if (ColoredPackageItem.hasColorTag(box)) {
            CompoundTag compound = box.getTag();

            CompoundTag targetCompound = this.box.getOrCreateTag();
            targetCompound.putString(ColoredPackageItem.TAG_COLOR, compound.getString(ColoredPackageItem.TAG_COLOR)); // Add color tag to this.box
        }
    }

    // Add custom "colored package" particle instead if private shadowed box stack has PackageColor tag (after we re-add the color tag)
    @Inject(method = "handle", at = @At("HEAD"), cancellable = true)
    public void coloredPackages$handleColored(NetworkEvent.Context ctx, CallbackInfoReturnable<Boolean> cir) {

        // Only for colored packages with our color tag
        if (ColoredPackageItem.hasColorTag(box)) {
            if (ctx.getDirection().getReceptionSide().isClient()) {
                ctx.enqueueWork(() -> {
                    ClientPacketHandler.spawnPackageParticles(box, location);
                });
            }
            cir.setReturnValue(true); // Return for colored packages
        }
    }
}
