The League of Warriors is an adventure game implemented in Java using Object-Oriented Programming (OOP) principles. The game is structured as a grid-based map where the player's character can interact with various types of cells such as enemies, sanctuaries, portals, and empty cells. The game includes character evolution, combat, and exploration mechanics, and utilizes several design patterns, including Singleton, Builder, Factory, and Visitor.

Key Features:
- Character selection: The player can choose from multiple characters with unique attributes and abilities.
- Grid-based world: The game world is represented as a matrix, and characters can move through it to engage with various events.
- Combat system: The player can fight enemies, using normal attacks or special abilities that cost mana.
- Level progression: Characters gain experience points and level up, improving their attributes like strength, dexterity, and charisma.
- Game events: Events include battling enemies, recovering health and mana in sanctuaries, and progressing through levels via portals.
- Graphical user interface (GUI): The game features a GUI using Swing, including login screens, gameplay views, and a final page displaying progress.

Design Patterns Used:
- Singleton: Ensures a single instance of the Game class to manage the game state globally.
- Builder: Facilitates the step-by-step creation of complex objects, used for building Information objects.
- Factory: Used to instantiate different characters based on the player's account selection.
- Visitor: Allows abilities to interact with entities in different ways based on their types (e.g., different effects depending on the entity's immunities or abilities).
