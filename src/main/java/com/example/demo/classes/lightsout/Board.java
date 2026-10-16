package com.example.demo.classes.lightsout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class Board {
	
	private boolean[][] tiles;
	private int stage;
	private ArrayList<Stage> stages;
	
	public Board() {
		tiles = new boolean[0][0];
		stage = 0;

		stages = new ArrayList<>();

		try(InputStream input = Board.class.getResourceAsStream("/static/lightsoutStages.json")) {
			StringBuilder jsonText = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            	String line;
            	while ((line = reader.readLine()) != null) {
            		jsonText.append(line);
            	}
            	JSONObject jsonObject = new JSONObject(jsonText.toString());
            	JSONArray jsonArray = jsonObject.optJSONArray("data");
            	Gson gson = new Gson();
        		Type listType = new TypeToken<Stage>() {}.getType();
        		for(Object o : jsonArray) {
					stages.add(gson.fromJson(o.toString(), listType));
				}
            }
		} catch (Exception e) {e.printStackTrace();}
		
		initStage();
		
	}
	
	public boolean[][] getTiles() {
		return tiles;
	}
	
	private void toggleTile(int row, int col) {
		tiles[row][col] = !tiles[row][col];
	}
	
	public void nextStage() {
		levelUp();
		initStage();
	}
	
	private void initStage() {
		boolean[][] temp = stages.get(stage).getTiles();
		tiles = new boolean[temp.length][temp[0].length];
		for(int i = 0; i < temp.length; i++) {
			for(int j = 0; j < temp[i].length; j++) {
				tiles[i][j] = temp[i][j];
			}
		}
	}
	
	private void levelUp() {
		stage++;
		stage = stage % stages.size();
	}
	
	public void clicked(int id) {
		int col = id % tiles.length;
		int row = id / tiles[0].length;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				try {
					toggleTile(row + i, col + j);
				} catch(Exception e) {}
			}
		}
	}
	
	public boolean checkWin() {
		for(boolean[] b1 : tiles) {
			for(boolean b2 : b1) {
				if(!b2) return false;
			}
		}
		return true;
	}
	
}