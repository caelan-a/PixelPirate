package com.pixelpirate.game;

public class Resources {

	private int food;
	private int money;
	private int people;
	private int spices;
	
	Resources() {
		food = 3;
		money = 10;
		people = 1;
		spices = 2;
	}
	
	public int getFood() {
		return food;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getPeople() {
		return people;
	}
	
	public int getSpices() {
		return spices;
	}
	
	public void setFood(int amount) {
		food = getFood() + amount; 
	}
	
	public void setMoney(int amount) {
		money = getMoney() + amount;
	}
	
	public void setPeople(int amount) {
		people = getPeople() + amount;
	}
	
	public void setSpices(int amount) {
		spices = getSpices() + amount;
	}
	
	public void giveTreasure(int food, int money, int spices) {
		setFood(food);
		setMoney(money);
		setSpices(spices);
	}
	
	
}
