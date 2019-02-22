package Mortgageplan;

public class Customer {

	private static int prospectNumber = 0;
	
	private String name;
	private double totalLoan;
	private double interest;
	private int years;
	
	private boolean invalidData = false;
	
	private Customer() {}
	
	public static Customer createCustomer(String[] info) {
		if(info.length < 4)
			return null;
		String _name = info[0];
		double _totalLoan = 0.0f;
		try {
			_totalLoan = Double.parseDouble(info[1]);
		} catch (NumberFormatException e) {
			if(info[1].charAt(info[1].length() - 1) == '.') {
				_totalLoan = Double.parseDouble(info[1].substring(0, info[1].length() - 2));
			}
		}
		double _interest = Double.parseDouble(info[2]) / 100.0;
		int _years = 0;
		try {
			_years = Integer.parseInt(info[3]);
		} catch (NumberFormatException e) {
			if(info[3].charAt(info[3].length() - 1) == '.') {
				_years = Integer.parseInt(info[3].substring(0, info[3].length() - 2));
			}
		}

		Customer result = new Customer();
		
		if(_name == null || _totalLoan == 0.0f || _interest < 0.0f || _years <= 0)
			result.invalidData = true;
		
		result.name = _name;
		result.totalLoan = _totalLoan;
		result.interest = _interest;
		result.years = _years;
		return result;
	}
	
	public void logMortage() {
		prospectNumber++;
		if(invalidData)
			System.err.println("Prospect " + prospectNumber + " had invalid data");
		else {
			System.out.println("****************************************************************************************************");
			System.out.println("Prospect " + prospectNumber + ": " + name
					+ " wants to borrow " + totalLoan
					+ " € for a period of " + years 
					+ " years and pay " + Mortgageplan.monthlyPayment(interest, totalLoan, years) 
					+ " € each month.");
		}
	}
	
}
