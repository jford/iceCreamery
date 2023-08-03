package com.electraink.iceCreamery.testKitchen.datastore.imageCtl;

import com.electraink.iceCreamery.testKitchen.datastore.*;

import java.io.File;
import com.electraink.iceCreamery.utilities.*;

import java.io.*;
import java.util.*;

public class ImageMgr
{
	private RecipeMgr recipeMgr;
	private boolean debug;
	private Vector<String> imageList = new Vector<String>();
	
	public static String pathMrkr = DataMgr.getPathMrkr();
	public static String imageDirName = Utilities.getTomcatHome() + pathMrkr + "webapps" + pathMrkr + "iceCreamery" + pathMrkr + "images" + pathMrkr;

	public ImageMgr()
	{
		recipeMgr = new RecipeMgr();
		debug = DataMgr.debug;
	}
	
	public Vector<String> getImageList()
	{
		File imageDir = new File(imageDirName);
		File[] imageFiles = imageDir.listFiles();
		for(int count = 0; count < imageFiles.length; count++)
		{
			if(imageFiles[count].isFile())
				imageList.add(imageFiles[count].getName());
		}
		// if there are files in the imageDir, sort them alphabetically
		if(!imageList.isEmpty())
		{
			// treeset removes duplicates and automatically sorts
			TreeSet<String> fNames = new TreeSet<String>(imageList);
			
			// once duplicates purged and list sorted, return to vector 
			imageList = new Vector<String>(fNames);
		}
		return imageList;
	}
	
	public Vector<String> getImageUsage(String imageFilename)
	{
		Vector<String> imageUsage = new Vector<String>();

		RecipeMgr recipeMgr = new RecipeMgr();
		Recipes recipes = recipeMgr.getRecipes();
		Iterator<Recipe> iRecipes = recipes.iterator();
		while(iRecipes.hasNext())
		{
			Recipe recipe = (Recipe)iRecipes.next();
			if(recipe.getImageFilename().equals(imageFilename))
				imageUsage.add(recipe.getRecipeName());
		}
		return imageUsage;
	}
}
