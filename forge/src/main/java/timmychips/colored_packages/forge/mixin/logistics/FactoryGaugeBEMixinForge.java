package timmychips.colored_packages.forge.mixin.logistics;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBehaviour;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlock;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlockEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import timmychips.colored_packages.AllDyedBlocks;

import java.util.EnumMap;
import java.util.List;

@Mixin(FactoryPanelBlockEntity.class)
public class FactoryGaugeBEMixinForge {

    @ModifyExpressionValue(method = "lazyTick", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", remap = false))
    private boolean coloredPackages$modifyShouldBeRestocker(boolean original) {
        if (original) return true;

        FactoryPanelBlockEntity self = (FactoryPanelBlockEntity) (Object) this;

        // Get Packager block state from factory panel
        BlockState state = self.getLevel().getBlockState(self.getBlockPos().relative(FactoryPanelBlock.connectedDirection(self.getBlockState())
                .getOpposite()));

        return AllDyedBlocks.DYED_PACKAGER.has(state);
    }
}
