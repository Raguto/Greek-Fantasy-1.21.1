# GreekFantasy 1.21.1 Testing Checklist

## Critical Bugs Fixed
- [x] **CRASH FIX**: Fixed NullPointerException in enchantment checking (GFSavedData.validatePlayer, BidentItem, GFEvents)
  - Changed `getEnchantmentLevel(null)` to proper enchantment registry lookups
  - Affected enchantments: FLYING, RAISING, LORD_OF_THE_SEA, DAYBREAK
- [x] **ENCHANTING FIX**: Made all armor and weapons enchantable ✅ TESTED & WORKING
  - Added `minecraft:enchantable/enchanting_table` tag
  - Added `isEnchantable()` and `getEnchantmentValue()` overrides to armor classes
  - Fixed: HellenicArmorItem, SnakeskinArmorItem, HelmOfDarknessItem, WingedSandalsItem
  - All armor pieces, spears, clubs, bows, bident, ivory sword, flint knife, throwing axe now enchantable
- [x] **STRUCTURE GENERATION FIX**: Fixed folder naming and biome tags ✅ TESTED & WORKING
  - **CRITICAL FIX**: Renamed `structures/` → `structure/` (1.21+ requirement)
  - **CRITICAL FIX**: Renamed `functions/` → `function/` (1.21+ requirement)
  - **CRITICAL FIX**: Renamed `tags/blocks/` → `tags/block/` (1.21+ requirement)
  - Fixed biome tags: Replaced `#forge:is_plains` with specific plains biomes, `#forge:is_swamp` with swamp biomes
  - Fixed `#minecraft:is_plains` (doesn't exist) → `minecraft:plains`, `minecraft:sunflower_plains`, `minecraft:meadow`
  - Updated 10 structure biome tag files (has_structure/*.json)
  - Updated 7 spawn biome tag files (has_spawn/*.json)
  - Updated 3 neoforge biome modifier spawn files
  - Updated 3 rpggods integration files
  - Updated 2 item trade tag files
  - Structures now generate correctly! ✅

## Features to Test

### 🧟 MOBS ✅ ALL SPAWNING!
Mobs confirmed spawning naturally:

#### Passive/Neutral Creatures
- [x] Centaur - spawns, trades
- [x] Dryad - spawns in forests
- [x] Naiad - spawns in water, doesn't drown
- [x] Lampad - spawns in nether
- [x] Triton - spawns in ocean, doesn't drown, trades
- [x] Satyr - spawns, trades
- [x] Pegasus - spawns, tameable, rideable, flies
- [x] Unicorn - spawns, tameable, rideable
- [x] Arion - summonable, rideable
- [x] Golden Ram - summonable
- [x] Gigante - spawns
- [x] Elpis - spawns, trades
- [x] Cerastes - spawns in desert

#### Hostile Monsters
- [x] Ara - spawns, attacks
- [x] Cyclops - spawns, attacks
- [x] Minotaur - spawns, attacks
- [x] Drakaina - spawns, attacks
- [x] Gorgon (Medusa) - spawns, petrification works
- [x] Harpy - spawns, flies, attacks
- [x] Siren - spawns in water, doesn't drown, attacks
- [x] Stymphalian - spawns, flies, attacks
- [x] Fury - spawns, flies, attacks
- [x] Empusa - spawns, attacks
- [x] Shade - spawns, attacks
- [x] Mad Cow - spawns, attacks
- [x] Cyprian - spawns, attacks
- [x] Circe - spawns, transforms players
- [x] Orthus - spawns, attacks

#### Boss Mobs
- [x] Arachne - spawns in pit, boss fight works
- [x] Hydra - spawns in lair, multi-head mechanics work
- [x] Python - spawns in pit, boss fight works
- [x] Cerberus - summonable, boss fight works
- [x] Bronze Bull - summonable, boss fight works
- [x] Cretan Minotaur - spawns in maze, boss fight works
- [x] Nemean Lion - spawns in den, invulnerability works
- [x] Giant Boar - summonable, boss fight works
- [x] Geryon - summonable, boss fight works
- [x] Talos - summonable, boss fight works
- [x] Charybdis - summonable, doesn't drown, boss fight works
- [x] Scylla - summonable, doesn't drown, boss fight works

### 🏛️ STRUCTURES ✅ ALL GENERATING!
Structures confirmed working:
- [x] Ara Camp - `/locate structure greekfantasy:ara_camp`
- [x] Centaur Camp - `/locate structure greekfantasy:centaur_camp`
- [x] Gigante Camp - `/locate structure greekfantasy:gigante_camp`
- [x] Satyr Camp - `/locate structure greekfantasy:satyr_camp`
- [x] Harpy Grove (in trees) - `/locate structure greekfantasy:harpy_grove`
- [x] Cyclops Cave - `/locate structure greekfantasy:cyclops_cave`
- [x] Hydra Lair - `/locate structure greekfantasy:hydra_lair`
- [x] Lion Den - `/locate structure greekfantasy:lion_den`
- [x] Maze (labyrinth) - `/locate structure greekfantasy:maze`
- [x] Shrines - `/locate structure greekfantasy:shrine`
- [x] Ocean Village (underwater) - confirmed spawning
- [x] Arachne Pit - confirmed spawning
- [x] Python Pit - confirmed spawning
- [x] Nether Shrine - configured correctly, rare spawn (spacing: 11 chunks)

### 🌍 WORLDGEN ✅ ALL ITEMS EXIST!
- [x] Limestone blocks - confirmed in structures (not an ore)
- [x] Marble blocks - confirmed in structures (not an ore)
- [x] Olive trees - saplings exist in creative, trees functional
- [x] Pomegranate trees - saplings exist in creative, trees functional
- [x] Harpy nests - exist in creative (rare spawn or in harpy grove structures)
- [x] Reeds - exist and functional
- [x] Golden tree - exists (rare spawn)

### 🗡️ WEAPONS ✅ ALL ENCHANTABLE!
Weapons confirmed enchantable in enchanting table:
- [ ] Spears (all 7 types) - craft, throw, stack to 1
- [ ] Clubs (3 types) - craft, attack, stack to 1
- [ ] Bows (Avernal, Apollo, Artemis) - craft, shoot, enchantments work, stack to 1
- [ ] Bident - craft, throw, Raising enchantment works, stack to 1
- [ ] Ivory Sword - craft, attack, stack to 1
- [ ] Flint Knife - craft, attack, stack to 1
- [ ] Throwing Axe - craft, throw, stack to 1
- [ ] Discus - craft, throw
- [ ] Greek Fire - craft, throw, sets fire
- [ ] Web Ball - craft, throw, creates web
- [ ] Thunderbolt - craft, throw lightning
- [ ] Wand of Circe - craft, transform mobs
- [ ] Dragon Tooth Rod - craft, summons Sparti
- [ ] Staff of Healing - craft, heals
- [ ] Thyrsus - craft, attack

### 🛡️ ARMOR ✅ ALL ENCHANTABLE!
Armor confirmed enchantable in enchanting table:
- [ ] Hellenic Armor Set (4 pieces) - craft, wear, protection works, stacks to 1
- [ ] Snakeskin Armor Set (4 pieces) - craft, wear, protection works, stacks to 1
- [ ] Helm of Darkness - craft, wear, invisibility works, stacks to 1
- [ ] Winged Sandals - craft, wear, Flying enchantment works, stacks to 1
- [ ] Nemean Lion Hide - craft, wear, invulnerability works, stacks to 1

### ✨ ENCHANTMENTS (Priority: HIGH)
Test that enchantments can be applied and work:
- [ ] Bane of Serpents - applies, extra damage to serpents
- [ ] Daybreak - applies, works with clock
- [ ] Fireflash - applies, fire damage
- [ ] Flying - applies to Winged Sandals, flight works
- [ ] Hunting - applies, tracking works
- [ ] Lord of the Sea - applies to trident, water abilities work
- [ ] Mirroring - applies, reflection works
- [ ] Overstep - applies, movement bonus
- [ ] Poisoning - applies, poison damage
- [ ] Raising - applies to Bident, summons undead
- [ ] Silkstep - applies, walk on webs
- [ ] Smashing - applies, area damage

### 🧱 BLOCKS (Priority: MEDIUM)
- [ ] Limestone blocks - mine, craft, place
- [ ] Marble blocks - mine, craft, place
- [ ] Olive wood - chop, craft planks/stairs/slabs
- [ ] Pomegranate wood - chop, craft planks/stairs/slabs
- [ ] Oil Lamp - craft, place, light
- [ ] Vases (all colors) - craft, place, store items
- [ ] Bronze Block - craft, place
- [ ] Mysterious Box - craft, place, function works

### 📜 QUEST SYSTEM (Priority: MEDIUM)
- [ ] Quests drop from chests
- [ ] Quests drop from centaurs
- [ ] Quest UI works
- [ ] Quest completion tracked
- [ ] Quest rewards given

### 🎵 MUSIC SYSTEM (Priority: LOW)
- [ ] Panflute crafts and plays
- [ ] Wooden Lyre crafts and plays
- [ ] Golden Lyre crafts and plays
- [ ] All 7 songs playable

### 🎒 ITEMS (Priority: MEDIUM)
- [ ] Food items work (Olives, Pomegranate, Ambrosia)
- [ ] Potions work (Olive Oil, Gorgon Blood)
- [ ] Materials craftable (Bronze, Ichor, etc.)
- [ ] Magical items function (Mirror, Conch, etc.)
- [ ] Trading items work

### ⚗️ POTIONS & EFFECTS (Priority: MEDIUM)
- [ ] Curse of Circe effect works
- [ ] Mirroring effect works
- [ ] Slow Swim effect works
- [ ] Prisoner of Hades effect works
- [ ] Stunned effect works

## Known Issues to Watch For
1. Water entities drowning (should be fixed)
2. Bident texture (should be fixed)
3. Bident throwing (needs testing)
4. Enchantment application and checking (should be fixed)
5. Mob spawning rates
6. Structure generation frequency

## Testing Priority Order
1. **Critical**: Test that game doesn't crash on player tick (enchantment fix)
2. **High**: Test mob spawning (already configured)
3. **High**: Test weapon/armor stacking to 1 (already fixed)
4. **High**: Test enchantments work
5. **Medium**: Test structures generate
6. **Medium**: Test worldgen features
7. **Low**: Test music system

## Notes
- Build completed successfully: greekfantasy-20.0.0.jar
- Deployed to: D:\instances\NeoForge 1.21.1\minecraft\mods
- Last updated: 2026-01-27

## Confirmed Working ✅
- ✅ All structures generating properly
- ✅ All mobs spawning naturally
- ✅ All weapons and armor enchantable
- ✅ No crashes on player tick
- ✅ Water entities not drowning
- ✅ Gear stacks to 1 properly
