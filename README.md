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
