package rurki;

public class Functions {
     int[][]points = {{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
     int[][][] limits = {{{-1, 0}, {0, 1}}, {{-1, -1}, {0, 1}}, {{-1, -1}, {0, 0}}, {{-1, -1}, {1, 0}}, {{0, -1}, {1, 0}}};

     public Functions(){

     }

     private boolean domain(double x, double y, int xi, int yi){
         return Math.abs(x-xi)<1 && Math.abs(y-yi)<1;
     }
     private double dx_pyramid(int i,double x,double y){
         return base_func(i,x+0.001,y)- base_func(i,x,y);
     }

     private double dy_pyramid(int i,double x, double y){
         return base_func(i,x,y+0.001)- base_func(i,x,y);
     }

     private double firstToIntegrate(int k,int i, int j, double x,double y){
         return k*dx_pyramid(i,x,y)*dx_pyramid(j,x,y);
     }

     private double secondToIntegrate(int k,int i, int j, double x,double y){
         return k*dy_pyramid(i,x,y)*dy_pyramid(j,x,y);
     }

     public double base_func(int i,double x, double y){
         int xi = points[i][0];
         int yi = points[i][1];
         if(domain(x,y,xi,yi)){
             return (1-Math.abs(x-xi))* (1- Math.abs(y-yi));
         }else return 0;
     }

     private double g_x(double x,double y,int i){
         return Math.pow(x,2/3)*base_func(i,x,y);
     }

    private double g_y(double y,double x,int i){
        return Math.pow(x,2/3)*base_func(i,x,y);
    }

     public double integralF(int k,int i, int j,int x_start,int y_start,int x_end,int y_end){
        double sum=0;
        double delta = 0.1;

        double x_s = x_start;
        while(x_s<x_end){
            double y_s = y_start;
            while(y_s<y_end){
                sum += delta*delta * firstToIntegrate(k,i,j,x_s +delta/2,y_s +delta/2);
                y_s+=delta;
            }
            x_s+=delta;
        }
        return sum;
     }

     public double integralS(int k,int i, int j,int x_start,int y_start,int x_end,int y_end){
         double sum=0;
         double delta = 0.1;

         double x_s = x_start;
         while(x_s<x_end){
             double y_s = y_start;
             while(y_s<y_end){
                 sum += delta*delta * secondToIntegrate(k,i,j,x_s +delta/2,y_s +delta/2);
                 y_s+=delta;
             }
             x_s+=delta;
         }
         return sum;
     }

     public double integralX(int i,int val,double start,double end){
         double sum = 0;
         double delta = 0.1;
         double curent = start;
         while(curent<end){
            sum+= delta*g_x(curent+delta/2,val,i);
            curent+=delta;
         }
         return sum;
     }

    public double integralY(int i,int val,double start,double end){
        double sum = 0;
        double delta = 0.1;
        double curent = start;
        while(curent<end){
            sum+= delta*g_y(curent+delta/2,val,i);
            curent+=delta;
        }
        return sum;
    }

    public double calcResult(double[] W,double x,double y){
         double tmp=0;
         for(int i=0;i<5;i++){
             tmp +=W[i]*base_func(i,x,y);
         }
         return tmp;
    }



}
