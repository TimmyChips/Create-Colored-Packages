package timmychips.colored_packages.content.logistics.box;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import timmychips.colored_packages.ColoredPackages;

public enum ColoredPackageTag {
    PACKAGE_COLOR;

    public final TagKey<Item> tag;

    ColoredPackageTag() {
        this.tag = TagKey.create(Registries.ITEM, ColoredPackages.asResource(this.name()));
    }
}
