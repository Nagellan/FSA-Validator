import javafx.util.Pair;
import java.util.LinkedList;

/**
 * This class represents a state in FSA.
 * Each state has a set of transitions represented as pairs of letter and state connected by it with the original one.
 */
public class State {
    public State(String name) {
        this.name = name;
        this.trans = new LinkedList<>();
    }

    private String name;                                // name of a state
    private LinkedList<Pair<String, State>> trans;      // transitions of state

    /**
     * This is a getter method for 'name' variable.
     *
     * @return value of 'name'
     */
    public String getName() {
        return name;
    }

    public void addTrans(String letter, State state) {
        trans.add(new Pair<>(letter, state));
    }

    public String showTrans(int i) {
        return name + " > " + trans.get(i).getKey() + " > " + trans.get(i).getValue().getName();
    }
}
