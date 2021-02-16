module StudentLoanCalc {
	
	exports app;
	exports pkgLogic;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.media;
	requires poi;

	opens app.controller to javafx.fxml;
}