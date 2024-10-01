package com.example.statistics;

public class Probability {

	/** 
	 * Performs exponential math via Exponentiation by Squaring
	 * P(X = n) = p*(1-p)^(n-1)
	 * @param base - The base number being exponentiated
	 * @param exponent - The exponent the base number will be subject to
	 * @return - The value of base ^ exponent
	 */
	public static double exponentiate(double base, int exponent) {
		//error handling
		if (base == 0 && exponent < 0) throw new IllegalArgumentException("Error: cannot raise 0 to a negative exponent");
		
		double result = 1;
		int exponentPositive = exponent < 0 ? -exponent : exponent;
		
		while (exponentPositive > 0) {
			//bitwise to see if exponent is odd
			if ((exponentPositive & 1) == 1) result *= base;
			base *= base;
			//bitwise form of exponentPositive = exponentPositive / 2;
			exponentPositive >>= 1;
		}
		//ternary for handling negative exponent
		return exponent < 0 ? 1 / result : result;
	}
	
	/** 
	 * Probability Mass Function for Geometric Distribution
	 * Returns the probability of an event occurring on the nth trial in a geometric distribution
	 * P(X = n) = p*(1-p)^(n-1)
	 * @param n - The trial number a success occurs on
	 * @param p - The probability of success
	 * @return - The probability of success on the nth trial
	 */
	public double geometricPMF(int n, double p) {
		//error handling
		if (n < 1) throw new IllegalArgumentException("Error: invalid value for n");
		if (p < 0 || p > 1) throw new IllegalArgumentException("Error: invalid value for p");
		
		//base cases
		if (p == 1) return 0;
		if (p == 0) return n == 1 ? 1 : 0;
		
		return p * exponentiate(1 - p, n - 1);
	}
	
	/** 
	 * Cumulative Distribution Function for Geometric Distribution
	 * Calculates the probability that a random variable is less than or equal to n
	 * For this, it returns the probability of an event occurring on or before the nth trial
	 * P(X<=n)
	 * @param n - The trial number
	 * @param p - The probability of success
	 * @return - The probability a random variable X takes on a value less than or equal to n
	 */
	public double geometricCDF(int n, double p) {
		//error handling
		if (n < 0) throw new IllegalArgumentException("Error: invalid value for n");
		if (p < 0 || p > 1) throw new IllegalArgumentException("Error: invalid value for p");
		
		//base cases
		if (p == 1) return n == 0 ? 0 : 1;
		if (p == 0) return 0;
		
		return 1 - exponentiate(1 - p, n);
	}
	
	/**
	 * Cumulative Probability Function
	 * Returns the probability of an event occurring on or before, on or after, before, or after the nth trial in a geometric distribution
	 * P(X<=n), P(X>=n), P(X<n), P(X>n)
	 * @param n - The trial number a success occurs on
	 * @param p - The probability of success
	 * @param type - Type of Cumulative Probability ("<=", ">=", "<", ">")
	 * @return - The probability of an event occurring within the given type
	 */
	public double calculateCumulativeProbability(int n, double p, String type) {
		//error handling
		if (n < 1) throw new IllegalArgumentException("Error: invalid value for n");
		if (p < 0 || p > 1) throw new IllegalArgumentException("Error: invalid value for p");
		
		//different cases
		if (type.equals("<=")) return geometricCDF(n, p);
		else if (type.equals(">=")) return 1 - geometricCDF(n - 1, p);
		else if (type.equals("<")) return geometricCDF(n - 1, p);
		else if (type.equals(">")) return geometricCDF(n, p) / p;
		else throw new IllegalArgumentException("Error: invalid value for type");
	}
	
}
