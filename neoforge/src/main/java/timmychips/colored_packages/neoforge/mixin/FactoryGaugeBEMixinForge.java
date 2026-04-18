package timmychips.colored_packages.neoforge.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBehaviour;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlock;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlockEntity;
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

@Debug(export = true)
@Mixin(FactoryPanelBlockEntity.class)
public class FactoryGaugeBEMixinForge extends SmartBlockEntity {

    @Shadow public boolean restocker; // Shadow field so that coloredPackages$lazyTickDyedPackager can properly set the factory gauge's restock packager block

    public FactoryGaugeBEMixinForge(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // Copied from Factory Gauges addBehaviours
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        FactoryPanelBlockEntity self = (FactoryPanelBlockEntity) (Object) this;

        self.panels = new EnumMap<>(FactoryPanelBlock.PanelSlot.class);
        self.redraw = true;
        for (FactoryPanelBlock.PanelSlot slot : FactoryPanelBlock.PanelSlot.values()) {
            FactoryPanelBehaviour e = new FactoryPanelBehaviour(self, slot);
            self.panels.put(slot, e);
            behaviours.add(e);
        }
    }

    // Copied from lazyTick from target class, but for Dyed Packager as well
    @Inject(method = "lazyTick", at = @At(value = "RETURN"))
    public void coloredPackages$lazyTickDyedPackager(CallbackInfo ci) {
        FactoryPanelBlockEntity self = (FactoryPanelBlockEntity) (Object) this;

        Level level = self.getLevel();

        if (level.isClientSide())
            return;

        if (self.activePanels() == 0)
            level.setBlockAndUpdate(self.getBlockPos(), Blocks.AIR.defaultBlockState());

        if (AllBlocks.FACTORY_GAUGE.has(getBlockState())) {
            boolean shouldBeRestocker = AllDyedBlocks.DYED_PACKAGER
                    .has(level.getBlockState(worldPosition.relative(FactoryPanelBlock.connectedDirection(getBlockState())
                            .getOpposite())))
                    ||
                    AllBlocks.PACKAGER // Perform for packager too
                            .has(level.getBlockState(worldPosition.relative(FactoryPanelBlock.connectedDirection(getBlockState())
                                    .getOpposite())));

            if (self.restocker == shouldBeRestocker)
                return;
            restocker = shouldBeRestocker;
            self.redraw = true;
            self.sendData();
        }
    }
}
