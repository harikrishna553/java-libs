package com.sample.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class InvoiceReport {

	public static void main(String[] args) {
		try {
			// Compile JRXML
			String jrxmlFile = "src/main/resources/invoice.jrxml";
			JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

			// Sample Data
			List<Invoice> invoices = getSampleInvoices();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(invoices);

			// Fill report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

			// Export PDF
			String outputFile = "invoice_report.pdf";
			JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
			System.out.println("PDF generated successfully: " + outputFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Invoice {
		private String invoiceNumber;
		private Date invoiceDate;
		private String customerName;
		private String itemName;
		private Integer quantity;
		private Float unitPrice;
		private Float totalPrice;

		public Invoice(String invoiceNumber, Date invoiceDate, String customerName, String itemName, Integer quantity,
				Float unitPrice) {
			this.invoiceNumber = invoiceNumber;
			this.invoiceDate = invoiceDate;
			this.customerName = customerName;
			this.itemName = itemName;
			this.quantity = quantity;
			this.unitPrice = unitPrice;
			this.totalPrice = quantity * unitPrice;
		}

		public String getInvoiceNumber() {
			return invoiceNumber;
		}

		public Date getInvoiceDate() {
			return invoiceDate;
		}

		public String getCustomerName() {
			return customerName;
		}

		public String getItemName() {
			return itemName;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public Float getUnitPrice() {
			return unitPrice;
		}

		public Float getTotalPrice() {
			return totalPrice;
		}
	}

	private static List<Invoice> getSampleInvoices() {
		List<Invoice> list = new ArrayList<>();
		list.add(new Invoice("INV001", new Date(), "Alice", "Laptop", 2, 75000f));
		list.add(new Invoice("INV002", new Date(), "Bob", "Mouse", 5, 500f));
		list.add(new Invoice("INV003", new Date(), "Charlie", "Keyboard", 3, 1200f));
		return list;
	}
}
