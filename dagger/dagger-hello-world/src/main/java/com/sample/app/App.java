package com.sample.app;

import com.sample.app.components.DataPrinterComponent;
import com.sample.app.util.DataPrinter;
import com.sample.app.components.DaggerDataPrinterComponent;

public class App {
	
	public static void main(String[] args) {
		DataPrinterComponent dataPrinterComponent = DaggerDataPrinterComponent.builder().build();
		DataPrinter dataPrinter = dataPrinterComponent.dataPrinter();
		
		dataPrinter.print();
	}

}
