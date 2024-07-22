package lab7;

//import java.util.Random;
import mpi.MPI;

public class CollectiveMultiplierAll {
    static final int SIZE = 500;
    static final int MASTER = 0;
    public static void main(String[] args) {

        int[][] A = new int[SIZE][SIZE];
        int[][] B = new int[SIZE][SIZE];
        int[][] C = new int[SIZE][SIZE];

        MPI.Init(args);

        int taskId = MPI.COMM_WORLD.Rank();
        int tasksNumber = MPI.COMM_WORLD.Size();
        int slice = SIZE / tasksNumber;

        if (SIZE % tasksNumber != 0) {
            System.out.println("Should be no residue! SIZE % tasksNumber != 0");
            MPI.Finalize();
            System.exit(1);
        }

        if (tasksNumber < 2) {
            System.out.println("At least 2 tasks!");
            MPI.Finalize();
            System.exit(1);
        }

        long start = 0;

        if (taskId == MASTER) {

            //Random random = new Random();
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    A[i][j] = 10;
                    // A[i][j] = random.nextInt(11);
                }
            }

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    B[i][j] = 10;
                    // B[i][j] = random.nextInt(11);
                }
            }

            start = System.currentTimeMillis();
        }

        int[][] aBuffer = new int[slice][SIZE];
        MPI.COMM_WORLD.Scatter(A, taskId * slice, slice, MPI.OBJECT, aBuffer, 0, slice, MPI.OBJECT, MASTER);
        MPI.COMM_WORLD.Bcast(B, 0, SIZE, MPI.OBJECT, MASTER);

        int[][] cBuffer = new int[slice][SIZE];

        for (int i = 0; i < slice; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    cBuffer[i][j] += aBuffer[i][k] * B[k][j];
                }
            }
        }



        MPI.COMM_WORLD.Allgather(cBuffer, 0, slice, MPI.OBJECT, C,taskId * slice, slice, MPI.OBJECT);


        if (taskId == MASTER) {
            long end = System.currentTimeMillis();

            System.out.println("\nCollective all gather time: " + (end - start));
            int[][] SequentialMatrix = new int[SIZE][SIZE];
            SequentialMultiplier.multiply(A, B, SequentialMatrix);

            MatrixHelper.compareMatrices(C, SequentialMatrix);
        }

        MPI.Finalize();
    }
}

