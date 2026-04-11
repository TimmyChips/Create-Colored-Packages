package timmychips.colored_packages.forge.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBehaviour;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlock;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlockEntity;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.logistics.packager.repackager.RepackagerBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;
import timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerBlockEntityForge;
import timmychips.colored_packages.forge.content.logistics.packager.repackager.DyedRepackagerBlockEntityForge;

import java.util.EnumMap;
import java.util.List;

@Debug(export = true)
@Mixin(FactoryPanelBlockEntity.class)
public class FactoryGaugeBEMixinForge extends SmartBlockEntity {

    @Shadow public boolean restocker;

    public FactoryGaugeBEMixinForge(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

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

    @Inject(method = "lazyTick", at = @At(value = "RETURN"))
    public void coloredPackages$lazyTickDyedPackager(CallbackInfo ci) {
        FactoryPanelBlockEntity self = (FactoryPanelBlockEntity) (Object) this;

//        ColoredPackages.LOGGER.info("lazytick!");

        Level level = self.getLevel();

//        self.lazyTick();
        if (level.isClientSide())
            return;

//        if (self.panels == null) return;

        if (self.activePanels() == 0)
            level.setBlockAndUpdate(self.getBlockPos(), Blocks.AIR.defaultBlockState());

        if (AllBlocks.FACTORY_GAUGE.has(getBlockState())) {
            boolean shouldBeRestocker = AllDyedBlocks.DYED_PACKAGER
                    .has(level.getBlockState(worldPosition.relative(FactoryPanelBlock.connectedDirection(getBlockState())
                            .getOpposite())))
                    ||
                    AllBlocks.PACKAGER
                            .has(level.getBlockState(worldPosition.relative(FactoryPanelBlock.connectedDirection(getBlockState())
                                    .getOpposite())));

//            ColoredPackages.LOGGER.info("should be restocker? {}", shouldBeRestocker);
            if (self.restocker == shouldBeRestocker)
                return;
            restocker = shouldBeRestocker;
//            self.restocker = false;
            self.redraw = true;
            self.sendData();
        }
    }

//    @Inject(method = "getRestockedPackager", at = @At("HEAD"), cancellable = true)
//    public void coloredPackages$getRestockedPackager(CallbackInfoReturnable<PackagerBlockEntity> cir) {
//        FactoryPanelBlockEntity self = (FactoryPanelBlockEntity) (Object) this;
//
////        ColoredPackages.LOGGER.info("getRestockedPackager");
//
//        BlockState state = getBlockState();
//        if (!self.restocker || !AllBlocks.FACTORY_GAUGE.has(state))
//            return;
//        BlockPos packagerPos = worldPosition.relative(FactoryPanelBlock.connectedDirection(state)
//                .getOpposite());
//        if (!level.isLoaded(packagerPos))
//            return;
//        BlockEntity be = level.getBlockEntity(packagerPos);
////        ColoredPackages.LOGGER.info("be: {}", be);
//        if (be == null || !(be instanceof DyedPackagerBlockEntityForge pbe))
//            return;
//        if (pbe instanceof DyedRepackagerBlockEntityForge)
//            return;
//        cir.setReturnValue(pbe);
//    }
}
