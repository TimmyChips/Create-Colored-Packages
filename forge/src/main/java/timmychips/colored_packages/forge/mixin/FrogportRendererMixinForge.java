package timmychips.colored_packages.forge.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.packagePort.frogport.FrogportBlockEntity;
import com.simibubi.create.content.logistics.packagePort.frogport.FrogportRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import timmychips.colored_packages.content.logistics.box.util.ColoredPackagePartialUtil;

@Debug(export = true)
@Mixin(FrogportRenderer.class)
public class FrogportRendererMixinForge {

    // Idk if the renderer class is actually used, but it's here
    @Inject(method = "renderPackage", at = @At("HEAD"))
    public void coloredPackages$renderPackager(FrogportBlockEntity blockEntity, PoseStack ms, MultiBufferSource buffer, int light, int overlay, Vec3 diff, float scale, float itemDistance, CallbackInfo ci) {
        if (blockEntity.animatedPackage != null) {
            if (!((double)scale < 0.45)) {
                ResourceLocation key = ForgeRegistries.ITEMS.getKey(blockEntity.animatedPackage.getItem());

                // Get colored package partial model if possible; else get regular Create package from method
                PartialModel model = ColoredPackagePartialUtil.getPartialFromTagColor(blockEntity.animatedPackage); // Get colored partial model from ItemStack

                if (key != null) {
                    SuperByteBuffer rigBuffer = CachedBuffers.partial((PartialModel) AllPartialModels.PACKAGE_RIGGING.get(key), blockEntity.getBlockState());
                    SuperByteBuffer boxBuffer = CachedBuffers.partial(model, blockEntity.getBlockState());
                    boolean animating = blockEntity.isAnimationInProgress();
                    boolean depositing = blockEntity.currentlyDepositing;

                    for(SuperByteBuffer buf : new SuperByteBuffer[]{boxBuffer, rigBuffer}) {
                        ((SuperByteBuffer)((SuperByteBuffer)((SuperByteBuffer)((SuperByteBuffer)((SuperByteBuffer)buf.translate(0.0F, 0.1875F, 0.0F)).translate(diff.normalize().scale((double)itemDistance).subtract((double)0.0F, animating && depositing ? (double)0.75F : (double)0.0F, (double)0.0F))).center()).scale(scale)).uncenter()).light(light).overlay(overlay).renderInto(ms, buffer.getBuffer(RenderType.cutout()));
                        if (!blockEntity.currentlyDepositing) {
                            break;
                        }
                    }

                }
            }
        }
    }
}
