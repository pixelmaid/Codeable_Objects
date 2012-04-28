package com.math;

/**
 * User: jenniferjacobs
 * Date: 4/28/12
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Vec2d {
    public double x;
    public double y;

   public Vec2d( double x, double y){

    this.x = x;
    this.y=y;

   }

   public Vec2d add (Vec2d val ){
        return new Vec2d(this.x+val.x,this.y+val.y);
   }

   public Vec2d sub(Vec2d val){
       return new Vec2d(this.x-val.x,this.y-val.y);
   }

   public Vec2d div(double val){
       return new Vec2d(this.x/val,this.y/val);
   }

   public Vec2d mul(double val){
        return new Vec2d(this.x*val,this.y*val);
   }


  public double DistanceSqrd(Vec2d vec1, Vec2d vec2 ){
      return (Math.pow(vec1.x-vec2.x,2) + Math.pow(vec1.y-vec2.y,2));
  }

  public double  Distance(Vec2d vec1, Vec2d vec2 ){
            //Returns the distance between two points
            return Math.sqrt( DistanceSqrd(vec1,vec2));
  }

  public double LengthSqrd(Vec2d vec ){
            //Returns the length of a vector sqaured. Faster than Length(), but only marginally
            return Math.pow(vec.x,2) + Math.pow(vec.y,2);
  }


   public double Length(){
            //Returns the length of a vector'
            return Math.sqrt( LengthSqrd(this) );
   }

  public Vec2d Normalize(Vec2d vec){
    //Returns a new vector that has the same direction as vec, but has a length of one.
     if( vec.x == 0 && vec.y == 0){
            return new Vec2d(0,0);
     }

      return vec.div(vec.Length());
  }


  public double Dot( Vec2d b ){
            //Computes the dot product of a and b'
            return this.x*b.x + this.y+b.y;
  }


  public Vec2d ProjectOnto(Vec2d v, Vec2d w ){
            //'Projects w onto v.'
            return v.mul(w.Dot(v) / LengthSqrd(v));
  }


}
