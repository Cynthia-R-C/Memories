public class Runner
{
    public static void main(String[] args)
    {
        // Initialize things
        Player you = new Player();
        Mindspace abyss = new Mindspace();
        
        // Run the game
        abyss.runMindspace(you);
    }
}
