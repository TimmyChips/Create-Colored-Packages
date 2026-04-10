package timmychips.colored_packages.content.logistics.box.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import timmychips.colored_packages.ColoredPackages;

import java.awt.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Enum with all the dye colors and the corresponding Style color for the colored package tooltip.
 * <p>The  {@link #styleWithColor(Integer)} method works by creating a style with the color integer value.
 * <p>Enum values are defined in two ways:
 * <ul>
 * <li>  Get the {@link ChatFormatting}.COLOR enum value (the Minecraft preset colors like "gold") and then use {@link ChatFormatting#getColor()} to get the integer value
 * <li>  Create a new color with rgb values, then convert it to the integer value with getRGB()
 */
public enum ColorTooltipFormattingHelper {
    BLACK("black", styleWithColor(new Color(30, 30, 52).getRGB())),
    BLUE("blue", styleWithColor(ChatFormatting.BLUE.getColor())),
    BROWN("brown", styleWithColor(new Color(153, 93, 51).getRGB())),
    CYAN("cyan", styleWithColor(ChatFormatting.DARK_AQUA.getColor())),
    GRAY("gray", styleWithColor(ChatFormatting.DARK_GRAY.getColor())),
    GREEN("green", styleWithColor(new Color(99, 134, 46).getRGB())),
    LIGHT_BLUE("Light Blue", styleWithColor(new Color(113, 169, 255).getRGB())),
    LIGHT_GRAY("light gray", styleWithColor(ChatFormatting.GRAY.getColor())),
    LIME("lime", styleWithColor(new Color(131, 212, 28).getRGB())),
    MAGENTA("magenta", styleWithColor(ChatFormatting.LIGHT_PURPLE.getColor())),
    ORANGE("orange", styleWithColor(new Color(255, 159, 42).getRGB())),
    PINK("pink", styleWithColor(new Color(247, 180, 214).getRGB())),
    PURPLE("purple", styleWithColor(new Color(207, 115, 255).getRGB())),
    RED("red", styleWithColor(ChatFormatting.RED.getColor())),
    WHITE("white", styleWithColor(new Color(212, 212, 212).getRGB())),
    YELLOW("yellow", styleWithColor(ChatFormatting.YELLOW.getColor()));

    //TODO: need to update the other names to have capitalization and spacing

    // Store enumerate values to map for getting (i.e. converts BLUE it's style to blue=style and places in map)
    private static final Map<String, Style> FORMAT_BY_NAME = Arrays.stream(values())
            .collect(Collectors.toMap(ColorTooltipFormattingHelper::getName, style -> style.style));
    private final String langName;
    private final Style style;

    ColorTooltipFormattingHelper(String langName, Style style) {
        ColoredPackages.LOGGER.info("test!!!");
        this.langName = langName;
        this.style = style;
    }

    /**
     *
     * @param rgb The rgb integer color value
     * @return The style with the color input
     */
    public static Style styleWithColor(Integer rgb) {
        return Style.EMPTY.withColor(rgb);
    }

    // Gets the name of the Enum (e.g. BLUE) and converts it to lowercase (e.g. blue)
    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    /**
     * Gets the style from the enum value Map
     * @param color The input color string to get the style from
     * @return The Style color associated with input
     */
    public static Style getByName(String color) {
        color = color.toLowerCase();
        try {
            return FORMAT_BY_NAME.get(color);
        } catch (Exception e) {
            ColoredPackages.LOGGER.info("Unable to get style object from color: {}", color);
        }
        return Style.EMPTY;
    }

    // Return the name with proper capitalization and spacing; i.e. "Light Blue" instead of "light_blue"
//    public static String getLangName(String name) {
//
//    }
}
