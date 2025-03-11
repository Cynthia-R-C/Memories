import java.util.ArrayList;

public class Player {
    double health;
    double et;
    double understanding;
    double doubt;
    double power;
    ArrayList<String> inventory = new ArrayList<String>();
    
    // Constructer; sets default values
    public Player() {
        health = 10;
        et = 0;
        understanding = 0;
        doubt = 0;
        power = 1;
    }
    
    // Prints out the name and stats of a player.
    public String toString() {
        return "Name: ???" + "\nLife: " + health + "/10\nEmotional Turbulence: " + et + "/10\nUnderstanding: " + understanding + "/10\n???: " + doubt + "/?" + "\nPower: " + power + "/10";
    }
    
    // Returns the inventory of the player
    public ArrayList<String> getInventory() {
        return inventory;
    }
    
    // Prints a user friendly version of the inventory
    public void printInventory() {
        
        // If the inventory is empty
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        }
        
        // If it's not empty
        else {
            String s = "Current items in inventory: ";
            for (String item : inventory) {
                s += item + ", ";
            }
            String info = s.substring(0,s.length()-2); // cuts off the last ", " part of the string
            System.out.println(info);
        }
    }
    
    // Adds an item to the inventory
    public void addItem(String item) {
        inventory.add(item);
    }
    
    // Removes an item from the inventory
    public void removeItem(String item) {
        // First find the index of the item - assumes there aren't duplicates
        int loc = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).equals(item)) {
                loc = i;
            }
        }
        inventory.remove(loc);
    }
    
    // Checks if the player has an item in their inventory
    public boolean hasItem(String item) {
        for (String thing : inventory) {
            if (thing.equals(item)) {
                return true;
            }
        }
        return false;
    }
    
    // Retrieves the health of a player
    public double getHealth() {
        return health;
    }
    
    // Changes the health of the player by n
    public void changeHealth(double n) {
        // Following if statements make sure the health never exceeds 10 or drops below 0
        if (health + n >= 10) {
            health = 10;
        }
        else if (health + n <= 0) {
            health = 0;
        }
        else {
            health += n;
        }
    }
    
    // Retrieves the emotional turbulence value of the player
    public double getEmotion() {
        return et;
    }
    
    // Changes the emotional turbulence of the player by n
    public void changeEmotion(double n) {
        // Following if statements make sure the et never exceeds 10 or drops below 0
        if (et + n >= 10) {
            et = 10;
        }
        else if (et + n <= 0) {
            et = 0;
        }
        else {
            et += n;
        }
    }
    
    // Retrieves the understanding value of the player
    public double getUnderstanding() {
        return understanding;
    }
    
    // Changes the understanding of the player by n
    public void changeUnderstanding(double n) {
        // Following if statements make sure the understanding never exceeds 10 or drops below 0
        if (understanding + n >= 10) {
            understanding = 10;
        }
        else if (understanding + n <= 0) {
            understanding = 0;
        }
        else {
            understanding += n;
        }
    }
    
    // Retrieves the doubt value of the player
    public double getDoubt() {
        return doubt;
    }
    
    // Changes the doubt of the player by n
    public void changeDoubt(double n) {
        // Following if statements make sure the doubt never exceeds 5 or drops below 0
        if (doubt + n >= 5) {
            doubt = 5;
        }
        else if (doubt + n <= 0) {
            doubt = 0;
        }
        else {
            doubt += n;
        }
    }
    
    // Retrieves the power value of the player
    public double getPower() {
        return power;
    }
    
    // Changes the power of the player by n
    public void changePower(double n) {
        // Following if statements make sure the doubt never exceeds 5 or drops below 0
        if (power + n >= 5) {
            power = 5;
        }
        else if (power + n <= 0) {
            power = 0;
        }
        else {
            power += n;
        }
    }
    
    // Simulate attacking a demon
    public void attack(Demon demon) {
        System.out.println("You attack the demon. The demon loses some existence.");
        demon.changeHealth(-1*power);
    }
    
}
