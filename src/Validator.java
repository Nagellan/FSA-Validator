import java.util.*;

/**
 * This class represents an FSA. It checks the FSA and returns an error if exists.
 *
 * @author Irek Nazmiev <i.nazmiev@innopolis.ru> B17-05, Innopolis University
 */
public class Validator {
    public Validator(Scanner in) {
        Dictionary fileData = formatInFile(in);

        this.states = (LinkedList<State>)fileData.get("states");
        this.alpha = (LinkedList<String>)fileData.get("alpha");
        this.initState = (State)fileData.get("initState");
        this.finState = (State)fileData.get("finState");
        this.trans = (LinkedList<LinkedList<State>>) fileData.get("trans");
    }

    private LinkedList<State> states;   // set of states of FSA
    private LinkedList<String> alpha;   // alphabet of FST
    private State initState;            // initial state of FST
    private State finState;             // finish state of FST
    private LinkedList<LinkedList<State>> trans;   // transitions of FST

    /**
     * This method formats the input file data into the convenient format of Linked Lists and necessary objects.
     *
     * @param in - input file with data to format
     * @return Dictionary with formatted data
     */
    private Dictionary formatInFile(Scanner in) {
        Dictionary fileData = new Hashtable();

        String statesStr = in.next();           // read lines from the input file
        String alphaStr = in.next();
        String initStateStr = in.next();
        String finStateStr = in.next();
        String transStr = in.next();

        LinkedList<State> states = formatStates(statesStr);     // reformat lines
        LinkedList<String> alpha = formatAlpha(alphaStr);
        String initStateName = initStateStr.substring(9, initStateStr.length() - 1);
        String finStateName = finStateStr.substring(8, finStateStr.length() - 1);
        LinkedList<LinkedList<State>> trans = formatTrans(transStr, states);

        fileData.put("states", states);
        fileData.put("alpha", alpha);
        fileData.put("initState", findByName(initStateName, states));
        //fileData.put("finState", findByName(finStateName, states));
        fileData.put("trans", trans);

        return fileData;
    }

    /**
     * This method casts the given string with states' description (1st line of input file) to a convenient format -
     * linked list of States.
     *
     * @param statesStr - 1st line of input file with states' description
     * @return LinkedList of States
     */
    private LinkedList<State> formatStates(String statesStr) {
        LinkedList<State> resStates = new LinkedList<>();
        String[] states = statesStr.substring(8, statesStr.length() - 1).split(",");

        for (String stateName : states) {
            resStates.add(new State(stateName));
        }

        return resStates;
    }

    /**
     * This method casts the given string with alphabet's description (2nd line of input file) to a convenient format -
     * linked list of letters (words).
     *
     * @param alphaStr - 2nd line of input file with alphabet's description
     * @return LinkedList of Strings (words, letters)
     */
    private LinkedList<String> formatAlpha(String alphaStr) {
        LinkedList<String> resAlpha = new LinkedList<>();
        String[] alpha = alphaStr.substring(7, alphaStr.length() - 1).split(",");

        resAlpha.addAll(Arrays.asList(alpha));

        return resAlpha;
    }

    /**
     * This method casts the given string with transition's description (5th line of input file) to a convenient format -
     * linked list containing a list of 2 states - connected by this transition.
     * Both these states store a transition to another states.
     *
     * @param transStr - 5th line of input file with transition's description
     * @return LinkedList of pairs: connected first and second states
     */
    private LinkedList<LinkedList<State>> formatTrans(String transStr, LinkedList<State> states) {
        LinkedList<LinkedList<State>> resTrans = new LinkedList<>();
        String[] trans = transStr.substring(7, transStr.length() - 1).split(",");

        for (String transitionStr : trans) {
            String[] transSep = transitionStr.split(">");
            State state1 = findByName(transSep[0], states);
            State state2 = findByName(transSep[2], states);

            state1.addTrans(transSep[1], state2);
            if (state1 != state2)
                state2.addTrans(transSep[1], state1);

            resTrans.add(new LinkedList(Arrays.asList(state1, state2)));
        }
        return resTrans;
    }

    /**
     * This method finds and returns state in State list by the name
     *
     * @param name - name of state to find
     * @return state with given name
     */
    private State findByName(String name, LinkedList<State> states) {
        for (State state : states) {
            if (state.getName().equals(name))
                return state;
        }

        return null;
    }

    /**
     * This method starts the validation of FSA.
     *
     * @return String with the result of validation which later will be printed into the output file
     */
    public String start() {
        return "";
    }
}