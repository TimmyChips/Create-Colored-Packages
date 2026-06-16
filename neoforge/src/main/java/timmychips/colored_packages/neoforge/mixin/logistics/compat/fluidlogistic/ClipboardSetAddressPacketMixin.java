package timmychips.colored_packages.forge.mixin.compat.fluidlogistic;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.yision.fluidlogistics.network.ClipboardSetAddressPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import timmychips.colored_packages.AllDyedBlocks;

@Mixin(ClipboardSetAddressPacket.class)
public class ClipboardSetAddressPacketMixin {
    @Shadow @Final private BlockPos pos;

    @ModifyExpressionValue(method = "lambda$handle$0", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",
    remap = false, ordinal = 0))
    private boolean coloredPackages$modifyPackagerBool(boolean original, NetworkEvent.Context context) {
        if (original) return true;

        ClipboardSetAddressPacket self = (ClipboardSetAddressPacket) (Object) this;

        ServerPlayer player = context.getSender();
        Level level = player.level();
        BlockState state = level.getBlockState(this.pos);

        return AllDyedBlocks.DYED_PACKAGER.has(state) || AllDyedBlocks.DYED_REPACKAGER.has(state);
    }
}
