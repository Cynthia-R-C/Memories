import java.util.ArrayList;

public class Demon {
    double health;
    double power;
    double maxHealth;
    ArrayList<String> moves = new ArrayList<String>();
    
    // Constructor - initializes health and power
    public Demon(Player player) {
        power = player.getEmotion()/1.5;
        health = 1 + power*2;
        maxHealth = health;
        
        // Add moves
        moves.add("slashs");
        moves.add("stabs");
        moves.add("claws");
        moves.add("slices");
        moves.add("sinks its teeth into");
    }
    
    // Returns a boolean of whether the demon can see the player
    public boolean canSee(Player player) {
        return player.getEmotion() != 0;
    }
    
    // Simulates a demon attack
    public void attack(Player player) {
        
        // Select the move
        int loc = (int)(Math.random() * moves.size());
        String move = moves.get(loc);
        
        // Run the dialogue
        System.out.println("The demon " + move + " you. -" + power + " Life.");
        
        // Change player health
        player.changeHealth(-1*power);
    }
    
    // Retrieves the health value
    public double getHealth() {
        return health;
    }
    
    // Changes the health of the demon by n
    public void changeHealth(double n) {
        // Following if statements make sure the health never exceeds 10 or drops below 0
        if (health + n >= maxHealth) {
            health = maxHealth;
        }
        else if (health + n <= 0) {
            health = 0;
        }
        else {
            health += n;
        }
    }
}
