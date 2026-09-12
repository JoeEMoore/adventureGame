# Game Design Principles — Context for AI-Assisted Ideation

Distilled from: Hunicke/LeBlanc/Zubek's MDA framework, Raph Koster's
"Game design is simple, actually" (2025) and his broader Theory of Fun
work, and Matthew VanDevander's game design essays (Low Tide
Productions). Condensed for use as Cursor context when brainstorming
features for a dungeon-crawler roguelike.

---

## 1. What "fun" actually is

Fun is best understood as the satisfaction of making progress on
prediction — recognizing a pattern, testing a hypothesis about how a
system works, and getting confirmation. Anything that isn't really a
form of problem-solving usually isn't going to carry the weight of a
game's core system design, even if it's valuable to the overall
experience (music, art, story).

Practical implication: every mechanic you add should be askable as
"what is the player learning to predict here?" If a system doesn't
let the player form and test a hypothesis, it's decoration, not a
game system.

## 2. Problems vs. toys

A **toy** is a set of rules and interactions with no goal attached —
something you play *with*. A **problem** or **game** is a toy plus a
goal that turns exploration into a test. Building a good toy first
(fun to fiddle with, no stakes) and then attaching goals/stakes to it
is a reliable design order — often easier than designing the goal
structure first and hoping the underlying system is enjoyable.

Applied to a dungeon crawler: does moving through a room, swinging a
weapon, or browsing the shop feel like a toy on its own — satisfying
to interact with even before points/goals are layered on? If a
system only "works" because of the win/lose stakes around it, it's
worth separately testing whether the interaction itself has any
inherent appeal.

## 3. Good problems have depth, uncertainty, and generality

A well-designed core problem:
- **Evolves as you dig into it** — your first working strategy should
  stop working eventually, forcing new strategies (not just harder
  numbers).
- **Has genuinely uncertain outcomes** — not a single deterministic
  answer (that's a puzzle, which gets "solved" and then discarded).
- **Recurs across many different situations** — the same underlying
  mechanic shows up in different dressing/context repeatedly.

A shallow problem is one with a single dominant strategy that, once
found, is repeated forever without variation.

## 4. Two kinds of loop

- **Operational loop**: the moment-to-moment interaction — look,
  form a hypothesis, act, get a result, update the hypothesis. This
  is "how you turn the wheel."
- **Progression loop (a spiral, not a circle)**: the same verb
  repeated across *varying situations* that escalate over time. It's
  a spiral because the situation changes even though the action
  doesn't — you're not solving the identical problem twice, you're
  applying the same tool to a harder version of it.

A weak core loop repeats the *exact same* situation over and over
(no escalation). A strong core loop keeps the verb constant but
continuously varies the *situation* that verb is applied to.

## 5. Feedback has four required parts

For any player action, the game must communicate:
1. What actions (verbs) are even available.
2. That the verb was used (confirmation of input).
3. How the state of the problem changed as a result.
4. Whether that change was good or bad relative to the player's goal.

Missing any of these four breaks the learning loop — the player
can't reliably update their strategy. Great feedback can also be
where a lot of the "juice" and delight of a game lives (animation,
sound, camera shake) — but juice without an underlying real problem
is just decoration, and can even be actively deceptive if it implies
depth the mechanic doesn't have.

## 6. Escalation and variation over raw difficulty

The move from "picking up a stick" to *Snake* to *Pac-Man* is about
adding obstacles and *situation variety* to the same core loop, not
about making the same problem numerically harder. Good escalation:
- introduces new situations the existing strategy doesn't trivially
  solve
- eventually invalidates early "good enough" strategies, forcing
  players up a learning ladder
- can come from adding randomness/variation to keep a mastered
  system from becoming fully solved (see: Pac-Man → Ms. Pac-Man)

## 7. Pacing follows a rising, wave-like curve

Good pacing isn't flat difficulty or flat intensity — it's a
repeating rise-peak-release-plateau pattern (tension, then a boss/
peak, then a breather that doesn't fully reset, then rising again).
Players need room to *practice* at a level just past their current
skill (not too easy = boredom/grinding without new strategies; not
too hard = can't even perceive the problem). Peaks conventionally
map to boss encounters; breathers map to shops, safe rooms, or
narrative beats.

## 8. Games are built from composed sub-games

Almost no game is a single loop — they're loops chained together,
where the output of one loop becomes an input/constraint on another
(a "value chain"), or webbed together into an economy (stocks and
flows — hit points as a spendable "currency" against a poison
debuff, gold as a currency spent in a shop, etc.). Decomposing your
game into its elemental small problems, each with its own
interaction loop and learning curve, makes it much easier to reason
about which part is weak.

## 9. Mechanics vs. dressing (the MDA framework)

Formal breakdown from the MDA paper:
- **Mechanics** — the actual rules, data, and algorithms (weapons,
  ammo, dice, spawn rules).
- **Dynamics** — the run-time behavior that emerges from mechanics
  interacting with player input over time (bluffing emerges from
  card-game mechanics; camping/sniping emerges from shooter
  mechanics).
- **Aesthetics** — the emotional response the player actually has
  (challenge, fellowship, discovery, fantasy, narrative, sensation,
  expression, submission — a game usually leans on several of these
  at once, in different proportions).

Design flows Mechanics → Dynamics → Aesthetics from the designer's
side; the player experiences it in reverse (Aesthetics first, then
notices Dynamics, then eventually understands Mechanics). Useful
design move: pick the *aesthetic* goal first (what should this boss
fight feel like?), then reason backward to what dynamics produce
that feeling, then to what mechanics produce those dynamics — rather
than starting from "what mechanic would be cool" and hoping a good
feeling falls out.

The same underlying mechanic can be "dressed" completely
differently (a resource-depletion problem can be a gas tank, a
poison-damage-over-time status effect, or a countdown timer) — the
dressing changes how the player perceives and learns the problem
even when the math underneath is identical.

## 10. Loot, collectibles, and "why is this here"

Items/collectibles should never exist purely "because you could."
Two failure modes to avoid:
- **Collectibles with no gameplay purpose**, scattered to reward
  obsessive exploration rather than to teach or gate anything — these
  read as filler and can push players toward a resentful, obligation-
  driven "correct" way to play rather than genuine exploration.
- **Dead ends with literally nothing in them** at all, which reads
  as a broken promise to the player if the game has otherwise trained
  them to expect rewards behind exploration.

The better standard: loot/gold/items should set up a *challenge or
a choice* (a tougher room implies better loot; a shop creates a
resource-allocation decision) rather than being a pure completionist
checkbox. If you can't articulate what a piece of loot is teaching
or gating, it's a good candidate to cut or attach to something more
meaningful.

## 11. Cohesion between mechanics and "story"/theming

Where a game's difficulty, verbs, and framing pull in different
directions from its narrative or thematic dressing, that mismatch is
felt by players even when they can't name it precisely. Practical
check for a dungeon crawler: does the framing of a room/enemy/reward
match what the mechanics actually demand of the player in that
moment? (A "safe zone" that's still mechanically dangerous, or a
"scary" enemy that mechanically plays as a pushover, both undercut
the intended feeling.)

## 12. Player motivation is a filter, not a universal target

Not every player wants the same category of problem — some enjoy
problems about defeating opponents, others about managing resources,
others about social/cooperative dynamics. Knowing which
problem-categories your game leans into (in a solo dungeon crawler:
likely combat mastery + resource/build management + risk-reward
exploration) helps you prioritize which systems deserve the most
design attention, rather than trying to be everything to everyone.

---

## Quick-reference checklist for feature ideas

When evaluating a new feature/mechanic idea, ask:
- [ ] What is the player predicting/learning here, specifically?
- [ ] Is this a toy that's satisfying on its own, or does it only
      "work" because of the stakes around it?
- [ ] Does the difficulty/interest come from *new situations*, or
      just bigger numbers?
- [ ] Does the feedback loop cover all four parts (available verbs →
      confirmation → state change → good/bad signal)?
- [ ] Where does this sit on the tension curve — is it a peak, a
      breather, or a ramp?
- [ ] Can I state what aesthetic goal (challenge, discovery,
      fellowship, etc.) this mechanic is actually serving?
- [ ] If this is loot/reward, what choice or challenge does it set
      up — or is it just a checkbox?
