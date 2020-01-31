package rurki;

public class GausianSolver {
    private static final double EPSILON = 1e-10;

    public static double[] lsolve(double[][] A, double[] b) {
        int n = b.length;

        for (int p = 0; p < n; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
            double   t    = b[p]; b[p] = b[max]; b[max] = t;

            // singular or nearly singular
            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }
   /* public static double[] solve(double[][]B,double[][]L){
        double[] solved = new double[5];
        double[][] tmpMatrix = new double[5][6];

        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                tmpMatrix[i][j]=B[i][i];
            }
            tmpMatrix[i][5] = L[i][0];
        }

        for(int i=0;i<5;i++){
            double maxE = Math.abs(tmpMatrix[i][i]);
            int maxR = i;

            for(int j=i+1;j<5;j++){
              if(Math.abs(tmpMatrix[i][j])>maxE){
                  maxE = Math.abs(tmpMatrix[i][j]);
                  maxR = j;
              }
            }

            for(int j=i;j<6;j++){
               double tmp =  tmpMatrix[maxR][j];
               tmpMatrix[maxR][j] = tmpMatrix[i][j];
               tmpMatrix[i][j] = tmp;
            }

            for(int j=i+1;j<5;j++){
                double a = - tmpMatrix[j][i]/tmpMatrix[i][i];
                for(int k=i;k<6;k++){
                    if(k==j){
                        tmpMatrix[j][k]=0;
                    }else{
                        tmpMatrix[j][k]+=a*tmpMatrix[i][k];
                    }
                }
            }

        }

        for(int i=4;i>=0;i--){
         solved[i] = tmpMatrix[i][5] /tmpMatrix[i][i];

         for(int j=i-1;j>=0;j--){
             tmpMatrix[j][5] -= tmpMatrix[j][i] * solved[i];
         }

        }
        return solved;
    }

    */
}
