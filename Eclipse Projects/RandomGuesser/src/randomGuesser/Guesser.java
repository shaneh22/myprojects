package randomGuesser;

public class Guesser {

	public static void main(String[] args) {
		int DEATH_NUMBER = 0xdead;
		int range = 0xffffffff;
		int guess = 0;
		long startTime = System.currentTimeMillis();
		int survivalCount = 0;
		while(guess != DEATH_NUMBER) {
			guess = (int) (Math.random() * range);
			survivalCount++;
		}
		System.out.println("0xDEAD");
		System.out.println("In "+ survivalCount + " tries");
		System.out.println("Survived " + ((System.currentTimeMillis() - startTime)/1000) + "s");
	}

}
