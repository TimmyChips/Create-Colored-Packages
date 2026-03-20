package timmychips.colored_packages.content.logistics.box.red;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import timmychips.colored_packages.AllPackagePartialModels;

public class RedPackageVisual extends PackageVisual {

    public final TransformedInstance instance;

    public RedPackageVisual(VisualizationContext ctx, RedPackageEntity entity, float partialTick) {
        super(ctx, entity, partialTick);

        ItemStack box = entity.box;
        if (box.isEmpty() || !PackageItem.isPackage(box))
            box = AllBlocks.CARDBOARD_BLOCK.asStack();
//        PartialModel model = AllPartialModels.PACKAGES.get(BuiltInRegistries.ITEM.getKey(box.getItem()));
        PartialModel model = AllPackagePartialModels.COLORED_PACKAGES.get(BuiltInRegistries.ITEM.getKey(box.getItem()));

        instance = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(model))
                .createInstance();

        //TODO make ColoredPackageVisualTemplate or similar name that has the animate method as a parent
        //  that way any colored package object can extend that one and simply call "super.animate(partialTick)"
        //  instead of copy-pasting the same method
//        super.animate(partialTick);
        animate(partialTick);
    }

    private void animate(float partialTick) {
        float yaw = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());

        Vec3 pos = super.entity.position();
        var renderOrigin = renderOrigin();
        var x = (float) (Mth.lerp(partialTick, this.entity.xo, pos.x) - renderOrigin.getX());
        var y = (float) (Mth.lerp(partialTick, this.entity.yo, pos.y) - renderOrigin.getY());
        var z = (float) (Mth.lerp(partialTick, this.entity.zo, pos.z) - renderOrigin.getZ());

        long randomBits = (long) entity.getId() * 31L * 493286711L;
        randomBits = randomBits * randomBits * 4392167121L + randomBits * 98761L;
        float xNudge = (((float) (randomBits >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float yNudge = (((float) (randomBits >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float zNudge = (((float) (randomBits >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;

        instance.setIdentityTransform()
                .translate(x - 0.5 + xNudge, y + yNudge, z - 0.5 + zNudge)
                .rotateYCenteredDegrees(-yaw - 90)
                .light(computePackedLight(partialTick))
                .setChanged();
    }
}
