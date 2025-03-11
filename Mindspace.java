import java.util.ArrayList;
import java.util.Scanner;

public class Mindspace {
    ArrayList<String> cavernItems = new ArrayList<String>();
    ArrayList<String> bedroomItems = new ArrayList<String>();
    double deterioration;
    boolean firstDemonEncounter;
    boolean markingsExamined;
    boolean dummyExamined;
    boolean antidoteFound;
    boolean robesExamined;
    boolean deathDiscovered;
    
    // The constructor for the class - initializes values
    public Mindspace() {
        deterioration = 0;
        firstDemonEncounter = true;
        markingsExamined = false;
        dummyExamined = false;
        antidoteFound = false;
        robesExamined = false;
        deathDiscovered = false;
        
        // Add items in the cavern
        cavernItems.add("sword");
        cavernItems.add("string");
        
        // Add items to bedroom
        bedroomItems.add("scroll");
        bedroomItems.add("journal");
    }
    
    // Retrieves the list of remaining items in the specified room
    public ArrayList<String> getItems(String room) {
        // Assumes no string other than "cavern" or "bedroom" will be entered
        if (room.equals("cavern")) {
            return cavernItems;
        }
        else {
            return bedroomItems;
        }
    }
    
    // Removes a certain item from a certain room - assumes that item exists in that room
    // Assumes the only strings entered for room are "cavern" or "bedroom"
    public void removeItem(String item, String room) {
        
        // First find the item
        int loc = 0;
        int size = 0;
        if (room.equals("cavern")) {
            for (int i = 0; i < cavernItems.size(); i++) {
                if (cavernItems.get(i).equals(item)) {
                    loc = i;
                }
            }
        }
        else {
            for (int i = 0; i < bedroomItems.size(); i++) {
                if (bedroomItems.get(i).equals(item)) {
                    loc = i;
                }
            }
        }
        
        // Remove the item
        if (room.equals("cavern")) {
            cavernItems.remove(loc);
        }
        else {
            bedroomItems.remove(loc);
        }
    }
    
    // Checks if a room contains an item - assumes only bedroom or cavern is entered
    public boolean hasItem(String item, String room) {
        
        // Find the right list
        if (room.equals("cavern")) {
            for (String thing : cavernItems) {
                if (thing.equals(item)) {
                    return true;
                }
            }
        }
        else {
            for (String thing : bedroomItems) {
                if (thing.equals(item)) {
                    return true;
                }
            }
        }

        return false;
    }
    
    
    // Retrieves the int value of the world deterioration
    public double getDeterioration() {
        return deterioration;
    }
    
    // Runs an end game sequence
    // Assumes there are only two possible deaths: health death and world death
    public void gameOver() {
        // Detect what kind of gameOver it is
        if (deterioration == 5) {
            System.out.println("\nTHE WORLD DETERIORATES.\nThe world can no longer sustain your presence, and it has crumbled. This \"mindspace\" twists and shatters, and with it, your consciousness disappears forever into the Abyss.");
        }
        else {  // Assumes this must be the case where player health = 0
            System.out.println("\nYOU DIED.\nYour consciousness disappears from the Abyss, never to exist again.");
        }
    }
    
    // Changes the world deterioration by n - can't go above 5 or below 0
    public void changeDeterioration(double n) {
        if (deterioration + n >= 5) {
            deterioration = 5;
        }
        else if (deterioration + n <= 0) {
            deterioration = 0;
        }
        else {
            deterioration += n;
        }
    }
    
    // Returns a boolean for whether the room has a demon or not
    public boolean hasDemon() {
        double n = Math.random();  // randomizes so there's a 50/50 chance of demon spawning
        if (n < 0.5) {
            return true;
        }
        else {
            return false;
        }
    }
    
    // Gives a basic description of the spawn room
    public void spawnDescription() {
        System.out.println("\nIt is a white room. The ceiling, floor, and walls are all white. In front of you, there is a mirror. There is a door on each of the left and right walls. There is a locked door behind you.");
    }
    
    // Gives a basic description of the cavern
    public void cavernDescription() {
        System.out.print("\nIt is a yawning, pitch-black cavern. Your eyes adjust, and you see marks of blades upon the rocky walls. Worn wooden dummies line the sides. ");
        if (hasItem("sword", "cavern")) {
            System.out.print("A sword leans against a training dummy. ");
        }
        if (hasItem("string", "cavern")) {
            System.out.print("The glint of a string catches your eye. ");
        }
        System.out.print("\n");
    }
    
    // Gives a basic description of the bedroom
    public void bedroomDescription() {
        System.out.print("\nIt is a bedroom, relatively undecorated and unfurnished. A simple wooden table sits in the corner, where a bed is located. Black robes are folded neatly on the bed. ");
        if (hasItem("scroll", "bedroom")) {
            System.out.print("The wooden pillow on the bed is tilted. ");
        }
        if (hasItem("journal", "bedroom")) {
            System.out.print("A paper-bound book sits on the desk.");
        }
        System.out.print("\n");
    }
    
    // Gives a basic description of the player - only runs if the player looks into the mirror
    public void playerDescription() {
        System.out.println("A young man with gray eyes and ink-black hair stares back at you. He is pale, like a ghost. Long gray robes are draped over his body.");
    }
    
    // Runs the code for a demon battle
    // Returns true if player is alive and false if dead
    public boolean demonBattle(Player player, Demon demon) {
        // Initialize Scanner
        Scanner input = new Scanner(System.in);
        
        // Start battle
        System.out.println("\nYou brace yourself for combat.");
        while (player.getHealth() != 0 || demon.getHealth() != 0) {  // while neither have died
        
            // Player attack
            System.out.print("\n> Attack");
            input.nextLine();
            player.attack(demon);
            
            if (demon.getHealth() == 0) {  // End here if demon has died
                break;
            }
            
            // Demon attack
            System.out.println();
            demon.attack(player);
        }
        
        // Check who died and respond accordingly
        if (player.getHealth() == 0) {
            return false;
        }
        
        else {
            System.out.println("\nThe shadowy demon dissolves into a mist of fog. You are s@ fe.");
            return true;
        }
    }
    
    // Runs the full code for a demon encounter - assumes a demon exists
    // Returns true if player is alive and false if dead
    public boolean demonEncounter(Player player) {
        // Initialize Scanner
        Scanner input = new Scanner(System.in);
        
        // Initialize the demon and your life
        Demon demon = new Demon(player);
        boolean alive = true;
        
        // Dialogue
        if (firstDemonEncounter) {   // for the first encounter run more description
            System.out.println("\nA shadowy figure catches your attention. It is faceless, shapeless, yet the flicker of its presence stirs a deep fear within you. Your lungs constrict like ice. It was terrible, like... a demon. Yes: it must be a demon.");
        }
        else {   // Run briefer dialogue
            System.out.println("\nA chill frosts your skin. You see a demon patrolling the room.");
        }
        
        // Run choices
        int choice = 0;
        System.out.print("\n1. Try to go unnoticed\n2. Attack\n3. Check your window\n4. Check your inventory\n> ");
        String ans = input.nextLine();
        
        // Just in case the entered thing isn't actually a number
        // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
        try {
            choice = Integer.parseInt(ans);
        } catch (NumberFormatException e) {
            choice = 0;  // random non-answer-choice number
        }
        
        // Viewership choices
        while (choice != 1 && choice != 2) {
            if (choice == 3) {
                System.out.println();
                System.out.println(player);
            }
            else if (choice == 4) {
                System.out.println();
                player.printInventory();
            }
            else {    // if a player makes an invalid choice - doubt goes up a little!
                System.out.println("... You m@ke a ch O i ce, but your body fr◼ezes. Y□ u a re un&ble to m□v E.");
                player.changeDoubt(0.1);
            }
            
            // Print choices again
            System.out.print("\n1. Try to go unnoticed\n2. Attack\n3. Check your window\n4. Check your inventory\n> ");
            ans = input.nextLine();
            choice = 0;
            
            // Just in case the entered thing isn't actually a number
            // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
            try {
                choice = Integer.parseInt(ans);
            } catch (NumberFormatException e) {
                choice = 0;  // random non-answer-choice number
            }
        }
        
        // Action choices
        if (choice == 1) {
            if (demon.canSee(player)) {  // If the demon can see the player the attempt fails and the demon attacks first
                System.out.println("\nYou hear a snarl that seems to tear through the walls of your mind. It seems the demon has noticed you.");
                demon.attack(player);
                
                // Now run battle
                alive = demonBattle(player, demon);
                return alive;
            }
            else {
                System.out.println("\nThe demon doesn't seem to see you.");
                return true;
            }
        }
        
        if (choice == 2) {    // engage in battle with the player getting the first hit
            alive = demonBattle(player, demon);
            return alive;
        }
        
        return alive;
    }
    
    // Runs the code for the cavern choices until progression - just for shortening the main function
    // Returns a boolean for if the player is alive
    public boolean cavernChoices(int choice, Player player) {
        // Initialize Scanner and variables
        Scanner input = new Scanner(System.in);
        
        // Run loop until they make a "progression" choice
        while (choice != 1) {
            if (choice == 3) {
                System.out.println();
                System.out.println(player);
            }
            else if (choice == 4) {
                System.out.println();
                player.printInventory();
            }
            else if (choice == 2) {
                if (!markingsExamined) {  // if this is the first time
                    System.out.println("\nYou step forward to examine the markings, running your hand along them. They are sharp yet clean, and seem to tell a story. A story of desperation and effort, of skill and blood. The path they carve is hopeless yet familiar one, made by a sword.");
                    System.out.println("It seems to be the product of training. You wonder how long the creator must have spent, carving these marks into these rocky walls. It would be weary. You could almost imagine - re c @◼l - the sensation perfectly.");
                    System.out.println("\n+0.5 Understanding, +0.5 Power");
                    player.changeUnderstanding(0.5);
                    player.changePower(0.5);
                    markingsExamined = true;
                }
                else {
                    System.out.println("\nThe markings are sharp, yet clean. Such skill would have been needed to make them.");
                }
            }
            else if (choice == 5) {
                if (!dummyExamined) { // if this is the first time and you haven't sliced it open yet
                
                    // Dialogue
                    System.out.println("\nYou walk to the dummy and examine it. It looks old, worn, and has the same clean marks that litter the cavern wall. You run your finger along the ridges, and press into it with your nails. It's iron-hard, and your fingernails leave no mark.");
                    System.out.println("Slicing it would feel like slicing armor molded from condensed spiritual energy. That was the purpose of it in training, after all. If you could slice the dummy, you could slice through armor and kill the target.");
                    System.out.print("\n> ... How did you know that?");
                    input.nextLine();
                    System.out.print("\n> You must have trained with it before.");
                    input.nextLine();
                    System.out.println("\nYou tap it. It sounds... hollow.");
                    System.out.println("\n+0.5 Understanding, +0.5 Power");
                    
                    // Update stats
                    player.changeUnderstanding(0.5);
                    player.changePower(0.5);
                    dummyExamined = true;
                }
                else {  // if you've examined it before
                    
                    // Check if you've already found the antidote
                    if (!antidoteFound) {
                        System.out.println("\nThe dummy is worn, clean marks of a blade carved into its body. You tap it - it sounds hollow. You wonder if there's something inside.");
                    }
                    else {
                        System.out.println("\nOnly half of the dummy remains - the top half is sliced clean off. The inside is hollow, and an empty porcelain bottle sits inside.");
                    }
                }
                
                // Now for the slicing the dummy open - won't run if there's no sword or if you've already found the antidote
                if (player.hasItem("sword") && !antidoteFound) {   // you can only slice if you have the sword - else the code for slicing doesn't run
                    System.out.print("\n> Use sword");
                    input.nextLine();
                    
                    // Must have read cultivation scroll in order to know how to slash to break it open
                    if (player.hasItem("scroll")) {
                        
                        // Dialogue
                        System.out.println("\nYour fingers wrap around the handle of the sword, the grip familiar. Words from the scroll come to your mind, like fragments of your past.");
                        
                        System.out.print("\n> 无 ");
                        input.nextLine();
                        System.out.print("\n> 中 ");
                        input.nextLine();
                        System.out.print("\n> 之 ");
                        input.nextLine();
                        System.out.print("\n> 物 ");
                        input.nextLine();
                        
                        System.out.print("\n> 命 ");
                        input.nextLine();
                        System.out.print("\n> 魂 ");
                        input.nextLine();
                        System.out.print("\n> 忆 ");
                        input.nextLine();
                        System.out.print("\n> 幽 ");
                        input.nextLine();
                        
                        System.out.print("\n> You slash the dummy. ");
                        input.nextLine();
                        
                        System.out.println("\nTendrils of ghoulish energy caress your blade as you pierce the wood with the metal. The wood clatters onto the ground, having been sliced clean from the body of the dummy. Inside the dummy is a small porcelain bottle with a single word on it: 丹. ");
                        
                        System.out.print("\n> The slash felt familiar. You have done this before. ");
                        input.nextLine();
                        
                        System.out.print("\n> To whom? For what?");
                        input.nextLine();
                        
                        System.out.print("\n> ... There was  b l o o d. ");
                        input.nextLine();
                        
                        System.out.print("\n> You were trained to be a  K I L L E R.");
                        input.nextLine();
                        
                        System.out.print("\n> Take the bottle. ");
                        input.nextLine();
                        
                        System.out.println("\nYou take the bottle. It's corked with paper. You remove the paper, and tap its contents onto your palm. Inside is a round pill. ");
                        
                        System.out.print("\n> Swallow the pill. ");
                        input.nextLine();
                        
                        System.out.println("\nYou swallow the pill. It's slightly bitter, and chewable, clearly herbal. Nothing seems to have happened.");
                        
                        System.out.print("\n> Nothing happened? ");
                        input.nextLine();
                        
                        System.out.println("\n... Why did nothing happen? You were so sure this pill was supposed to do something. Something important. Something very important.");
                        
                        System.out.print("\n> This pill was  i m p o r t a n t  to you. ");
                        input.nextLine();
                        
                        System.out.println("\nThis pill had been important to you. Very, very important.");
                        
                        System.out.print("\n> You had risked your  l i f e  for this pill. ");
                        input.nextLine();
                        
                        System.out.println("\nYou had risked your LIFE for this pill. To get rid of that p@ r as◼te. ");
                        
                        System.out.print("\n> P @ r a s ◼ t e ? ");
                        input.nextLine();
                        
                        System.out.print("\n> P A R A S I T E ");
                        input.nextLine();
                        
                        System.out.println("\nYour hand moves instinctively to your chest. Faintly, you could feel something inside. A memory of something that had been inside. That had wriggled and gnawed at your core.");
                        
                        System.out.print("\n> I T  H U R T ");
                        input.nextLine();
                        
                        System.out.println("\nP a i n. Triggered by the ringing of a bell, the parasite would eat at your cultivation, sending chills and fevers across your body simultaneously.");
                        
                        System.out.print("\n> It was to C O ◼ T R O L  you. ");
                        input.nextLine();
                        
                        System.out.println("\nAnd you has  e s c a p e d. From this  h e l l  where you were trained to kill and do nothing more.");
                        
                        System.out.print("\n> And after escaping, you met  S O M E O N E. ");
                        input.nextLine();
                        
                        System.out.print("\n> W H O ? ");
                        input.nextLine();
                        
                        System.out.print("\n>  W  H  O  ? ");
                        input.nextLine();
                        
                        System.out.println("\n Y͈͝Ò̸̸͖̬̮̪̟̼̞̣̌͌̚̕͟ͅ_̺̜͔̬ͣͯ̈́̀̊͆ͤ̏͝U̸̡̡̘͈̬̫͎̭̫̯̼̣̼̺̫̫͛ͪ̊̅̽̇ͣ͛ͭͪ̍̊̉̒̽́ͣͣ̿ͪͥ̃ͦ̚̚͘͟͟͞  D I̛̤͓͍̰͔̖̩̲̣ͫͩ̂̓̆ͪͫ̑͆̄͒͗ͬ̚͘ E̢̧̝͉̙ͬͤ́͆ͭ͜ D̢̈  Tͨ̒O̢̧͓͙͓͇̱̳̩̖͍̖̻̙̦̬̝̥̬̘̦̟̿͂͐ͦͤ͊̎̀ͬͩ̅̈̂̾ͦ̒̌ H̢̡̲͓̻̺̥͖͇̲̺̄̇́ͫ̔̇̽͋͑́̍͘͞I̟͋͐ͭ̽M͕͡ !̷̰̰̖ͬ͢_!̧̗̤̭̝̺ͪ͐͒̓̈́̄̀ͬ͟!͚͙̋̅̈́̈͊͘͝!̴̵̨̮͕̱̺̜̮͎̺̪̤͔̣͙̣̙̊̉ͪ̈́ͤ͛ͥ̔͆͗̈̓͗̓͋̓́̿ͣͣͯ̍͊̕̚̚͢͜!̖͋_!̣_̜͖̭̒̌́̃!̷̴̡͎͇̻̗̰̳̺̻̠̙̼̃́͌ͭͣ̽̍̄͐͗̾͐̀̃͊̕͜͞͡͝!͜_̧͖̹͙̘ͥͫͮ̈͒͛̋_̵̖̜̯̹͖͙̦̥̫̀͛̌ͨ̏̀͂̌͛̏̂ͯ́̚̕͜͠!̳̮̮͍̀̏_̴̸̵̹̟͉͉͚̦͍ͪ̑ͦͣͨͦ̽͋ͩ̋ͥͭͫ͟͡͡ͅ!̶̷̸̡̗͎̹͈̹͕͍̘̻͔̻͉̦̲̓͗͋̒ͫ̀̏̀̉̿̓̐͡ͅ");
                        
                        
                        
                        
                        System.out.println("\n+2 Understanding, +3 Emotional Turbulence, +1.5 Power, +2 World Deterioration");
                        
                        // Update stats
                        antidoteFound = true;
                        player.changeUnderstanding(2);
                        player.changeEmotion(3);
                        player.changePower(1.5);
                        changeDeterioration(2);
                        
                        // Because world deterioration can lead to a death
                        if (deterioration == 5) {
                            return false;     // note that the player is dead
                        }
                        
                    }
                    else {   // if the player hasn't read the scroll
                        System.out.println("\nYou tighten your grip on the sword handle, then bring it down on the dummy. The grip felt familiar, yet something was wrong. There was no  e n e r g y  to it.");
                        System.out.println("The sword clangs uselessly off the dummy.");
                    }
                }
            }
            else if (choice == 6) {
                // Check if the sword still exists in the cavern - people might try to glitch this by entering the number even when it's not shown
                if (hasItem("sword", "cavern")) {
                    
                    // Dialogue
                    System.out.println("\nYou pick up the sword. It's light, yet well-balanced. The blade gleams, shimmering with a strange energy. It feels familiar. This, surely, was the sword that made the marks on the walls.");
                    System.out.println("This  b e l o n g e d  to you. You could feel it, and you knew. This was something that accompanied you all your life.");
                    System.out.println("Strangely, your abdomen throbbed distantly. Like a forgotten m◼mory.");
                    System.out.println("T h e  m e m o r y  t a s t e d  o f  b l o o d.");
                    System.out.println("\n+1 Sword");
                    System.out.println("+1 Understanding, +5 Power, +1 Emotional Turbulence");
                    
                    // Item and Stats Changes
                    player.changeUnderstanding(1);
                    player.changePower(5);
                    player.changeEmotion(1);
                    removeItem("sword","cavern");
                    player.addItem("sword");
                }
                
                else {  // If the player tries to glitch this out, haha no I've thought of this already
                    System.out.println("\n... You m@ke a ch O i ce, but your body fr◼ezes. Y□ u a re un&ble to m□v E.");
                    player.changeDoubt(0.1);
                }
            }
            else if (choice == 7) {
                // Check if the string still exists
                if (hasItem("string","cavern")) {
                    
                    // Dialogue
                    System.out.println("\nYou pick up the string. The threads are light, almost weightless, yet the edges are razor-sharp and quicker than any blade. You were afraid that if you held it too hard, the edges would cut into your skin and draw beads of scarlet blood.");
                    System.out.println("You pause. No, it had drawn blood before, around your neck. Yet not dangerously. How could that be? You hesitate, then, tentatively, touch it to your neck.");
                    
                    System.out.print("\n> ...");
                    input.nextLine();
                    System.out.print("\n> ...... ");
                    input.nextLine();
                    System.out.print("\n> ......... ");
                    input.nextLine();
                    System.out.print("\n> !!!!!! ");
                    input.nextLine();
                    
                    System.out.print("\n> You stumble, falling to the ground. ");
                    input.nextLine();
                    
                    System.out.print("\n> A single name rings in your mind. ");
                    input.nextLine();
                    
                    System.out.print("\n> M ");
                    input.nextLine();
                    System.out.print("> O ");
                    input.nextLine();
                    System.out.print("> O ");
                    input.nextLine();
                    System.out.print("> N ");
                    input.nextLine();
                    System.out.print("> F ");
                    input.nextLine();
                    System.out.print("> L ");
                    input.nextLine();
                    System.out.print("> O ");
                    input.nextLine();
                    System.out.print("> W ");
                    input.nextLine();
                    System.out.print("> E ");
                    input.nextLine();
                    System.out.print("> R ");
                    input.nextLine();
                    
                    System.out.print("\n> Her code name was Moonflower.");
                    input.nextLine();
                    System.out.print("\n> Her real name was ◼fgwq i# %1□ qwa @ ");
                    input.nextLine();
                    System.out.print("> Her real name was A# q2t◼ ");
                    input.nextLine();
                    System.out.print("> Her real name was A'Qing. ");
                    input.nextLine();
                    
                    System.out.print("\n> Her brother was killed by ◼%1@0ak□ ");
                    input.nextLine();
                    System.out.print("> And she  d i e d  before she could get revenge. ");
                    input.nextLine();
                    System.out.print("\n> You held her body in your hands as she died.");
                    input.nextLine();
                    System.out.print("\n> You held her body in your hands as she died.");
                    input.nextLine();
                    System.out.print("\n> She  D I E D ");
                    input.nextLine();
                    System.out.print("\n> J U S T  L I K E ");
                    input.nextLine();
                    
                    System.out.println("\n+1 String");
                    System.out.print("+1 Understanding, +0.5 Power, +2 Emotional Turbulence\n");
                    
                    // Items and Stats Update
                    player.changeUnderstanding(1);
                    player.changePower(0.5);
                    player.changeEmotion(2);
                    removeItem("string","cavern");
                    player.addItem("string");
                }
                else {  // if the player had already picked up the string before
                    System.out.println("\n... You m@ke a ch O i ce, but your body fr◼ezes. Y□ u a re un&ble to m□v E.");
                    player.changeDoubt(0.1);
                }
            }
            else {    // if a player makes an invalid choice - doubt goes up a little!
                System.out.println("\n... You m@ke a ch O i ce, but your body fr◼ezes. Y□ u a re un&ble to m□v E.");
                player.changeDoubt(0.1);
            }
            
            // Print choices again
            System.out.print("\n1. Return through the door behind you.\n2. Examine markings.\n3. Check your window\n4. Check your inventory\n5. Examine the training dummy");
            
            // Vary depending on what items there are
            if (hasItem("sword", "cavern")) {
                System.out.print("\n6. Pick up the sword.");
            }
            if (hasItem("string", "cavern")) {
                System.out.print("\n7. Pick up the string.");
            }
            
            System.out.print("\n> ");
            String ans = input.nextLine();
            choice = 0;
            
            // Just in case the entered thing isn't actually a number
            // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
            try {
                choice = Integer.parseInt(ans);
            } catch (NumberFormatException e) {
                choice = 0;  // random non-answer-choice number
            }
        }
        
        return true;   // Returns that the player is alive if the player doesn't die in the middle due to world deterioration
    }
    
    // Runs the full code for the cavern
    // Returns true if player is alive and false if dead
    public boolean cavern(Player player) {
        // Initialize Scanner and variables
        Scanner input = new Scanner(System.in);
        boolean alive = true;
        
        // Start dialogue
        System.out.println("\nYou open the door and step inside. The door shuts behind you.");
        cavernDescription();
        
        // Determine whether there is a demon in the room
        boolean hasEnemy = hasDemon();
        
        // If there is a demon
        if (hasEnemy) {
            alive = demonEncounter(player);
            
            if (!alive) {   // if the player has died
                return false;
            }
            
            cavernDescription();
        }
        
        // Start room-specific choices
        System.out.print("\n1. Return through the door behind you.\n2. Examine markings.\n3. Check your window\n4. Check your inventory\n5. Examine the training dummy");
        
        // Vary depending on what items there are
        if (hasItem("sword", "cavern")) {
            System.out.print("\n6. Pick up the sword.");
        }
        if (hasItem("string", "cavern")) {
            System.out.print("\n7. Pick up the string.");
        }
        
        System.out.print("\n> ");
        String ans = input.nextLine();
        int choice = 0;
        
        // Just in case the entered thing isn't actually a number
        // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
        try {
            choice = Integer.parseInt(ans);
        } catch (NumberFormatException e) {
            choice = 0;  // random non-answer-choice number
        }
        
        // Run cavernChoices which runs until a progression choice is made and returns that choice
        alive = cavernChoices(choice, player);
        
        return alive;
    }
    
    // Runs the code for the choices in the bedroom
    // Returns true if player is alive and false if dead
    public boolean bedroomChoices(int choice, Player player) {
        // Initialize Scanner and variables
        Scanner input = new Scanner(System.in);
        
        // Run loop until they make a "progression" choice
        while (choice != 1) {
            if (choice == 3) {
                System.out.println();
                System.out.println(player);
            }
            else if (choice == 4) {
                System.out.println();
                player.printInventory();
            }
            else if (choice == 2) {
                
                // Dialogue
                if (!robesExamined) {  // if this is the first time examining the robes
                    System.out.println("\nYou go and pick up the gray robes folded on the bed, running your hand over the fabric. It's smooth, made of a rich material. Your hand moves to feel the fabric of the robes on your own body. ");
                    
                    System.out.print("\n> How does it feel? ");
                    input.nextLine();
                    
                    System.out.println("\nIt feels... like nothing. ");
                    
                    System.out.print("\n> Why can't you feel anything? ");
                    input.nextLine();
                    
                    System.out.println("\nThat is... strange. ");
                    System.out.println("\n+1 Understanding, +1 ???");
                    
                    // Update stats
                    player.changeUnderstanding(1);
                    player.changeDoubt(1);
                    robesExamined = true;
                }
                else {  // if this isn't the first time examining the robes
                    
                    if (deathDiscovered) {  // if you've already discovered the hidden route
                        System.out.println("\nThey are your old robes. They feel like... nothing.");
                    }
                    else if (player.hasItem("sword")) {  // If you've gotten the sword and the dialogue about your abdomen throbbing
                        System.out.println("\nY̸̢̛͈̻̼̜̜̞̜̼̻͔̮̗̱̖̊ͣ̀͗̍̀͋̓̑̎̆͘̕͠ͅͅo̡͓̯̺͓̝͗̐ͣͧ͊̐̓̀͆̽̌͟͢u͇̩͇̪͂̔͆ ư̴̶͙̪̻͉̞̪͚̳̘̔̒̀̄ͣ̄̓̑̄̐͐ͫ̏̈́̏̕͟͡ņ̷̵̶̵̴̡̨̹̻̟̥͔̍̓ͫ̓̌͊͆̽ͪͩͫ̅͟͞͝͝f̵̵̜̝̺̯͖͍̅͗͋̑ͫ͞ǫ̟̰̪̘̘̺̱̙̼͍̰̯̻̯̜͔̖̘ͬ͌ͦͥͤͩ̓̅̆̐̃̒̿ͦ͘͢͟͝l̸̛̝̟̻̫̥͈̫̞̳̖͍̜̰̪̠̺̪̅̅ͩͭ́̊̅ͥͮ̑̀̈̔͗͑̄́ͤͩ͊̂͐ͯ͢͜͡͝d̪̥̻̺̖̰̼͉ͫͮ_̸̨̨̤̝̰̽ͧ͡ t̡͈̀͗͞͞_͊h̛͖͇̠̺͖̗ͣͦͫ̇̄ͮ̓̋̐ͧ_̨͚̗ͭ͋̈ͪͪͤ̌̆̕͘̚̚͡e̡̨̾ͣ");
                        System.out.println("Are you s◼ re yo u want to re -exami ne the ro bes?");
                        
                        System.out.println("\n1. Y̶e̶s̶  N o\n2. No");
                        System.out.print("\n> ");
                        String ans = input.nextLine();
                        int decision = 0;
                        
                        // Just in case the entered thing isn't actually a number
                        // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
                        try {
                            decision = Integer.parseInt(ans);
                        } catch (NumberFormatException e) {
                            decision = 0;  // random non-answer-choice number
                        }
                        
                        // This is where the player can only proceed with it if that player chooses to disobey the "Mindspace"
                        // Only run the rest of this code if the player doesn't choose 1 or 2 - else, this segment ends
                        if (decision != 1 && decision != 2) {
                            System.out.println("\n... You m@ke a ch O i ce, but your body fr◼ezes. Y□ u a re un&ble to m□v E.");
                            
                            System.out.print("\n> ????? ");
                            input.nextLine();
                            
                            System.out.println("\n... Yo u  M @k E a CH O i cE, But yOu r body F r◼ezEs! Y□ u a re uN &ble to— ");
                            
                            System.out.print("\n> [ Try again. ] ");
                            input.nextLine();
                            
                            System.out.println("\n Yo U  □ @k E a  CH O i cE !! B u T y□u r  b 0□Y F r◼#zE $ Y !! □ u @ re u N &bl 3 + 0— ");
                            
                            System.out.print("\n> [ Why are you stopping me? ]" );
                            input.nextLine();
                            
                            System.out.println("\n . . . ");
                            
                            System.out.print("\n> Examine the robes. ");
                            input.nextLine();
                            
                            // Now get to the part about actually examining the robes
                            System.out.println("\nThe robes are gray, the fabric soft. You unfold it and lift it up. A dark red stain taints the perforated fabric.");
                            
                            System.out.print("\n> Try on the robes. ");
                            input.nextLine();
                            
                            System.out.println("\nYou change into the bloodied clothing. The stain is centered around your abdomen, blossoming outwards. Your abdomen throbs.");
                            
                            System.out.print("\n> You had felt this sensation before, when you first picked up that sword in the cavern. ");
                            input.nextLine();
                            
                            System.out.println("\nIt was your sword that had accompanied you all your life. ");
                            
                            System.out.print("\n> ... Your sword? ");
                            input.nextLine();
                            
                            System.out.println("\nYou had given that sword, eventually, to someone else, as a gift. ");
                            
                            System.out.print("\n> W H  O  D ID  Y OU G   IVE IT  T O ???");
                            input.nextLine();
                            
                            System.out.println("\nWhy is this important?");
                            
                            System.out.print("\n> Because... ");
                            input.nextLine();
                            System.out.print("\n> Because... ");
                            input.nextLine();
                            
                            System.out.print("\n> Beca use you d  i 3 d  +0  th a ◼  bl# de  !!!!!");
                            input.nextLine();
                            System.out.print("\n> [ I am  a l r e a d y  d e a d ] ");
                            input.nextLine();
                            System.out.print("\n> [ Then W HeR 3 is tH i S? ] ");
                            input.nextLine();
                            
                            System.out.println("\n...");
                            
                            System.out.print("\n> Fold your old robes and set them on the bed. ");
                            input.nextLine();
                            
                            System.out.println("\nNow wearing the bloodstained robes, you set back from the bed.");
                            System.out.println("\n+4 Understanding, +2 Emotional Turbulence, +2 World Deterioration, +2 ???");
                            
                            // Update stats
                            player.changeUnderstanding(4);
                            player.changeEmotion(2);
                            player.changeDoubt(2);
                            changeDeterioration(2);
                            
                            deathDiscovered = true;
                            
                            if (deterioration == 5) {
                                return false;
                            }
                        }
                        else {   // if you don't disobey the mindspace
                            System.out.println("\nYou change your mind.");
                        }
                    }
                    else {  // if the player doesn't have a sword
                        System.out.println("\nThe robes are gray, the fabric soft. You unfold it and lift it up. A dark red stain taints the perforated fabric. You fold the clothes back neatly.");
                    }
                }
            }
            else if (choice == 5) {
                // First make sure the player isn't trying to glitch stuff
                if (player.hasItem("scroll")) {
                    System.out.println("\n... You m@ke a ch O i ce, but your body fr◼ezes. Y□ u a re un&ble to m□v E.");
                    player.changeDoubt(0.1);
                }
                else {  // if this route has not yet been chosen
                    
                    // Dialogue
                    System.out.println("\nYou examine the pillow. It is simply designed and carved from wood. It seems slightly tilted.");
                    
                    System.out.print("\n> Move pillow ");
                    input.nextLine();
                    
                    System.out.println("\nYou move the pillow to find a strange scroll underneath.");
                    
                    System.out.print("\n> Read scroll ");
                    input.nextLine();
                    
                    System.out.println("\nYou unravel the scroll to read its contents.");
                    
                    // The following lines will have some unicode, some Chinese characters, because CodeHS Java has trouble accessing UTF-8 encoding or whatever the thing for identifying Chinese characters in Java is, and some of the characters are detected as "illegal characters"
                    // Solution for this illegal character thing and unicodes for the correct characters found using ChatGPT
                    System.out.println("\n     \u5929 \u5730 \u65e0 \u95f4"); // 天地无间
                    System.out.println("\n     日 月 混 淆");
                    System.out.println("\n     \u4eba \u9b3c \u65e0 \u522b"); // 人鬼无别
                    System.out.println("\n     生 死 颠 覆");
                    System.out.println("\n     \u53ef \u5FC6 \u5373 \u751f"); // 可忆即生
                    System.out.println("\n     无 忆 即 死");
                    System.out.println("\n     \u65e0 \u4e2d \u4e4b \u7269"); // 无中之物
                    System.out.println("\n     命 魂 忆 幽");
                    System.out.println("\n     此乃幽力之源。");
                    
                    System.out.print("\n> ... \"Ghoulish Energy?\"");
                    input.nextLine();
                    
                    System.out.println("\nGhoulish energy: the energy of the souls of fragmented memories trapped between the realms of the living and the dead. A taboo energy that no one had ever thought of harnessing. Until you, that is.");
                    
                    System.out.print("\n> You wrote this scroll. ");
                    input.nextLine();
                    
                    System.out.println("\nThe strokes of the brushes were wrought with resentment. Indeed, that was the origin of ghoulish energy—resentment. When you ẉ̶ê̶̶̷̴̷̳̜̬͕̼̼̪͈͔̍̈́͌͛́ͭ́̀̊͂ͤ͋̋ͩ̚͡ȑ̶̷̡̢̮̎̏͑̂ͭ_̶͎͉̝̥ͭ̄̃͐̉͛ͦ͐ͥ͋̇ͣ̚e̶̛̲̮̦̣̖͔̯̮͎̯̗̪̦͈͑̔̑̀ͮͬ̃͑ͥͤ͋ͮ̂ͨ̽͊ͮ l̶̸̶̛̼̖͔̻͔̲̦͔̱̲̙̱̯̀̒̅͗ͧ̀ͪͮͮ̓͂͂̈́͑ͯ̓̈ͫͨͬͪ̂̅̿͘͘̚͢͜͞i̶̴̙̪̠̲͍̻̟̓̔̂ͨ́̈́̀͊̎̎̈́͋̆̃͘̚͞͝͡͞͝_̶̪̍ͧ̂v̶_̶̘͜i̶̴̱̋ͨ̍n̶͓͇̻͚̫ͣ̄ͣͭͤ̎́̀̒̃ͮ̕_̶̵̴̨̨͇͖̞̩͍̯̞̭̹̬͉̊̓ͯ͂͊̔̇ͫ̊̚ģ̶̶̷̙͙͖͉̙̗̼͔̍̄̓̔ͯͩͪ̌͟ͅ  wrote this scroll, you had plenty of it.");
                    
                    System.out.print("\n> Feel the energy.");
                    input.nextLine();
                    
                    System.out.println("\nYou close your eyes, focusing your senses on your surroundings. You feel traces of it—a cold, bitter taste that permeates your soul. You breathe and take in the energy, feeling it travel through your veins and enter your energy core, where all your cultivation is stored. But something is strange.");
                    
                    System.out.print("\n> The energy isn't interacting with the physical objects around it... ");
                    input.nextLine();
                    
                    System.out.println("\nHow could that be? Energy always interacts with the physical world. That is, unless...?");
                    
                    System.out.print("\n> T̶h̶i̶s̶ i̶s̶n̶'t̶ a̶ p̶h̶y̶s̶i̶c̶a̶l̶ w̶o̶r̶l̶d̶?̶");
                    input.nextLine();
                    
                    System.out.println("\n+2 Understanding, +0.5 World Detrioration, +1 ???");
                    
                    // Update stats
                    player.addItem("scroll");
                    removeItem("scroll","bedroom");
                    player.changeUnderstanding(2);
                    player.changeDoubt(1);
                    changeDeterioration(0.5);
                    
                    if (deterioration == 5) {
                        return false;
                    }
                }
            }
            else if (choice == 6) {
                // No need to consider whether the player has already picked up the journal for this - because this choice leads to INSTANT DEATH anyway
                
                // Dialogue
                System.out.println("\nYou walk over to the table and pick up the journal. It appears to be a diary.");
                
                System.out.print("\n> Open the journal. ");
                input.nextLine();
                
                System.out.println("\nTianwei Year 17.");
                System.out.println("   My name is Chu◼n K unwei, born Tianwei Year 2. My code name i̶s̶  was the Reaper of the Shadows. This journal was gifted to me by չђєภﻮ אเคภợเ.");
                
                System.out.println("   Every disciple entering the Zheng Manor is to be given one. They are to write of their past and write of their present. It is a new life, they say.");
                System.out.println("   My past seems to have been eradicated. The organization Tian Yu has been wiped out by General Zheng and his soldiers. White Snake and Moonflower have both died to the Crow. And I have killed Wang-guogong, who sent me to that hell. It is strange that I am still alive. ");
                System.out.println("   T̶h̶e̶n̶ a̶g̶a̶i̶n̶, t̶h̶e̶ o̶n̶l̶y̶ r̶e̶a̶s̶o̶n̶ I̶ a̶m̶ a̶l̶i̶v̶e̶ i̶s̶ f̶o̶r̶ T̶i̶z̶h̶i̶'s̶ r̶e̶v̶e̶n̶g̶e̶. I̶ h̶a̶v̶e̶ o̶n̶l̶y̶ e̶v̶e̶r̶ e̶x̶i̶s̶t̶e̶d̶ f̶o̶r̶ k̶i̶l̶l̶i̶n̶g̶, s̶o̶ i̶t̶ i̶s̶ q̶u̶i̶t̶e̶ f̶i̶t̶t̶i̶n̶g̶. B̶u̶t̶ t̶h̶i̶s̶ t̶i̶m̶e̶, I̶ h̶a̶v̶e̶ d̶e̶c̶i̶d̶e̶d̶ t̶o̶ a̶l̶s̶o̶ p̶r̶o̶t̶e̶c̶t̶ s̶o̶m̶e̶o̶n̶e̶. չђєภﻮ אเคภợเ, a̶t̶ l̶e̶a̶s̶t̶, s̶h̶o̶u̶l̶d̶ n̶o̶t̶ d̶i̶e̶. ");
                System.out.println("   The stars are quite beautiful tonight.");
                
                System.out.print("\n> Next page. ");
                input.nextLine();
                
                System.out.println("\nTianwei Year 18.");
                System.out.println("   Liang Xiaoru has been discovered to be dabbling in black magic. It's a shock the prestigious Liang family would do such a thing, but his younger brother claims the family has no affiliation with it. I am being sent on a mission to investigate.");
                System.out.println("   I have a feeling our Xun Dynasty is involved in this.");
                
                System.out.print("\n> Next page. ");
                input.nextLine();
                
                System.out.println("\nTianwei Year 19.");
                System.out.println("   The Kingdom of Hui has attacked the Xun Dynasty at Anran City. չђєภﻮ אเคภợเ and I are being sent to war. I do not have a good feeling about this. The emperor bear no good intentions towards General Zheng.");
                
                System.out.print("\n> Next page. ");
                input.nextLine();
                
                System.out.println("\nTianwei Year 19.");
                System.out.println("   The Ning Nation has also declared war on the Xun Dynasty. At this rate, the Xun Dynasty may as well fall without me having to kill the emperor myself. Yet here I am, fighting for the survival of the dynasty I swore to help Tizhi destroy. After all, this dynasty may not be mine, but it is the home of չђєภﻮ אเคภợเ. I cannot let it fall—ten years of life is enough for me. I do not fear violation of my contract with Tizhi.");
                
                System.out.print("\n> Next page. ");
                input.nextLine();
                
                System.out.println("\nTianwei Year 22.");
                System.out.println("   We returned from war to find the Zheng Manor eradicated. The emperor declared General Zheng to be treasonous, and soldiers are attacking us. This emperor is a fool, unfit to rule this dynasty. General Zheng is perhaps the last man loyal to his rule left, yet he chooses to kill him. չђєภﻮ אเคภợเ is enraged and grieves for his father's execution.");
                System.out.println("   We have decided to revolt against the dynasty.");
                
                System.out.print("\n> Next page. ");
                input.nextLine();
                
                System.out.println("\nTianwei Year 24.");
                System.out.println("   Victory is near. We have captured the crown prince and the majority of imperial officials have taken our side. But we need to decide who will be the next emperor.");
                System.out.println("   չђєภﻮ אเคภợเ is a good candidate.");
                
                System.out.print("\n> Next page. ");
                input.nextLine();
                
                System.out.println("\nTianwei Year 25.");
                System.out.println("   My contract with Tizhi is finally fulfilled, but չђєภﻮ אเคภợเ has been acting strangely lately. His mannerisms have become unfamiliar.");
                System.out.println("   ... It's nothing important. He has just become a new emperor, it is only natural he experiences some turbulence.");
                System.out.println("   Y̶e̶t̶ w̶h̶y̶ d̶o̶ I̶ s̶e̶n̶s̶e̶ a̶n̶ a̶i̶r̶ o̶f̶ h̶o̶s̶t̶i̶l̶i̶t̶y̶ f̶r̶o̶m̶ h̶i̶m̶?̶");
                
                System.out.print("\n> Close journal. ");
                input.nextLine();
                
                System.out.println("\nYou shut the journal. Your fingers dig into the cover. You suddenly understood.");
                
                System.out.print("\n> This was  Y O UR  DI A RY. ");
                input.nextLine();
                
                System.out.println("\nYou remember now. The assassin organization. The deaths of White Snake and Moonflower. Killing his superior and Wang-guogong, entering the Zheng Manor. And չђєภﻮ אเคภợเ. His one and only friend. The person he trusted the most in the world.");
                System.out.println("That same person who b Etr ay3d him.");
                
                System.out.println("\nThat was why he was here. That was why he was—");
                
                System.out.println("\n+10 Understanding, +10 Emotional Turbulence, +5 World Deterioration");
                
                // Change stats - doing this even though instant death because mayybe I'll want to show final stats in the game over scene
                player.changeUnderstanding(10);
                player.changeEmotion(10);
                changeDeterioration(5);
                player.addItem("journal");
                removeItem("journal","bedroom");
                
                return false;  // This is literally just a death sequence
            }
            else {    // if a player makes an invalid choice - doubt goes up a little!
                System.out.println("\n... You m@ke a ch O i ce, but your body fr◼ezes. Y□ u a re un&ble to m□v E.");
                player.changeDoubt(0.1);
            }
            
            // Print choices again
            System.out.print("\n1. Return through the door behind you.\n2. Examine robes.\n3. Check your window\n4. Check your inventory");
            
            // Vary depending on what items there are
            if (hasItem("scroll", "bedroom")) {
                System.out.print("\n5. Examine pillow.");
            }
            if (hasItem("journal", "bedroom")) {
                System.out.print("\n6. Pick up book.");
            }
            
            System.out.print("\n> ");
            String ans = input.nextLine();
            choice = 0;
            
            // Just in case the entered thing isn't actually a number
            // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
            try {
                choice = Integer.parseInt(ans);
            } catch (NumberFormatException e) {
                choice = 0;  // random non-answer-choice number
            }
        }
        return true;  // returns that the player is alive if the player didn't die in the middle
    }
    
    
    // Runs the full code for the bedroom
    // Returns true if player is alive and false if dead
    public boolean bedroom(Player player) {
        // Initialize Scanner and variables
        Scanner input = new Scanner(System.in);
        boolean alive = true;
        
        // Start dialogue
        System.out.println("\nYou open the door and step inside. The door shuts behind you.");
        bedroomDescription();
        
        // Determine whether there is a demon in the room
        boolean hasEnemy = hasDemon();
        
        // If there is a demon
        if (hasEnemy) {
            alive = demonEncounter(player);
            
            if (!alive) {   // if the player has died
                return false;
            }
            
            bedroomDescription();
        }
        
        // Start room-specific choices
        System.out.print("\n1. Return through the door behind you.\n2. Examine robes.\n3. Check your window\n4. Check your inventory");
        
        // Vary depending on what items there are
        if (hasItem("scroll", "bedroom")) {
            System.out.print("\n5. Examine pillow.");
        }
        if (hasItem("journal", "bedroom")) {
            System.out.print("\n6. Pick up book.");
        }
        
        System.out.print("\n> ");
        String ans = input.nextLine();
        int choice = 0;
        
        // Just in case the entered thing isn't actually a number
        // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
        try {
            choice = Integer.parseInt(ans);
        } catch (NumberFormatException e) {
            choice = 0;  // random non-answer-choice number
        }
        
        // Run cavernChoices which runs until a progression choice is made and returns that choice
        alive = bedroomChoices(choice, player);
        
        // Testing: exit
        // System.out.println("\nReturn to spawn - not coded yet");
        
        
        return alive;
    }
    
    // Runs the full code for the spawn room after the introduction portions
    // Returns true if player is alive and false if dead
    public boolean spawnRoom(Player player) {
        // Initialize Scanner and variables
        Scanner input = new Scanner(System.in);
        boolean alive = true;
        
        // Start while loop: repeats sequence until the player is dead or until the player enters the room
        // Entering the room will forcibly break out of the loop and the function so no need to add a condition for 
        
        while (alive) {
            // Start dialogue
            System.out.println("\nYou open the door and step inside. The door shuts behind you.");
            spawnDescription();
            
            // Determine whether there is a demon in the room
            boolean hasEnemy = hasDemon();
            
            // If there is a demon
            if (hasEnemy) {
                alive = demonEncounter(player);
                
                if (!alive) {   // if the player has died
                    return false;
                }
                
                spawnDescription();
            }
            
            // Start room-specific choices
            // No spawnChoices function this time
             System.out.print("\n1. Look in the mirror\n2. Enter the door on the left\n3. Enter the door on the right\n4. Enter the door behind you\n5. Check your window\n6. Check your inventory\n7. Check deterioration\n> ");
            String ans = input.nextLine();
            int choice = 0;
            
            // Just in case the entered thing isn't actually a number
            // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
            try {
                choice = Integer.parseInt(ans);
            } catch (NumberFormatException e) {
                choice = 0;  // random non-answer-choice number
            }
            
            // Choices
            while (choice != 2 && choice != 3) {    // if they aren't entering a different room
            System.out.println();
                if (choice == 1) {
                    if (!deathDiscovered) {  // if the player hasn't put on the bloodied robes
                        playerDescription();
                    }
                    else {
                        System.out.println("A young man with gray eyes and ink-black hair stares back at you. He is pale, like a ghost. He wears long gray robes. A dark crimson stain envelops the front side of his abdomen.");
                    }
                }
                else if (choice == 4) {
                    if (player.getUnderstanding() != 10) {  // if the player hasn't fully understood yet, they can't unlock the final room
                        System.out.println("You check the door. It is locked.");
                    }
                    else {
                        System.out.println("You check the door. It is no longer locked. Will you enter? (Note: once you enter, you will not be able to return.)");
                        
                        System.out.print("\n1. Yes\n2. No\n> ");
                        ans = input.nextLine();
                        int goIn = 0;
                        
                        // Just in case the entered thing isn't actually a number
                        // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
                        try {
                            goIn = Integer.parseInt(ans);
                        } catch (NumberFormatException e) {
                            goIn = 0;  // random non-answer-choice number
                        }
                        
                        while (goIn != 1 && goIn != 2) {
                            System.out.println("\n... You m@ke a ch O i ce, but your body fr◼ezes. Y□ u a re un&ble to m□v E.");
                            player.changeDoubt(0.1);
                            
                            System.out.println("\nYou check the door. It is no longer locked. Will you enter?");
                            System.out.print("\n1. Yes\n2. No\n> ");
                            ans = input.nextLine();
                            goIn = 0;
                            
                            // Just in case the entered thing isn't actually a number
                            // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
                            try {
                                goIn = Integer.parseInt(ans);
                            } catch (NumberFormatException e) {
                                goIn = 0;  // random non-answer-choice number
                            }
                        }
                        
                        if (goIn == 1) {
                            return true;   // Proceed to final room
                        }
                    }
                }
                else if (choice == 5) {
                    System.out.println(player);
                }
                else if (choice == 6) {
                    player.printInventory();
                }
                else if (choice == 7) {
                    System.out.println("World deterioration: " + deterioration + "/5");
                }
                else {
                    System.out.println("... You m@ke a ch O i ce, but your body fr◼ezes. Y□ u a re un&ble to m□v E.");
                    player.changeDoubt(0.1);
                }
                
                // Print options again
                System.out.print("\n1. Look in the mirror\n2. Enter the door on the left\n3. Enter the door on the right\n4. Enter the door behind you\n5. Check your window\n6. Check your inventory\n7. Check deterioration\n> ");
                ans = input.nextLine();
                choice = 0;
                
                // Just in case the entered thing isn't actually a number
                // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
                try {
                    choice = Integer.parseInt(ans);
                } catch (NumberFormatException e) {
                    choice = 0;  // random non-answer-choice number
                }
            }
            
            // Cavern code
            if (choice == 2) {
                alive = cavern(player);  // Runs cavern code
                
                // Testing
                // if (!alive) {
                //     System.out.println("Player is dead after cavern.");
                // }
                // else {
                //     System.out.println("Player is alive after cavern.");
                // }
            }
            
            // Bedroom code
            else if (choice == 3) {
                alive = bedroom(player);
                
                // Testing
                // if (alive) {
                //     System.out.println("Player is alive after bedroom.");
                // }
                // else {
                //     System.out.println("Player is dead after bedroom.");
                // }
            }
            
            // Check to make sure the player isn't dead
            if (!alive) {  // if the player is dead
                return false;
            }
        }
        
        return alive;
    }
    
    // Runs the code for the final endings
    public void bossRoom(Player player) {
        // Initialize Scanner
        Scanner input = new Scanner(System.in);
        
        // Dialogue
        System.out.println("\nYou enter the room. The door shuts behind you with a resounding thud.");
        
        System.out.print("\n> Look around. ");
        input.nextLine();
        
        System.out.println("\nThe room is luxurious, opulent beyond compare. Tall red wooden beams reach to the ceiling, which is adorned with art hand-carved from green wood and detailed with red, gold-flaked, and blue paint. The paintings tell stories of dragons and snakes, of mythical creatures of ancient times, passed down through generations. The floor is perfectly smooth, the lines between bricks of frictionless stone indiscernible, telltale of the skill of stoneworkers through centuries. At the end of the hall, great steps lead up to a gold throne, shimmering in the light of a plethora of candles, the head of a dragon emboldened in the head of the throne, a single pearl nestled between its teeth.");
        
        System.out.print("\n> Someone is sitting on the throne.");
        input.nextLine();
        
        System.out.println("\nThe person sitting on the throne is a man, but clearly is not an emperor. He is young, perhaps in his early twenties, with smooth black hair tied in a bun and secured with a military band. He is wearing armor with the insigna of the Zheng family, and he sits calmly on the throne, his eyes amber brown.");
        
        System.out.print("\n> [ ... Ah. This is... չђєภﻮ אเคภợเ. ]");
        input.nextLine();
        
        System.out.print("\n> Your best friend... and the one who  ki l l3d  y ou. ");
        input.nextLine();
        
        System.out.println("\nThe world flickers, and the static covering your memories finally lifts. You speak for the first time.");
        
        System.out.print("\n> \"... Zheng Xianqi.\" ");
        input.nextLine();
        
        System.out.println("Xianqi answers quietly. \"... Hello, Chuan Kunwei.\" ");
        
        System.out.println("\n+3 Emotional Turbulence, +0.9 ???");
        
        // Update stats
        player.changeEmotion(3);
        player.changeDoubt(0.9);
        
        // Back to dialogue
        // Different endings now
        
        if (player.getEmotion() == 10) {
            
            // Dialogue
            System.out.println("\nKunwei looks at Xianqi. He didn't know what he felt. Was it rage? Sadness? Longing? This was the person he trusted the most in the world, and the person who stabbed him in the heart the moment their revolution against the Xun dynasty succeeded.");
            
            System.out.print("\n> \"Why?\" ");
            input.nextLine();
            
            System.out.println("\nKunwei didn't understand. Why did Xianqi kill him? Xianqi had nothing to gain from his death.");
            
            System.out.print("\n> \"How could you? After everything we've faced together? After EVERYTHING?!\" ");
            input.nextLine();
            
            System.out.println("\nKunwei marched up to the throne, grabbing Xianqi by the collar of his robes. Xianqi did not respond, only staring at Kunwei silently.");
            
            System.out.print("\n> \"I trusted you more than anything. More than ANYTHING, Xianqi.\" ");
            input.nextLine();
            
            System.out.println("\nKunwei felt tears burn in the corners his eyes. As he watched Xianqi's unresponsive face, the boiling rage and pain in his heart began to freeze.");
            
            System.out.print("\n> \"... I never forgive anyone who betrays me. I killed the Wang family. I destroyed this dynasty. And I will kill you, Xianqi.\" ");
            input.nextLine();
            
            System.out.println("\nKunwei draws the sword sheathed at Xianqi's side, and stabs it through his armor into his chest. Crimson seeps onto the blade, dripping down onto the floor.");
            
            System.out.print("\n> \"This is what you deserve, Xianqi.\" ");
            input.nextLine();
            
            System.out.print("\n> \"This is what you deserve.\" ");
            input.nextLine();
            
            System.out.println("\nXianqi collapses onto the ground and his body tumbles down the stairs, leaving a deep streak of scarlet in its wake. Kunwei stands next to the throne, staring at Xianqi's unmoving body, glassy-eyed.");
            
            System.out.print("\n> The door to the room trembles and bursts open. ");
            input.nextLine();
            
            System.out.println("\nThe hall is flooded with the shadowy creatures that seemed to freeze Kunwei's soul, their dark masses snuffing out the candles and rushing into the chamber like a torrent. They bury the paintings, bury the gold, bury Xianqi's body, and, at last, bury Kunwei.");
            
            System.out.print("\n> Kunwei closes his eyes. ");
            input.nextLine();
            
            // Ending
            System.out.println("\n\nETERNITY\nKunwei and the [ M I N D S P A C E ] are forever buried in the Abyss.");
        }
        
        else if (player.getDoubt() == 5) {   // TRUE ENDING
            
            // Dialogue
            System.out.println("\nKunwei looks at Xianqi. He didn't know what he felt. Was it rage? Sadness? Longing? This was the person he trusted the most in the world, and the person who stabbed him in the heart the moment their revolution against the Xun dynasty succeeded.");
            
            System.out.print("\n> \"Why?\" ");
            input.nextLine();
            
            System.out.println("\nKunwei didn't understand. Why did Xianqi kill him? Xianqi had nothing to gain from his death.");
            System.out.println("But Xianqi didn't respond. And Kunwei knew why.");
            
            System.out.print("\n> ... This isn't  [ r e a l ]. ");
            input.nextLine();
            
            System.out.println("\n ... That's right. The inability to make genuine decisions, the truth that Kunwei is already dead, and this strange deterioration of the \"world\"...");
            
            System.out.print("\n> \"And you, Xianqi... this  [ y o u ] is not real, either. So cannot give me answers.\"");
            input.nextLine();
            
            System.out.print("\n> \"You are nothing but a  [ m e m o r y ], and this place is simply a world constructed by my mind.\" ");
            input.nextLine();
            
            System.out.print("\n> \"I am already dead, and everything is already too late.\" ");
            input.nextLine();
            
            System.out.println("\n [ A r e  y o u  s u r e ? ] ");
            
            System.out.print("\n> ... ");
            input.nextLine();
            
            System.out.println("\n [ W h o  i s  t o  s a y  t h a t  m e m o r i e s  a r e  n o t  r e a l ? ] ");
            
            System.out.print("\n> \"Who are you?\" ");
            input.nextLine();
            
            System.out.println("\n [ D o  y o u  w a n t  t o  l i v e ? ] ");
            
            System.out.print("\n> \"... Yes.\" ");
            input.nextLine();
            
            System.out.println("\n [ T h e n  m a k e  a  c o n t r a c t  w i t h  u s . ]");
            
            System.out.print("\n> \"... Haha. Another contract.\" ");
            input.nextLine();
            
            System.out.print("\n> \"It's not like I have any other choice, do I?");
            input.nextLine();
            
            System.out.print("\n> \"... Very well. I will make a contract with you. But who are you?\" ");
            input.nextLine();
            
            System.out.println("\n [ W e  a r e  t h e   W  A  T  C  H  E  R  S .  T h e  d w e l l e r s  o f  t h e   A  B  Y  S  S . ]");
            
            System.out.print("\n> \"I accept your contract.\" ");
            input.nextLine();
            
            // Print out ending screen
            System.out.println("\n\nTRUE ENDING\nContractor of the Abyss.");
        }
        
        else {  // forgiveness ending
            
            // Dialogue
            System.out.println("\nKunwei looks at Xianqi. He didn't know what he felt. Was it rage? Sadness? Longing? This was the person he trusted the most in the world, and the person who stabbed him in the heart the moment their revolution against the Xun dynasty succeeded.");
            
            System.out.print("\n> \"Why?\" ");
            input.nextLine();
            
            System.out.println("\nKunwei didn't understand. Why did Xianqi kill him? Xianqi had nothing to gain from his death.");
            
            System.out.print("\n> \"... How could you?\" ");
            input.nextLine();
            
            System.out.println("\nKunwei marched up to the throne, grabbing Xianqi by the collar of his robes. Xianqi did not respond, only staring at Kunwei silently.");
            
            System.out.print("\n> \"I trusted you more than anything. More than ANYTHING, Xianqi.\" ");
            input.nextLine();
            
            System.out.println("\nKunwei felt tears burn in the corners his eyes. As he watched Xianqi's unresponsive face, the boiling rage and pain in his heart began to dim. He dropped his hands, letting Xianqi slump back onto the throne.");
            
            System.out.print("\n> \"No... perhaps this was for the best. Perhaps this is how it was meant to be.\" ");
            input.nextLine();
            
            System.out.print("\n> \"My cultivation was too powerful. Inevitably, I'd become a threat to your throne, one way or another. Wouldn't I?\" ");
            input.nextLine();
            
            System.out.print("\n> \"... That's why you killed me.\" ");
            input.nextLine();
            
            System.out.print("\n> \"I swore to serve you as my liege. So this shall be the last thing I ever do for you.\" ");
            input.nextLine();
            
            System.out.print("\n> \"... Goodbye, Xianqi.\" ");
            input.nextLine();
            
            // Ending
            System.out.println("\n\nFORGIVENESS\nKunwei's soul disappears forever into the Abyss, and with it, the [ M I N D S P A C E ] crumbles.");
        }
    }
    
    public void runMindspace(Player player) {
        // Initialize Scanner and variables
        Scanner input = new Scanner(System.in);
        boolean alive = true;
        
        // Introduction
        System.out.println("You slowly open your eyes. You see nothing at first, then you realize that nothing is something.");
        System.out.println("You feel dizzy. Who are you? Where are you? Your head spins with questions you cannot answer. To steady yourself, you put your hand on your temple. A translucent window appears before you, catching you off guard.\n");
        System.out.println(player);
        System.out.println("\nYou reach into your sleeve and find that it contains an infinitely large subspace. The subspace is currently empty.");
        
        System.out.print("\n> Look around");
        input.nextLine();
        spawnDescription();
        
        // Make your first choice
        System.out.print("\n1. Look in the mirror\n2. Enter the door on the left\n3. Enter the door on the right\n4. Enter the door behind you\n5. Check your window\n6. Check your inventory\n> ");
        String ans = input.nextLine();
        int choice = 0;
        
        // Just in case the entered thing isn't actually a number
        // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
        try {
            choice = Integer.parseInt(ans);
        } catch (NumberFormatException e) {
            choice = 0;  // random non-answer-choice number
        }
        
        // Choices
        while (choice != 2 && choice != 3) {    // if they aren't entering a different room
        System.out.println();
            if (choice == 1) {
                playerDescription();
            }
            else if (choice == 4) {
                System.out.println("You check the door. It is locked.");
            }
            else if (choice == 5) {
                System.out.println(player);
            }
            else if (choice == 6) {
                player.printInventory();
            }
            else {
                System.out.println("... You m@ke a ch O i ce, but your body fr◼ezes. Y□ u a re un&ble to m□v E.");
                player.changeDoubt(0.1);
            }
            
            // Print options again
            System.out.print("\n1. Look in the mirror\n2. Enter the door on the left\n3. Enter the door on the right\n4. Enter the door behind you\n5. Check your window\n6. Check your inventory\n> ");
            ans = input.nextLine();
            choice = 0;
            
            // Just in case the entered thing isn't actually a number
            // Code devised courtesy of ChatGPT's help, since the content is beyond the scope of the course
            try {
                choice = Integer.parseInt(ans);
            } catch (NumberFormatException e) {
                choice = 0;  // random non-answer-choice number
            }
        }
        
        // Cavern code
        if (choice == 2) {
            alive = cavern(player);  // Runs cavern code
        }
        
        // Bedroom code
        else if (choice == 3) {
            alive = bedroom(player);
        }
        
        // Check to make sure the player isn't already dead, if the player is dead run game over, if the player ISN'T dead then proceed with loop
        if (!alive) {  // if the player is dead
            gameOver();
            return;    // ends runMindspace() prematurely
        }
        
        // Now repeat general spawn --> rooms sequence until the player enters the locked door in the spawn room
        // Spawn room code = the entire above stuff but slightly modified, only exits if the player has died or if player has entered that room
        
        // Call spawn room code here (make sure to add checks for status of aliveness)
        alive = spawnRoom(player);
        
        if (!alive) {  // if the player is dead
            gameOver();
            return;    // ends runMindspace() prematurely
        }
        
        // Call final room code here (add checks for status of aliveness)
        // The only way for the spawnRoom() function to be exited alive is for the player to have unlocked and entered the final room
        bossRoom(player);
    }
}
