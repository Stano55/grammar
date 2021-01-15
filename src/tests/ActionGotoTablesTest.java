package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import grammar.ContextFreeGrammar;
import grammar.Rule;
import syntacticAnalyserLRone.ActionGotoTables;
import syntacticAnalyserLRone.LRoneItem;
import syntacticAnalyserLRone.State;

public class ActionGotoTablesTest {

	
	HashSet<String> terminals1, terminals2;
	HashSet<String> nonterminals1, nonterminals2;
	Rule rule11, rule12, rule13, rule14, rule15, rule21, rule22, rule23, rule24, rule25, rule26, rule27;
	HashSet<Rule> rules1, rules2;
	String startsymbol1, startsymbol2;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		terminals1 = new HashSet<String>(Arrays.asList("$","a","b","c"));
		nonterminals1 = new HashSet<String>(Arrays.asList("S","O","P"));
		rule11 = new Rule(new ArrayList<String>(Arrays.asList("S")), new ArrayList<String>(Arrays.asList("O","$")));
		rule12 = new Rule(new ArrayList<String>(Arrays.asList("O")), new ArrayList<String>(Arrays.asList("P","P")));
		rule13 = new Rule(new ArrayList<String>(Arrays.asList("P")), new ArrayList<String>(Arrays.asList("a")));
		rule14 = new Rule(new ArrayList<String>(Arrays.asList("P")), new ArrayList<String>(Arrays.asList("a", "b")));
		rule15 = new Rule(new ArrayList<String>(Arrays.asList("P")), new ArrayList<String>(Arrays.asList("c")));
		rules1= new HashSet<Rule>(Arrays.asList(rule11,rule12,rule13, rule14, rule15));
		startsymbol1="S";
		
		terminals2 = new HashSet<String>(Arrays.asList("a","b","0","1", "$"));
		nonterminals2 = new HashSet<String>(Arrays.asList("S","E","A","B"));
		rule21 = new Rule(new ArrayList<String>(Arrays.asList("S")), new ArrayList<String>(Arrays.asList("E","$")));
		rule22 = new Rule(new ArrayList<String>(Arrays.asList("E")), new ArrayList<String>(Arrays.asList("A","0")));
		rule23 = new Rule(new ArrayList<String>(Arrays.asList("E")), new ArrayList<String>(Arrays.asList("b","A","1")));
		rule24 = new Rule(new ArrayList<String>(Arrays.asList("E")), new ArrayList<String>(Arrays.asList("B","1")));
		rule25 = new Rule(new ArrayList<String>(Arrays.asList("E")), new ArrayList<String>(Arrays.asList("b","B","0")));
		rule26 = new Rule(new ArrayList<String>(Arrays.asList("A")), new ArrayList<String>(Arrays.asList("a")));
		rule27 = new Rule(new ArrayList<String>(Arrays.asList("B")), new ArrayList<String>(Arrays.asList("a")));
		rules2= new HashSet<Rule>(Arrays.asList(rule21,rule22,rule23, rule24, rule25, rule26, rule27));
		startsymbol2="S";
	}
	
	
	@Test
	public void test() {
		
		try {
			ContextFreeGrammar g = new ContextFreeGrammar(terminals2, nonterminals2, rules2, startsymbol2);
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
			
			fail("Error in test.");
		}
	
	}

	@SuppressWarnings("static-access")
	@Test
	public void test1() {
		
		try {
			
			ContextFreeGrammar g = new ContextFreeGrammar(terminals1, nonterminals1, rules1, startsymbol1);
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

