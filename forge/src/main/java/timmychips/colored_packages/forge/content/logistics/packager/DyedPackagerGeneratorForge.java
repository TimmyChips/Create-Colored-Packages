package timmychips.colored_packages.forge.content.logistics.packager;

import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.content.logistics.packager.PackagerGenerator;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

public class DyedPackagerGeneratorForge extends PackagerGenerator {

    // get model from Create's version but with PackagerBlock changed to DyedPackagerBlockForge
    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                BlockState state) {
        String suffix = state.getOptionalValue(DyedPackagerBlockForge.LINKED)
                .orElse(false) ? "linked" : state.getValue(DyedPackagerBlockForge.POWERED) ? "powered" : "";
        return state.getValue(DyedPackagerBlockForge.FACING)
                .getAxis() == Direction.Axis.Y ? AssetLookup.partialBaseModel(ctx, prov, "vertical", suffix)
                : AssetLookup.partialBaseModel(ctx, prov, suffix);
    }
}
