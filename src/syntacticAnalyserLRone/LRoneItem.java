package syntacticAnalyserLRone;

import java.util.ArrayList;
import java.util.HashSet;

import grammar.Rule;
public class LRoneItem {
		
	Rule LRrule;													// atribute for LR rule with a "." serving as pointer
	HashSet<String> expectedSymbols = new HashSet<String>();		// atribute representing expected symbols of exact LR item
	boolean processed;
	
	public LRoneItem(Rule rule, HashSet<String> expectSymbols){    // parameters are rule we want to make a LR(1) item from and expected symbols for that LR(1) items
		ArrayList<String> rightSide = new ArrayList<String>();     
						rightSide.addAll(rule.getRightSide());
		ArrayList<String> leftSide = new ArrayList<String>();
						leftSide.addAll(rule.getLeftSide());
		
		if(rightSide.contains(".")) {							// creacte new LR(1) item with "." serving as pointer of which part of rule is processed
			int i = rightSide.indexOf(".");						// or creates new LR(1) item with "." pointer on new place
			rightSide.set(i, rule.getRightSide().get(i+1));
			rightSide.set(i+1, ".");
		} else {
			int size = rightSide.size();	
			rightSide.add(rightSide.get(size-1));
			for(int i = size-1; i > 0; i--) {		
				rightSide.set(i, rightSide.get(i-1));
			}
			rightSide.set(0, ".");
		}
		
		this.LRrule = new Rule(leftSide, rightSide);
		this.expectedSymbols = expectSymbols;
		this.processed = false;
	}

	public Rule getLRrule() {
		return LRrule;
	}


	public void setLRrule(Rule lRrule) {
		this.LRrule = lRrule;
	}


	public HashSet<String> getExpectedSymbols() {
		return expectedSymbols;
	}


	public void setExpectedSymbols(HashSet<String> expectedSymbols) {
		this.expectedSymbols = expectedSymbols;
	}


	public boolean isProcessed() {
		return processed;
	}


	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
}
