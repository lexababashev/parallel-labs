package lab6;

//import java.util.Random;
import mpi.MPI;

public class BlockingMultiplier {
    static final int A_ROWS = 1000;
    static final int A_COLS = 1000;
    static final int B_COLS = 1000;
    static final int MASTER = 0; /* id of main process */

    /* message types для комунікації між майстром та воркерами*/
    static final int FROM_MASTER = 1;
    static final int FROM_WORKER = 2;

    public static void main(String[] args) {

        int[][] A = new int[A_ROWS][A_COLS];
        int[][] B = new int[A_COLS][B_COLS];
        int[][] C = new int[A_ROWS][B_COLS];

        MPI.Init(args);
        int taskId = MPI.COMM_WORLD.Rank();
        int tasksNumber = MPI.COMM_WORLD.Size();
        int workersNumber = tasksNumber - 1;

        int[] rows = {0}, offset = {0};

        if (tasksNumber < 2) {
            System.out.println("At least 2 MPI tasks!");
            MPI.Finalize();
            System.exit(1);
        }


        if (taskId == MASTER) {

            //Random random = new Random();
            for (int i = 0; i < A_ROWS; i++) {
                for (int j = 0; j < A_COLS; j++) {
                    A[i][j] = 10;
                    //A[i][j] = random.nextInt(11);
                }
            }

            for (int i = 0; i < A_COLS; i++) {
                for (int j = 0; j < B_COLS; j++) {
                    B[i][j] = 10;
                    //B[i][j] = random.nextInt(11);
                }
            }

            int rowsForOneTask = A_ROWS / workersNumber;
            int rowsExtra = A_ROWS % workersNumber;

            long start = System.currentTimeMillis();
            // send data to tasks
            for (int destination = 1; destination <= workersNumber; destination++) {
                rows[0] = (destination <= rowsExtra) ? rowsForOneTask + 1 : rowsForOneTask;

                MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, destination, FROM_MASTER);
                MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, destination, FROM_MASTER);
                MPI.COMM_WORLD.Send(A, offset[0], rows[0], MPI.OBJECT, destination, FROM_MASTER);
                MPI.COMM_WORLD.Send(B, 0, A_COLS, MPI.OBJECT, destination, FROM_MASTER);

                System.out.println("Sent " + rows[0] + " rows to worker " + destination);

                offset[0] += rows[0];
            }

            // receive results from tasks
            for (int source = 1; source <= workersNumber; source++) {

                MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(C, offset[0], rows[0], MPI.OBJECT, source, FROM_WORKER);

                System.out.println("Worker " + source + " ready");
            }
            long end = System.currentTimeMillis();

            System.out.println("Blocking time: " + (end - start));

            int SequentialMatrix[][] = new int[A_ROWS][B_COLS];
            SequentialMultiplier.multiply(A, B, SequentialMatrix);
            MatrixHelper.compareMatrices(C, SequentialMatrix);

        } else { // worker task
            // receive data
            MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(A, 0, rows[0], MPI.OBJECT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(B, 0, A_COLS, MPI.OBJECT, MASTER, FROM_MASTER);

            for (int k = 0; k < B_COLS; k++) {
                for (int i = 0; i < rows[0]; i++) {
                    for (int j = 0; j < A_COLS; j++) {
                        C[i][k] += A[i][j] * B[j][k];
                    }
                }
            }

            // send result
            MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(C, 0, rows[0], MPI.OBJECT, MASTER, FROM_WORKER);

        }

        MPI.Finalize();
    }
}
