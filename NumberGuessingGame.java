
import java.util.*;
public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to the Number Guessing Game...");
        int lowerBound = 1;
        int upperBound = 100;
        int rounds = 3;
        int totalScore = 0;

        for (int round = 1; round <= rounds; round++) {
            System.out.println("Round " + round + " - Guess a number between " + lowerBound + " and " + upperBound);
            int targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
            int attempts = 0;
            boolean guessedCorrectly = false;

            while (!guessedCorrectly) {
                System.out.print("Enter your guess: ");
                int userGuess = sc.nextInt();
                attempts++;

                if (userGuess == targetNumber) {
                    System.out.println("Congratulations.. You have guessed the correct number in " + attempts + " attempts.");
                    int roundScore = calculateScore(attempts);
                    totalScore += roundScore;
                    System.out.println("Round Score: " + roundScore + " | Total Score: " + totalScore);
                    guessedCorrectly = true;
                } else if (userGuess < targetNumber) {
                    System.out.println("the Number is Too low. Try again.");
                } else {
                    System.out.println("the Number is Too high. Try again.");
                }

                if (attempts == 5) {
                    System.out.println("Sorry, you've reached the maximum number of attempts..");
                    System.out.println("The correct number was: " + targetNumber);
                    break;
                }
            }
        }

        System.out.println("Game Over.. Your final score is: " + totalScore);
    }

    private static int calculateScore(int attempts) {
        if (attempts <= 3) {
            return 10;
        } else if (attempts <= 5) {
            return 5;
        } else {
            return 2;
        }
    }
}
