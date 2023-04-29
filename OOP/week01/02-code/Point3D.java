class Point3D {
  double x;       
  double y;
  double z;
  void build(double a,double b,double c){
    this.x = a; this.y = b; this.z = c;
  }
  double getSquaredModulus(){
    return this.x * this.x + this.y * this.y + this.z * this.z;
  }
  boolean isEqual(Point3D q){
    return this.x == q.x && this.y == q.y && this.z == q.z;
  }
}
