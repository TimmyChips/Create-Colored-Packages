package timmychips.colored_packages.forge.mixin.compat.fluidlogistic;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.yision.fluidlogistics.network.HandPointerPackagerTogglePacket;
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

@Debug(export = true)
@Mixin(HandPointerPackagerTogglePacket.class)
public class HandPointerPackagerTogglePacketMixin {

    @Shadow
    @Final
    private BlockPos pos;

    // For the server packet that actually updates the Packager's state when right-clicked with Hand Pointer item
    @ModifyExpressionValue(method = "lambda$handle$0", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",
            ordinal = 0, remap = false))
    private boolean coloredPackages$modifyPackagerBool(boolean original, NetworkEvent.Context context) {
        if (original) return true;

        HandPointerPackagerTogglePacket self = (HandPointerPackagerTogglePacket) (Object) this;

        ServerPlayer player = context.getSender();
        Level level = player.level();
        BlockState state = level.getBlockState(this.pos);

        return AllDyedBlocks.DYED_PACKAGER.has(state) || AllDyedBlocks.DYED_REPACKAGER.has(state);
    }
}
