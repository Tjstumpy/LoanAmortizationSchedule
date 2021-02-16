package app.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import app.StudentCalc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pkgLogic.Loan;
import pkgLogic.Payment;

public class LoanCalcViewController implements Initializable   {

	private StudentCalc SC = null;
	
	@FXML
	private TextField LoanAmount;
	
	@FXML
	private TextField InterestRate;
	
	@FXML
	private TextField NbrOfYears;
	
	@FXML
	private DatePicker PaymentStartDate;
	
	@FXML
	private TextField AdditionalPayment;
	
	@FXML
	private Label lblTotalPayments;
	
	@FXML
	private Label lblTotalInterest;
	
	@FXML
	private TableView<Payment> tvResults;
	
	@FXML
	private TableColumn<Payment, Integer> colPaymentNumber;
	
	@FXML
	private TableColumn<Payment, LocalDate> colDueDate;
	
	@FXML
	private TableColumn<Payment, Double> colPayment;
	
	@FXML
	private TableColumn<Payment, Double> colAdditionalPayment;
	
	@FXML
	private TableColumn<Payment, Double> colInterest;
	
	@FXML
	private TableColumn<Payment, Double> colPrinciple;
	
	@FXML
	private TableColumn<Payment, Double> colBalance;
	
	private ObservableList<Payment> paymentList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colPaymentNumber.setCellValueFactory(new PropertyValueFactory<>("PaymentNbr"));
		colDueDate.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
		colPayment.setCellValueFactory(new PropertyValueFactory<>("Payment"));
		colAdditionalPayment.setCellValueFactory(new PropertyValueFactory<>("AdditionalPayment"));
		colInterest.setCellValueFactory(new PropertyValueFactory<>("InterestPayment"));
		colPrinciple.setCellValueFactory(new PropertyValueFactory<>("Principle"));
		colBalance.setCellValueFactory(new PropertyValueFactory<>("EndingBalance"));

		tvResults.setItems(paymentList);
	}

	public void setMainApp(StudentCalc sc) {
		this.SC = sc;
	}
	
	/**
	 * btnCalcLoan - Fire this event when the button clicks
	 * 
	 * @version 1.0
	 * @param event
	 */
	@FXML
	private void btnCalcLoan(ActionEvent event) {
		
		paymentList.clear();
		lblTotalPayments.setText("");
		lblTotalInterest.setText("");
		double rate = Double.parseDouble(InterestRate.getText());
		int years = Integer.parseInt(NbrOfYears.getText());
		double loanAmount = Double.parseDouble(LoanAmount.getText());
		double extraPayment = Double.parseDouble(AdditionalPayment.getText());
		LocalDate startDate = PaymentStartDate.getValue();
		Loan loan = new Loan(startDate, loanAmount, rate, extraPayment, (years*12));
		
		for(Payment p: loan.getLoanPayments())
		{			
			paymentList.add(p);
		}
		
		lblTotalInterest.setText(String.valueOf(loan.GetTotalInterest()));
		lblTotalPayments.setText(String.valueOf(loan.GetTotalPayments()));
		
	}
	
	
	
}
