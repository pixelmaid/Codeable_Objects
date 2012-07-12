/*
 * Codeable Objects by Jennifer Jacobs is licensed under a Creative Commons Attribution-ShareAlike 3.0 Unported License.
 * Based on a work at hero-worship.com/portfolio/codeable-objects.
 *
 * This file is part of the Codeable Objects Framework.
 *
 *     Codeable Objects is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Codeable Objects is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Codeable Objects.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import processing.core.PApplet;

import com.primitive2d.*;
public class ScreenManager {

	public static Vector<LineCollection> drawables = new Vector<LineCollection>();
	public static boolean drawBounding= false;
	public static PApplet parent;
	private static boolean pan=false;
	private static boolean zoom=false;
	
	private static int zoomKey = 90;
	private static int normalizeKey = 78;
	private static int printKey = 80;
	private static double posX = 0;
	private static double posY = 0;
	private static double posZ = 0;
	private static double lastMouseX=0;
	private static double lastMouseY=0;
	private String filename = "foo";
	
	public ScreenManager(PApplet parent){
		this.parent = parent;
		this.parent.registerDraw(this);
	     this.parent.registerMouseEvent(this);
	     this.parent.registerKeyEvent(this);
	}
	
	public static void addtoScreen(LineCollection lc){
		drawables.add(lc);
	}
	
	public static void removeFromScreen(LineCollection lc){
		drawables.remove(lc);
	}
	
	public void setFilename(String filename){
		this.filename=filename;
	}
	
	public void draw() {
		parent.background(255);
		
		parent.translate((float)posX,(float)posY,(float)posZ);
	
		
		for(int i=0;i<drawables.size();i++){
			parent.stroke(drawables.get(i).r,drawables.get(i).g,drawables.get(i).b);
			drawables.get(i).draw(parent, drawables.get(i).strokeWeight);
			drawables.get(i).drawOrigin(parent);
			//drawables.get(i).drawBoundingBox(parent);
			if(drawables.get(i).selected){
				drawables.get(i).drawSliders();
			}
		}
				
	}
	
	public void print() {
		parent.translate(0,0,0);
		parent.beginRaw(PApplet.PDF, this.filename + new Date().getTime()+".pdf");
		
		
		for(int i=0;i<drawables.size();i++){
			parent.stroke(drawables.get(i).r,drawables.get(i).g,drawables.get(i).b);
			drawables.get(i).draw(parent, drawables.get(i).strokeWeight);
		}
		 
		
		parent.endRaw();
		
	}
	
	public void keyEvent(KeyEvent event){
		
		int keyCode = event.getKeyCode();
		switch(event.getID()){
			case KeyEvent.KEY_PRESSED:
				this.keyPressed(keyCode);
				break;
			case KeyEvent.KEY_RELEASED:
				this.keyReleased(keyCode);
				break;
		
		}
	}
	
	
	
	public void mouseEvent(MouseEvent event) {

		 int x = event.getX();
	        int y = event.getY();

	        switch (event.getID()) {
	            case MouseEvent.MOUSE_PRESSED:
	                this.mousePressed(x,y);
	                break;
	            case MouseEvent.MOUSE_RELEASED:
	            	this.mouseReleased(x,y);
	                break;
	            case MouseEvent.MOUSE_CLICKED:
	            	this.mouseClicked(x,y);
	                break;
	            case MouseEvent.MOUSE_DRAGGED:
	                this.mouseDragged(x,y);

	                break;
	            case MouseEvent.MOUSE_MOVED:
	                this.mouseMoved();
	                break;
	        }
		
	}

	private void mouseClicked(int x, int y) {
		
		
	}

	private void mouseReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	private void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	private void mousePressed(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	private void mouseMoved(){
		if(pan){
			posX+= parent.mouseX-lastMouseX;
			posY+= parent.mouseY-lastMouseY;
		}
		if(zoom){
			posZ+=parent.mouseY-lastMouseY;
		}
		lastMouseX= parent.mouseX;
		lastMouseY = parent.mouseY;
		
	}

	private void keyPressed(int keyCode){
		//System.out.println(keyCode);
		if(keyCode==KeyEvent.VK_SPACE){
			pan=true;
			zoom=false;
		}
		if(keyCode==zoomKey){
			zoom=true;
			pan=false;
		}
		if(keyCode==normalizeKey){
			posX=0;
			posY=0;
			posZ=0;
		}
		if(keyCode==printKey){
			this.print();
		}
	}
	
	private void keyReleased(int keyCode){
		if(keyCode==KeyEvent.VK_SPACE){
			pan=false;
		}
		if(keyCode==zoomKey){
			zoom=false;
		}
	
	}
	
	
	
	

}
