package entei;

import acm.graphics.GImage;

public class Trophy extends GImage{
	
	private static String path = "media/equipment/trophy.png";
	private static int Size = 100;

	public Trophy() {
		super(path);
		super.setSize(Size, Size);
		
	}

}
