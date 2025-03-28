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

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import javax.inject.Inject;
import com.google.inject.Provides;
import joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.IndexedSprite;
import net.runelite.api.MessageNode;
import net.runelite.api.Player;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.config.ConfigManager;

@PluginDescriptor(
	name = "EmojiScape",
	description = "Adds Runescape icons to chat"
)
@Slf4j
public class emojiscapePlugin extends Plugin
{
	private static final Pattern TAG_REGEXP = Pattern.compile("<[^>]*>");
	private static final Pattern WHITESPACE_REGEXP = Pattern.compile("[\\s\\u00A0]");
	private static final Pattern SLASH_REGEXP = Pattern.compile("[\\/]");
	private static final Pattern PUNCTUATION_REGEXP = Pattern.compile("[\\W\\_\\d]");

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ConfigManager configManager;

	@Inject
	private emojiscapeConfig config;


	private int modIconsStart = -1;

	@Override
	protected void startUp()
	{
		clientThread.invokeLater(this::loadRSEmojiIcons);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			loadRSEmojiIcons();
		}
	}

	private void loadRSEmojiIcons()
	{
		final IndexedSprite[] modIcons = client.getModIcons();
		if (modIconsStart != -1 || modIcons == null)
		{
			return;
		}

		final RSEmoji[] RSEmojis = RSEmoji.values();
		final IndexedSprite[] newModIcons = Arrays.copyOf(modIcons, modIcons.length + RSEmojis.length);
		modIconsStart = modIcons.length;

		for (int i = 0; i < RSEmojis.length; i++)
		{
			final RSEmoji RSEmoji = RSEmojis[i];

			try
			{
				final BufferedImage image = RSEmoji.loadImage(RSEmoji);
				final IndexedSprite sprite = ImageUtil.getImageIndexedSprite(image, client);
				newModIcons[modIconsStart + i] = sprite;
			}
			catch (Exception ex)
			{
				log.warn("Failed to load the sprite for RSEmoji " + RSEmoji, ex);
			}
		}

		log.debug("Adding RSEmoji icons");
		client.setModIcons(newModIcons);
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		if (client.getGameState() != GameState.LOGGED_IN || modIconsStart == -1)
		{
			return;
		}

		switch (chatMessage.getType())
		{
			case CLAN_MESSAGE:
			case CLAN_GUEST_MESSAGE:
				if (!config.ccAnnouncements()) {
					return;
				}
			case PUBLICCHAT:
			case MODCHAT:
			case FRIENDSCHAT:
			case CLAN_CHAT:
			case CLAN_GUEST_CHAT:
			case PRIVATECHAT:
			case PRIVATECHATOUT:
			case MODPRIVATECHAT:
				break;
			default:
				return;
		}

		final MessageNode messageNode = chatMessage.getMessageNode();
		final String message = messageNode.getValue();
		final String updatedMessage = updateMessage(message);

		if (updatedMessage == null)
		{
			return;
		}

		messageNode.setRuneLiteFormatMessage(updatedMessage);
		client.refreshChat();
	}

	@Subscribe
	public void onOverheadTextChanged(final OverheadTextChanged event)
	{
		if (!(event.getActor() instanceof Player))
		{
			return;
		}

		final String message = event.getOverheadText();
		final String updatedMessage = updateMessage(message);

		if (updatedMessage == null)
		{
			return;
		}

		event.getActor().setOverheadText(updatedMessage);
	}

	@Nullable
	String updateMessage(final String message)
	{
		String surroundLeft;
		String surroundRight;
		switch (config.afterTextStyle())
		{
			case ROUND:
				surroundLeft = "(";
				surroundRight = ")";
				break;
			case SQUARE:
				surroundLeft = "[";
				surroundRight = "]";
				break;
			default:
				surroundLeft = "";
				surroundRight = "";
		}
		final String[] slashWords = SLASH_REGEXP.split(message);
		boolean editedMessage = false;
		for (int s = 0; s < slashWords.length; s++)
		{
			final String[] messageWords = WHITESPACE_REGEXP.split(slashWords[s]);

			for (int i = 0; i < messageWords.length; i++)
			{
				boolean longTriggerUsed = false;
				boolean editWord = false;
				//Remove tags except for <lt> and <gt>
				final String pretrigger = removeTags(messageWords[i]);
				final Matcher matcherTrigger = PUNCTUATION_REGEXP.matcher(pretrigger);
				final String trigger = matcherTrigger.replaceAll("");
				if (trigger.equals(""))
				{
					continue;
				}
				final RSEmoji rsEmoji = RSEmoji.getRSEmoji(trigger.toLowerCase());
				final RSEmoji rsShortEmoji = RSEmoji.getShortRSEmoji(trigger.toLowerCase());

				if (rsEmoji == null && rsShortEmoji == null)
				{
					continue;
				}

				boolean skillLong = false;
				boolean skillShort = false;
				boolean miscLong = false;
				boolean miscShort = false;

				switch (config.skillIcons())
				{
					case LONG:
						skillLong = true;
						break;
					case SHORT:
						skillShort = true;
						break;
					case BOTH:
						skillLong = true;
						skillShort = true;
						break;
				}

				switch (config.miscIcons())
				{
					case LONG:
						miscLong = true;
						break;
					case SHORT:
						miscShort = true;
						break;
					case BOTH:
						miscLong = true;
						miscShort = true;
						break;
				}

				int rsEmojiId = 0;

				if (rsEmoji != null)
				{
					rsEmojiId = modIconsStart + rsEmoji.ordinal();
					if ((skillLong && rsEmoji.ordinal() <= 24)
					|| (config.prayerIcons() && 25 <= rsEmoji.ordinal() && rsEmoji.ordinal() <= 32)
					|| (miscLong && 33 <= rsEmoji.ordinal() && rsEmoji.ordinal() <= 51))
					{
						editWord = true;
					}
					longTriggerUsed = true;
				}

				if (rsShortEmoji != null && !longTriggerUsed)
				{
					rsEmojiId = modIconsStart + rsShortEmoji.ordinal();
					if ((skillShort && rsShortEmoji.ordinal() <= 24)
					|| (config.prayerIcons() && 25 <= rsShortEmoji.ordinal() && rsShortEmoji.ordinal() <= 32)
					|| (miscShort && 33 <= rsShortEmoji.ordinal() && rsShortEmoji.ordinal() <= 51))
					{
						editWord = true;
					}
				}

				if (editWord && rsEmojiId != 0)
				{
					if (config.swapIconMode() == IconMode.REPLACE)
					{
						messageWords[i] = messageWords[i].replace(trigger, "<img=" + rsEmojiId + ">");
					}
					else if (config.swapIconMode() == IconMode.APPEND)
					{
						messageWords[i] = messageWords[i].replace(trigger, trigger + surroundLeft + "<img=" + rsEmojiId + ">" + surroundRight);
					}
				}
				editedMessage = true;
			}
			slashWords[s] = Strings.join(messageWords, " ");
		}

		if (!editedMessage)
		{
			return null;
		}

		return Strings.join(slashWords, "/");
	}

	/**
	 * Remove tags, except for &lt;lt&gt; and &lt;gt&gt;
	 *
	 * @return
	 */
	private static String removeTags(String str)
	{
		StringBuffer stringBuffer = new StringBuffer();
		Matcher matcher = TAG_REGEXP.matcher(str);
		while (matcher.find())
		{
			matcher.appendReplacement(stringBuffer, "");
			String match = matcher.group(0);
			switch (match)
			{
				case "<lt>":
				case "<gt>":
					stringBuffer.append(match);
					break;
			}
		}
		matcher.appendTail(stringBuffer);
		return stringBuffer.toString();
	}

	@Provides
	emojiscapeConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(emojiscapeConfig.class);
	}
}