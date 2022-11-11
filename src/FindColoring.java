import java.util.*;
import java.io.*;

public class FindColoring {

    final static int GREEN = 2;
    final static int YELLOW = 1;
    final static int GRAY = 0;

    public static void main(String[] args) {
        // test 1, all gray
        String guess1 = "silky";
        String answer1 = "crane";
        int[] coloring1 = computeColoring(guess1, answer1);
        int[] coloring1answer = new int[] {0, 0, 0, 0, 0};
        checkAnswer(1, guess1, answer1, coloring1, coloring1answer);

        // test 2, all green
        String guess2 = "crane";
        String answer2 = "crane";
        int[] coloring2 = computeColoring(guess2, answer2);
        int[] coloring2answer = new int[] {2, 2, 2, 2, 2};
        checkAnswer(2, guess2, answer2, coloring2, coloring2answer);

        // test 3, all yellow
        String guess3 = "nails";
        String answer3 = "snail";
        int[] coloring3 = computeColoring(guess3, answer3);
        int[] coloring3answer = new int[] {1, 1, 1, 1, 1};
        checkAnswer(3, guess3, answer3, coloring3, coloring3answer);

        // test 4, greens and grays
        String guess4 = "blade";
        String answer4 = "bloke";
        int[] coloring4 = computeColoring(guess4, answer4);
        int[] coloring4answer = new int[] {2, 2, 0, 0, 2};
        checkAnswer(4, guess4, answer4, coloring4, coloring4answer);

        // test 5, double letters
        String guess5 = "theme";
        String answer5 = "three";
        int[] coloring5 = computeColoring(guess5, answer5);
        int[] coloring5answer = new int[] {2, 2, 1, 0, 2};
        checkAnswer(5, guess5, answer5, coloring5, coloring5answer);

        // test 6, guess has more of a letter that is in answer
        String guess6 = "emcee";
        String answer6 = "three";
        int[] coloring6 = computeColoring(guess6, answer6);
        int[] coloring6answer = new int[] {0, 0, 0, 2, 2};
        checkAnswer(6, guess6, answer6, coloring6, coloring6answer);

        // test 7, guess has more of a letter that is in answer
        String guess7 = "emcee";
        String answer7 = "theme";
        int[] coloring7 = computeColoring(guess7, answer7);
        int[] coloring7answer = new int[] {1, 1, 0, 0, 2};
        checkAnswer(7, guess7, answer7, coloring7, coloring7answer);

        // test 8, double yellows
        String guess8 = "ledge";
        String answer8 = "speed";
        int[] coloring8 = computeColoring(guess8, answer8);
        int[] coloring8answer = new int[] {0, 1, 1, 0, 1};
        checkAnswer(8, guess8, answer8, coloring8, coloring8answer);

        // test 9, double greens and a yellow
        String guess9 = "weary";
        String answer9 = "wordy";
        int[] coloring9 = computeColoring(guess9, answer9);
        int[] coloring9answer = new int[] {2, 0, 0, 1, 2};
        checkAnswer(9, guess9, answer9, coloring9, coloring9answer);
    }

    public static int[] computeColoring(String guess, String answer) {
        int[] coloring = new int[5];
        int[] placeholder = new int[5];
        // Steps for completion:
        // 1. preprocessing - set up coloring array, process guess and answer strings by letters and letter counts
        // 2. mark greens - simpler to complete this in the first pass of the words, letter and position are correct
        // 3. mark yellows - be extra careful with how double, triple, ... letters are handled
        // 4. mark grays - simpler to complete this last (or second), letter is not in answer

        for (int i = 0; i < 5; i++) {
            if (Objects.equals(guess.charAt(i), answer.charAt(i))) {
                coloring[i] = 2;
                placeholder[i] = 1;
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (coloring[i] != 2 & coloring[j] != 2 & coloring[i] != 1) {
                    if (Objects.equals(guess.charAt(i), answer.charAt(j)) & placeholder[j] != 1) {
                        coloring[i] = 1;
                        placeholder[j] = 1;
                    }
                }
            }
        }
        //
        return coloring;
    }

    public static void checkAnswer(int testNum, String guess, String answer, int[] coloring, int[] coloringAnswer) {
        // check format
        if (coloring.length != 5) {
            System.out.println("ERROR: coloring array is not length 5.");
        }
        for (int i = 0; i < 5; i++) {
            if (coloring[i] < 0 || coloring[i] > 2) {
                System.out.println("ERROR: coloring values are not between 0 and 2.");
            }
        }

        // check markings
        boolean correct = true;
        for (int i = 0; i < 5; i++) {
            if (coloring[i] != coloringAnswer[i]) {
                correct = false;
                break;
            }
        }

        // printing output
        System.out.println();
        if (correct) {
            System.out.println("Test " + testNum + " is CORRECT.");
        } else {
            System.out.println("Test " + testNum + " is INCORRECT.");
        }
        System.out.println("Guess word is  " + guess);
        System.out.println("Answer word is " + answer);
        System.out.print("Guess coloring:  ");
        for (int i = 0; i < 5; i++) {
            System.out.print(coloring[i] + " ");
        }
        System.out.println();
        System.out.print("Answer coloring: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(coloringAnswer[i] + " ");
        }
        System.out.println();
        System.out.println();
    }

}
