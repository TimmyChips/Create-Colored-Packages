package timmychips.colored_packages.neoforge.mixin.compat.fluidlogistic;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.yision.fluidlogistics.client.handpointer.HandPointerInteractionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import timmychips.colored_packages.AllDyedBlocks;

@Debug(export = true)
@Mixin(HandPointerInteractionHandler.class)
public class HandPointerInteractionHandlerMixin {
    @ModifyExpressionValue(method = "tryTogglePackager", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",
            ordinal = 0, remap = false))
    private boolean coloredPackages$modifyPackagerBool(boolean original, Player player, Level level, BlockPos pos, BlockState state) {
        if (original) return true;
        return AllDyedBlocks.DYED_PACKAGER.has(state);
    }

    @ModifyExpressionValue(method = "tryTogglePackager", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",
            ordinal = 1, remap = false))
    private boolean coloredPackages$modifyRepackagerBool(boolean original, Player player, Level level, BlockPos pos, BlockState state) {
        if (original) return true;
        return AllDyedBlocks.DYED_REPACKAGER.has(state);
    }

    @ModifyExpressionValue(method = "isPackagerFamily", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z"),
            remap = false)
    private static boolean coloredPackages$modifyPackagerFamily(boolean original, BlockState state) {
        if (original) return true;
        return AllDyedBlocks.DYED_PACKAGER.has(state) || AllDyedBlocks.DYED_REPACKAGER.has(state);
    }
}
