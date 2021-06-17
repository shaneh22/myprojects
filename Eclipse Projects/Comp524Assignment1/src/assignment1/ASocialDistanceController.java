package assignment1;
import java.util.Scanner;

public class ASocialDistanceController implements SocialDistanceController {
	@Override
	public void processInput() {
		Scanner scan = new Scanner(System.in);
		int distance, duration, exhalationLevel;
		for(;;) {
			System.out.println(ENTER_DATA);
			System.out.println(DISTANCE);
			distance = scan.nextInt();
			if(distance == -1) {
				System.out.println(QUIT);
				scan.close();
				break;
			}
			System.out.println(DURATION);
			duration = scan.nextInt();
			System.out.println(EXHALATION_LEVEL);
			exhalationLevel = scan.nextInt();
			SocialDistanceModelFactory.getSingleton().setValues(duration, distance, exhalationLevel);
		}
		
	}

}
