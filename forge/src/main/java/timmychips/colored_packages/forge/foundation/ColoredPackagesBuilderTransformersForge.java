package timmychips.colored_packages.forge.foundation;

import com.simibubi.create.content.logistics.packager.PackagerGenerator;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerGeneratorForge;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ColoredPackagesBuilderTransformersForge {
    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> dyedPackager() {
        return b -> b.initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion())
                .properties(p -> p.isRedstoneConductor(($1, $2, $3) -> false))
                .properties(p -> p.mapColor(MapColor.TERRACOTTA_BLUE)
                        .sound(SoundType.NETHERITE_BLOCK))
                .transform(pickaxeOnly())
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(new DyedPackagerGeneratorForge()::generate)
                .item()
                .model(AssetLookup::customItemModel)
                .build();
    }
}
