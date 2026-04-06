# VALOR: Legends, Monsters, and Heroes Adaptation

CS 611 pair assignment #5

### Running the program

1. clone the repo
2. cd to the correct directory
3. CMD + SHIFT + P then Java: Configure Classpath and set project directory as a source for correct package recognition
4. in the terminal run `./run.sh`

### Steps to convert to legends of valor

- [x] Set up the new board
  - [x] change printing
  - [x] change the space types e.g. market to nexus
  - [x] add new space types: cave and obstacle
- [x] change monster spawning and make sure battles work properly
  - [x] monsters spawn on
  - [x] monsters move after hero moves
- [ ] update other rules and do more stuff
  - [x] user wins when one hero reaches monster nexus, user loses when one monster reaches hero nexus
  - [ ] ensure obstacles don't block off the path (can avoid pathfinding by just ensuring obstacles are not next to or diagonal to each other)
  - [ ] maybe allow monsters to move side to side to block hero from passing

### Design choices

- Added interfaces for behaviors such as Consumable, Copyable, AttackWith
  - made equippable an abstract class because it handled so much state that would be reused
- Created factories that create new instances of objects that implement the Copyable interface so that marketplaces, battles, and heroes could be correctly initiated
- Used generics in Copyable, factories, and the IO class to increase reusability of code
- Used abstract classes and inheritance for similar families of objects such as Fighters, Heros, Monsters, and all their concrete subtypes as well as Items
- created classes such as Inventory and Stats to ensure single responsibility of each class
