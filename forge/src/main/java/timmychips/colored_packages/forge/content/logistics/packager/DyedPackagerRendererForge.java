package timmychips.colored_packages.forge.content.logistics.packager;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.logistics.packager.PackagerRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import timmychips.colored_packages.AllPackagePartialModels;
import timmychips.colored_packages.AllPackagerSpriteShifts;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.DyedPackagerBlock;
import timmychips.colored_packages.content.logistics.packager.DyedPackagerVisual;
import timmychips.colored_packages.forge.AllDyedBlocksForge;

public class DyedPackagerRendererForge extends SmartBlockEntityRenderer<DyedPackagerBlockEntityForge> {
    public DyedPackagerRendererForge(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(DyedPackagerBlockEntityForge be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        renderPackagerSafe(be, partialTicks, ms, buffer, light, overlay);
        renderDyed(be, ms, buffer, light);
    }

    /// Create's PackagerRender renderSafe method, I just can't extend PackageRenderer since the BlockEntity type isn't correct :(
    protected void renderPackagerSafe(DyedPackagerBlockEntityForge be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        ItemStack renderedBox = be.getRenderedBox();
        float trayOffset = be.getTrayOffset(partialTicks);
        BlockState blockState = be.getBlockState();
        Direction facing = blockState.getValue(DyedPackagerBlock.FACING)
                .getOpposite();

        if (!VisualizationManager.supportsVisualization(be.getLevel())) {
            var hatchModel = PackagerRenderer.getHatchModel(be);

            SuperByteBuffer sbb = CachedBuffers.partial(hatchModel, blockState);
            sbb.translate(Vec3.atLowerCornerOf(facing.getNormal())
                            .scale(.49999f))
                    .rotateYCenteredDegrees(AngleHelper.horizontalAngle(facing))
                    .rotateXCenteredDegrees(AngleHelper.verticalAngle(facing))
                    .light(light)
                    .renderInto(ms, buffer.getBuffer(RenderType.solid()));

            sbb = CachedBuffers.partial(DyedPackagerVisual.getTrayModel(blockState), blockState);
            sbb.translate(Vec3.atLowerCornerOf(facing.getNormal())
                            .scale(trayOffset))
                    .rotateYCenteredDegrees(facing.toYRot())
                    .light(light)
                    .renderInto(ms, buffer.getBuffer(RenderType.cutoutMipped()));
        }

        if (!renderedBox.isEmpty()) {
            ms.pushPose();
            var msr = TransformStack.of(ms);
            msr.translate(Vec3.atLowerCornerOf(facing.getNormal())
                            .scale(trayOffset))
                    .translate(.5f, .5f, .5f)
                    .rotateYDegrees(facing.toYRot())
                    .translate(0, 2 / 16f, 0)
                    .scale(1.49f, 1.49f, 1.49f);
            Minecraft.getInstance()
                    .getItemRenderer()
                    .renderStatic(null, renderedBox, ItemDisplayContext.FIXED, false, ms, buffer, be.getLevel(), light,
                            overlay, 0);
            ms.popPose();
        }
    }

    // Render color label + sprite shift
    protected void renderDyed(DyedPackagerBlockEntityForge be, PoseStack ms, MultiBufferSource buffer, int light) {

        DyeColor color = be.color.orElse(null); // Get dyed packager's current color value
        BlockState blockState = be.getBlockState(); // Dyed packager BE block state

        PartialModel colorLabelPartial = AllPackagePartialModels.DYED_PACKAGER_COLOR_LABEL; // Get partial model of the color label
        SuperByteBuffer packagerBuffer = CachedBuffers.partial(colorLabelPartial, blockState) // Get buffer from color label partial for rendering
                .light(light);

        SpriteShiftEntry spriteShift = getSpriteShiftEntry(color); // Get color label sprite from color input

        packagerBuffer.shiftUV(spriteShift); // Shift texture atlas UV to get new texture
        packagerBuffer.renderInto(ms, buffer.getBuffer(RenderType.cutoutMipped())); // Render color label on dyed packager
    }

    protected static SpriteShiftEntry getSpriteShiftEntry(DyeColor color) {
//        ColoredPackages.LOGGER.info("color: {}", color);
        if (color != null) {
//            ColoredPackages.LOGGER.info("sprite shift: {}", AllPackagerSpriteShifts.DYED_PACKAGERS.get(color));
            return AllPackagerSpriteShifts.DYED_PACKAGERS.get(color);
        }
        else return AllPackagerSpriteShifts.DYED_PACKAGERS.get(DyeColor.RED);
    }
}
