package timmychips.colored_packages.forge.mixin;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.packagePort.frogport.FrogportBlockEntity;
import com.simibubi.create.content.logistics.packagePort.frogport.FrogportVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import timmychips.colored_packages.content.logistics.box.util.ColoredPackagePartialUtil;

@Debug(export = true)
@Mixin(FrogportVisual.class)
public abstract class FrogportVisualMixinForge extends AbstractBlockEntityVisual<FrogportBlockEntity> {

    @Final @Shadow private TransformedInstance box;
    @Final @Shadow private TransformedInstance rig;

    public FrogportVisualMixinForge(VisualizationContext ctx, FrogportBlockEntity blockEntity, float partialTick) {
        super(ctx, blockEntity, partialTick);
    }

    // Copied from Create with minor tweaks
    // TODO: better way to mixin and return?
    @Inject(method = "renderPackage", at = @At("HEAD"), cancellable = true)
    public void coloredPackages$renderPackage(Vec3 diff, float scale, float itemDistance, CallbackInfo ci) {
        if (blockEntity.animatedPackage == null || scale < 0.45) {
            rig.handle()
                    .setVisible(false);
            box.handle()
                    .setVisible(false);
            ci.cancel();
            return;
        }
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(blockEntity.animatedPackage.getItem());
        if (key == null) {
            rig.handle()
                    .setVisible(false);
            box.handle()
                    .setVisible(false);
            ci.cancel();
            return;
        }

        boolean animating = blockEntity.isAnimationInProgress();
        boolean depositing = blockEntity.currentlyDepositing;

        /// Set model to either colored package based on the animatedPackage PackageColor tag, or regular Create package from getPartialFromTagColor() method
        PartialModel model = ColoredPackagePartialUtil.getPartialFromTagColor(blockEntity.animatedPackage); // Get colored partial model from ItemStack if possible

        instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(model))
                .stealInstance(box);
        box.handle().setVisible(true);

        box.setIdentityTransform()
                .translate(getVisualPosition())
                .translate(0, 3 / 16f, 0)
                .translate(diff.normalize()
                        .scale(itemDistance)
                        .subtract(0, animating && depositing ? 0.75 : 0, 0))
                .center()
                .scale(scale)
                .uncenter()
                .setChanged();

        if (!depositing) {
            rig.handle()
                    .setVisible(false);
            ci.cancel();
            return;
        }

        instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(AllPartialModels.PACKAGE_RIGGING.get(key)))
                .stealInstance(rig);
        rig.handle().setVisible(true);

        rig.pose.set(box.pose);
        rig.setChanged();

        ci.cancel();
    }
}
