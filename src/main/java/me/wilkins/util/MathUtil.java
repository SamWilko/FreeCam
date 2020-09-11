package me.wilkins.util;

import org.bukkit.Location;

public class MathUtil {

	public static double calculateDistanceFrom(Location a, Location b){

		double distance2D;
		double distance3D;

		double lengthA = b.getX() - a.getX();
		double lengthB = b.getZ() - a.getZ();

		if(lengthA < 0)
			lengthA *= -1;
		if(lengthB < 0)
			lengthB *= -1;

		distance2D = Math.sqrt(Math.pow(lengthA, 2) + Math.pow(lengthB, 2));

		double lengthC = b.getY() - a.getY();
		if(lengthC < 0)
			lengthC *= -1;

		distance3D = Math.sqrt(Math.pow(distance2D, 2) + Math.pow(lengthC, 2));

		return distance3D;
	}
}
