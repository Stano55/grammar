package syntacticAnalyserLRone;

import java.util.ArrayList;

import grammar.ContextFreeGrammar;
import grammar.Rule;

public class ActionGotoTables {
	ArrayList<State> states = new ArrayList<State>();
	private ArrayList<LRoneItem> newItems = new ArrayList<LRoneItem>();
	private ContextFreeGrammar grammar;
	public ActionGotoTables(ContextFreeGrammar grammar) {
		this.grammar = grammar;
		State state = new State(grammar);
		states.add(state);
		LRoneAutomaton();
		actionTable();
		gotoTable();
	}
	
	
	private void LRoneAutomaton() {
		
		try {
		for(int x = 0; x < states.size(); x++) {
			State s = states.get(x);
			ArrayList<LRoneItem> items = new ArrayList<LRoneItem>();
			items = s.getLrOneItems();
			for(String a : s.getTransitions()) {
				for(int y = 0; y < items.size(); y ++) {
					LRoneItem l = items.get(y);
					int i = l.getLRrule().getRightSide().indexOf(".");
					if( l.getLRrule().getRightSide().indexOf(".") != l.getLRrule().getRightSide().size()-1 && a == l.getLRrule().getRightSide().get(i+1) ) { 
						LRoneItem k = new LRoneItem(l.getLRrule(), l.getExpectedSymbols());
						newItems.add(k);
					}
				}
				State newState = new State(newItems, grammar);
				s.nextStates.put(a, newState.stateNumber);
				states.add(newState);
				newItems.clear();
			}	
		} 
		} catch (Exception e) {
			System.out.println("Chyba tu.");
		}
	}
	private void actionTable() {
		String outPut = format("ACTION");
		for(State s : states) {
			outPut = outPut + format("s" + s.stateNumber);
		}
		System.out.println(outPut);
		for(String t : grammar.getTerminals()) {
			outPut = format(t);
			for(State s : states) {
				if(s.getTransitions().contains(t)) {
					outPut = outPut + format("P");
				} else if (s.getReductions().contains(t)) {
					int i = getRuleNumber(s);
					outPut = outPut + format("R" + i);
				} else {
					outPut = outPut + format("");
				}
			}
			System.out.println(outPut);
		}
		outPut= format("epsilon");
		for(State s : states) {
			if(s.getReductions().contains("epsilon")) {
				outPut = outPut + format("A");
			} else {
				outPut = outPut + format("");
			}
		}
		System.out.println(outPut);
		System.out.println();
	}
	
	private void gotoTable() {
		String outPut = format("GOTO");
		for(State s : states) {
			outPut = outPut + format("s" + s.stateNumber);
		}
		
		System.out.println(outPut);
		for(String t : grammar.getTerminals()) {
			outPut = format(t);
			for(State s : states) {
				if(s.nextStates.containsKey(t)) {
					outPut = outPut + format("s" + s.nextStates.get(t));
				}
				else {
					outPut = outPut + format("");
				}
			}
			System.out.println(outPut);
		}
		for(String t : grammar.getNonterminals()) {
			outPut = format(t);
			for(State s : states) {
				if(s.nextStates.containsKey(t)) {
					outPut = outPut + format("s" + s.nextStates.get(t));
				}
				else {
					outPut = outPut + format("");
				}
			}
			System.out.println(outPut);
		}
		
		
	}
	
	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
	
	private String format(String str) {
		return String.format("%1$"+7+ "s", str);
		
	}
	
	private int getRuleNumber(State s) {
		ArrayList<String> rightSide = new ArrayList<String>();
		for(LRoneItem i : s.getLrOneItems()) {
			if(i.getLRrule().getRightSide().indexOf(".") == i.getLRrule().getRightSide().size()-1) {
				rightSide.addAll(i.getLRrule().getRightSide());
				rightSide.remove(".");
			}
		}
			int i = 0;
		for(Rule r : grammar.getRules()) {
			if(r.getRightSide().equals(rightSide)) {
				return i;
			}
			i++;
		}
		return 0;
	}
	
}

