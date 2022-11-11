# WordleSolver
A solver that utilizes information theory to solve for 5 letter words in Wordle using as few guesses as possible (credits to Youtube channel 
3Blue1Brown for inspiration. https://www.youtube.com/watch?v=v68zYyaEmEA

## Wordle background
Wordle is a word guessing game in which you must guess the 5-letter word in up to 6 tries. Each guess provides you information in the form of coloring on the letters - 
Green means the letter is correct and in the right spot, Yellow means the letter is in the word but in the wrong spot, and Gray means the letter is not in the word.

EDGE CASES: If there are multiple letters in a word, such as "greet", the guess "meete" will result in Gray-Yellow-Green-Yellow-Gray. This is because there is no "m" in
the word, the first "e" exists but is in the wrong spot, the second "e" is in the right spot, "t" exists in the word, and the last "e" is gray because two "e's" have
already been found.

## The Process
We can find all the possible guesses and answers using inspect element on the website (turned into text files here). With this, we can use information theory to
consistently guess the correct word in a few guesses (Shannon's entropy - we rank words based on their expected information value in bits, and we use the best guess until
we get the answer.

## Details
FindColoring tests that edge cases are covered when correctly coloring guesses. CalculateEntropy computes the entropy of each guess and ranks the best and worst guesses.
WordleSolver solves for the answer using the previous steps.
