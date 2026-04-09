package timmychips.colored_packages.forge.mixin;

import com.simibubi.create.content.logistics.box.PackageDestroyPacket;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
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
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;

@Debug(export = true)
@Mixin(PackageDestroyPacket.class)
public class PackageDestroyPacketMixinForge {

    @Shadow protected Vec3 location;
    @Shadow private ItemStack box;

//    @Inject(method = "<init>(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/item/ItemStack;)V", at = @At("RETURN"))
//    public void coloredPackages$init(Vec3 location, ItemStack box, CallbackInfo ci) {
//
//        ColoredPackages.LOGGER.info("init box stack: {}", box);
//        ColoredPackages.LOGGER.info("init has color tag? {}", ColoredPackageItem.hasColorTag(box));
//
//        if (ColoredPackageItem.hasColorTag(box)) {
//            CompoundTag compound = box.getTag();
//
//            CompoundTag targetCompound = this.box.getOrCreateTag();
//            targetCompound.putString(ColoredPackageItem.TAG_COLOR, compound.getString(ColoredPackageItem.TAG_COLOR));
//            ColoredPackages.LOGGER.info("tags: {}", this.box.getTags());
//
//
//            ColoredPackages.LOGGER.info("private box has tag? {}", ColoredPackageItem.hasColorTag(this.box));
//        }
//    }

    @Inject(method = "handle", at = @At("HEAD"), cancellable = true)
    public void coloredPackages$handleColored(NetworkEvent.Context ctx, CallbackInfoReturnable<Boolean> cir) {

        ColoredPackages.LOGGER.info("handle box stack: {}", box);
        ColoredPackages.LOGGER.info("has color tag? {}", ColoredPackageItem.hasColorTag(box));

        if (ColoredPackageItem.hasColorTag(box)) {

            ColoredPackages.LOGGER.info("box color tag: {}", box.getTag().getString(ColoredPackageItem.TAG_COLOR));

            ctx.enqueueWork(() -> {
                for (int i = 0; i < 20; i++) {
                    ClientLevel level = Minecraft.getInstance().level;
                    Vec3 motion = VecHelper.offsetRandomly(Vec3.ZERO, level.getRandom(), .125f);
                    Vec3 pos = location.add(motion.scale(4));
                    level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, box), pos.x, pos.y,
                            pos.z, motion.x, motion.y, motion.z);
                }
            });
            cir.setReturnValue(true);
        }
    }
}
