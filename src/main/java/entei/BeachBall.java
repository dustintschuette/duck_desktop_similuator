package entei;

import acm.graphics.GImage;

public class BeachBall extends GImage {
	
	private static double DEC = 0.2;
	
	private double dx, dy;
	
	private DuckPane game;
	

	public BeachBall(String name, DuckPane game) {
		super(name);
		this.game = game;
		super.setSize(DuckPane.BALL_SIZE, DuckPane.BALL_SIZE);
		// TODO Auto-generated constructor stub
	}
	
	public void setVelocity(double dx2, double dy2) {
		this.dx = dx2;
		this.dy = dy2;
	}
	
	
	public void update() {
		super.move(dx, dy);
		if(dx == 0 && dy == 0) {
			return;
		}
		
		if(dx > 0) {
			dx -= DEC;
		}else {
			dx += DEC;
		}
		if(dy > 0) {
			dy -= DEC;
		}
		else {
			dy += DEC;
		}
		
		
		if(Math.abs(dx) < 0.25) {
			dx = 0;
		}
		if(Math.abs(dy) < 0.25) {
			dy = 0;
		}
		
		//make sure ball stays in bounds
		
		if(super.getX() + dx + super.getWidth() >  1920 || super.getX() + dx < 0) {
			dx *= -1;
		}
		
		if(super.getY() + dy + super.getHeight() >  1080 || super.getY() + dy < 0) {
			dy *= -1;
		}
		if(Util.intersectGRectCircle(getBounds(), game.getDuckHitBox())) {
			dx *= -1;
			dy *= -1;
		}
		
		
	}

}
