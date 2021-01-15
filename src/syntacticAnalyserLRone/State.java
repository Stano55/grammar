package syntacticAnalyserLRone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import FirstandFollow.FirstAndFollowClass;
import grammar.ContextFreeGrammar;
import grammar.Rule;

public class State {
	
	private static int counter= 0;							// counter for monitoring states numbers
	int stateNumber;										// attribute for state number
	HashMap<String, Integer> nextStates;					// attribute for pairs for monitoring to which states we move from exact state
	ArrayList<LRoneItem> lrOneItems;						// attribute for storing LR(1) itmes 
	HashSet<String> transitions;							// attribute for storing symbols on which we make transitions to another state	
	HashSet<String> reductions;								// attribute for storing symbols on which we make reductions
	boolean processed;	
	
	ContextFreeGrammar grammar;
	
	public State(ContextFreeGrammar grammar) {												// constructor for first state, input parameter is context free grammar
		this.grammar= grammar;
		this.lrOneItems = new ArrayList<LRoneItem>();
		this.transitions = new HashSet<String>();
		this.reductions = new HashSet<String>();
		this.nextStates = new HashMap<String, Integer>();
		HashSet<String> expectedSymbols = new HashSet<String>();
		expectedSymbols.add("epsilon");
		LRoneItem lrItem = new LRoneItem(grammar.getStartrule(), expectedSymbols);
		lrOneItems.add(lrItem);
		
		try {
			closure();
		} catch (Exception e) {
			System.out.println("Chyba.");
		}
		try {
			transitionsAndReductions();
		} catch (Exception e) {
			System.out.println("Chyba.");
		}
		this.stateNumber = counter;
		counter++;
	}

	public State(ArrayList<LRoneItem> items, ContextFreeGrammar grammar) {									// constructor when we are moving from previous state with LR(1) items
		this.grammar = grammar;																				// input parameters are set of LR(1) rules which came from previous state and 
		this.lrOneItems = new ArrayList<LRoneItem>();														// context free grammar
		this.lrOneItems.addAll(items);
		this.transitions = new HashSet<String>();
		this.reductions = new HashSet<String>();
		this.nextStates = new HashMap<String, Integer>();
		
		
		
		try {
			closure1();
		} catch (Exception e) {
			System.out.println("Chyba C1.");
		}
		try {
			transitionsAndReductions();
		} catch (Exception e) {
			System.out.println("Chyba TaR.");
		}
		this.stateNumber = counter;
		counter++;
	}
	@SuppressWarnings("static-access")
	private void closure() throws Exception {																		// closure operation for the first state
		FirstAndFollowClass follow = new FirstAndFollowClass();
		for (int i = 0; i< lrOneItems.size(); i++) {
			
			if(!lrOneItems.get(i).processed) {
				Rule rule = lrOneItems.get(i).LRrule;
				for(int j = 0; j < rule.getRightSide().size(); j++) {
					if(rule.getRightSide().get(j) == ".") {
						if (grammar.getNonterminals().contains(rule.getRightSide().get(j+1))) { 
							
						for(Rule r : grammar.getRules()) {
							if(r.getLeftSide().get(0) == rule.getRightSide().get(j+1)) {
								
								String symbol = rule.getRightSide().get(j+1);
								LRoneItem item = new LRoneItem(r, follow.Follow(grammar, symbol));
								lrOneItems.add(item);
							}
						}
						
					}
					}
				}
			}
			lrOneItems.get(i).setProcessed(true);	
		}
	}
	
	
	private void closure1() throws Exception {																	//closure operation for other than first state
		int size = lrOneItems.size();
		
		for (int i = 0; i< size; i++) {
			if(!lrOneItems.get(i).processed) {
				Rule rule = lrOneItems.get(i).LRrule;
				for(int j = 0; j < rule.getRightSide().size(); j++) {
					if(rule.getRightSide().get(j) == ".") {
						
						if(!(j+1 == rule.getRightSide().size())) {
						if (grammar.getNonterminals().contains(rule.getRightSide().get(j+1))) {
						
						for(Rule r : grammar.getRules()) {
							if(r.getLeftSide().get(0) == rule.getRightSide().get(j+1)) {
								LRoneItem item = new LRoneItem(r, lrOneItems.get(i).expectedSymbols);
								lrOneItems.add(item);
								size++;
							}
						}
						
					}
					}
					}
				}
			}
			lrOneItems.get(i).setProcessed(true);	
		}
	}
	
	private void transitionsAndReductions() {																		// Operation which adds symbols to transitions and reductions set
		
		for(int j = 0; j < lrOneItems.size(); j++) {
			LRoneItem item = lrOneItems.get(j);
			ArrayList<String> rside = item.getLRrule().getRightSide();
			
			int i = rside.lastIndexOf(".");
			if(! (rside.indexOf(".") == rside.size()-1)) {
				this.transitions.add(rside.get(i+1));
			} 
			else {
				this.reductions.addAll(item.getExpectedSymbols());
			}
		}
	}
	
	public HashSet<String> getTransitions() {
		return transitions;
	}

	public void setTransitions(HashSet<String> transitions) {
		this.transitions = transitions;
	}

	public HashSet<String> getReductions() {
		return reductions;
	}

	public void setReductions(HashSet<String> reductions) {
		this.reductions = reductions;
	}
	
	public ArrayList<LRoneItem> getLrOneItems() {
		return lrOneItems;
	}

	public void setLrOneItems(ArrayList<LRoneItem> lrOneItems) {
		this.lrOneItems = lrOneItems;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public int getStateNumber() {
		return stateNumber;
	}

	public void setStateNumber(int stateNumber) {
		this.stateNumber = stateNumber;
	}
}
