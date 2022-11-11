import java.util.*;
import java.io.*;
import java.lang.Math;

public class CalculateEntropy {

    static ArrayList<String> wordleAnswers;
    static ArrayList<String> wordleGuesses;
    static ArrayList<WordEntropy> initialEntropies;

    public static void main(String[] args) throws IOException {
        // reads in valid wordle answers file
        Scanner s = new Scanner(new File("C:\\Users\\Andrew Kim\\OneDrive\\Desktop\\Projects\\WordleSolver\\src\\wordle_answers.txt")).useDelimiter(",");
        wordleAnswers = new ArrayList<>();
        while (s.hasNext()) {
            wordleAnswers.add(s.next().substring(1, 6));
        }

        // reads in valid wordle guesses file
        s = new Scanner(new File("C:\\Users\\Andrew Kim\\OneDrive\\Desktop\\Projects\\WordleSolver\\src\\wordle_guesses.txt")).useDelimiter(",");
        wordleGuesses = new ArrayList<>();
        while (s.hasNext()) {
            wordleGuesses.add(s.next().substring(1, 6));
        }
        s.close();

        initialEntropies = calculateEntropy(wordleGuesses, wordleAnswers);
        Collections.sort(initialEntropies, new EntropyRank());

        bestWordleGuesses();
        worstWordleGuesses();
    }

    // @params: (ArrayList<String>) guesses is the set of all possible wordle guesses
    //          (ArrayList<String>) answers is the subset of valid wordle answers in consideration
    // @return: (ArrayList<WordEntropy>) the list of guesses and their calculated entropies
    //              entropy is defined as expected information value, which is calculated from 
    //              a summation of P(coloring) * -log_2(P(coloring))
    //              P(coloring) = # of answers that match the coloring / total # of possible answers
    //              information value is measured in bits, 1 bit of information halves the set of possible answers
    public static ArrayList<WordEntropy> calculateEntropy(ArrayList<String> guesses, ArrayList<String> answers) {

        ArrayList<WordEntropy> entropies = new ArrayList<>();

        for (String guess: guesses) {
            int[] histogram = new int[243];
            for (String answer: answers) {
                int[] color = computeColoring(guess, answer);
                int index = computeIndex(color);
                histogram[index] += 1;
            }
            double entropy = 0;
            for (double coloringnumanswers : histogram) {
                double probability = coloringnumanswers / answers.size();
                if (probability > 0) {
                    entropy += -1 * probability * (Math.log(probability) / Math.log(2));
                }
            }
            entropies.add(new WordEntropy(guess, entropy));
        }

        return entropies;
    }

    // @params: (String) guess word, (String) answer word
    // @return: (int[]) array of a 5 digit base-3 number that uniquely represents a coloring index
    //          gray = 0, yellow = 1, green = 2 and each digit corresponds to a letter
    //          format is {c_0, c_1, c_2, c_3, c_4} = "C R A N E", c_0 = C coloring, c_1 = R coloring, ...
    public static int[] computeColoring(String guess, String answer) {
        int[] coloring = new int[5];
        int[] placeholder = new int[5];

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
        return coloring;
    }

    // @params: (int[]) array representing a 5 digit base-3 coloring
    // @return: (int) the coloring index in base 10, to be used as array indice
    //          array {c_0, c_1, c_2, c_3, c_4} = c_4 * 3^4 + c_3 * 3^3 + c_2 * 3^2 + c_1 * 3^1 + c_0 * 3^0
    public static int computeIndex(int[] coloring) {
        int index = 0;
        for (int i = 0; i < 5; i++) {
            index += coloring[i] * Math.pow(3, i);
        }

        return index;
    }

    // prints solution output
    public static void bestWordleGuesses() {
        System.out.println("Best Wordle Openers:");
        for (int i = 0; i < 10; i++) {
            WordEntropy we = initialEntropies.get(i);
            System.out.println("Rank: " + (i+1) + ", Word: " + we.getWord() + ", Entropy: " + we.getEntropy());
        }
        System.out.println();
    }

    // prints solution output
    public static void worstWordleGuesses() {
        System.out.println("Worst Wordle Openers:");
        for (int i = wordleGuesses.size() - 1; i >= wordleGuesses.size() - 10; i--) {
            WordEntropy we = initialEntropies.get(i);
            System.out.println("Rank: " + (i+1) + ", Word: " + we.getWord() + ", Entropy: " + we.getEntropy());
        }
        System.out.println();
    }
}

// Word Entropy class used for storing word and entropy pairs
class WordEntropy {
    String word;
    double entropy;

    public WordEntropy(String w, double e) {
        this.word = w;
        this.entropy = e;
    }

    public String getWord() {
        return this.word;
    }

    public double getEntropy() {
        return this.entropy;
    }
}

// custom comparator for sorting by entropy
class EntropyRank implements Comparator<WordEntropy> {
    @Override
    public int compare(WordEntropy we1, WordEntropy we2) {
        if(we2.getEntropy() > we1.getEntropy()) {
            return 1;
        } else if (we1.getEntropy() > we2.getEntropy()) {
            return -1;
        } else {
            return we1.getWord().compareTo(we2.getWord());
        }
    }
}
