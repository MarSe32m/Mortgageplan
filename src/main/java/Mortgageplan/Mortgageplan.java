package Mortgageplan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Mortgageplan {

	public LinkedList<Customer> prospects = new LinkedList<Customer>();
	
	public void printProspects() {
		String file = loadAsString("prospects.txt");
		if(file == null) {
			System.err.println("Error loading prospects.txt as a string");
			return;
		}
		String[] sF = file.split("\n");
		LinkedList<Customer> customers = createCustomers(sF);
		
		for(Customer c : customers)
			c.logMortage();
		System.out.println("****************************************************************************************************");
		prospects = customers;
		
		System.out.println("Press enter to exit...");
		try {
	        System.in.read();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	

	private LinkedList<Customer> createCustomers(String[] segmentedFile) {
		LinkedList<Customer> result = new LinkedList<Customer>();
		
		for(int i = 1; i < segmentedFile.length; i++) {
			if(segmentedFile[i].isEmpty())
				break;
			String[] info = segmentedFile[i].split(",");
			int k = 0;
			String name = info[k++];
			
			if(!isNumeric(info[k])) {
				name += " " + info[k++];
			}
			String totalLoan = info[k++];
			String interest = info[k++];
			String years = info[k];
			Customer customer = Customer.createCustomer(new String[]{name, totalLoan, interest, years});
			if(customer != null)
				result.add(customer);
		}
		
		return result;
	}
	
	public static double monthlyPayment(double interest, double totalLoan, int years) {
		double b = interest;
		double U = totalLoan;
		int p = years * 12;
		return U * (b * pow((1 + b), p)) / (pow((1 + b), p) - 1.0f);
	}
	
	public static float pow(double a, int exp) {
		float result = 1.0f;
		if(exp == 0)
			return result;
		if(exp % 12 == 0) {
			int q = exp / 12;
			result = pow(a*a, 6);
			result = pow(result, q);
			return result;
		}
		int exponent = exp;
		while(exponent != 0) {
			if(exponent > 0) {
				result *= a;
				exponent--;
			} else {
				result *= 1 / a;
				exponent++;
			}
				
		}
		return result;
	}
	
	private boolean isNumeric(String string) {
		return string.matches("-?\\d+(\\.\\d+)?");
	}
	
	private String loadAsString(String file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String buffer = "";
			while((buffer = reader.readLine()) != null) {
				result.append(buffer + "\n");
			}
			reader.close();
		} catch(IOException e) {
			System.err.println("File not found at: " + file);
		}
		return result.toString();
	}

}
