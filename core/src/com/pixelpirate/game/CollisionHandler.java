package com.pixelpirate.game;

import java.util.ArrayList;
import java.util.List;

import com.pixelpirate.game.Canon.Canonball;

//@author Caelan Anderson
public class CollisionHandler {

	private List<Collider> ColliderList;
	
	CollisionHandler() {
		ColliderList = new ArrayList<Collider>();
	}
	
	public void addCollider(Collider collider) {
		ColliderList.add(collider);
	}
	
	//	Check collision for cannonball
	public boolean checkCollision(Canonball ball) {
		for(int i = 0; i < ColliderList.size(); i++) {
			if(ColliderList.get(i).checkLocalCollision(ball)) {
				return true;
			}
		}
		return false;
	}

}
