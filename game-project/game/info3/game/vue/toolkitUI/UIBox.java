package info3.game.vue.toolkitUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import automate.EnumCategory;
import info3.game.modele.GameModele;
import info3.game.modele.Weapon;
import info3.game.modele.MoveableEntityClass.BasicCannonBall;
import info3.game.modele.MoveableEntityClass.CannonBall;
import info3.game.modele.MoveableEntityClass.EnumCannonBall;
import info3.game.vue.view.View;

public class UIBox extends UIComponent {

	private Color borderColor;
	private Color backgroundColor;
	private UIImage image, playerImage;
	private Weapon weapon;
	private EnumCannonBall boulet;

	private static final Font FONT = new Font("TimesRoman", Font.PLAIN, 12);

	public UIBox(int c, Weapon w, UIImage im) {
		super(0, 0, c, c);
		image = im;
		weapon = w;
	}
	
	public UIBox(int c, EnumCannonBall b, UIImage im) {
		super(0, 0, c, c);
		image = im;
		boulet = b;
	}

	public UIBox(int x, int y, int c, UIImage im) {
		super(x, y, c, c);
		image = im;
	}

	public UIBox(int x, int y, int c, UIImage im, UIImage p) {
		super(x, y, c, c);
		image = im;
		playerImage = p;
	}

	public Weapon getWeapon() {
		return weapon;
	}
	

	public String getStringWeapon() {
		return weapon.getName();
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(getPositionX() + 4, getPositionY() + 4, getWidth() - 4, getHeight() - 4);

		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int centerX = this.getPositionX();
		int centerY = this.getPositionY();
		image.setPositionX(centerX + imageWidth + 1);
		image.setPositionY(centerY + imageHeight + 1);
		image.paint(g);

		if (playerImage!=null) {
			playerImage.setPositionX(getPositionX()-8);
			playerImage.setPositionY(getPositionY()-8);
			playerImage.paint(g);
		}
		
		if (boulet!=null) {
			g.setColor(Color.white);
				g.setFont(View.FONT4);
			if (boulet!=EnumCannonBall.Basic) {
				g.drawString(String.valueOf(GameModele.pirateBoat.getAmount(boulet)) , getPositionX() + 4, getPositionY() +64);
			}
			else {
				g.setFont(View.FONT6);
				String rawString = "∞";
				ByteBuffer buffer = StandardCharsets.UTF_8.encode(rawString); 

				String utf8EncodedString = StandardCharsets.UTF_8.decode(buffer).toString();
				g.drawString(utf8EncodedString, getPositionX() + 4, getPositionY() +64);
			}
		}

	}

}
