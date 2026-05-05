package timmychips.colored_packages.neoforge.mixin.compat.fluidlogistic;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.yision.fluidlogistics.network.ClipboardSetAddressPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import timmychips.colored_packages.AllDyedBlocks;

@Debug(export = true)
@Mixin(ClipboardSetAddressPacket.class)
public class ClipboardSetAddressPacketMixin {
    @ModifyExpressionValue(method = "handle", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",
    remap = false, ordinal = 0))
    private boolean coloredPackages$modifyPackagerBool(boolean original, ServerPlayer player) {
        if (original) return true;

        ClipboardSetAddressPacket self = (ClipboardSetAddressPacket) (Object) this;

        Level level = player.level();
        BlockState state = level.getBlockState(self.pos());

        return AllDyedBlocks.DYED_PACKAGER.has(state) || AllDyedBlocks.DYED_REPACKAGER.has(state);
    }
}
