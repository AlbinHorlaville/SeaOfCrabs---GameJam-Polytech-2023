package info3.game.modele;

public abstract class Ennemy extends MoveableEntity {
	
	protected int level;		//To delete
	
	public Ennemy(int healthPoint, int damage, int level) { // constrctor to delete
		super(healthPoint, damage);
		this.level = level;
	}
	
	public Ennemy(int lifePoint, int damage) { // good constructor
		super(lifePoint, damage);
	}	
	
	
	
	
}
