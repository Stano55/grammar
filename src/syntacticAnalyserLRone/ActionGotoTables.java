package syntacticAnalyserLRone;

import java.util.ArrayList;

import grammar.ContextFreeGrammar;

public class ActionGotoTables {
	ArrayList<State> states = new ArrayList<State>();
	private ArrayList<LRoneItem> newItems = new ArrayList<LRoneItem>();
	private ContextFreeGrammar grammar;
	public ActionGotoTables(ContextFreeGrammar grammar) {
		this.grammar = grammar;
		State state = new State(grammar);
		states.add(state);
		LRoneAutomaton();
	}
	
	
	private void LRoneAutomaton() {
		
		for(int x = 0; x < states.size(); x++) {
			State s = states.get(x);
			ArrayList<LRoneItem> items = new ArrayList<LRoneItem>();
			items = s.getLrOneItems();
			for(String a : s.getTransitions()) {
				for(int y = 0; y < items.size(); y ++) {
					LRoneItem l = items.get(y);
					int i = l.getLRrule().getRightSide().indexOf(".");
					if(a == l.getLRrule().getRightSide().get(i+1) ) { 
						LRoneItem k = new LRoneItem(l.getLRrule(), l.getExpectedSymbols());
						newItems.add(k);
					}
				}
				State newState = new State(newItems, grammar);
				newState.previousState = s.stateNumber;
				newState.symbol = a;
				states.add(newState);
				newItems.clear();
			}	
		}
		
	}
	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
}

