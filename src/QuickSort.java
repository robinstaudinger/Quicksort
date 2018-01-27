
/*************************************************************************
 *
 *  Pace University
 *  Fall 2017
 *  Data Structures and Algorithms
 *
 *  Course: CS 241 CRN 73913
 *  Author: Robin Kaulbars-Staudinger
 *  Collaborators: none
 *  References: http://www.geeksforgeeks.org/quick-sort/
 *  				Data Structures and Algorithm Analysis in Java, Third Edition
 *	
 *
 *  Assignment: 5
 *  Problem: Create sorting algorithms
 *  Description: In this assignment a Selection Sort and
 *  		two types of Quick Sorts are be implemented. The
 *  		algorithms are then tested and analyzed for speed.
 *  
 *
 *  Input: 
 *  Output: running times
 *
 *  Visible data fields:
 *
 *  Visible methods:
 *  
 *  SS(int[] A)
 *  QSS(int[] A)
 *  QSS(int[] A, int left, int right)
 *  QS(int[] A)
 *  QS(int[] A, int left, int right)
 *  median3(int[] A, int left, int right)
 *  swap(int[] A, int a, int b)
 *  increasingArray()
 *  decreasingArray()
 *  randomArray()
 *  
 *
 *   Remarks
 *   -------
 *   
 *   version 	increasing		decreasing		random
 *   1			27685939			133242981		79318435
 *   2a			47079106			127132134		1814556
 *   2b			4418490			1181429			2022880
 *   
 *   The most significant difference is seen when sorting the
 *   decreasing array, this is because when choosing the pivot
 *   at random instead of taking the first one, some items will
 *   be on the right side of the pivot already. In the Simple QS
 *   we will need to swap all of the entries. Thus the time being
 *   more or less equal to Selection Sort, that is O(n^2).
 *
 *************************************************************************/

import java.util.Random;

public class QuickSort {

	static Random random = new Random();

	/*
	 * Selection sort: compare the first unsorted number with the rest of the list
	 * and swap with the smallest if it's smaller than the first
	 * 
	 * @param A: the array to sort
	 */
	public static void SS(int[] A) {
		int min;
		for (int j = 0; j < A.length - 2; j++) {
			min = j;
			for (int i = j + 1; i < A.length - 1; i++) {
				if (A[i] < A[min]) {
					min = i;
				}
			}

			if (min != j) {
				swap(A, j, min);
			}
		}

	}

	// Run the simple quick sort
	public static void QSS(int[] A) {
		QSS(A, 0, A.length - 1);
	}

	/*
	 * Quick sort that uses the first item as pivot.
	 * 
	 * @param A: the sub array that is to be sorted
	 * 
	 * @param left: the beginning of the sub array
	 * 
	 * @param right: the end of the sub array
	 */
	private static void QSS(int[] A, int left, int right) {
		if (left < right) {
			int pivot = A[left];

			int i = right + 1;

			for (int j = right; j > left; j--) {
				if (A[j] > pivot) {
					i--;
					swap(A, i, j);
				}
			}
			swap(A, i - 1, left);
			QSS(A, left, i - 2);
			QSS(A, i, right);
		}
	}

	// run the quick sort
	public static void QS(int[] A) {
		QS(A, 0, A.length - 1);
		
	}

	/*
	 * Quick sort that calculates the median of three random numbers and uses it as
	 * pivot.
	 * 
	 * @param A: the sub array that is to be sorted
	 * 
	 * @param left: the beginning of the sub array
	 * 
	 * @param right: the end of the sub array
	 */
	private static void QS(int[] A, int left, int right) {
		if (left < right) {
			int pivot = median3(A, left, right);

			// partition
			int i = left - 1;
			for (int j = left; j < right; j++) {
				if (A[j] < pivot) {
					i++;
					swap(A, i, j);
				}
			}
			swap(A, i + 1, right);
			QS(A, left, i );
			QS(A, i + 2, right);
		}
	}

	// returns median of three random items from the list
	private static int median3(int[] A, int left, int right) {
		int first = random.nextInt(right - left) + left + 1;
		int second = random.nextInt(right - left) + left + 1;
		int third = random.nextInt(right - left) + left + 1;

		if (A[first] >= A[second] && A[first] <= A[third] || A[first] <= A[second] && A[first] >= A[third]) {
			swap(A, first, right);
			return A[right];
		}
		if (A[second] >= A[first] && A[second] <= A[third] || A[second] <= A[first] && A[second] >= A[third]) {
			swap(A, second, right);
			return A[right];
		}
		swap(A, third, right);
		return A[right];
	}

	// swap the references to point to each others values
	private static void swap(int[] A, int a, int b) {
		int temp = A[a];
		A[a] = A[b];
		A[b] = temp;

	}

	// creates an array with items in increasing order
	private static int[] increasingArray() {
		int[] A = new int[10000];
		for (int i = 0; i < A.length; i++)
			A[i] = i;
		return A;
	}

	// creates an array with items in decreasing order
	private static int[] decreasingArray() {
		int[] A = new int[10000];
		for (int i = 1; i < A.length; i++)
			A[A.length - i] = i;
		return A;
	}

	// creates an array with items in increasing order
	private static int[] randomArray() {
		int index, temp;
		int[] A = new int[10000];
		for (int i = 0; i < A.length; i++) {
			A[i] = i;
		}
		for (int i = A.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			temp = A[index];
			A[index] = A[i];
			A[i] = temp;
		}
		return A;
	}

	public static void main(String[] args) {
		long startingTime = System.nanoTime();
		SS(increasingArray());
		long SSready = System.nanoTime();
		QSS(increasingArray());
		long QSSready = System.nanoTime();
		QS(increasingArray());

		long QSready = System.nanoTime();

		long SStime = SSready - startingTime;
		long QSStime = QSSready - SSready;
		long QStime = QSready - QSSready;
		System.out.println("Increasing order: ");
		System.out.println("Selection Sort took: " + SStime);
		System.out.println("Quick Sort Simple took: " + QSStime);
		System.out.println("Quick Sort on took: " + QStime);

		startingTime = System.nanoTime();
		SS(decreasingArray());
		SSready = System.nanoTime();
		QSS(decreasingArray());
		QSSready = System.nanoTime();
		QS(decreasingArray());

		QSready = System.nanoTime();

		SStime = SSready - startingTime;
		QSStime = QSSready - SSready;
		QStime = QSready - QSSready;

		System.out.println("Decreasing order: ");
		System.out.println("Selection Sort took: " + SStime);
		System.out.println("Quick Sort Simple took: " + QSStime);
		System.out.println("Quick Sort took: " + QStime);

		startingTime = System.nanoTime();
		SS(randomArray());
		SSready = System.nanoTime();
		QSS(randomArray());
		QSSready = System.nanoTime();
		QS(randomArray());

		QSready = System.nanoTime();

		SStime = SSready - startingTime;
		QSStime = QSSready - SSready;
		QStime = QSready - QSSready;

		System.out.println("Random order: ");
		System.out.println("Selection Sort took: " + SStime);
		System.out.println("Quick Sort Simple took: " + QSStime);
		System.out.println("Quick Sort took: " + QStime);
	}
}
