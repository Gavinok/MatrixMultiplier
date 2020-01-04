/*
 * @CreateTime: Mar 1, 2018 2:28 PM
 * @Author: Gavin Jaeger-Freeborn
 * @Contact: gavinfre@uvic.ca
 * @Last Modified By: Gavin Jaeger-Freeborn
 * @Last Modified Time: Mar 1, 2018 2:40 PM
 * @Student Number:T00611983
 * @ COMP 3411 Assignment 1
 * @Description: MultiplyMatrices uses multi threading to calculate the product of two matrices
				 each element of the final matrix is given its own thread.
 */
import java.util.Arrays;
class MultiplyMatrices
{
	private int[][] result; // stores the resulting matrix from the multiplication.

	/*
	 * converts a matrix into an array a of the specified index
	 */
	public int[] matrixToArrays(int[][] newMatrix, Boolean row, int index)
	{
		int[] array;
		if(row)
		{
			array = new int[newMatrix[index].length]; 
			for(int i = 0; i < newMatrix[index].length; i++)
			{
				array[i] = newMatrix[index][i];
			}
		} else
		{
			array = new int[newMatrix.length]; 
			for(int i = 0; i < newMatrix.length; i++)
			{
				array[i] = newMatrix[i][index];
			}
		}
		return array;
	}

	/*
	 * MultiplyMatrice multiplies the first matrix by the other using multiple threads then returns the resulting matrix
	 */
	public int[][] MultiplyMatrices(int[][] newMatrix1, int[][] newMatrix2)
	{       
		int[] array1 = new int[newMatrix2[0].length];
		int[] array2 = new int[newMatrix1.length];
		int ans = 0;
		MultiplyArrays[][] resultarray = new MultiplyArrays[newMatrix1.length][newMatrix2[0].length];
		Thread[][] t = new Thread[newMatrix1.length][newMatrix2[0].length];
		result  = new int[newMatrix1.length][newMatrix2[0].length];

		/* 
		 * multiplies the two matrices together
		 */
		for(int i = 0; i < newMatrix1.length; i++)
		{
			array1 = matrixToArrays(newMatrix1, true, i);
			for(int j = 0; j < newMatrix2[0].length; j++)
			{
				array2 = matrixToArrays(newMatrix2, false, j);
				resultarray[i][j] = new MultiplyArrays(array1, array2);
				t[i][j] = new Thread(resultarray[i][j]);
				t[i][j].start();
			}

		}
		/*
		   waits for the thread to be done then stores the result in a 2d array called result
		   */
		for(int i = 0; i < newMatrix1.length; i++)
		{
			for(int j = 0; j < newMatrix2[0].length; j++)
			{
				while(t[i][j].isAlive())
				{}
				result[i][j] = (resultarray[i][j].getResult());
			}
		}
		return result;
	}
	/*
	 * getResult() returns the result matrix
	 */
	public int[][] getResult ()
	{
		return result;
	}

	/*
	 * PrintResultMatrix() prints the result matrix to the terminal
	 */
	public void PrintResultMatrix()
	{
		for (int[] array : result)
		{
			System.out.println(Arrays.toString(array));
		}
	}
	public void PrintResultMatrix(int[][] matrix)
	{
		for (int[] array : matrix)
		{
			System.out.println(Arrays.toString(array));
		}
	}
	/*
	   main simply sends two matrices to me multiplied then prints the result.
	   this is then repeated with another matrix
	   */
	public static void main(String[] args)
	{
		int[][] matrix1 = new int [][] 
		{new int[] { 1, 2, 3, 4 },
			new int[] { 5, 6, 7, 8},};
		int[][] matrix2 = new int [][] 
		{new int[] { 1, 2},
			new int[] { 1, 2},
			new int[] { 1, 2 },
			new int[] { 1, 2},};
		MultiplyMatrices matrix = new MultiplyMatrices();
		matrix.MultiplyMatrices(matrix1, matrix2);
		System.out.println("first matrix: ");
		matrix.PrintResultMatrix(matrix1);
		System.out.println("secound matrix: ");
		matrix.PrintResultMatrix(matrix2);
		System.out.println("first product: ");
		matrix.PrintResultMatrix();
		matrix1 = new int [][] 
		{new int[] { 3, 2, 1, 4 },
			new int[] { 4, 5, 1, 8},
			new int[] { 3, 2, 1, 4 },
			new int[] { 4, 5, 1, 8},
			new int[] { 3, 2, 1, 4 },
			new int[] { 4, 5, 1, 8},};
		matrix2 = new int [][] 
		{new int[] { 1, 2, 3, 1},
			new int[] { 1, 2, 1, 2},
			new int[] { 1, 2, 5, 6},
			new int[] { 1, 2, 5, 6},};
		matrix.MultiplyMatrices(matrix1, matrix2);
		matrix.MultiplyMatrices(matrix1, matrix2);
		System.out.println("first matrix: ");
		matrix.PrintResultMatrix(matrix1);
		System.out.println("secound matrix: ");
		matrix.PrintResultMatrix(matrix2);
		System.out.println("secound product: ");
		matrix.PrintResultMatrix();
	}

}
/*
 * The MultiplyArrays class is used to make a thread for each of the elements in the result matrix.
 */
class MultiplyArrays implements Runnable
{
	private int result;
	private int[] Array1;
	private int[] Array2;
	/*
	 * clones the two arrays it is given
	 */
	public MultiplyArrays(int[] newArray1, int[] newArray2)
	{
		Array1 = newArray1.clone();
		Array2 = newArray2.clone();
	}
	/*
	 * multiplies the two arrays it is given
	 */
	public int Multiply()
	{
		for(int scan = 0; scan < Array1.length; scan++)
		{
			result += (Array1[scan] * Array2[scan]);
		}
		return result;
	}
	/*
	 * returns the result of the multiplication
	 */
	public  int getResult()
	{
		return result;
	}
	/*
	 * once a thread is started this the thread runs the Multiply() function 
	 * with the two given arrays supplying one result for each element of the matrix
	 */
	public void run()
	{
		Multiply();
	}
}
