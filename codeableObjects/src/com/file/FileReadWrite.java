package com.file;
import java.awt.List;
import java.io.*;
import java.util.ArrayList;

import processing.core.PApplet;



public class FileReadWrite {
	File file;
	ArrayList<String> lines;
	String filepath;
	public FileReadWrite(String filepath, PApplet parent){
		
		this.filepath = parent.sketchPath+"/data/"+filepath;
		file = new File(this.filepath);
		lines =  new ArrayList<String>();
	}
	
	public double[] readFile(){
		double[] vars = new double[100];
		try{
		BufferedReader in = new BufferedReader(new FileReader(filepath));
		String line;
		int count = 0;
		
			while((line = in.readLine()) != null)
			{
				//System.out.println(line);
				vars[count] = Double.parseDouble(line); 
				//System.out.println(vars[count]);
				count ++;
			}
		
		
		}
		catch(IOException e){
			System.out.println("could not read file");
		}
		
		return vars;
	}
	
	public void writeFile(double[] vars){
		
		try{
		BufferedWriter in = new BufferedWriter(new FileWriter(filepath));
		String line;
		int count = 0;
		
			for(int i=0;i<vars.length;i++){
				in.write(Double.toString(vars[i])+"\n");
			}
			in.close();
		
		}
		catch(IOException e){
			System.out.println("could not write to file");
		}
		
		
	}

}
