package timmychips.colored_packages.forge.mixin;

import com.simibubi.create.content.logistics.box.PackageDestroyPacket;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.colored_packages.AllPackageParticles;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;

@Debug(export = true)
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

            ctx.enqueueWork(() -> {
                for (int i = 0; i < 20; i++) {
                    ClientLevel level = Minecraft.getInstance().level;
                    Vec3 motion = VecHelper.offsetRandomly(Vec3.ZERO, level.getRandom(), .125f);
                    Vec3 pos = location.add(motion.scale(4));
                    // Summon colored package particle instead
                    level.addParticle(new ItemParticleOption(AllPackageParticles.COLORED_PACKAGE.get(), box), pos.x, pos.y,
                            pos.z, motion.x, motion.y, motion.z);
                }
            });
            cir.setReturnValue(true); // Return for colored packages
        }
    }
}
