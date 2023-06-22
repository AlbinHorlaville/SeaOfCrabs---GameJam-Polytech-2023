package info3.game.modele.MoveableEntityClass;

import info3.game.vue.avatar.BasicCannonBallAvatar;

public class StunningCannonBall extends CannonBall {
	
	public StunningCannonBall() {
		super(0, BASIC_RANGE, BASIC_RATE_OF_FIRE);
		this.setAvatar(new BasicCannonBallAvatar(this));
		this.r = this.avatar.getWidth();
	}

	@Override
	public void hit() {
		if (ennemyAimed instanceof Ship) {
			((Ship) ennemyAimed).stunt();
		}
		else if (ennemyAimed instanceof Tentacle){
			
		}

	}
}