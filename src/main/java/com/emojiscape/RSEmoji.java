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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;
import net.runelite.client.util.ImageUtil;

@Slf4j
enum RSEmoji
{
	AGILITY, ATTACK, COMBAT, MELEE, CONSTRUCTION, COOKING, CRAFTING, DEFENCE, FARMING, FIREMAKING, FISHING, FLETCHING, HERBLORE,
	HITPOINTS, HUNTER, MAGIC, MINING, PRAYER, RANGED, RUNECRAFT, SLAYER, SMITHING, STRENGTH, THIEVING, WOODCUTTING,

	RETRIBUTION, REDEMPTION, SMITE, PRESERVE, CHIVALRY, PIETY, RIGOUR, AUGURY,

	BANK, ALTAR, SHORTCUT, QUEST, DIARY, MINIGAME, FAVOUR, ARCEUUS, HOSIDIUS, LOVAKENGJ, PISCARILIUS, SHAYZIEN, COINS, EXCHANGE,
	IRONMAN, HARDCORE, ULTIMATE, JMOD, PMOD,
	;

	public String getProperty(String LongorShort) throws IOException
	{
		int failCount = 0;
		int maxFails = 3;
		Properties prop = new Properties();
		while (true)
		{
			Path path = Paths.get(RuneLite.RUNELITE_DIR + "/emojiscape.properties");
			try
			{
				prop.load(Files.newInputStream(path));
				break;
			}
			catch (Exception e)
			{
				InputStream inputStream = getClass().getResourceAsStream("/emojiscape.properties");
				log.error("Could not find emojiscape.properties so creating it at: " + path.toAbsolutePath());

				Files.copy(inputStream, path);
				if (++failCount == maxFails) throw e;
			}
		}
		return prop.getProperty(this.name() + "." + LongorShort);
	}

	public String[] longTrigger() throws IOException
	{
		return getProperty("LongTrigger").split("\\s*,\\s*");

	}

	public String[] shortTrigger() throws IOException
	{
		return getProperty("ShortTrigger").split("\\s*,\\s*");
	}

	private static final Map<String, RSEmoji> skillLongEmojiMap;
	private static final Map<String, RSEmoji> skillShortEmojiMap;

	static
	{
		ImmutableMap.Builder<String, RSEmoji> builder = new ImmutableMap.Builder<>();

		for (final RSEmoji RSEmoji : values())
		{
			try
			{
				for (int i = 0; i < RSEmoji.longTrigger().length; i++)
				{
					builder.put(RSEmoji.longTrigger()[i], RSEmoji);
				}

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		skillLongEmojiMap = builder.build();
	}

	static
	{
		ImmutableMap.Builder<String, RSEmoji> builder = new ImmutableMap.Builder<>();

		for (final RSEmoji RSEmoji : values())
		{
			try
			{
				for (int i = 0; i < RSEmoji.shortTrigger().length; i++)
				{
					builder.put(RSEmoji.shortTrigger()[i], RSEmoji);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		skillShortEmojiMap = builder.build();
	}


	BufferedImage loadImage(RSEmoji rsEmoji)
	{
		switch (rsEmoji)
		{
			case AGILITY: case ATTACK: case COMBAT: case CONSTRUCTION: case COOKING: case CRAFTING: case DEFENCE: case FARMING:
			case FIREMAKING: case FISHING: case FLETCHING: case HERBLORE: case HITPOINTS: case HUNTER: case MAGIC: case MELEE:
			case MINING: case PRAYER: case RANGED: case RUNECRAFT: case SLAYER: case SMITHING: case STRENGTH: case THIEVING: case WOODCUTTING:
				return ImageUtil.loadImageResource(getClass(), "/Skills/" + this.name().toLowerCase() + ".png");

			case AUGURY: case CHIVALRY: case PIETY: case PRESERVE: case REDEMPTION: case RETRIBUTION: case RIGOUR: case SMITE:
				return ImageUtil.loadImageResource(getClass(), "/Prayers/" + this.name().toLowerCase() + ".png");

			default: return ImageUtil.loadImageResource(getClass(), "/Misc/" + this.name().toLowerCase() + ".png");
		}
	}

	public static RSEmoji getRSEmoji(String longTrigger)
	{
		return skillLongEmojiMap.get(longTrigger);
	}

	public static RSEmoji getShortRSEmoji(String shortTrigger)
	{
		return skillShortEmojiMap.get(shortTrigger);
	}

}
