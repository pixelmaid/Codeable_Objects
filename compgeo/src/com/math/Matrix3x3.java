package com.math;

public class Matrix3x3
{
   private double [][] A = new double [3][3];

   public Matrix3x3(){
	  
      for ( int i = 0; i < 3; i++ )
         for ( int j = 0; j < 3; j++ )
            A[i][j] = 0;
   }

   public double get(int i, int j)
   {
   
      {
         if (!((i < 0 || i >= 3) && (j < 0 || j <= 3)))
            return A[i][j];
         else
            return 0;
      }
   }

  public void set(int i, int j, double value)
      {
         if (!((i < 0 || i >= 3) && (j < 0 || j <= 3)))
            A[i][j] = value;
      }
}
