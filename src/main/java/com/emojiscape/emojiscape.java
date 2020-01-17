package com.emojiscape;

import com.google.common.collect.ImmutableMap;
import java.awt.image.BufferedImage;
import java.util.Map;
import net.runelite.client.util.ImageUtil;
import javax.inject.Inject;


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
	WOODCUTTING("woodcutting", "wc"),
	;

	@Inject
	private emojiscapeConfig config;

	private static final Map<String, RSEmoji> rsLongEmojiMap;
	private static final Map<String, RSEmoji> rsShortEmojiMap;

	private final String longTrigger;
	private final String shortTrigger;

	static
	{
		ImmutableMap.Builder<String, RSEmoji> builder = new ImmutableMap.Builder<>();

		for (final RSEmoji rsEmoji : values())
		{
			builder.put(rsEmoji.longTrigger, rsEmoji);
		}

		rsLongEmojiMap = builder.build();
	}

	static
	{
		ImmutableMap.Builder<String, RSEmoji> builder = new ImmutableMap.Builder<>();

		for (final RSEmoji rsEmoji : values())
		{
			builder.put(rsEmoji.shortTrigger, rsEmoji);
		}

		rsShortEmojiMap = builder.build();
	}

	RSEmoji(String longTrigger, String shortTrigger)
	{
		this.longTrigger = longTrigger;
		this.shortTrigger = shortTrigger;
	}

	BufferedImage loadImage()
	{
		return ImageUtil.getResourceStreamFromClass(getClass(), "/" + this.name().toLowerCase() + ".png");
	}

	public static RSEmoji getRSEmoji(String longTrigger)
	{
		return rsLongEmojiMap.get(longTrigger);
	}

	public static RSEmoji getShortRSEmoji(String shortTrigger)
	{
		return rsShortEmojiMap.get(shortTrigger);
	}

}
