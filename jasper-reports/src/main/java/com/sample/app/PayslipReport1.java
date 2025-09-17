package com.sample.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PayslipReport1 {

	public static void main(String[] args) {
		try {
			String jrxmlFile = "src/main/resources/payslip.jrxml";
			JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

			List<Payslip> payslips = getSamplePayslips();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(payslips);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, "professional_payslip.pdf");

			System.out.println("Professional Payslip PDF generated successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Payslip {
		private String employeeName, employeeId, designation, department, month;
		private Float basicPay, hra, allowances, tax, pf, otherDeductions, netPay;
		private Integer year;

		public Payslip(String employeeName, String employeeId, String designation, String department, Float basicPay,
				Float hra, Float allowances, Float tax, Float pf, Float otherDeductions, String month, Integer year) {
			this.employeeName = employeeName;
			this.employeeId = employeeId;
			this.designation = designation;
			this.department = department;
			this.basicPay = basicPay;
			this.hra = hra;
			this.allowances = allowances;
			this.tax = tax;
			this.pf = pf;
			this.otherDeductions = otherDeductions;
			this.netPay = basicPay + hra + allowances - (tax + pf + otherDeductions);
			this.month = month;
			this.year = year;
		}

		public String getEmployeeName() {
			return employeeName;
		}

		public String getEmployeeId() {
			return employeeId;
		}

		public String getDesignation() {
			return designation;
		}

		public String getDepartment() {
			return department;
		}

		public Float getBasicPay() {
			return basicPay;
		}

		public Float getHra() {
			return hra;
		}

		public Float getAllowances() {
			return allowances;
		}

		public Float getTax() {
			return tax;
		}

		public Float getPf() {
			return pf;
		}

		public Float getOtherDeductions() {
			return otherDeductions;
		}

		public Float getNetPay() {
			return netPay;
		}

		public String getMonth() {
			return month;
		}

		public Integer getYear() {
			return year;
		}
	}

	private static List<Payslip> getSamplePayslips() {
		List<Payslip> list = new ArrayList<>();
		list.add(new Payslip("Alice", "E001", "Developer", "IT", 50000f, 10000f, 5000f, 8000f, 6000f, 2000f,
				"September", 2025));
		list.add(new Payslip("Bob", "E002", "Manager", "HR", 75000f, 15000f, 7000f, 12000f, 9000f, 3000f, "September",
				2025));
		return list;
	}
}
