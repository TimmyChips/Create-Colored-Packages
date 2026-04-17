package timmychips.colored_packages.content.logistics.packager;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.logistics.packager.PackagerRenderer;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import timmychips.colored_packages.AllDyedBlocks;

import java.util.function.Consumer;

public class DyedPackagerVisual<T extends PackagerBlockEntity> extends AbstractBlockEntityVisual<T> implements SimpleDynamicVisual {
    public final TransformedInstance hatch;
    public final TransformedInstance tray;

    public float lastTrayOffset = Float.NaN;
    public PartialModel lastHatchPartial;


    public DyedPackagerVisual(VisualizationContext ctx, T blockEntity, float partialTick) {
        super(ctx, blockEntity, partialTick);

        lastHatchPartial = PackagerRenderer.getHatchModel(blockEntity);
        hatch = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(lastHatchPartial))
                .createInstance();

        tray = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(getTrayModel(this.blockState)))
                .createInstance();

        Direction facing = blockState.getValue(PackagerBlock.FACING)
                .getOpposite();

        var lowerCorner = Vec3.atLowerCornerOf(facing.getNormal());
        hatch.setIdentityTransform()
                .translate(getVisualPosition())
                .translate(lowerCorner
                        .scale(.49999f))
                .rotateYCenteredDegrees(AngleHelper.horizontalAngle(facing))
                .rotateXCenteredDegrees(AngleHelper.verticalAngle(facing))
                .setChanged();

        animate(partialTick);
    }

    // From PackagerRenderer class with slight tweak to return correct tray model based on if dyed packager or dyed repackager
    public static PartialModel getTrayModel(BlockState blockState) {
        return AllDyedBlocks.DYED_PACKAGER.has(blockState) ? AllPartialModels.PACKAGER_TRAY_REGULAR
                : AllPartialModels.PACKAGER_TRAY_DEFRAG;
    }

    @Override
    public void beginFrame(Context ctx) {
        animate(ctx.partialTick());
    }

    // Copied from PackagerVisual class
    public void animate(float partialTick) {
        var hatchPartial = PackagerRenderer.getHatchModel(blockEntity);

        if (hatchPartial != this.lastHatchPartial) {
            instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(hatchPartial))
                    .stealInstance(hatch);

            this.lastHatchPartial = hatchPartial;
        }

        float trayOffset = blockEntity.getTrayOffset(partialTick);

        if (trayOffset != lastTrayOffset) {
            Direction facing = blockState.getValue(PackagerBlock.FACING)
                    .getOpposite();

            var lowerCorner = Vec3.atLowerCornerOf(facing.getNormal());

            tray.setIdentityTransform()
                    .translate(getVisualPosition())
                    .translate(lowerCorner.scale(trayOffset))
                    .rotateYCenteredDegrees(facing.toYRot())
                    .setChanged();

            lastTrayOffset = trayOffset;
        }
    }

    @Override
    public void updateLight(float partialTick) {
        relight(hatch, tray);
    }

    @Override
    protected void _delete() {
        hatch.delete();
        tray.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {

    }
}
