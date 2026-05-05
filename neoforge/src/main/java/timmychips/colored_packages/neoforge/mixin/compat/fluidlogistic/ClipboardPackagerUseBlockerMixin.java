package timmychips.colored_packages.neoforge.mixin.compat.fluidlogistic;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.yision.fluidlogistics.handler.ClipboardPackagerUseBlocker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import timmychips.colored_packages.AllDyedBlocks;

@Debug(export = true)
@Mixin(ClipboardPackagerUseBlocker.class)
public class ClipboardPackagerUseBlockerMixin {
    @ModifyExpressionValue(method = "isBlockedPackager", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",
    ordinal = 0, remap = false))
    private static boolean coloredPackages$modifyPackagerBool(boolean original, Level level, BlockPos pos) {
        if (original) return true;

        BlockState state = level.getBlockState(pos);

        return AllDyedBlocks.DYED_PACKAGER.has(state) || AllDyedBlocks.DYED_REPACKAGER.has(state);
    }
}
