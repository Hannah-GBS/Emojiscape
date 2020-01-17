package com.emojiscape;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("EmojiScape")
public interface emojiscapeConfig extends Config
{
	@ConfigItem(
		keyName = "longTriggers",
		name = "Long Triggers",
		description = "Matches full skill names (eg. \"Attack\", \"Construction\", etc)"
	)
	default boolean longTriggers()
	{
		return true;
	}

	@ConfigItem(
		keyName = "shortTriggers",
		name = "Short Triggers",
		description = "Matches abbreviated skill names (eg. \"att\", \"con\", etc)"
	)
	default boolean shortTriggers()
	{
		return true;
	}

}