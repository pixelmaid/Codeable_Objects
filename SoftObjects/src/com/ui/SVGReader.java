package com.ui;
//imports in svgs and converts them to polygons
import com.primitive2d.Polygon;
import java.awt.geom.Point2D;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import processing.core.PApplet;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class SVGReader {
	
	private Vector< Polygon > polygons;
	
	public SVGReader(){
		polygons = new Vector< Polygon >();
	}
	
	public Vector< Polygon > getPolygons(){
		return (Vector< Polygon>)polygons.clone();
	}
	
	public boolean readSVGFile(String path){
		
		//Clear any previous polygons
		polygons.clear();
		
		try{
			//Open the SVG file
			
			File fXmlFile = new File( ScreenManager.parent.sketchPath+"/"+path );
			
			//Setup the XML parser
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			//Validate that this is indeed a SVG file
			if( doc.getDocumentElement().getNodeName() != "svg" ){
				System.out.println("ERROR: This is not a valid SVG file");
				return false;
			}
			
			//Parse the SVG file
			if( !parseRectangles( doc ) ){
				System.out.println("ERROR: Failed to parse a rect object from the SVG file");
				return false;
			}
			if( !parsePolygonObjects( doc , "polygon") ){
				System.out.println("ERROR: Failed to parse a polygon object from the SVG file");
				return false;
			}
			if( !parsePolygonObjects( doc , "polyline") ){
				System.out.println("ERROR: Failed to parse a polyline object from the SVG file");
				return false;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		 }
		
		return true;
		  
	}
	
	private boolean parseRectangles( Document doc ){
		NodeList nList = doc.getElementsByTagName("rect");
		
		for (int i = 0; i < nList.getLength(); i++) {
		    //Get the ith rectangle node
			Node nNode = nList.item(i);
			
			//If the node has attributes then parse them
			if( nNode.hasAttributes() ){
				//Get all the attributes of the rect
				NamedNodeMap map = nNode.getAttributes();
				
				String xValue = map.getNamedItem( "x" ).getNodeValue();
				String yValue = map.getNamedItem( "y" ).getNodeValue();
				String wValue = map.getNamedItem( "width" ).getNodeValue();
				String hValue = map.getNamedItem( "height" ).getNodeValue();
				
				if( xValue != "" || yValue != "" || wValue != "" || hValue != " "){
					double x = stringToDouble(xValue);
					double y = stringToDouble(yValue);
					double w = stringToDouble(wValue);
					double h = stringToDouble(hValue);
							
					Polygon p = new Polygon();
					p.addPoint(x, y);
					p.addPoint(x+w, y);
					p.addPoint(x+w, y+h);
					p.addPoint(x, y+h);
					p.closePoly();
					polygons.add( p );
				}
				
			}
			
		}
		
		return true;
	}
	
	private boolean parsePolygonObjects( Document doc , String polygonObjectType ){
		NodeList nList = doc.getElementsByTagName(polygonObjectType);
		
		for (int i = 0; i < nList.getLength(); i++) {
		    //Get the ith polygon node
			Node nNode = nList.item(i);
			
			//If the node has attributes then parse them
			if( nNode.hasAttributes() ){
				//Get all the attributes of the polygon
				NamedNodeMap map = nNode.getAttributes();
				
				//Get the points
				String points = map.getNamedItem( "points" ).getNodeValue();
				
				Polygon p = new Polygon();
				
				//Get a list of the point pairs
				List<String> pointsPairsList = Arrays.asList( points.split(" "));
				
				//Validate that we have some actual points to parse
				if( pointsPairsList.size() > 1 ){
					//Separate and add each pair of points to the new polygon
					for(int j=0; j<pointsPairsList.size(); j++){
						//The first item in the list might be a space so check to make sure it is a valid points pair
						if( pointsPairsList.get(j).contains(",") ){
							
							//Separate the points
							List<String> pointsPair = Arrays.asList( pointsPairsList.get(j).split(","));
						
							//Validate that there are two points, if so then add them to the polygon
							if( pointsPair.size() == 2 ){
								double x = stringToDouble( pointsPair.get(0) );
								double y = stringToDouble( pointsPair.get(1) );
								p.addPoint(x,y);
							}else{
								System.out.println("ERROR: Failed to parse a pair of points from a " + polygonObjectType + " in the SVG file");
								return false;
							}
						}
					}
					
					//Close the polygon and add it to the polygons buffer
					p.closePoly();
					polygons.add( p );
				}
			}
			
		}
		
		return true;
	}
	
	private float stringToFloat(String s){
		return (new Float(s));
	}
	private double stringToDouble(String s){
		return (new Float(s).doubleValue());
	}

}
