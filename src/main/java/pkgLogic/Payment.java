package pkgLogic;

import java.time.LocalDate;

public class Payment {
	
	private int PaymentNbr;
	private LocalDate DueDate;
	private double Payment;
	private double AdditionalPayment;
	private double InterestPayment;
	private double Principle;
	private double EndingBalance;
	
	public Payment(double beginningBalance, int paymentNbr, LocalDate dueDate, Loan loan) {
		this.PaymentNbr = paymentNbr;
		this.DueDate = dueDate;
		this.Payment = (beginningBalance > loan.GetPMT()) ? loan.GetPMT() : (beginningBalance + (beginningBalance * (loan.getInterestRate() / 12)));
		this.AdditionalPayment = loan.getAdditionalPayment();
		
		InterestPayment = beginningBalance * (loan.getInterestRate() / 12);
		
		Principle = loan.GetPMT() + loan.getAdditionalPayment() - InterestPayment;
		
		EndingBalance = beginningBalance - Principle;
	}

	public int getPaymentNbr() {
		return PaymentNbr;
	}

	public LocalDate getDueDate() {
		return DueDate;
	}
	
	public void setDueDate(LocalDate ld) {
		DueDate = ld;
	}

	public double getPayment() {
		return roundNum(Payment);
	}

	public double getAdditionalPayment() {
		return roundNum(AdditionalPayment);
	}
	
	public void setAdditionalPayment() {
		AdditionalPayment = 0;
	}

	public double getInterestPayment() {
		return roundNum(InterestPayment);
	}

	public double getPrinciple() {
		return roundNum(Principle);
	}

	public double getEndingBalance() {
		if(EndingBalance < 0)
			EndingBalance = 0;
		return roundNum(EndingBalance);
	}
	
	public double GetTotalPayment() {
		return roundNum(this.getPayment() + this.AdditionalPayment);
	}
	
	public double roundNum(double d) {
		return Math.round(d * 100) / 100.0;
	}
	
}
