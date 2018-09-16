import java.util.*;

/**
 * This class represents an FSA. It checks the FSA and returns an error if exists.
 *
 * @author Irek Nazmiev <i.nazmiev@innopolis.ru> B17-05, Innopolis University
 */
public class Validator {
    public Validator(Scanner in) {
            this.result = "";                       // result of validation

            HashMap fileData = formatInFile(in);

            this.states = (LinkedList<State>) fileData.get("states");
            this.alpha = (LinkedList<String>) fileData.get("alpha");
            this.initState = (State) fileData.get("initState");
            this.finState = (State) fileData.get("finState");
            this.trans = (LinkedList<LinkedList<State>>) fileData.get("trans");
    }

    private LinkedList<State> states;               // set of states of FSA
    private LinkedList<String> alpha;               // alphabet of FST
    private State initState;                        // initial state of FST
    private State finState;                         // finish state of FST
    private LinkedList<LinkedList<State>> trans;    // transitions of FST
    private String result;                          // result of validation

    /**
     * This method formats the input file data into the convenient format of Linked Lists and necessary objects.
     * In addition, it also checks on an Error 5.
     *
     * @param in - input file with data to format
     * @return HashMap with formatted data
     */
    private HashMap formatInFile(Scanner in) {
        HashMap fileData = new HashMap();

        String statesStr = in.next();           // read lines from the input file
        String alphaStr = in.next();
        String initStateStr = in.next();
        String finStateStr = in.next();
        String transStr = in.next();

        if (fileIsMalformed(statesStr, alphaStr, initStateStr, finStateStr, transStr)) {
            this.result = "E5: Input file is malformed";
            return fileData;
        }

        LinkedList<State> states = formatStates(statesStr);     // reformat lines
        LinkedList<String> alpha = formatAlpha(alphaStr);
        String initStateName = initStateStr.substring(9, initStateStr.length() - 1);
        String finStateName = finStateStr.substring(8, finStateStr.length() - 1);
        LinkedList<LinkedList<State>> trans = formatTrans(transStr, states);

        fileData.put("states", states);
        fileData.put("alpha", alpha);
        fileData.put("initState", findByName(initStateName, states));
        fileData.put("finState", findByName(finStateName, states));
        fileData.put("trans", trans);

        return fileData;
    }

    /**
     * This method checks whether input file is malformed or not.
     *
     * @param statesStr - 1st line of in file
     * @param alphaStr - 2nd line of in file
     * @param initStateStr - 3rd line of in file
     * @param finStateStr - 4th line of in file
     * @param transStr - 5th line of on file
     * @return boolean expression answering the given question
     */
    private boolean fileIsMalformed(String statesStr, String alphaStr,
                                    String initStateStr, String finStateStr, String transStr) {
        return !(statesStr.substring(0, 8).equals("states={") & statesStr.charAt(statesStr.length() - 1) == '}'
                & alphaStr.substring(0, 7).equals("alpha={") & alphaStr.charAt(alphaStr.length() - 1) == '}'
                & initStateStr.substring(0, 9).equals("init.st={") & initStateStr.charAt(initStateStr.length() - 1) == '}'
                & finStateStr.substring(0, 8).equals("fin.st={") & finStateStr.charAt(finStateStr.length() - 1) == '}'
                & transStr.substring(0, 7).equals("trans={") & transStr.charAt(transStr.length() - 1) == '}');
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

            if (state1 != null)
                state1.addTrans(transSep[1], state2);
            if (state2 != null & state1 != state2)
                state2.addTrans(transSep[1], state1);

            resTrans.add(new LinkedList(Arrays.asList(state1, state2)));
        }
        return resTrans;
    }

    /**
     * This method finds and returns state in State list by the name. It also checks Error 1.
     *
     * @param name - name of state to find
     * @return state with given name
     */
    private State findByName(String name, LinkedList<State> states) {
        for (State state : states) {
            if (state.getName().equals(name))
                return state;
        }
        if (!name.equals(""))
            this.result += "E1: A state s is not in set of states\n";

        return null;
    }

    /**
     * This method starts the validation of FSA.
     *
     * @return String with the result of validation which later will be printed into the output file
     */
    public String start() {
        if (!result.isEmpty() && result.substring(0, 2).equals("E5"))
            return result;

        return result;
    }
}