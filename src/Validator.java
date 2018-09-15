import java.util.LinkedList;

/**
 * This class represents an FSA. It checks the FSA and returns an error if exists
 *
 * @author Irek Nazmiev <i.nazmiev@innopolis.ru> B17-05, Innopolis University
 */
public class Validator {
    public Validator() {
        this.states = new LinkedList<>();
        this.alpha = new LinkedList<>();
        this.initState = new State();
        this.finState = new State();
        this.trans = new LinkedList<>();
    }

    private LinkedList<State> states;   // set of states of FSA

    private LinkedList<String> alpha;   // alphabet of FST

    private State initState;            // initial state of FST

    private State finState;             // finish state of FST

    private LinkedList<String> trans;   // transitions of FST
}
