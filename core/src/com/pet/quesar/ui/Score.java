package com.pet.quesar.ui;

public class Score extends TextImage {

	private int score;
	private int destScore;
	
	private float speed = 100;
	
	public Score(float x, float y, int Score,int Size) {
		super(Integer.toString(Score), x, y,Size);
		score = Score;
	}
	
	public void incrementScore(int i) {
		destScore = i;
		if(destScore < 0) destScore = 0;
	}
	
	public void update(float dt) {
		if(score < destScore) {
			score += (int) (speed * dt);
			if(score > destScore) {
				score = destScore;
			}
		}
		else if(score > destScore) {
			score -= (int) (speed * dt);
			if(score < destScore) {
				score = destScore;
			}
		}
		setText(Integer.toString(score));
	}
	
	public int getScore() { return score; }
	public boolean isDone() { return score == destScore; }

}
