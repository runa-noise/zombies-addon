# Change log

## 4.9.0-beta1
10-29-2026
- Changes
  - Developer name updated
    - Package changed: kr.hahaha98757 -> kr.runanoise
    - New repository URL reflected
    - Backward compatibility: FileRemover from the previous package retained
  - Configuration improvements: Added relevant messages
  - Player Visibility
    - "Semi PV" now renders fire as semi-transparent
    - Player layers no longer overlap during rendering
  - Auto Splits: Added settings to modify commands
  - Not Last: Added settings to adjust activation timing
  - Auto Rejoin: Added settings to adjust reconnection attempt intervals
  - Recorder
    - Added various settings
    - Updated output text
    - Now deletes .seg files older than 7 days
- Bug Fixes
  - Auto Splits: Corrected default port value
  - Recorder
    - Fixed an issue where the Recorder would save incorrect data upon a helicopter escape victory
    - Updated to ensure the helicopter-call event is recorded when a victory occurs
- Code Refactoring
  - Refactored Mixins
  - Updated Enums


## 4.8.7
09-24-2026
- Added: Patcher
  - Fixed an issue where rifle sounds were not audible.
- Removed: SST Korean patch

## 4.8.6
09-23-2026
- Modification: FileRemover
  - Verified existence of main function
- Code modification: Refactoring

## 4.8.5
07-24-2026
- Bug fix: ZSV no longer works.
- Code modifications
  - Modified parts to make them directly accessible
  - Cleaned up modules and changed some classes to objects
  - Modified config code

## 4.8.4
06-21-2026
- Code Modifications
  - Gradle Update Rollback: 8.14.4 → 8.8

## 4.8.3
06-02-2026
- Added
  - Speedrun Mode
  - Internal Timer: Text Style Options
- Removed: blockUnlegitMods, detectUnlegitMods Options
- Changed: Wave Delays
  - Text Style Options
  - Wrath Mode Added.
- Bug Fixes
  - RoundStartEvent does not fire at game start.
  - Game uninstalled immediately upon start.
- Code Modifications
  - Kotlin Update: 2.2.20 → 2.3.21
  - Gradle Update: 8.8 → 8.14.4

## 4.8.2
05-06-2026
- Bug fix: Timer correction
  - Timer correction was not canceled on the practice server.

## 4.8.1
05-06-2026
- Change: Zombies Utils settings
  - Recommended compatible version: 1.4.2 → 1.4.4

## 4.8.0
05-05-2026
- Fix: Last Weapons
  - Does not recognize levels in Arabic numerals: Reflects fixes from the practice server.

## 4.8.0-rc1
05-04-2026
- Change: Practice Server Compatibility
  - Recorder does not save records.
  - Uses the old method for difficulty detection.
  - Last Weapons recognizes levels in Arabic numerals.
  - Modules do not work in rounds without information.
  - Removed scoreboard information from the lobby.


## 4.7.4
05-03-2026
- Changes
  - Compatible Zombies Utils version: 1.3.7 -> 1.4.2
  - Internal Timer: Server mode persists even when downed or dead.
- Fix: Better Zombies Left
  - Detect remaining zombie lines based on string content rather than score.
  - Rolled back to 4.7.2.
- Bug fixes
  - Powerup Patterns: Timer progresses below 0.
  - Remove existing game if attempting to start a new game with an existing game's server number.

## 4.7.3
04-26-2026
- Change: Better Zombies Left
  - Works in scoreboard-related modes such as Feather Client.
- Bug fix: Internal Timer
  - Fixed a bug where Client mode was always used upon game over in Server mode.
- Code modification: Condition for calling WaveDelays.playSound()

## 4.7.2
04-10-2026
- Modification: Translation string
  - Remove Windows-only text from automatic updates.

## 4.7.1
04-10-2026
- Change: Automatic updates are now available on other operating systems.
- Code fix: Removed meaningless super.initGui().

## 4.7.0
03-04-2026
- No changes.

## 4.7.0-rc1
03-02-2026
- Added: PRR mob spawn data.
- Changed: Recorder
  - If the timer calibration fails, only the first round of the game is recorded, not the overall record.

## 4.7.0-beta2
02-26-2026
- Bug Fix: Internal Timer
  - A bug where the Wave Delays sound would play twice in Client mode.
  - A bug where the timer would display incorrect values when changing modes in-game.

## 4.7.0-beta1
02-25-2026
- Modified: Internal Timer
  - Added a configuration option: Allows selecting between client and server timer calculations.
    - Client: Existing method. Timers are calculated based on client TPS. They are synchronized with the server clock every 20 ticks.
    - Server: Timers are calculated based on server TPS. The timer is paused upon downing, dying, or reconnecting, and is then merged with the client timer for compensation.
  - Modified so that subsequent compensation is not performed if the timer compensation fails.


## 4.6.4
02-25-2026
- Fixed: Fixed DEN and PRN mob spawn information.
- Added: Added PRH mob spawn information.

## 4.6.3
02-18-2026
- Added: Feather Client detection notification.

## 4.6.3-rc1
02-14-2026
- Added: Better Zombies Left
  - Added DER and PRN mob spawn information.
  - Added PRH and PRR helicopter escape mob spawn information.
- Fixes
  - Fixed not checking for updates if the version is incorrect.
  - Fixed not working in single player mode: requires debug mode to be enabled.
- Code fixes
  - Modified modFile object initialization method.
  - How Better Zombies Left works.

## 4.6.3-pre2
02-11-2026
- Added: Better Zombies Left: Added DEH mob spawn information.

## 4.6.3-pre1
02-09-2026
- Added: Better Zombies Left.
- Code fix: Refactored the Mixin class.

## 4.6.2
01-29-2026
- Added: Last Weapons now works even when the game is over.
- Fixed: Last Weapons now displays the number of items in your skill set that are on cooldown.

## 4.6.1
12-23-2025
- Added: Integrity check for mods downloaded via auto-update.
- Code fix: Changed GUI class to object.

## 4.6.0
12-07-2025
- Fix
  - Not Last: Different commas apply for each language. Removed commas when there are two players
- Added
  - Debug Mode: Not Last can be used when passing a round

## 4.6.0-rc1
12-04-2025
- Added: Japanese translation
- Fixed: English and Korean translations

## 4.6.0-pre1
11-14-2025
- Fix: Increased the auto timer range for Powerup Patterns.

## 4.6.0-beta2
11-10-2025
- Changes
  - /sla quick command: Apply the housing map described in ZMP.
    - Removed: mogi_a (Bad Blood)
    - Added: loliepop5 (Alien Arcadium)
  - Semi Player Visibility
    - Added minimum and maximum transparency settings.
    - Added transparency change speed settings.
      - Linear: Changes at a constant rate (previous method)
      - Fixed: Fixed at minimum value
      - Smooth: Changes smoothly (quadratic curve)
      - Sharp: Changes quickly (irrational curve)

## 4.6.0-beta1
10-28-2025
- Added
  - Recorder
  - Exception Messages: Added exception messages when starting a game or round, recording, or saving in the Recorder.
- Changed
  - Difficulty Detection
    - Detection via the scoreboard (Hypixel update)
    - Added difficulty detection behavior for game objects created mid-game.
  - Debug Mode: Added details to translations, text animation, and output.
- Removed: Ability to change difficulty using the /zombiesaddon command.
- Fixed
  - Not Last: Matches player name color to rank color.
  - Changed language file loading to run only once.
- Code Fixes: Tools.kt Reorganization


## 4.5.3
2025-10-28
- Bug fix: Key input was ignored

## 4.5.2
10-12-2025
- Code Fix:
  - Changed the class that triggers LastClientTickEvent.
  - Performance Improvement: Changed the collection of config category objects.
  - Removed logs related to mod activation options.
  - Changed the way key input detection function is called.
- Bug Fix:
  - Typo Fix: Fixed a typo in the Korean translation of Zombies Utils.

## 4.5.1
10-05-2025
- Added
  - Korean Patcher: Korean translation for Zombies Utils.
- 수정
  - Client-only Mod: Fixed the mod being loaded on servers.
- Code Fix: Fixed translation keys.

## 4.5.0
09-30-2025
- Change:
  - Update Checker
    - Fixed translated strings.
    - Changed terms
      - Recommended Version -> Latest Version
      - Latest Version -> Development Version
    - Changed version URL.
    - Changed version comparison method.
    - Changed output messages.
- Fix
  - mcmod.info: Fixed description and credits.

## 4.5.0-rc2
- Auto Rejoin fix: Changed the default keybinding. Performance improvements.
- Fix: Disabled "Not Last" when Auto Rejoin is enabled.
- Fixed update checker: Fixed log. Added comments.

## 4.5.0-rc1
- Auto Splits fix: Added options to the /autosplits command. Fixed message.

## 4.5.0-pre2
- Fix: More accurate timers and Auto Splits. Config defaults. Auto SLA disables SLA at game end.
- Bug fix: ZSV's hotkeys worked in reverse.
- Code fix: Changed classes that inherit from modules to singleton objects.

## 4.5.0-pre1
- Bug fix: Incorrect prefix.
- Added log.

## 4.5.0-beta7
- Bug fix: Powerup Patterns' automatic timer no longer works. Hotkeys entered during the game's start are now detected.
- Powerup Patterns fix: The detection range for powerup has been adjusted.

## 4.5.0-beta6
- Bug fixes: Last Weapons fixed.
- Powerup Patterns fixed: Added an auto-timer key.

## 4.5.0-beta5
- Bug fix: Version does not change.

## 4.5.0-beta4
- Bug fix: Incorrect game remove timing.

## 4.5.0-beta3
- Bug fix: The game no longer ends in Prison. If the mode is activated during the game, it will start from round 1.

## 4.5.0-beta2
- Bug fix: The game wouldn't end.
- Change: The timer now stops when the game ends.

## 4.5.0-beta1
- Internal Timer fix: More accurate timer.
- Bug fixes: Added null check. Translation fixes. Log messages are not translated.
- Powerup Patterns Fixed: Patterns are now recorded separately for each game.
- Added: /za_debug command and debug mode. Added Korean language option to Zombies Utils.
- Player Visibility Fix: Fixed Player Invisibility from conflicting with SST's Player Invisible.
- Changed: Aligned game handling with Zombies Utils.


## 4.4.4
- Bug fix: Saved location information not being loaded.

## 4.4.3
- Bug fix: Module on/off not applied
- Code fix: Warning suppression

## 4.4.2
- Bug fix: Config wouldn't open with incorrect Mixin.

## 4.4.1
- Bugfix: Wrong boss color in Wave Delays.
- Gui fix: Changed the game exit name in the Gui to the original translated string.

## 4.4.0
- Bugfix: Empty slots in skills are not displayed in Last Weapons.

## 4.4.0-rc1
- ZSV Fix: Fixed the game not freezing when loading text.
- Bugfix: Fixed a bug where if loading a version fails, it would fall back to the wrong version.

## 4.4.0-pre4
- Bugfix: Some strings would crash when changing languages.
- Code fix: Replaced some try statements with runCatching functions.

## 4.4.0-pre3
- Fixed a bug: Missing boss colors in Wave Delays.
- Replaces "playWave", the old usage with "playType" in "CustomPlaySound.json" when starting the game.

## 4.4.0-pre2
- Fixed Wave Delays: Fixed incorrect order of prefixes.

## 4.4.0-pre1
- Changed Java to Kotlin.
- Fixed bugs.
- Code reorganization.
- Fixed config: Translated option names and descriptions.
- Fixed Detect Unlegit Mods: Apprise it with GUI., Added a feature that automatic deletion the mods.(Windows only)
- Fixed Update Checker: Apprise recommended version and required updates with GUI, Added auto update.(Windows only)
- Changed version system.
- Fixed Player Visibility: Can be set semi-transparent range, It works only ingame.
- Fixed Block Alarm: Does not work when not alive, Change the default key to P.
- Fixed Auto Splits: Changed to for LiveSplits only.
- Added Internal Timer: Same functionality as the existing Auto Splits.
- Fixed Wave Delays: Fixed prefixes for Giant and The Old One.
- Fixed ZSV: Korean language support.
- Fixed SLA: Added option to remove window name and text color.


## 4.3.2
- Reduces excessive allocations

## 4.3.1
- Fixed bugs.

## 4.3.0
- No changes.

## 4.3.0-pre3
- Fixed bugs.
- Added /de, /bb, /aa, and /pr commands.
- Fixed Last Weapons.

## 4.3.0-pre2
- Fixed bugs.
- Fixed Wave Delays.
- Fixed Powerup Patterns.

## 4.3.0-pre1
- Fixed bugs.
- Added commands.
- Added Language option in config.


## 4.2.3
- Fixed bugs.

## 4.2.2
- Fixed bugs.
- Added /zombiesaddon command.
- Removed /info command.

## 4.2.1
- Fixed bugs.

## 4.2.0
- Fixed bugs.

## 4.2.0-pre6
- Fixed bugs.
- Fixed Last Weapons.

## 4.2.0-pre5
- Fixed bugs.

## 4.2.0-pre4
- Fixed bugs.
- Added /waveDelays and /autoSplits commands.
- Blocks the unlegit feature of SST.

## 4.2.0-pre3
- Fixed bugs.
- Multiple languages are not supported.(Only English or Korean.)

## 4.2.0-pre2
- Fixed bugs.

## 4.2.0-pre1
- Code optimization.
- Fixed bugs.
- Renamed Cornering to PV.
- Fixed PV.
- Added Manual Timer for Powerup Patterns.
- Fixed SLA.
- Added /ZAHUD command.
- Fixed Wave Delays.
- Added SST Setting and Zombies Utils Setting.
- Supported multiple languages.


## 4.1.3
- Fixed bugs.


## 4.1.2
- Fixed bugs.


## 4.1.1
- Fixed bugs.
- Fixed Auto Rejoin.


## 4.1.0
- Fixed Last Weapons.

## 4.1.0-pre6
- Fixed bugs.
- Fixed Powerup Patterns.

## 4.1.0-pre5
- Fixed Last Weapons.
- Fixed bugs.

## 4.1.0-pre4
- Fixed a bug.

## 4.1.0-pre3
- Added Text Macro.
- Code optimization.
- Fixed Last Weapons.

## 4.1.0-pre2
- Fixed bugs.

## 4.1.0-pre1
- Fixed bugs.
- Fixed Powerup Alarm.
- Added SST Patch.
- Added Korean Patch.
- Added Last Weapons.
- Added Detect Unlegit Mods.


## 4.0.1
- Fixed bugs.
- Fixed SLA.
- Fixed Powerup Alarm.
- Fixed Wave Delays.


## 4.0.0
- Removed DPS Counter.
- Removed Player Tracker.
- Removed Boss Tracker.
- Removed Gstep Guide.
- Fixed Powerup Alarm.


## 3.1.0-pre8
- Fixed bugs.
- Fixed Wave Delays.
- Fixed Boss Tracker.
- Fixed Player Tracker.
- Removed Grow ESP.
- Removed Grow Guide.
- Removed Boss Spawn Tracker.

## 3.1.0-pre7
- Fixed bugs.
- Fixed Wave Delays.

## 3.1.0-pre6
- Fixed bugs.
- Fixed Wave Delays.
- Fixed SLA.

## 3.1.0-pre5
- Fixed bugs.

## 3.1.0-pre4
- Fixed bugs.

## 3.1.0-pre3
- Fixed bugs.
- Fixed SLA.

## 3.1.0-pre2
- Fixed bugs.

## 3.1.0-pre1
- Added Prison at Wave Delays.
- Boss Wave Alarm, SLA, Boss Spawn Tracker, Boss Tracker is not work in Prison.


## 3.0.0
- Fixed bug.
- Fixed a bug that Boss Tracker did display non-boss mobs.


## 2.0.0
- Fixed bug.
- Renamed Boss Alarm.
- Fixed a bug that some functions did not work when the nickname contained "_".


## 1.17.2-pre1
- Fixed bug.
- Added Player Tracker.
- Added Boss Tracker.

## 1.17.1
- Fixed bugs.

## 1.17.0
- Fixed bugs.

## 1.17.0-pre4
- Fixed bugs.
- Added config option.
- Removed /updatelog command.

## 1.17.0-pre3
- Fixed bugs.

## 1.17.0-pre2
- Fixed bugs.

## 1.17.0-pre1
- Fixed bugs.
- Fixed Auto Splits.
- Renamed NOTLAST.
- Fixed config.
- Fixed Wave Delays.
- Fixed Not Last.


## 1.16.0
- Fixed bugs.
- Renamed Extra Setting.

## 1.16.0-pre2
- Fixed bugs.

## 1.16.0-pre1
- Fixed bugs.
- Removed Lrod Order.
- Added RL-mode for Auto Splits.
- Fixed Update Checker.
- Added Extra Setting.
- Fixed Wave Delays.
- Removed config for Powerup Alarm.


## 1.15.1
- Fixed bugs.
- Removed Countdown.

## 1.15.0
- Fixed bugs.
- Added Play Sound for Auto Splits.


## 1.14.0
- Fixed bugs.
- Timer of Zombies Utils is off when use with Zombies Utils.
- Removed Health Indicator for Block Alarm.

## 1.14.0-pre4
- Fixed bugs.

## 1.14.0-pre3
- Fixed bugs.

## 1.14.0-pre2
- Fixed bugs.
- Fixed Gstep Guide.

## 1.14.0-pre1
- Added Gstep Guide.
- Fixed DPS Counter, NOTLAST, Auto Splits, and Powerup Alarm.
- Fixed bugs.


## 1.13.0
- Fixed bugs.
- Added Grow ESP.
- Wave delay of SST is off when use with SST.


## 1.12.3
- Fixed bugs.
- Fixed update checker

## 1.12.2
- Fixed update checker.
- Fixed /info command.

## 1.12.1
- Removed Hologram Remover.
- Added update checker.

## 1.12.0
- Added Grow Guide for Auto Splits.
- Fixed bugs.
- Fixed config.
- Added mod version on the display.
- Config reset when mod update.
- Added option which default value of mod be set.


## 1.11.0
- Added Health Indicator for Block Alarm.
- Rename Hologram Bug Generator to Hologram Remover.
- Removed rev and dead for Block Alarm.


## 1.10.3
- Fixed bugs.

## 1.10.2
- Added Hologram Bug Generator.

## 1.10.1
- Fixed bugs.

## 1.10.0
- Changed package.
- Code Reorganization.
- Added Lrod Order.
- Added Advanced SLA.
- Fixed commands.


## 1.9.2
- Fixed bugs.

## 1.9.1
- Fixed bugs.

## 1.9.0
- Fixed bugs.
- Added Boss Alarm.
- Fixed forge command bug.


## 1.8.9
- Fixed bugs.

## 1.8.8
- Fixed bugs.
- Added Boss Wave Alarm for Auto Splits.

## 1.8.7
- Fixed bugs.

## 1.8.6
- Fixed bugs.
- Removed /zombies command.
- Added config for Zombies Overlay.
- Fixed Powerup Alarm.

## 1.8.5
- Fixed bugs.
- Fixed CommandInfo.
- Added /zombies Command for Zombies Overlay.

## 1.8.4
- Fixed bugs.
- Fixed Auto Splits.

## 1.8.3
- Fixed bugs.

## 1.8.2
- Fixed bugs.

## 1.8.1
- Fixed Powerup Alarm.
- Fixed bugs.
- Added config for SLA.

## 1.8.0
- Added Powerup Alarm.
- Fixed bugs.
- Added config for Zombies Addon.
- Fixed Auto Splits.


## 1.7.6
- Fixed bug.

## 1.7.5
- Fixed bug.

## 1.7.4
- Fixed bug.

## 1.7.3
- Fixed bug.

## 1.7.2
- Fixed bug.

## 1.7.1
- Fixed bug.

## 1.7.0
- Fixed Auto Splits.
- Fixed /ZSV and /SLA command.
- Fixed Auto Rejoin.
- Added config for Auto Splits.

## 1.6.5
- Fixed bug.

## 1.6.4
- Fixed bug.

## 1.6.3
- Fixed bug.

## 1.6.2
- Fixed NOTLAST and Auto Rejoin.
- Fixed bug.

## 1.6.1
- Fixed Block Alarm.

## 1.6.0
- Remake /setMap, /setstrat, and /setlines commands name to /SLA, /ZSV, and /ZSVLines.
- Remove /blockAlarm command.
- Added /cornering command.
- Added config for Cornering and Block Alarm.


## 1.5.2
- Fixed bug.

## 1.5.1
- Fixed bug.

## 1.5.0
- Added Auto Rejoin.
- Fixed bug.
- Fixed Auto Splits.


## 1.4.5
- Fixed Block Alarm.

## 1.4.4
- Fixed Block Alarm.

## 1.4.3
- Fixed bug.
- You can use Block Alarm in Korean.

## 1.4.2
- Fixed bug.
- Fixed Block Alarm.
- Added /blockAlarm command.

## 1.4.1
- Fixed mod's message.

## 1.4.0
- Added ZSV and SLA.


## 1.3.0
- Added Auto Splits.


## 1.2.1
- Added /updatelog command.
- Fixed /info command.

## 1.2.0
- Added NOTLAST.
- Fixed /info command.


## 1.1.0
- Removed ZHF.


## 1.0.0
- Added ZHF, Cornering, Block Alarm, and DPS Counter.
- Fixed Block Alarm.