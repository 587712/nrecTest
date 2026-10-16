package com.example.demo.classes.mastermind;

import java.util.ArrayList;

public class Board {
	
	private ArrayList<ArrayList<TileValue>> attempts;
	private ArrayList<ArrayList<FeedbackValues>> feedback;
	private ArrayList<TileValue> code;
	private boolean victory;
	
	public Board() {
		code = new ArrayList<>();
		attempts = new ArrayList<>();
		feedback = new ArrayList<>();
		
		victory = false;
	}
	
	public ArrayList<ArrayList<TileValue>> getAttempts() {
		return attempts;
	}
	public ArrayList<ArrayList<FeedbackValues>> getFeedback() {
		return feedback;
	}
	public boolean getVictory() {
		return victory;
	}
	
	public void setCode(String[] values) {
		if(code.isEmpty()) {
			for(String val : values) {
				code.add(TileValue.valueOf(val));
			}
		}
	}
	
	public void rndCode() {
		if(code.isEmpty()) {
			for(int i = 0; i < 4; i++) {
				int temp = (int) Math.floor(Math.random() * (TileValue.values().length - 1) + 1);
				code.add(TileValue.values()[temp]);
			}
		}
	}
	
	public void addRow(String[] values) {
		ArrayList<TileValue> row = new ArrayList<>();
		for(String val : values) {
			row.add(TileValue.valueOf(val));
		}
		compareRow(row);
		attempts.add(row);
	}
	
	private void compareRow(ArrayList<TileValue> vals) {
		ArrayList<FeedbackValues> fb = new ArrayList<>();
		for(int i = 0; i < vals.size(); i++) {
			if(vals.get(i) == code.get(i)) fb.add(FeedbackValues.CORRECT);
			else fb.add(compare(vals.get(i)));
		}
		if(!(fb.contains(FeedbackValues.INCORRECT) || fb.contains(FeedbackValues.PARTIAL))) victory = true;
		feedback.add(fb);
	}
	
	private FeedbackValues compare(TileValue val) {
		if(code.contains(val)) return FeedbackValues.PARTIAL;
		else return FeedbackValues.INCORRECT;
	}
	
}
