package entei;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;

public class Folder extends GCompound{

		private GImage folderPic = new GImage("media/folder.png");
		private GLabel folderName = new GLabel("", 5, 65);
		private static int SIZE = 60;
		
		public Folder(String s) {
			folderName.setLabel(s);
			
			super.add(folderPic);
			super.add(folderName);
			folderPic.setSize(SIZE, SIZE);
		}
		
		
		
}
