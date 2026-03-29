package timmychips.colored_packages.forge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorBlockEntity;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorPackage;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorVisual;
import com.simibubi.create.content.logistics.box.PackageItem;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import dev.engine_room.flywheel.lib.visual.SimpleTickableVisual;
import dev.engine_room.flywheel.lib.visual.util.SmartRecycler;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;
import timmychips.colored_packages.content.logistics.box.util.ColoredPackagePartialUtil;

@Mixin(ChainConveyorVisual.class)
public class ChainConveyerVisualMixinForge extends SingleAxisRotatingVisual<ChainConveyorBlockEntity> {
    @Mutable @Final @Shadow private SmartRecycler<ResourceLocation, TransformedInstance> boxes;
    @Final @Shadow private SmartRecycler<ResourceLocation, TransformedInstance> rigging;

    public ChainConveyerVisualMixinForge(VisualizationContext context, ChainConveyorBlockEntity blockEntity, float partialTick, Model model) {
        super(context, blockEntity, partialTick, model);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void coloredPackages$init(VisualizationContext context, ChainConveyorBlockEntity blockEntity, float partialTick, CallbackInfo ci) {
        boxes = new SmartRecycler<>(key -> {
            ColoredPackages.LOGGER.info("recycler key: {}", key);
            return instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(AllPartialModels.PACKAGES.get(key))).createInstance();
        });
    }

    //    @Inject(method = "<init>", at = @At(value = "TAIL"))
//    private void coloredPackages$constructor(VisualizationContext context, ChainConveyorBlockEntity blockEntity, float partialTick, CallbackInfo ci) {
//        boxes.
//    }
    /*
    @Inject(method = "setupBoxVisual", at = @At(value = "HEAD"))
    private void coloredPackages$boxVisual(ChainConveyorBlockEntity be, ChainConveyorPackage box, float partialTicks, CallbackInfo ci) {
        ChainConveyorPackage.ChainConveyorPackagePhysicsData physicsData = box.physicsData(be.getLevel());

//        ColoredPackages.LOGGER.info("boxes TransformedInstance: {}", boxes.get(physicsData.modelKey));
        ColoredPackages.LOGGER.info("TEST!");
        ColoredPackages.LOGGER.info("boxes: {}", boxes);

        TransformedInstance testbox = null;
        testbox = boxes.get(ColoredPackages.asResource("red_package_12x12"));
        ColoredPackages.LOGGER.info("test box: {}", testbox);
        ColoredPackages.LOGGER.info("VISUAL chain conveyor resource: {}", physicsData.modelKey);

        ColoredPackages.LOGGER.info("VISUAL chain conveyor partial: {}", boxes.get(physicsData.modelKey));
//        BlockState blockState = be.getBlockState();
    }

     */

    /*
    @Inject(method = "setupBoxVisual", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LightTexture;pack(II)I", shift = At.Shift.AFTER))
    private void coloredPackagers$renderBox(
            ChainConveyorBlockEntity be, ChainConveyorPackage box, float partialTicks, CallbackInfo ci,
            @Local(name = "physicsData") ChainConveyorPackage.ChainConveyorPackagePhysicsData physicsData) {

        ColoredPackages.LOGGER.info("physics data: {}", physicsData);
        if (physicsData == null) {
            ResourceLocation resource = ColoredPackagePartialUtil.getPartialResourceFromTag(box.item); // need to pass in ItemStack to get ResourceLocation

            String splitStr = resource.getPath().split("item/")[1];
            ColoredPackages.LOGGER.info("splitStr: {}", splitStr);
            ResourceLocation key = ColoredPackages.asResource(splitStr);
//            ColoredPackages.LOGGER.info("key: {}", key);
            physicsData.modelKey = key;

            ColoredPackages.LOGGER.info("physics data model: {}", physicsData.modelKey);
        }
    }

     */

    @Inject(method = "setupBoxVisual", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LightTexture;pack(II)I", shift = At.Shift.AFTER))
    private void coloredPackagers$renderBox(ChainConveyorBlockEntity be, ChainConveyorPackage box, float partialTicks, CallbackInfo ci) {
        if (box.worldPosition == null)
            return;
        if (box.item == null || box.item.isEmpty())
            return;

        ChainConveyorPackage.ChainConveyorPackagePhysicsData physicsData = box.physicsData(be.getLevel());
        if (physicsData.prevPos == null)
            return;

        Vec3 position = physicsData.prevPos.lerp(physicsData.pos, partialTicks);
        Vec3 targetPosition = physicsData.prevTargetPos.lerp(physicsData.targetPos, partialTicks);
        float yaw = AngleHelper.angleLerp(partialTicks, physicsData.prevYaw, physicsData.yaw);
        Vec3 offset =
                new Vec3(targetPosition.x - this.pos.getX(), targetPosition.y - this.pos.getY(), targetPosition.z - this.pos.getZ());

        BlockPos containingPos = BlockPos.containing(position);
        Level level = be.getLevel();
        int light = LightTexture.pack(level.getBrightness(LightLayer.BLOCK, containingPos),
                level.getBrightness(LightLayer.SKY, containingPos));

        if (physicsData.modelKey == null) {

            ResourceLocation key = ForgeRegistries.ITEMS.getKey(box.item.getItem());
            if (key == null)
                return;

            CompoundTag compoundTag = box.item.getTag();
            if (compoundTag != null && !compoundTag.isEmpty()) {
                String color = compoundTag.getString(ColoredPackageItem.TAG_COLOR);

                ResourceLocation coloredKey = ColoredPackages.asResource(color + key.getPath().split("colored")[1]);
                ColoredPackages.LOGGER.info("Colored key: {}", coloredKey);

                physicsData.modelKey = coloredKey;
            } else physicsData.modelKey = key;
        }

        TransformedInstance rigBuffer = rigging.get(physicsData.modelKey);
        TransformedInstance boxBuffer = boxes.get(physicsData.modelKey);

        Vec3 dangleDiff = VecHelper.rotate(targetPosition.add(0, 0.5, 0)
                .subtract(position), -yaw, Direction.Axis.Y);
        float zRot = Mth.wrapDegrees((float) Mth.atan2(-dangleDiff.x, dangleDiff.y) * Mth.RAD_TO_DEG) / 2;
        float xRot = Mth.wrapDegrees((float) Mth.atan2(dangleDiff.z, dangleDiff.y) * Mth.RAD_TO_DEG) / 2;
        zRot = Mth.clamp(zRot, -25, 25);
        xRot = Mth.clamp(xRot, -25, 25);

        for (TransformedInstance buf : new TransformedInstance[]{rigBuffer, boxBuffer}) {
            buf.setIdentityTransform();
            buf.translate(getVisualPosition());
            buf.translate(offset);
            buf.translate(0, 10 / 16f, 0);
            buf.rotateYDegrees(yaw);

            buf.rotateZDegrees(zRot);
            buf.rotateXDegrees(xRot);

            if (physicsData.flipped && buf == rigBuffer)
                buf.rotateYDegrees(180);

            buf.uncenter();
            buf.translate(0, -PackageItem.getHookDistance(box.item) + 7 / 16f, 0);

            buf.light(light);

            buf.setChanged();
        }
    }
}
