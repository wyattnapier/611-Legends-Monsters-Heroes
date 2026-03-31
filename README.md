# Legends, Monsters, and Heroes

CS 611 individual assignment #4

### Running the program

1. clone the repo
2. cd to the correct directory
3. in the terminal run `./run.sh`

### Design choices

- Added interfaces for behaviors such as Consumable, Copyable, AttackWith
  - made equippable an abstract class because it handled so much state that would be reused
- Created factories that create new instances of objects that implement the Copyable interface so that marketplaces, battles, and heroes could be correctly initiated
- Used generics in Copyable, factories, and the IO class to increase reusability of code
- Used abstract classes and inheritance for similar families of objects such as Fighters, Heros, Monsters, and all their concrete subtypes as well as Items
- created classes such as Inventory and Stats to ensure single responsibility of each class

### UML Diagram

(will drag and drop here from GitHub in browser since it is an SVG)
