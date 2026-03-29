package timmychips.colored_packages.forge.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorBlockEntity;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorPackage;
import com.simibubi.create.content.kinetics.chainConveyor.ChainConveyorRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import timmychips.colored_packages.ColoredPackages;

@Mixin(ChainConveyorRenderer.class)
public class ChainConveyerRendererMixinForce {
//    @Inject(method = "renderBox", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/chainConveyor/ChainConveyorPackage;physicsData(Lnet/minecraft/world/level/LevelAccessor;)Lcom/simibubi/create/content/kinetics/chainConveyor/ChainConveyorPackage$ChainConveyorPackagePhysicsData;"))
//    private void coloredPackagers$renderBox(
//            ChainConveyorBlockEntity be, PoseStack ms, MultiBufferSource buffer, int overlay, BlockPos pos, ChainConveyorPackage box, float partialTicks, CallbackInfo ci) {
//    }
    @Inject(method = "renderBox", at = @At(value = "HEAD"))
    private void coloredPackagers$renderBox(
            ChainConveyorBlockEntity be, PoseStack ms, MultiBufferSource buffer, int overlay, BlockPos pos, ChainConveyorPackage box, float partialTicks, CallbackInfo ci) {
        ChainConveyorPackage.ChainConveyorPackagePhysicsData physicsData = box.physicsData(be.getLevel());

        ColoredPackages.LOGGER.info("TEST RENDERER!");

        ColoredPackages.LOGGER.info("chain conveyor resource: {}", physicsData.modelKey);
        BlockState blockState = be.getBlockState();
        ColoredPackages.LOGGER.info("chain conveyor partial: {}", CachedBuffers.partial(AllPartialModels.PACKAGES.get(physicsData.modelKey), blockState));
    }
}
