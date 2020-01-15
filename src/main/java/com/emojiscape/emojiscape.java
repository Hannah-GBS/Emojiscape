package com.emojiscape;

import com.google.common.collect.ImmutableMap;
import java.awt.image.BufferedImage;
import java.util.Map;
import net.runelite.client.util.ImageUtil;


enum RSEmoji
{
    AGILITY("agility", "agi"),
    ATTACK("attack", "att"),
    COMBAT("combat", "cmb"),
    CONSTRUCTION("construction", "con"),
    COOKING("cooking", "cook"),
    CRAFTING("crafting", "craft"),
    DEFENCE("defence", "def"),
    FARMING("farming", "farm"),
    FIREMAKING("firemaking", "fm"),
    FISHING("fishing", "fish"),
    FLETCHING("fletching", "fletch"),
    HERBLORE("herblore", "herb"),
    HITPOINTS("hitpoints", "hp"),
    HUNTER("hunter", "hunt"),
    MAGIC("magic", "mage"),
    MINING("mining", "mine"),
    PRAYER("prayer", "pray"),
    RANGED("ranged", "range"),
    RUNECRAFT("runecraft", "rc"),
    SLAYER("slayer", "slay"),
    SMITHING("smithing", "smith"),
    STRENGTH("strength", "str"),
    THIEVING("thieving", "thief"),
    WOODCUTTING("woodcutting", "wc"),;


    private static final Map<String, RSEmoji> rsEmojiMap;

    private final String trigger;
    private final String shortTrigger;

    static{
        ImmutableMap.Builder<String, RSEmoji> builder = new ImmutableMap.Builder<>();

        for (final RSEmoji rsEmoji : values())
        {
            builder.put(rsEmoji.trigger, rsEmoji);
            builder.put(rsEmoji.shortTrigger, rsEmoji);
        }

        rsEmojiMap = builder.build();
    }

    RSEmoji(String trigger, String shortTrigger) {this.trigger = trigger; this.shortTrigger = shortTrigger;}

    BufferedImage loadImage()
    {
        return ImageUtil.getResourceStreamFromClass(getClass(), "/skill_icons_small/" + this.name().toLowerCase() + ".png");
    }

    public static RSEmoji getRSEmoji(String trigger) {return rsEmojiMap.get(trigger);};
    static RSEmoji getRSEmojiShort(String shortTrigger) {return rsEmojiMap.get(shortTrigger);};
}
