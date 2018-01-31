/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.security.KeyStore.PrivateKeyEntry;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;

import javax.annotation.processing.RoundEnvironment;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.sun.corba.se.impl.orb.ParserTable.TestAcceptor1;
import com.sun.corba.se.spi.activation._ActivatorImplBase;
import com.sun.javafx.scene.control.skin.DateCellSkin;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import acm.io.*;
import acm.program.*;
import acm.util.*;
import acmx.export.java.util.Arrays;


public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		scoreTable = new int[N_CATEGORIES][nPlayers];
		selectedCategory = new boolean[N_CATEGORIES][nPlayers];
		playGame();
	}

	/**
	 * Method: Play Game
	 * -----------------
	 * iterate for each player 13 turn;
	 * produce the final winner and give the winner feedback.
	 */
	private void playGame() {
		for(int i = 0; i < N_SCORING_CATEGORIES; i ++) {
			for(int j = 0; j < playerNames.length; j ++ ) {
				round(j + 1);
			}
		}
		int winner = compare();
		showWinner(winner);
	}
	
	/**
	 * Method: Round
	 * ---------------
	 * @param nplayer: the nth player not the array index
	 * Control the game flow:
	 * roll dice and select dices to roll for another twice;
	 * choose the category while check whethe category is chosen before;
	 * from specified category, caculate the scores and update all scores on scoresTable;
	 */
	private void round(int nplayer) {
		/* roll dices */
		int[] dices = rollDices(nplayer);
		/* roll again */
		dices = rollAgain(dices, nplayer);
		/* and again */
		dices = rollAgain(dices, nplayer);
		
		/* choose a category */
		int category = getCategory(nplayer);
		int scores = getScores(dices, nplayer, category);
		
		/* add the points to the category */
		display.updateScorecard(category, nplayer, scores);
		addUp(nplayer);
	}
	
	/**
	 * Method: Get Dice Frequence
	 * ----------------------------
	 * @param dices: the array that contains the result of a roll;
	 * @return the frequency of each points in the five dices in the roll.
	 *         The result is an array with key as the points and value as frequency.
	 */
	private int[] getDiceFrequence(int[] dices) {
		/* the zero frequency will always be zero */
		int[] frequecy = new int[7];
		for (int i = 0; i < dices.length; i++) {
			int dice = dices[i];
			frequecy[dice] ++;
		}
		return frequecy;
	}
	
	/**
	 * Method: Get Category
	 * ---------------------
	 * @param nplayer: the nth player, not the array index for playerNames.
	 * @return an integer as the category number.
	 * If the player chooses a previous category, the method will ask for
	 * choice again.
	 */
	private int getCategory(int nplayer) {
		display.printMessage("Select category to this roll.");
		int category = errorCheck(nplayer);
		int r = category - 1, c = nplayer - 1;
		selectedCategory[r][c] = true;
		return category;
	}
	
	/**
	 * Method: Error Check
	 * @param nplayer: the nth player, not the array index for playerNames
	 * @return an integer as category number
	 * if the player do choose a previous category, an error, the method will iterate 
	 * until a right choice
	 */
	private int errorCheck(int nplayer) {
		int category = display.waitForPlayerToSelectCategory();
		int r = category - 1, c = nplayer -1;
		if (selectedCategory[r][c] == true) {
			display.printMessage("You have selected this category. Please"
					+ " choose anthor one.");
			category = errorCheck(nplayer);
		} 
		return category;
	}
	
	/**
	 * Method: Get Scores
	 * --------------------
	 * @param dices: an array as the dice result
	 * @param nplayer: the nth player
	 * @param category: an integer as category number
	 * @return an integer as the scores that the player get for this roll.
	 * preconditions: the category is chosen right
	 * postconditions: if the dices combination do not concur with the category,
	 * the method will return 0;
	 */
	private int getScores(int[] dices, int nplayer, int category) {
		int scores = 0;
		if (checkCategory(dices, category)) {
			scores = getScoresForCategory(dices, category);
		}
		int r = category -1, c = nplayer -1;
		scoreTable[r][c] = scores;
		return scores;
	}
	
	/**
	 * Method: Get Scores For Category
	 * --------------------------------
	 * @param dices: an array as the dices combination
	 * @param category: an integer as category number
	 * @return scores for the chosen category
	 */
	private int getScoresForCategory(int[] dices, int category) {
		int[] frequency = getDiceFrequence(dices);
		int scores = 0;
		switch (category) {
		case ONES:
			scores = getUpperScore(category, frequency);
			break;
		case TWOS: 
			scores = getUpperScore(category, frequency); 
			break;
		case THREES:
			scores = getUpperScore(category, frequency);
			break;
		case FOURS:
			scores = getUpperScore(category, frequency);
			break;
		case FIVES:
			scores = getUpperScore(category, frequency);
			break;
		case SIXES:
			scores = getUpperScore(category, frequency);
			break;
		case THREE_OF_A_KIND:
			scores = getNKindOfScores(category, frequency);
			break;
		case FOUR_OF_A_KIND:
			scores = getNKindOfScores(category, frequency);
			break;
		case FULL_HOUSE:
			scores = 25;
			break;
		case SMALL_STRAIGHT:
			scores = 30;
			break;
		case LARGE_STRAIGHT:
			scores = 40;
			break;
		case YAHTZEE:
			scores = 50;
			break;
		case CHANCE:
			scores = sumDices(frequency);
		}
		return scores;
	}
	
	/**
	 * Method: getNKindOfScores
	 * --------------------------
	 * @param category: an integer as category number.
	 * @param frequency: an array of the each points as keys and frequencies as values.
	 * @return all points on the dices sum up
	 * Preconditions: the chosen category is three_of_a_kind or four_of_a_kind;
	 * Postconditions: for these two categories, just sum all the  points on the dice
	 * and return the sum.
	 */
	private int getNKindOfScores(int category, int[] frequency) {
		int lookFor = category - 6, scores = 0;
		for (int i = 0; i < frequency.length; i ++) {
			if (frequency[i] == lookFor) {
				scores = sumDices(frequency);
			}
		}
		return scores;
	}
	
	/**
	 * Method: Sum Dices
	 * ------------------
	 * @param frequency: an array with points as key and frequencies as values
	 * @return the sum of points on all dices
	 */
	private int sumDices(int[] frequency) {
		int sum = 0;
		for (int i = 0; i < frequency.length; i ++) {
			sum += frequency[i] * i;
		}
		return sum;
	}
	
	
	/**
	 * Method: Get  Upper Scores
	 * ---------------------------
	 * @param category: an integer as category number
	 * @param frequency: an array with points as keys and frequencies as values.
	 * @return the sum of all points equal with category on the dices
	 * preconditions: the chosen category must be upper categories
	 */
	private int getUpperScore(int category, int[] frequency) {
		return frequency[category] * category;
	}
	
	/**
	 * Method: Roll Again
	 * -----------------------
	 * @param dices: an array as the dices combination  from first roll
	 * @param nplayer: the nth playe, not the index of the playerName
	 * @return the array as the new dices combination
	 * Only re-roll the dices that are selected.
	 */
	private int[] rollAgain(int[] dices, int nplayer) {
			display.printMessage("Select dice you want to re-roll and click \" Roll Again\" ");
			display.waitForPlayerToSelectDice();
		for (int i = 0; i < N_DICE; i++) {
			if (display.isDieSelected(i)) {
				dices[i] = rollDice();
			}
		}
		display.displayDice(dices);
		return dices;
	}
	
	/**
	 * Method: Roll Dice
	 * ---------------------
	 * @return an integer as the the result on a singe dice
	 */
	private int rollDice() {
		 return rgen.nextInt(1, 6);
	}
	
	/**
	 * Method: Roll Dices
	 * ---------------------
	 * @param nplayer: the nth player, not the index of playName
	 * @return the array as the  dices combination
	 */
	private int[] rollDices(int nplayer) {
		display.printMessage(playerNames[nplayer - 1] + 
				"'s turn, Click the \"Roll Dice\" button to roll the dice.");
		display.waitForPlayerToClickRoll(nplayer);
		int[] dices = new int[N_DICE];
		for(int i = 0; i < N_DICE; i ++) {
			dices[i] = rollDice();
		}
		printDices(dices);
		display.displayDice(dices);
		return dices;
	}
	
	
	/* check for category */
	/**
	 * Method: Check Category
	 * -----------------------
	 * @param dices: an array as dices combination
	 * @param category: the category number
	 * @return true if the dices combination meets the category;
	 *         false if not.
	 * For the upper categories, always retrurn true.
	 * For the Lower categories, have to check.
	 */
	private boolean checkCategory(int[] dices, int category) {
		int[] frequency = getDiceFrequence(dices);
		switch (category) {
		case ONES:
			return true;
		case TWOS:
			return true;
		case THREES:
			return true;
		case FOURS:
			return true;
		case FIVES:
			return true;
		case SIXES:
			return true;
		case THREE_OF_A_KIND:
			return isNOfAkind(frequency, 3);
		case FOUR_OF_A_KIND:
			return isNOfAkind(frequency, 4);
		case FULL_HOUSE:
			return isFullHouse(frequency);
		case SMALL_STRAIGHT:
			return isSmallStraight(frequency);
		case LARGE_STRAIGHT:
			return isLargeStraight(frequency);
		case YAHTZEE:
			return isYahtzee(frequency);
		case CHANCE:
			return true;
		}
		return false;
	}
	
	/**
	 * Method: is N Of Kind
	 * --------------------------
	 * @param frequency: an array with points as its keys and frequencies as its value
	 * @param n: 3 or 4 to represent the three-of-a-kind or four-of-a-kind category
	 * @return  true, if n dices shows the same points;
	 *          false, if not.
	 */
	private boolean isNOfAkind(int[] frequency, int n) {
		for (int i = 0; i < frequency.length; i ++) {
			if (frequency[i] == n) return true;
		}
		return false;
	}
	
	/**
	 * Method: is Yahtzee
	 * ----------------------
	 * @param frequency
	 * @return true, if all dices show the same points
	 *         false, if not
	 */
	private boolean isYahtzee(int[] frequency) {
		for (int i = 0; i < frequency.length; i ++) {
			if (frequency[i] == 5) return true;
		}
		return false;
	}
	/**
	 * Method: Is Full House
	 * -------------------------
	 * @param frequency
	 * @return true, if three dices show the same points 
	 * and the other two show another points but the same.
	 */
	private boolean isFullHouse(int[] frequency) {
		boolean isThreeSame = false, isTwoSame = false;
		for (int i  = 0; i < frequency.length; i ++) {
			if (frequency[i] == 3) isThreeSame = true;
			if(frequency[i] == 2) isTwoSame = true;
		}
		return isThreeSame && isTwoSame;
	}
	
	/**
	 * Method: Is Small Straight
	 * -----------------------------
	 * @param frequency
	 * @return true, if the points con compose a 4 consective numbers;
	 *         false, if not.
	 */
	private boolean isSmallStraight(int[] frequency) {
		int consective = getConsective(frequency);
		if (consective > 3) return true;
		else  return false;
	}
	
	/**
	 * Method: Is Large Straight
	 * ---------------------------
	 * @param frequency
	 * @return True, if the points can compose 5 consective numbers;
	 *         False if not.
	 */
	private boolean isLargeStraight(int[] frequency) {
		int consective = getConsective(frequency);
		if(consective ==  5) return true;
		else return false;
	}
	
	/**
	 * Method: Get Consective
	 * -------------------------
	 * @param frequency
	 * @return how many consective numbers the points can compose
	 */
	private int getConsective(int[] frequency) {
		int consective = 0;
		for (int i = 0; i < frequency.length; i++) {
			if(frequency[i] == 0) {
				consective = 0;
			} else {
				consective ++;
			}
		}
		return consective;
	}
	/* final add up */
	/**
	 * Method: Add Up
	 * ---------------------
	 * @param nplayer
	 * add the scores to update the upper, total, and lower scores
	 * both in the scoreTable and the 2D array.
	 * If the upper scores are bigger than 63, the upperBonus will be given.
	 */
	private void addUp(int nplayer) {
		int upperScores = addRangeScores(nplayer, ONES, SIXES);
		int upperBonus = 0;
		if (upperScores > 63) {
			upperBonus = 35;
			display.updateScorecard(UPPER_BONUS, nplayer, upperBonus);
		}
		int lowerScores = addRangeScores(nplayer, THREE_OF_A_KIND, CHANCE);
		int total = upperScores + upperBonus + lowerScores;
		
		display.updateScorecard(UPPER_SCORE, nplayer, upperScores);
		display.updateScorecard(LOWER_SCORE , nplayer, lowerScores);
		display.updateScorecard(TOTAL, nplayer, total);
		
		scoreTable[UPPER_SCORE - 1][nplayer - 1] = upperScores;
		scoreTable[LOWER_SCORE - 1][nplayer - 1] = lowerScores;
		scoreTable[TOTAL - 1][nplayer - 1] = total;
	}
	
	/**
	 * Method: Add Range Scores
	 * ------------------------
	 * @param nplayer
	 * @param lowCate: the start category to add scores.
	 * @param highCate: the ending category to add scores.
	 * @return the sum of scores from category lowCate to highCate.
	 */
	private int addRangeScores(int nplayer, int lowCate, int highCate) {
		int sum = 0;
		for (int i = lowCate - 1; i < highCate; i ++) {
			sum += scoreTable[i][nplayer - 1];
		}
		return sum;
	}
	/* produce the final winner */
	
	/**
	 * Method: Compare
	 * --------------------
	 * @return the player number with most scores after 13 rounds
	 */
	private int compare( ) {
		int winner = 0;
		int maxTotal = 0;
		for (int i  = 0 ; i < nPlayers; i ++) {
			int total = scoreTable[TOTAL - 1][i];
			if (total > maxTotal) {
				maxTotal = total;
				winner = i + 1;
			}
		}
		return winner;
	}
	
	/**
	 * Method: Show Winner
	 * ------------------------
	 * @param winner: the player number of the winner
	 * The feedback for the Winner.
	 */
	private void showWinner(int winner) {
		int index = winner - 1;
		int total = scoreTable[TOTAL - 1][index];
		display.printMessage("Congratulations! "+ playerNames[index] + ", you are the winner"
				+ " with total scores " + total);
	}
/* Private instance variables */
	private int nPlayers;
	private boolean[][] selectedCategory = null;
	private int[][] scoreTable = null;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
}
