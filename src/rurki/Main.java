package rurki;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    // max resolution value = 300
    final static int resolution =300;
    public static void main(String[] args) {
        Functions f = new Functions();

        double[][] BMatrix = new double[5][5];
        double[][] LMatrix = new double[5][1];
        int k=2;
        for(int i=0; i<5;i++){
            int x_start = f.limits[i][0][0];
            int y_start = f.limits[i][0][1];
            int x_end = f.limits[i][1][0];
            int y_end = f.limits[i][1][1];

            for(int j=0; j<5;j++){
                BMatrix[i][j] = f.integralF(k,i,j,x_start,y_start,x_end,y_end) + f.integralS(k,i,j,x_start,y_start,x_end,y_end);
            }

        }

        LMatrix[0][0] = f.integralX(0,1,-1,0) + f.integralY(0,-1,0,1);
        LMatrix[1][0] = f.integralY(1,-1,-1,1);
        LMatrix[2][0] = f.integralX(2,-1,-1,0) + f.integralY(2,-1,-1,0);
        LMatrix[3][0] = f.integralY(3,-1,-1,1);
        LMatrix[4][0] = f.integralX(4,-1,-1,1) + f.integralY(4,1,-1,0);
        //System.out.println(Arrays.deepToString(BMatrix));
        double[] values = new double[5];
        for(int i=0;i<5;i++){values[i] = LMatrix[i][0]; }
        double[] solved = GausianSolver.lsolve(BMatrix,values);

        double[][] to_Plot = new double[resolution*2][resolution*2];

        for(int i=0;i<resolution*2;i++){
            for(int j=0;j<resolution*2;j++){
            to_Plot[i][j]= f.calcResult(solved,(i-resolution)/(double)resolution,(j-resolution)/(double)resolution);
            }
        }
        double[][] to_PlotT = new double[resolution*2][resolution*2];
        for(int i=0;i<resolution*2;i++){
            for(int j=0;j<resolution*2;j++) {
                to_PlotT[resolution*2-1-i][j]=to_Plot[i][j];
            }
        }

        System.out.println(Arrays.deepToString(to_PlotT));

        org.tc33.jheatchart.HeatChart map = new org.tc33.jheatchart.HeatChart(to_PlotT);
        map.setXValues(-1,1/(float)resolution);
        map.setYValues(1,-1/(float)resolution);
        try {
            map.saveToFile(new File("java-heat-chart.png"));
        }catch(IOException e){
            System.out.println(e);
        }

    }

}
