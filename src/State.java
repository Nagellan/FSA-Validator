import javafx.util.Pair;
import java.util.LinkedList;

/**
 * This class represents a state in FSA
 * Each state has a set of transitions represented as pairs of letter and state connected by it with the original one.
 */
public class State {
    public State(String name) {
        this.name = name;
        this.trans = new LinkedList<>();
    }

    private String name;
    private LinkedList<Pair<String, State>> trans;

    public void addTrans(String letter, State toState) {
        this.trans.add(new Pair<>(letter, toState));
    }

    public String getName() {
        return name;
    }
}
