# Questgiver

A wrapper for Starsector's (v0.9.1a) common quest-related classes.

Your mod must be written in Kotlin to use this library in anything approaching a reasonable manner.

## Contents

### Quest related

- `BarEventDefinition`
  - Wraps `BaseBarEventWithPerson`
- `IntelDefinition`
  - Wraps `BaseIntelPlugin`
- `TextExtensions`
  - Various extension methods to make working with text easier.

Docs at [docs/-questgiver/index.md](docs/-questgiver/index.md)

## Usage

Initialize once, as early as possible.

```kt
Questgiver.initialize("your_mod_prefix")
```

### Bar Event

```kotlin
class MidgameBarEventCreator : BaseBarEventCreator() {
    override fun createBarEvent() = MidgameQuestBeginning().buildBarEvent()
}

class MidgameQuestBeginning : BarEventDefinition<MidgameQuestBeginning>(
    shouldShowEvent = { /* boolean */ },
    interactionPrompt = {
        para {
            "There's a $manOrWoman at the ${mark("special")} bar." // "special" will be highlighted
        }
    },
    textToStartInteraction = {
        "Talk to the $manOrWoman."
      // or
        game.text["textKey"]
    },
    onInteractionStarted = {
        MyMod.initQuest()
    },
    pages = listOf(
        Page(
            id = Page.Initial,
            onPageShown = {
                para {
                    "You walk up and talk to them."
                }
                para { "They respond." }
            },
            // The options available to the player after reading.
            options = listOf(
                Option(
                    text = { "Respond back to them." },
                    onOptionSelected = {
                        val wasQuestSuccessfullyStarted = MyMod.startQuest()

                        if (wasQuestSuccessfullyStarted) {
                            it.goToPage(Page.Wander)
                        } else {
                            it.close(hideQuestOfferAfterClose = true)
                        }
                    }
                )
            )
        ),
        Page(
            id = Page.Wander,
            onPageShown = {
                para {
                    "They thank you and leave."
                }
            },
            options = listOf(
                Option(
                    text = { "Leave" },
                    onOptionSelected = {
                        it.close(hideQuestOfferAfterClose = true)
                    }
                )
            )
        )
    ),
    personRank = Ranks.SPACE_SAILOR
) {
    enum class Page {
        Initial,
        Wander
    }

    override fun createInstanceOfSelf() = MidgameQuestBeginning()
}
```

## Intel

```kotlin
class MidgameIntel(val planet: SectorEntityToken) : IntelDefinition(
      title = { "Planet investigation" + if (Midgame.wasQuestCompleted) " - Completed" else String.empty },
    iconPath = { "graphics/intel/myIcon.png" },
    infoCreator = {
        if (!isEnding) {
            it?.para("Investigate a planet in ${planet.starSystem.baseName}", 0f)
        }
    },
    smallDescriptionCreator = { info, width, _ ->
        info.addImage(di.settings.getSpriteName("illustrations", "dead_gate"), width, 10f)
        val stage1TextColor = if (Midgame.wasQuestCompleted) Misc.getGrayColor() else Misc.getTextColor()
        info.para(textColor = stage1TextColor) {
            "You saw a decoded transmission detailing Gate activation codes."
        }
        info.para(textColor = stage1TextColor) {
            "Perhaps it's worth a visit to ${mark(planet.name)} in ${mark(planet.starSystem.baseName)}."
        }

        if (Midgame.wasQuestCompleted) {
            info.para {
                "You visited a cave on ${mark(planet.name)}" +
                        " in ${mark(planet.starSystem.baseName)}" +
                        " and found activation codes for ${Midgame.midgameRewardActivationCodeCount} Gates."
            }
            info.para {
                "The TriPad from the cave may have one more secret - you keep an eye on it as you continue to use the Gate network."
            }
        }
    },
    showDaysSinceCreated = true,
    startLocation = null,
    endLocation = planet,
    intelTags = listOf(
        Tags.INTEL_EXPLORATION,
        Tags.INTEL_STORY
    )
) {

    override fun advance(amount: Float) {
        super.advance(amount)

        // If it's not already ending or ended and the quest was completed, mark the quest as complete
        if ((!isEnding || !isEnded) && Midgame.wasQuestCompleted) {
            endAfterDelay()
        }
    }

    override fun createInstanceOfSelf() = MidgameIntel(planet)
}
```
