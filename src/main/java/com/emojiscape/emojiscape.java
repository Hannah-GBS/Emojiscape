/*
 * Copyright (c) 2020, Hannah Ryan <HannahRyanster@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
