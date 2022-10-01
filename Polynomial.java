import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Polynomial {
	double[] coeff = new double[1];
	int[] expo = new int[1];
	
	public Polynomial() {
		coeff[0] = 0;
		expo[0] = 0;
	}
	
	public Polynomial(double[] inputCoeff, int[] inputExpo) {
		coeff = inputCoeff;
		expo = inputExpo;
	}
	
	private Polynomial getPolyFromFile(String[] strPoly) {
		String strTempExpo = "";
		String strTempCoeff = "";
		int tempExpo = 0;
		double tempCoeff = 0.0;
		boolean exp = false;
		double[] rCoe = new double[1];
		int[] rExp = new int[1];
		Polynomial rPoly = new Polynomial();
		
		for (int i = 0; i < strPoly.length; i++) {
			tempExpo = 0;
			tempCoeff = 0.0;
			strTempExpo = "";
			rCoe[0] = 0;
			rExp[0] = 0;
			exp = false;
			
			for (int j = i; j < strPoly.length; j++) {
				if (strPoly[j].equals("x")) {
					exp = true;
				}
				else if (strPoly[j].equals("-") || strPoly[j].equals("+")) {
					tempCoeff = Double.parseDouble(strTempCoeff);
					if (!strTempExpo.equals("")) {
						tempExpo = Integer.parseInt(strTempExpo);
					}
					
					rCoe[0] = tempCoeff;
					rExp[0] = tempExpo;
					rPoly = rPoly.add(new Polynomial(rCoe, rExp));
					
					i = j;
					if (strPoly[j].equals("-")) {
						strTempCoeff = strPoly[j];
					}
					else {
						strTempCoeff = "";
					}
					break;
				}
				else if (!exp) {
					strTempCoeff += strPoly[j];
				}
				else {
					strTempExpo += strPoly[j];
				}
				
				if (j == strPoly.length - 1) {
					tempCoeff = Double.parseDouble(strTempCoeff);
					if (!strTempExpo.equals("")) {
						tempExpo = Integer.parseInt(strTempExpo);
					}
					
					rCoe[0] = tempCoeff;
					rExp[0] = tempExpo;
					rPoly = rPoly.add(new Polynomial(rCoe, rExp));
					
					i = j;
				}
			}		
		}
		
		return rPoly;
	}

	public Polynomial(File polyFile) throws FileNotFoundException, IOException {
		BufferedReader readPoly = new BufferedReader(new FileReader(polyFile));
		String[] strPoly = readPoly.readLine().split("");
		readPoly.close();
		
		Polynomial rPoly = getPolyFromFile(strPoly);
				
		coeff = rPoly.coeff;
		expo = rPoly.expo;
	}

	private int getUnique(Polynomial p2) {
		int count = 0;

		for (int x: p2.expo) {
			for (int i = 0; i < this.expo.length; i++) {
				if (this.expo[i] == x) {
					break;
				}
				if (i == this.expo.length - 1) {
					count++;
				}
			}
		}

		return count;
	}
	
	public Polynomial add(Polynomial addPoly) {
		int uniqueExpo = getUnique(addPoly);
		double[] rCoeff = new double[this.coeff.length + uniqueExpo];
		int[] rExpo = new int[this.expo.length + uniqueExpo];

		for (int i = 0; i < this.expo.length; i++) {
			rCoeff[i] = this.coeff[i];
			rExpo[i] = this.expo[i];
		}

		for (int i = 0; i < addPoly.expo.length; i++) {
			for (int j = 0; j < rExpo.length; j++) {
				if (addPoly.expo[i] == rExpo[j]) {
					rCoeff[j] += addPoly.coeff[i];
					break;
				}
				if (j == rExpo.length - 1) {
					for (int x = 0; x < rExpo.length; x++) {
						if (rExpo[x] == 0 && rCoeff[x] == 0) {
							rExpo[x] = addPoly.expo[i];
							rCoeff[x] = addPoly.coeff[i];
							break;
						}
					}
				}
			}
		}

		return new Polynomial(rCoeff, rExpo);
	}
	
	public double evaluate(double x) {
        double result = 0;

		for (int i = 0; i < this.expo.length; i++) {
			result += this.coeff[i] * Math.pow(x, this.expo[i]);
		}

        return result;
	}

    public boolean hasRoot(double x) {
        return (evaluate(x) == 0);
    }

	public Polynomial multiply(Polynomial multPoly) {
		Polynomial rPoly = new Polynomial();
		int[] rExpo = new int[this.expo.length];
		double[] rCoeff = new double[this.coeff.length];

		for (int i = 0; i < multPoly.expo.length; i++) {
			for (int j = 0; j < this.expo.length; j++) {
				rExpo[j] = this.expo[j] + multPoly.expo[i];
				rCoeff[j] = this.coeff[j]*multPoly.coeff[i];
			}

			rPoly = rPoly.add(new Polynomial(rCoeff, rExpo));
		}

		return rPoly;
	}
	
	private void printExpToFile(PrintStream ps, int exp, double coe) {
		if (coe >= 0) {
			ps.print("+");
		}
		ps.print(Double.toString(coe));
		if (exp != 0) {
			ps.print("x" + Integer.toString(exp));
		}
	}
	
	public void saveToFile(String fileName) throws IOException {
		PrintStream ps = new PrintStream(fileName);
		
		if (this.expo[0] == 0) {
			ps.print(this.coeff[0]);
		}
		else {
			ps.print(Double.toString(this.coeff[0]) + "x" + Integer.toString(this.expo[0]));
		}
		
		for (int i = 1; i < this.coeff.length; i++) {
			printExpToFile(ps, this.expo[i], this.coeff[i]);
		}
		
		ps.close();
	}

}