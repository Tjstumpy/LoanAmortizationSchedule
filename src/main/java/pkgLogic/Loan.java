package pkgLogic;

import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.FinanceLib;

public class Loan {

	private LocalDate StartDate;
	private double LoanAmount;
	private double LoanBalanceEnd;
	private double InterestRate;
	private double AdditionalPayment;
	private double LoanPaymentCount;
	private boolean bCompoundingOption;
	
	private ArrayList<Payment> loanPayments = new ArrayList<Payment>();
	
	public Loan(LocalDate startDate, double loanAmount, double interestRate, double additionalPayment,
			double loanPaymentCount) {
		super();
		StartDate = startDate;
		LoanAmount = loanAmount;
		InterestRate = interestRate;
		AdditionalPayment = additionalPayment;
		LoanPaymentCount = loanPaymentCount;
		LoanBalanceEnd = 0;
		bCompoundingOption = false;
		
		double RemainingBalance = LoanAmount;
		int PaymentCnt = 1;
		
		while(RemainingBalance >= this.GetPMT()) 
		{
			Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate.plusMonths(1), this);
			RemainingBalance = payment.getEndingBalance();
			loanPayments.add(payment);
		}
		
		Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate.plusMonths(1), this);
		
		RemainingBalance = payment.getEndingBalance();
		payment.setAdditionalPayment();
		loanPayments.add(payment);
		 
	}

	public Loan(LocalDate startDate, double loanAmount, double loanBalanceEnd, double interestRate,
			double additionalPayment, double loanPaymentCount, boolean bCompoundingOption) {
		super();
		StartDate = startDate;
		LoanAmount = loanAmount;
		LoanBalanceEnd = loanBalanceEnd;
		InterestRate = interestRate;
		AdditionalPayment = additionalPayment;
		LoanPaymentCount = loanPaymentCount;
		this.bCompoundingOption = bCompoundingOption;
	}

	public LocalDate getStartDate() {
		return StartDate;
	}

	public double getLoanAmount() {
		return LoanAmount;
	}

	public double getLoanBalanceEnd() {
		return LoanBalanceEnd;
	}

	public double getInterestRate() {
		return InterestRate;
	}

	public double getAdditionalPayment() {
		return AdditionalPayment;
	}

	public double getLoanPaymentCount() {
		return LoanPaymentCount;
	}

	public boolean isbCompoundingOption() {
		return bCompoundingOption;
	}
	
	public double GetPMT() {
		return  Math.abs(FinanceLib.pmt(this.getInterestRate()/12, this.LoanPaymentCount, this.LoanAmount,
								this.LoanBalanceEnd, this.bCompoundingOption));		
	}
	
	public double GetTotalPayments() {
		return roundNum(loanPayments.stream().mapToDouble(p -> p.GetTotalPayment()).sum());
	}
	
	public double GetTotalInterest() {
		return roundNum(GetTotalPayments() - LoanAmount);
	}
	public ArrayList<Payment> getLoanPayments(){
		for(int i = 0; i < loanPayments.size(); i++)
		{
			loanPayments.get(i).setDueDate(loanPayments.get(i).getDueDate().plusMonths(i));
		}
		return loanPayments;
	}
	
	public double roundNum(double d) {
		return Math.round(d * 100) / 100.0;
	}
}
