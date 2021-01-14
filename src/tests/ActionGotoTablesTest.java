package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import FirstandFollow.FirstAndFollowClass;
import grammar.ContextFreeGrammar;
import grammar.Grammar;
import grammar.Rule;
import syntacticAnalyserLRone.ActionGotoTables;
import syntacticAnalyserLRone.LRoneItem;
import syntacticAnalyserLRone.State;

public class ActionGotoTablesTest {

	
	HashSet<String> terminals;
	HashSet<String> nonterminals;
	Rule rule1;
	Rule rule2;
	Rule rule3;
	Rule rule4;
	Rule rule5;
	HashSet<Rule> rules1;
	String startsymbol;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		terminals = new HashSet<String>(Arrays.asList("$","a","b","c"));		//Set of terminals
		nonterminals = new HashSet<String>(Arrays.asList("S","O","P"));
		
		rule1 = new Rule(new ArrayList<String>(Arrays.asList("S")), new ArrayList<String>(Arrays.asList("O","$")));
		rule2 = new Rule(new ArrayList<String>(Arrays.asList("O")), new ArrayList<String>(Arrays.asList("P","P")));
		rule3 = new Rule(new ArrayList<String>(Arrays.asList("P")), new ArrayList<String>(Arrays.asList("a")));
		rule4 = new Rule(new ArrayList<String>(Arrays.asList("P")), new ArrayList<String>(Arrays.asList("a", "b")));
		rule5 = new Rule(new ArrayList<String>(Arrays.asList("P")), new ArrayList<String>(Arrays.asList("c")));
		rules1= new HashSet<Rule>(Arrays.asList(rule1,rule2,rule3, rule4, rule5));	//test1
		
		startsymbol="S";
	}
	
	/*
	@Test
	public void test() {
		
		try {
			ArrayList<String> leftSide = new ArrayList<String>();
			leftSide.add("A");
			ArrayList<String> rightSide = new ArrayList<String>();
			rightSide.add("B");
			rightSide.add("C");
			rightSide.add("D");
			
			Rule rule = new Rule(leftSide, rightSide);
			
			HashSet<String> exps = new HashSet<String>();
			exps.add("epsilon");
			LRoneItem item = new LRoneItem(rule, exps );
			
			System.out.print(item.getLRrule().getLeftSide().toString());
			System.out.print("=>");
			System.out.print(item.getLRrule().getRightSide().toString() + ", {");
			System.out.print(item.getExpectedSymbols().toString() + "}");
			System.out.println();
			
			
			
		} catch (Exception e) {
			
			fail("Not yet implemented");
		}
	
	}
	*/
	@SuppressWarnings("static-access")
	@Test
	public void test1() {
		
		try {
			
			ContextFreeGrammar g = new ContextFreeGrammar(terminals, nonterminals, rules1, startsymbol);
			ActionGotoTables test1 = new ActionGotoTables(g);
			for(State state : test1.getStates()) {
				System.out.println("State: " + state.getStateNumber());
				ArrayList<LRoneItem> items = state.getLrOneItems();
			
			for (int i = 0; i < items.size(); i++) {
				LRoneItem item = items.get(i);
				System.out.print(item.getLRrule().getLeftSide().toString());
				System.out.print("=>");
				System.out.print(item.getLRrule().getRightSide().toString() + ", {");
				System.out.print(item.getExpectedSymbols().toString() + "}");
				System.out.println();
				
			} 
				System.out.println(state.getReductions());
				System.out.println(state.getTransitions());
			}
			System.out.println();
		} catch (Exception e) {
			
			fail("Not yet implemented");
		}
		} 
		
		
		
	}

