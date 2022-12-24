package entei;



import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class Util {
	
	public static String loadFileAsString(String source) {
		StringBuilder builder = new StringBuilder();
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(source));
			String line;
			
			while((line = reader.readLine()) != null) {
				builder.append(line + "\n");
				
			}
			reader.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
		return builder.toString();
	}
	
	public static  int parseInt(String path) {
		
		try {
			return Integer.parseInt(path);
			
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	

	
public static  double parseDouble(String path) {
		
		try {
			return Double.parseDouble(path);
			
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		return -1.0;
	}
	
	
	public static boolean intersectGRectCircle(GRectangle boxA, GRectangle circle) {
		double minX = boxA.getX();
		double maxX = minX + boxA.getWidth();
		double minY = boxA.getY();
		double maxY = minY + boxA.getHeight();
		
		//r is radius
		float r = (float) (circle.getWidth()/2);
		//cX and cY are center point of the circle
		double cX = circle.getX() + circle.getWidth()/2;
		double cY = circle.getY() + circle.getHeight()/2;
		
		
		
		float d = 0.0f;
		if( cX < minX ) d += (cX - minX)*(cX - minX);
		if( cX > maxX ) d += (cX - maxX)*(cX - maxX);
		if( cY < minY ) d += (cY - minY)*(cY - minY);
		if( cY > maxY ) d += (cY - maxY)*(cY - maxY);
		return d < r*r;
	}
	
	public static boolean collision(GRectangle boxA, GRectangle boxB) {
		double min = boxA.getX();
		double max = boxA.getX() + boxA.getWidth();
		double minB = boxB.getX();
		double maxB = boxB.getX() + boxB.getWidth();
		double minY = boxA.getY();
		double maxY = boxA.getY() + boxA.getHeight();
		double minBY = boxB.getY();
		double maxBY = boxB.getY() + boxB.getHeight();
		
		if( minB > max || min > maxB) return false;
		if( minBY> maxY || minY > maxBY ) return false;
		return true;
			
	}
	
	public static ArrayList<GLabel> fixAchievementLength(String order, Font OSD) {
		int orderLength = 40;
		Double orderStartX = 365.0;
		Double orderStartY = 65.0;
		
		
		
		// TODO Auto-generated method stub
		ArrayList<GLabel> orderLabel = new ArrayList<GLabel>();
		String[] splitOrder = order.split("\\s+");
		String newLabel = "";

		int index = 0;
		while(index < splitOrder.length) {
			while(index < splitOrder.length && (newLabel.length() + splitOrder[index].length()) < orderLength) {
				newLabel += (" " + splitOrder[index]);
				index++;
		}
			GLabel temp = new GLabel(newLabel, orderStartX, (orderStartY + (20 * orderLabel.size())));
			temp.setFont(OSD);
			temp.setColor(Color.BLACK);
			orderLabel.add(temp);
			newLabel = "";
		}
		
		return orderLabel;
			
		
	}
}
