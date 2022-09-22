public class Polynomial {
	double[] coefficients = new double[1];
	
	public Polynomial() {
		coefficients[0] = 0;
	}
	
	public Polynomial(double[] inputCoeff) {
		coefficients = inputCoeff;
	}

    public int max(int a, int b) {
        if (a >= b) {
            return a;
        }
        return b;
    }

    public int min(int a, int b) {
        if (a <= b) {
            return a;
        }
        return b;
    }
	
	public Polynomial add(Polynomial addPoly) {
		int maxLength = max(addPoly.coefficients.length, this.coefficients.length);
        int minLength = min(addPoly.coefficients.length, this.coefficients.length);
		double[] resultPoly = new double[maxLength];
		
		for (int i = 0; i < minLength; i++) {
			resultPoly[i] = addPoly.coefficients[i] + this.coefficients[i];
		}
		
		if (addPoly.coefficients.length >= this.coefficients.length) {
			for (int i = minLength; i < maxLength; i++) {
				resultPoly[i] = addPoly.coefficients[i];
			}
		}
		else {
			for (int i = minLength; i < maxLength; i++) {
				resultPoly[i] = this.coefficients[i];
			}
		}
		
		return new Polynomial(resultPoly);
	}
	
	public double evaluate(double x) {
        double result = 0;
		int len = this.coefficients.length;

        for (int i = 0; i < len; i++) {
            result += this.coefficients[i] * (Math.pow(x, i));
        }

        return result;
	}

    public boolean hasRoot(double x) {
        return (evaluate(x) == 0);
    }
}