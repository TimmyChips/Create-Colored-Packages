package timmychips.colored_packages.forge.content.logistics.box;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import timmychips.colored_packages.AllPackagePartialModels;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;
import timmychips.colored_packages.content.logistics.box.util.ColoredPackagePartialUtil;

public class RedPackageRendererForge extends EntityRenderer<RedPackageEntityForge> {

    public RedPackageRendererForge(EntityRendererProvider.Context pContext) {
        super(pContext);
        shadowRadius = 0.5f;
    }

    @Override
    public void render(RedPackageEntityForge entity, float yaw, float pt, PoseStack ms, MultiBufferSource buffer, int light) {
        if (!VisualizationManager.supportsVisualization(entity.level())) {
            ItemStack box = entity.box;
            if (box.isEmpty() || !PackageItem.isPackage(box)) box = AllBlocks.CARDBOARD_BLOCK.asStack();

            PartialModel model;
            CompoundTag compoundTag = box.getTag();
            if (compoundTag != null) {
                String color = compoundTag.getString(ColoredPackageItem.TAG_COLOR);
                model = AllPackagePartialModels.coloredPartialFromColor(box, color);
            }
            model = ColoredPackagePartialUtil.getPartialFromTagColor(box);
//            else model = AllPartialModels.PACKAGES.get(ForgeRegistries.ITEMS.getKey(box.getItem()));

            renderBox(entity, yaw, ms, buffer, light, model);
        }
        super.render(entity, yaw, pt, ms, buffer, light);
    }

    public static void renderBox(Entity entity, float yaw, PoseStack ms, MultiBufferSource buffer, int light,
                                 PartialModel model) {
        if (model == null)
            return;
        SuperByteBuffer sbb = CachedBuffers.partial(model, Blocks.AIR.defaultBlockState());
        sbb.translate(-.5, 0, -.5)
                .rotateCentered(-AngleHelper.rad(yaw + 90), Direction.UP)
                .light(light)
                .nudge(entity.getId());
        sbb.renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }

    // Not needed, just return new null ResourceLocation to avoid error/warning
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull RedPackageEntityForge pEntity) {
        return ColoredPackages.asResource("null");
    }
}
