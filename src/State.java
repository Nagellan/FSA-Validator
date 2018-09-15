import javafx.util.Pair;
import java.util.LinkedList;

/**
 * This class represents a state in FSA
 * Each state has a set of transitions represented as pairs of letter and state connected by it with the original one.
 */
public class State {
    public State() {
        this.trans = new LinkedList<>();
    }

    private LinkedList<Pair<String, State>> trans;

    public void addTrans(String letter, State toState) {
        this.trans.add(new Pair<>(letter, toState));
    }
}
