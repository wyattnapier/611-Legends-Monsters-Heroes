# VALOR: Legends, Monsters, and Heroes Adaptation

CS 611 pair assignment #5

### Running the program

1. clone the repo
2. cd to the correct directory
3. CMD + SHIFT + P then Java: Configure Classpath and set project directory as a source for correct package recognition
4. in the terminal run `./run.sh`

### Division of work

Together we created a list of to-do items that we put in the readme as a checklist items. We discussed our overall outline for how to approach the problem, and then periodically checked in to assign new tasks based on highest priority and clarify any confusion. This way were able to independently handle our own tasks, but also remain on the same page and ensure that we split the work evenly.

### Design choices

- Both Wyatt and Arion had very similar overall implementations, but we decided to use Wyatt's because it had more substantial organization and employed the factory pattern. We did alter Wyatt's Board and Fighter's based on Arion's implementation so that the Fighters now hold their own board location as a private instance variable
- We were able to reuse a lot of spaces and then for the new spaces implemented a new interface for applying a buff at a space so besides changing the battle dynamics, updating our implementation for this assignment was fairly straightforward
- updated UML is saved as a pdf in this repo

##### Design choices from previous part

- Added interfaces for behaviors such as Consumable, Copyable, AttackWith
  - made equippable an abstract class because it handled so much state that would be reused
- Created factories that create new instances of objects that implement the Copyable interface so that marketplaces, battles, and heroes could be correctly initiated
- Used generics in Copyable, factories, and the IO class to increase reusability of code
- Used abstract classes and inheritance for similar families of objects such as Fighters, Heros, Monsters, and all their concrete subtypes as well as Items
- created classes such as Inventory and Stats to ensure single responsibility of each class

### Basic sample run (without colors)

```
./run.sh
Compiling...
Running...

Enter your name: wyatt
Hi wyatt, welcome to my Legends - Valor game. Each hero must defend their nexus and attempt to reach the opposing monster's nexus. Use your nexus as a marketplace. Good luck and have fun!

Pick your hero type to fight in lane 1!
[s] Sorcerer (favored on dexterity and agility)
[p] Paladin (favored on strength and dexterity)
[w] Warrior (favored on strength and agility)
Enter your choice --> s
Sorcerer Reign_Havoc has joined the party to defend lane 1!

Pick your hero type to fight in lane 2!
[s] Sorcerer (favored on dexterity and agility)
[p] Paladin (favored on strength and dexterity)
[w] Warrior (favored on strength and agility)
Enter your choice --> p
Paladin Garl_Glittergold has joined the party to defend lane 2!

Pick your hero type to fight in lane 3!
[s] Sorcerer (favored on dexterity and agility)
[p] Paladin (favored on strength and dexterity)
[w] Warrior (favored on strength and agility)
Enter your choice --> w
Warrior Flandal_Steelskin has joined the party to defend lane 3!

Monster respawn rate (extra wave at enemy nexus after this many full rounds):
[e] easy — every 6 rounds
[m] medium — every 4 rounds
[h] hard — every 2 rounds
Choice --> e
monsters spawned at the enemy nexus (top row).


+---+---+---+---+---+---+---+---+
| N | M | I | N | M | I | N | M |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| P | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
|H1 | N | I |H2 | N | I |H3 | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Reign_Havoc — H1 in lane 1 (yellow on board)
HP: 200 | MP: 800 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
M - nexus shop (does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> w

Reign_Havoc entered a cave and gained a temporary 100 point boost to their agility!

+---+---+---+---+---+---+---+---+
| N | M | I | N | M | I | N | M |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| P | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I |H2 | N | I |H3 | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Garl_Glittergold — H2 in lane 2 (yellow on board)
HP: 200 | MP: 100 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
M - nexus shop (does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> t

Choose a lane to teleport to from the list to the right. The leftmost lane is lane 1. Options: [1/3]
Your choice: 1
Garl_Glittergold teleported to Reign_Havoc in lane 1!

+---+---+---+---+---+---+---+---+
| N | M | I | N | M | I | N | M |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| P | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
|H1 |H2 | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I |H3 | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Flandal_Steelskin — H3 in lane 3 (yellow on board)
HP: 200 | MP: 200 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
M - nexus shop (does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> w

*** monster phase (attack / move) ***

--- monster attack results ---
(no attacks, monsters moved or had no targets in range)

Heroes regenerated 10% HP and MP

+---+---+---+---+---+---+---+---+
| M | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | M | I | P | M |
+---+---+---+---+---+---+---+---+
| P | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
|H1 |H2 | I | C | B | I |H3 | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Reign_Havoc — H1 in lane 1 (yellow on board)
HP: 220 | MP: 880 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> w

Reign_Havoc entered a bush and gained a temporary 100 point boost to their dexterity!

+---+---+---+---+---+---+---+---+
| M | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | M | I | P | M |
+---+---+---+---+---+---+---+---+
| P | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C |H2 | I | C | B | I |H3 | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Garl_Glittergold — H2 in lane 1 (yellow on board)
HP: 220 | MP: 110 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> w

Cannot move to that space. Try again!
+---+---+---+---+---+---+---+---+
| M | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | M | I | P | M |
+---+---+---+---+---+---+---+---+
| P | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C |H2 | I | C | B | I |H3 | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Garl_Glittergold — H2 in lane 1 (yellow on board)
HP: 220 | MP: 110 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> d

Cannot move to that space. Try again!
+---+---+---+---+---+---+---+---+
| M | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | M | I | P | M |
+---+---+---+---+---+---+---+---+
| P | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C |H2 | I | C | B | I |H3 | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Garl_Glittergold — H2 in lane 1 (yellow on board)
HP: 220 | MP: 110 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> s

+---+---+---+---+---+---+---+---+
| M | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | M | I | P | M |
+---+---+---+---+---+---+---+---+
| P | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | B | I |H3 | K |
+---+---+---+---+---+---+---+---+
| N |H2 | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Flandal_Steelskin — H3 in lane 3 (yellow on board)
HP: 220 | MP: 220 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> d

Flandal_Steelskin entered a koulou and gained a temporary 100 point boost to their strength!

*** monster phase (attack / move) ***

--- monster attack results ---
(no attacks, monsters moved or had no targets in range)

Heroes regenerated 10% HP and MP

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| M | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| P | P | I | B | M | I | C | M |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | B | I | P |H3 |
+---+---+---+---+---+---+---+---+
| N |H2 | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Reign_Havoc — H1 in lane 1 (yellow on board)
HP: 242 | MP: 968 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> w

Reign_Havoc entered a bush and gained a temporary 100 point boost to their dexterity!

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| M | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| P | P | I | B | M | I | C | M |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | B | I | P |H3 |
+---+---+---+---+---+---+---+---+
| N |H2 | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Garl_Glittergold — H2 in lane 1 (yellow on board)
HP: 242 | MP: 121 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
M - nexus shop (does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> w

Garl_Glittergold entered a koulou and gained a temporary 100 point boost to their strength!

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| M | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| P | P | I | B | M | I | C | M |
+---+---+---+---+---+---+---+---+
| K | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C |H2 | I | C | B | I | P |H3 |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Flandal_Steelskin — H3 in lane 3 (yellow on board)
HP: 242 | MP: 242 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> w

*** monster phase (attack / move) ***

--- monster attack results ---
(no attacks, monsters moved or had no targets in range)

Heroes regenerated 10% HP and MP

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| M | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
| K | K | I | C | M | I | C | M |
+---+---+---+---+---+---+---+---+
|H1 | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O |H3 |
+---+---+---+---+---+---+---+---+
| C |H2 | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Reign_Havoc — H1 in lane 1 (yellow on board)
HP: 266 | MP: 1064 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> w

Reign_Havoc entered a koulou and gained a temporary 100 point boost to their strength!

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| M | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | M | I | C | M |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O |H3 |
+---+---+---+---+---+---+---+---+
| C |H2 | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Garl_Glittergold — H2 in lane 1 (yellow on board)
HP: 266 | MP: 133 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> a

Garl_Glittergold entered a cave and gained a temporary 100 point boost to their agility!

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| M | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | M | I | C | M |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I | C | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O |H3 |
+---+---+---+---+---+---+---+---+
|H2 | K | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Flandal_Steelskin — H3 in lane 3 (yellow on board)
HP: 266 | MP: 266 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> w

*** monster phase (attack / move) ***

--- monster attack results ---
H1 Reign_Havoc dodged Monster Natsunomeryu's attack

Monster Casper attacked H3 Flandal_Steelskin for 100 damage

Heroes regenerated 10% HP and MP

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| M | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | K | I | C | M |
+---+---+---+---+---+---+---+---+
| B | C | I | B | M | I | C |H3 |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
|H2 | K | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Reign_Havoc — H1 in lane 1 (yellow on board)
HP: 292 | MP: 1170 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> f

monsters in range:
(0) Natsunomeryu — hp 100

damage roll (before dodge/defense): 45 — uses strength with tile buffs + equipped weapon

Reign_Havoc used a hands to do 40 damage to Natsunomeryu
Natsunomeryu has 60 hp left.

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| M | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | K | I | C | M |
+---+---+---+---+---+---+---+---+
| B | C | I | B | M | I | C |H3 |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
|H2 | K | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Garl_Glittergold — H2 in lane 1 (yellow on board)
HP: 292 | MP: 146 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> t

Choose a lane to teleport to from the list to the right. The leftmost lane is lane 1. Options: [2/3]
Your choice: 3
Garl_Glittergold teleported to Flandal_Steelskin in lane 3!

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| M | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | K | I | C | M |
+---+---+---+---+---+---+---+---+
| B | C | I | B | M | I |H2 |H3 |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Flandal_Steelskin — H3 in lane 3 (yellow on board)
HP: 182 | MP: 292 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> f

monsters in range:
(0) Casper — hp 100

damage roll (before dodge/defense): 38 — uses strength with tile buffs + equipped weapon

Flandal_Steelskin used a hands to do 36 damage to Casper
Casper has 64 hp left.

*** monster phase (attack / move) ***

--- monster attack results ---
H1 Reign_Havoc dodged Monster Natsunomeryu's attack

Monster Casper attacked H3 Flandal_Steelskin for 100 damage

Heroes regenerated 10% HP and MP

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| M | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | K | I | C | M |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I |H2 |H3 |
+---+---+---+---+---+---+---+---+
| B | O | I | P | M | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Reign_Havoc — H1 in lane 1 (yellow on board)
HP: 321 | MP: 1287 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> f

monsters in range:
(0) Natsunomeryu — hp 60

damage roll (before dodge/defense): 45 — uses strength with tile buffs + equipped weapon

Reign_Havoc used a hands to do 40 damage to Natsunomeryu
Natsunomeryu has 20 hp left.

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| M | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | K | I | C | M |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I |H2 |H3 |
+---+---+---+---+---+---+---+---+
| B | O | I | P | M | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Garl_Glittergold — H2 in lane 3 (yellow on board)
HP: 321 | MP: 160 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> f

monsters in range:
(0) Casper — hp 64

damage roll (before dodge/defense): 25 — uses strength with tile buffs + equipped weapon

Garl_Glittergold used a hands to do 23 damage to Casper
Casper has 41 hp left.

+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | P | I | P | P |
+---+---+---+---+---+---+---+---+
| M | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | K | I | C | M |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I |H2 |H3 |
+---+---+---+---+---+---+---+---+
| B | O | I | P | M | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | B | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Flandal_Steelskin — H3 in lane 3 (yellow on board)
HP: 90 | MP: 321 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> f

monsters in range:
(0) Casper — hp 41

damage roll (before dodge/defense): 38 — uses strength with tile buffs + equipped weapon

Flandal_Steelskin used a hands to do 36 damage to Casper
Casper has 5 hp left.

another monster wave reached the enemy nexus.

*** monster phase (attack / move) ***

--- monster attack results ---
Monster Natsunomeryu attacked H1 Reign_Havoc for 100 damage

Monster Casper attacked H3 Flandal_Steelskin for 100 damage -> H3 Flandal_Steelskin was defeated -> H3 Flandal_Steelskin died and respawned at their nexus

Heroes regenerated 10% HP and MP

+---+---+---+---+---+---+---+---+
| M | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | M | I | P | M |
+---+---+---+---+---+---+---+---+
| M | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | K | I | C | M |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I |H2 | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | M | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I |H3 | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Reign_Havoc — H1 in lane 1 (yellow on board)
HP: 243 | MP: 1415 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> f

monsters in range:
(0) Natsunomeryu — hp 20

damage roll (before dodge/defense): 45 — uses strength with tile buffs + equipped weapon

Reign_Havoc used a hands to do 40 damage to Natsunomeryu
Natsunomeryu was defeated!

Reign_Havoc gained 500 gold and 2 experience.

+---+---+---+---+---+---+---+---+
| M | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | M | I | P | M |
+---+---+---+---+---+---+---+---+
| P | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | K | I | C | M |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I |H2 | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | M | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I |H3 | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Garl_Glittergold — H2 in lane 3 (yellow on board)
HP: 353 | MP: 176 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> f

monsters in range:
(0) Casper — hp 5

damage roll (before dodge/defense): 25 — uses strength with tile buffs + equipped weapon

Garl_Glittergold used a hands to do 23 damage to Casper
Casper was defeated!

Garl_Glittergold gained 500 gold and 2 experience.

+---+---+---+---+---+---+---+---+
| M | N | I | N | N | I | N | N |
+---+---+---+---+---+---+---+---+
| C | O | I | C | M | I | P | M |
+---+---+---+---+---+---+---+---+
| P | P | I | B | K | I | C | P |
+---+---+---+---+---+---+---+---+
|H1 | K | I | C | K | I | C | P |
+---+---+---+---+---+---+---+---+
| B | C | I | B | P | I |H2 | P |
+---+---+---+---+---+---+---+---+
| B | O | I | P | P | I | O | P |
+---+---+---+---+---+---+---+---+
| C | K | I | C | M | I | P | K |
+---+---+---+---+---+---+---+---+
| N | N | I | N | N | I |H3 | N |
+---+---+---+---+---+---+---+---+

turn (round-robin): Flandal_Steelskin — H3 in lane 3 (yellow on board)
HP: 110 | MP: 220 | Gold: 2500

Please input your next move:
W/A/S/D - move
T - teleport to another lane
O - remove adjacent obstacle
F - attack a monster in range (same lane, up to 1 tile away)
C - cast a spell on a monster in range (same range as attack)
U - use a potion from this hero's inventory (ends turn)
I - manage inventory (view info, equip/unequip; does not end turn)
M - nexus shop (does not end turn)
R - recall hero to nexus
Q - quit game
H - help/information
Your move --> w

*** monster phase (attack / move) ***

--- monster attack results ---
(no attacks, monsters moved or had no targets in range)

Heroes regenerated 10% HP and MP

defeat — monsters reached your nexus.
Ending the game...
```
