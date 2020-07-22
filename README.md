# EmojiScape
This plugin converts common RS words in chat to their corresponding in-game icon.

If you have any questions or suggestions feel free to either make an Issue here, or @ me on the Runelite Discord (`@Hannah (loldudester)#6969`)

## Current icons:

- Skills
- High level Prayers (from Retribution to Augury)
- Misc (see Triggers table)

## Current config options:

- Icon Mode: Choose whether to replace the word with the icon, or to place the icon after the word.
- Skill Icons: Choose between Long Triggers ("Attack", "Defence", etc), Short Triggers ("att", "def", etc), or both.
- Prayer Icons: Enable high level Prayer icons.
- Misc Icons: Choose between Long Triggers, Short Triggers, or both.


## Triggers

Triggers are **not** case sensitive and can be preceded or followed by numbers and punctuation.

For example, "99fm!" would be shown as "99![fm](src/main/resources/Skills/firemaking.png)!" in Replace Text mode and "99fm(![fm](src/main/resources/Skills/firemaking.png))!" in After Text mode.

### Skills

Skills have both Long and Short triggers:

Icon | Long Trigger | Short Trigger
-----|--------------|--------------
![Agility](src/main/resources/Skills/agility.png) | agility | agi
![Attack](src/main/resources/Skills/attack.png) | attack | att
![Combat](src/main/resources/Skills/combat.png) | combat (or melee) | cmb
![Construction](src/main/resources/Skills/construction.png) | construction | con
![Cooking](src/main/resources/Skills/cooking.png) | cooking | cook
![Crafting](src/main/resources/Skills/crafting.png) | crafting | craft
![Defence](src/main/resources/Skills/defence.png) | defence | def
![Farming](src/main/resources/Skills/farming.png) | farming | farm
![Firemaking](src/main/resources/Skills/firemaking.png) | firemaking | fm
![Fishing](src/main/resources/Skills/fishing.png) | fishing | fish
![Fletching](src/main/resources/Skills/fletching.png) | fletching | fletch
![Herblore](src/main/resources/Skills/herblore.png) | herblore | herb
![Hitpoints](src/main/resources/Skills/hitpoints.png) | hitpoints | hp
![Hunter](src/main/resources/Skills/hunter.png) | hunter | hunt
![Magic](src/main/resources/Skills/magic.png) | magic | mage
![Mining](src/main/resources/Skills/mining.png) | mining | mine
![Prayer](src/main/resources/Skills/prayer.png) | prayer | pray
![Ranged](src/main/resources/Skills/ranged.png) | ranged | range
![Runecraft](src/main/resources/Skills/runecraft.png) | runecraft | rc
![Slayer](src/main/resources/Skills/slayer.png) | slayer | slay
![Smithing](src/main/resources/Skills/smithing.png) | smithing | smith
![Strength](src/main/resources/Skills/strength.png) | strength | str
![Thieving](src/main/resources/Skills/thieving.png) | thieving | thief
![Woodcutting](src/main/resources/Skills/woodcutting.png) | woodcutting | wc

### Prayers

Prayers only have one trigger each:

Icon | Trigger
-----|--------
![Retribution](src/main/resources/Prayers/retribution.png) | retribution
![Redemption](src/main/resources/Prayers/redemption.png) | redemption
![Smite](src/main/resources/Prayers/smite.png) | smite
![Preserve](src/main/resources/Prayers/preserve.png) | preserve
![Chivalry](src/main/resources/Prayers/chivalry.png) | chivalry
![Piety](src/main/resources/Prayers/piety.png) | piety
![Rigour](src/main/resources/Prayers/rigour.png) | rigour
![Augury](src/main/resources/Prayers/augury.png) | augury

### Misc

Some misc icons have short triggers, but not all. Those that don't work regardless of if Long or Short triggers are enabled:

Icon | Long Trigger | Short Trigger
-----|--------------|--------------
![Bank](src/main/resources/Misc/bank.png) | bank | bank
![Altar](src/main/resources/Misc/altar.png) | altar | altar
![Shortcut](src/main/resources/Misc/shortcut.png) | shortcut | shortcut
![Quest](src/main/resources/Misc/quest.png) | quest | qp
![Diary](src/main/resources/Misc/diary.png) | diary | diary
![Minigame](src/main/resources/Misc/minigame.png) | minigame | minigame
![Favour](src/main/resources/Misc/favour.png) | favour | favour
![Arceuus](src/main/resources/Misc/arceuus.png) | arceuus | arc
![Hosidius](src/main/resources/Misc/hosidius.png) | hosidius | hosi
![Lovakengj](src/main/resources/Misc/lovakengj.png) | lovakengj | lova
![Piscarilius](src/main/resources/Misc/piscarilius.png) | piscarilius | pisc
![Shayzien](src/main/resources/Misc/shayzien.png) | shayzien | shayz
![Coins](src/main/resources/Misc/coins.png) | coins | gp
![Exchange](src/main/resources/Misc/exchange.png) | exchange | ge
![Ironman](src/main/resources/Misc/ironman.png) | ironman | im
![Hardcore](src/main/resources/Misc/hardcore.png) | hardcore | hcim
![Ultimate](src/main/resources/Misc/ultimate.png) | ultimate | uim
![JMod](src/main/resources/Misc/jmod.png) | jmod | jmod
![pmod](src/main/resources/Misc/pmod.png) | pmod | pmod

### Custom Triggers

You can customise the triggers by editing the contents of the `emojiscape.properties` file in your `.runelite` directory (found at `%userprofile%\.runelite` on Windows and `~/.runelite` on MacOS and Linux).

You can set multiple triggers per icon with a comma separated list. For example, `IRONMAN.ShortTrigger=im, iron` would result in both "im" and "iron" showing the Ironman icon.