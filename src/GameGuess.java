import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class GameGuess {
    boolean hasSomethingEmpty = false;
    boolean isAllCorrect = false;
    boolean hasCorrectBalls = false;
    int correctBalls = 0;
    boolean hasCorrectPositions = false;
    int correctPositions = 0;

    Integer[] numericalPins = {correctBalls, correctPositions};
    Boolean[] guessStatuses = {hasSomethingEmpty, hasCorrectBalls, hasCorrectPositions, isAllCorrect};

    public GameGuess(ArrayList<Integer> Pass, ArrayList<Integer> Guess){
        Integer[] pass = new Integer[Pass.size()];
        Integer[] guess = new Integer[Guess.size()];
        pass = Pass.toArray(pass);
        guess = Guess.toArray(guess);
        Set<Integer> setA = new HashSet<Integer>(Arrays.asList(pass));
        Set<Integer> setB = new HashSet<Integer>(Arrays.asList(guess));

        for(int i = 0; i < pass.length; i++){
            if (guess[i] == 9) {
                hasSomethingEmpty = true;
                break;
            }
        }
        guessStatuses[0] = hasSomethingEmpty;

        if (!hasSomethingEmpty){
            Set<Integer> setC = intersection(setA, setB);
            if (!setC.isEmpty()){
                hasCorrectBalls = true;
                guessStatuses[1] = hasCorrectBalls;
                correctBalls = setC.size();
                numericalPins[0] = correctBalls;
                for(int i = 0; i < pass.length; i++){
                    if (Pass.get(i) == Guess.get(i)){
                        hasCorrectPositions = true;
                        correctPositions++;
                    }
                }
                if (correctPositions == 4){isAllCorrect=true;}
                guessStatuses[2] = hasCorrectPositions;
                guessStatuses[3] = isAllCorrect;
                numericalPins[1] = correctPositions;
            }
        }
    }

    public static Set<Integer> intersection(Set<Integer> a, Set<Integer> b){
        // unnecessary; just an optimization to iterate over the smaller set
        if (a.size() > b.size()) {
            return intersection(b, a);
        }

        Set<Integer> results = new HashSet<>();

        for (Integer element : a) {
            if (b.contains(element)) {
                results.add(element);
            }
        }

        return results;
    }

    public Integer[] getNumericalData(){
        return numericalPins;
    }

    public Boolean[] getStatus() {
        return guessStatuses;
    }

    public static void main(String[] args){
//        ArrayList<Integer> lock = new ArrayList<Integer>();
//        ArrayList<Integer> key = new ArrayList<Integer>();
//
//        lock.add(2);
//        lock.add(0);
//        lock.add(5);
//        lock.add(1);
//
//        for (int i=0; i<3;i++) {
//            key.add(i);
//        }
//        key.add(5);
//
//        GameGuess game = new GameGuess(lock, key);
//
//        System.out.println(Arrays.toString(game.getStatus()));
//        System.out.println(Arrays.toString(game.getNumericalData()));

    }

}
