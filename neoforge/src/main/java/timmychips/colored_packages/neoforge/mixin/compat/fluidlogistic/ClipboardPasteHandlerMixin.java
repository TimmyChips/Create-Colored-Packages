package timmychips.colored_packages.neoforge.mixin.compat.fluidlogistic;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.yision.fluidlogistics.handler.ClipboardPasteHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import timmychips.colored_packages.AllDyedBlocks;

@Debug(export = true)
@Mixin(ClipboardPasteHandler.class)
public class ClipboardPasteHandlerMixin {
    @ModifyExpressionValue(method = "onRightClickBlock", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",
    ordinal = 0, remap = false))
    private static boolean coloredPackages$modifyPackagerBool(boolean original, PlayerInteractEvent.RightClickBlock event) {
        if (original) return true;

        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        return AllDyedBlocks.DYED_PACKAGER.has(state) || AllDyedBlocks.DYED_REPACKAGER.has(state);
    }
}
