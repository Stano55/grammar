package syntacticAnalyserLRone;

import java.util.ArrayList;
import java.util.HashSet;
import FirstandFollow.FirstAndFollowClass;
import grammar.ContextFreeGrammar;
import grammar.Rule;

public class State {
	
	private static int counter= 0;
	int previousState;
	String symbol;
	int stateNumber;
	ArrayList<LRoneItem> lrOneItems;
	HashSet<String> transitions;
	HashSet<String> reductions;
	boolean processed;	
	
	ContextFreeGrammar grammar;
	
	public State(ContextFreeGrammar grammar) {												// constructor for first state		
		this.grammar= grammar;
		this.lrOneItems = new ArrayList<LRoneItem>();
		this.transitions = new HashSet<String>();
		this.reductions = new HashSet<String>();
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

	public State(ArrayList<LRoneItem> items, ContextFreeGrammar grammar) {									// constructor when state already contains LR(1) items
		this.grammar = grammar;
		this.lrOneItems = new ArrayList<LRoneItem>();
		this.lrOneItems.addAll(items);
		this.transitions = new HashSet<String>();
		this.reductions = new HashSet<String>();
		
		
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
	private void closure() throws Exception {
		FirstAndFollowClass follow = new FirstAndFollowClass();
		for (int i = 0; i< lrOneItems.size(); i++) {
			
			if(!lrOneItems.get(i).processed) {
				Rule rule = lrOneItems.get(i).LRrule;
				for(int j = 0; j < rule.getRightSide().size(); j++) {
					if(rule.getRightSide().get(j) == ".") {
						if (grammar.getNonterminals().contains(rule.getRightSide().get(j+1))) { 					// chceck if next symbol after "." is nonterminal	
							
						for(Rule r : grammar.getRules()) {
							if(r.getLeftSide().get(0) == rule.getRightSide().get(j+1)) {
								
								String symbol = r.getLeftSide().get(0);
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
	
	
	private void closure1() throws Exception {
		int size = lrOneItems.size();
		
		for (int i = 0; i< size; i++) {
			if(!lrOneItems.get(i).processed) {
				Rule rule = lrOneItems.get(i).LRrule;
				for(int j = 0; j < rule.getRightSide().size(); j++) {
					if(rule.getRightSide().get(j) == ".") {
						
						if(!(j+1 == rule.getRightSide().size())) {
						if (grammar.getNonterminals().contains(rule.getRightSide().get(j+1))) { 					// chceck if next symbol after "." is nonterminal	
						
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
	
	private void transitionsAndReductions() {
		
		for(int j = 0; j < lrOneItems.size(); j++) {
			LRoneItem item = lrOneItems.get(j);
			ArrayList<String> rside = item.getLRrule().getRightSide();
			
			int i = rside.lastIndexOf(".");
			System.out.println(i + "  " + rside.size());
			if(! (rside.indexOf(".") == rside.size()-1)) {
				this.transitions.add(rside.get(i+1));
			} 
			else {
				this.reductions.addAll(item.getExpectedSymbols());
			}
			System.out.println(item.getLRrule().getRightSide().toString());
			System.out.println("Stav: " + counter);
			System.out.println("Transitions: " + transitions.toString());
			System.out.println("Reduction: " + reductions.toString());
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
	public int getPreviousState() {
		return previousState;
	}

	public void setPreviousState(int previousState) {
		this.previousState = previousState;
	}
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
