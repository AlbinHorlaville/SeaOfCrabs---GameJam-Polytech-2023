package info3.game.modele.MoveableEntityClass;

import java.util.ArrayList;

import automate.AutomateLoader;
import automate.EnumCategory;
import automate.EnumDirection;
import info3.game.Controller;
import info3.game.modele.GameEntity;
import info3.game.modele.GameModele;
import info3.game.modele.map.EnumTiles;
import info3.game.modele.map.Tiles;
import info3.game.sound.SoundEffect;
import info3.game.sound.SoundTool;
import info3.game.vue.avatar.Avatar;

public class BoatPlayer extends Player {

	ArrayList<CannonBall> bouletDeCannon;
	CannonBall current_ball;

	private static final int DEFAULT_BOATPLAYER_LIFE_POINT = 100;

	private static final int DEFAULT_MAX_BOATPLAYER_LIFE_POINT = 100;

	public static final int DEFAULT_BOATPLAYER_SPEED = 25;
		
	private int currentSection;
	
	public boolean invincible;
	public int timerInvicibleMili;
	public int timerInvicibleSec;

	public BoatPlayer() {
		super(DEFAULT_BOATPLAYER_LIFE_POINT, 0, DEFAULT_MAX_BOATPLAYER_LIFE_POINT);

		bouletDeCannon = new ArrayList<>();
		this.current_ball = new BasicCannonBall();

		this.automate = AutomateLoader.findAutomate(GameEntity.PlayerBoat);
		this.current_state = automate.initial_state;

		this.facing = EnumDirection.N;
		this.currentSection = 0;
	}

	public BoatPlayer(int x, int y) {
		super(DEFAULT_BOATPLAYER_LIFE_POINT, 0, DEFAULT_MAX_BOATPLAYER_LIFE_POINT, x, y);

		bouletDeCannon = new ArrayList<>();
		// this.current_ball = new BasicCannonBall();

		this.automate = AutomateLoader.findAutomate(GameEntity.PlayerBoat);
		this.current_state = automate.initial_state;

		this.facing = EnumDirection.N;
		this.currentSection = 0;
	}

	public void addHealthPoints(int healthPoints) {
		this.m_healthPoints += healthPoints;
	}

	/**
	 * Cette m�thode doit �tre changer et permettra d'ajouter un boulet quand
	 * ramasser
	 * 
	 * @param boulet
	 */
	public void addBoulet(CannonBall boulet) {
		this.bouletDeCannon.add(boulet);
	}

	/*
	 * public boolean cell() { int tempX = this.x; int tempY = this.y;
	 * this.moveEntity(facing, DEFAULT_BOATPLAYER_SPEED); Tiles under =
	 * GameModele.map.getTileUnderEntity(x, y -
	 * (this.avatar.getHeight()/Avatar.SCALE_IMG));
	 * System.out.println(under.getType().toString()); if(under.isIsland()) { x =
	 * tempX; y = tempY; return true;
	 * 
	 * } return false; }
	 */
	
	@Override
	public void die() {
		Controller.getGameModele().gameover();
	}

	@Override
	public void move(EnumDirection eval) {
		facing = eval;
		int tempX = x;
		int tempY = y;
		// if(!cell()) {
		this.moveEntity(eval, DEFAULT_BOATPLAYER_SPEED);
		// }

		Tiles under = GameModele.map.getTileUnderEntity(this.x, y);
		int tileY = under.getTileY();
		int section = GameModele.map.getSectionOfEntity(this.x, y);
		if(under.notIslandAndNotWater()) {
			x = tempX;
			y = tempY;
		}
		else if (under.isIsland()) {
			GameModele.entities.remove(this);
			GameModele.player1.setLocation(under.getX(), under.getY());
			GameModele.player1.facing = this.facing;
			
			if (!GameModele.solo) {
				GameModele.player2.setLocation(under.getX(), under.getY());
				GameModele.player2.facing = this.facing;
				GameModele.entities.add(GameModele.player2);
			}
			
			GameModele.entities.add(GameModele.player1);
			GameModele.onSea = !GameModele.onSea;
		}

		under = GameModele.map.getTileUnderEntity(this.x, this.y);
		tileY = under.getTileY();
		section = GameModele.map.getSectionOfEntity(this.x, this.y);

		if (under.getTileX() < 9) {
			this.x = GameModele.map.getMap()[section].getTiles()[tileY][GameModele.map.getSectionWidth() - 10].getX();
			this.y = GameModele.map.getMap()[section].getTiles()[tileY][GameModele.map.getSectionWidth() - 10].getY();
		} else if (under.getTileX() > GameModele.map.getSectionWidth() - 10) {
			this.x = GameModele.map.getMap()[section].getTiles()[tileY][10].getX();
			this.y = GameModele.map.getMap()[section].getTiles()[tileY][10].getY();
		}
		
		this.currentSection = section;
	}
	
	public int getCurrentSection() {
		return this.currentSection;
	}

	@Override
	public void takeDamage(int damage) {
		int timeMili = GameModele.timer.getMiliSecondes();
		int timeSec = GameModele.timer.getSecondes();
		if(!invincible) {
			this.m_healthPoints -= damage;
			if(this.m_healthPoints <= 0) {
				this.die();
			}
			invincible = true;
			this.timerInvicibleMili = timeMili;
			this.timerInvicibleSec = timeSec;
		}
		else if(timerInvicibleMili <= timeMili && timerInvicibleSec + 1 <= timeSec){
			invincible = false;
		}
	}

	public void startFire(int mouseX, int mouseY) {
		SoundTool.playSoundEffect(SoundEffect.BoatAttack, 0);
		// A modifier pour choisir le boulet de cannon à tirer
		CannonBall b;
		if (bouletDeCannon.size() > 0)
			b = bouletDeCannon.remove(0);
		else
			b = new BasicCannonBall();
		b.setPositions(this.x, this.y, mouseX, mouseY);
		b.fire();
	}

	@Override
	public void hit(EnumDirection d, EnumCategory c) {

	}

}
