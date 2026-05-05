package timmychips.colored_packages.neoforge.mixin.logistics;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlock;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlockEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import timmychips.colored_packages.AllDyedBlocks;

@Debug(export = true)
@Mixin(FactoryPanelBlockEntity.class)
public class FactoryGaugeBEMixinForge {

    @Shadow public boolean restocker; // Shadow field so that coloredPackages$lazyTickDyedPackager can properly set the factory gauge's restock packager block
    @Shadow public AdvancementBehaviour advancements;

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
