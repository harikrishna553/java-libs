package com.sample.app;

import java.sql.Connection;
import java.sql.DriverManager;
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

public class EmployeeReport {

	public static void main(String[] args) {
		try {
			// Path to your JRXML file
			String jrxmlFile = "src/main/resources/employee.jrxml";

			// Compile JRXML to JasperReport
			JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

			// Choose your data source: JDBC or Collection
			// Option 1: JDBC Connection
			//Connection conn = getDatabaseConnection();
			//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);

			// Option 2: Java Collection
			
			List<Employee> employees = getSampleEmployees();
			JRBeanCollectionDataSource
			  dataSource = new JRBeanCollectionDataSource(employees); JasperPrint
			  jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(),
			  dataSource);
			 

			// Export to PDF
			String outputFile = "employee_report.pdf";
			JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

			System.out.println("PDF generated successfully: " + outputFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Sample JDBC connection method
	private static Connection getDatabaseConnection() throws Exception {
		String url = "jdbc:mysql://localhost:3306/your_database";
		String username = "your_user";
		String password = "your_password";
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, username, password);
	}

	// Sample data class
	public static class Employee {
		private Integer EMPLOYEE_ID;
		private Date EMPLOYEE_BDATE;
		private String EMPLOYEE_DESIGNATION;
		private String EMPLOYEE_EMPNAME;
		private Float EMPLOYEE_SALARY;

		// Constructor, getters and setters
		public Employee(Integer EMPLOYEE_ID, Date EMPLOYEE_BDATE, String EMPLOYEE_DESIGNATION, String EMPLOYEE_EMPNAME,
				Float EMPLOYEE_SALARY) {
			this.EMPLOYEE_ID = EMPLOYEE_ID;
			this.EMPLOYEE_BDATE = EMPLOYEE_BDATE;
			this.EMPLOYEE_DESIGNATION = EMPLOYEE_DESIGNATION;
			this.EMPLOYEE_EMPNAME = EMPLOYEE_EMPNAME;
			this.EMPLOYEE_SALARY = EMPLOYEE_SALARY;
		}

		public Integer getEMPLOYEE_ID() {
			return EMPLOYEE_ID;
		}

		public Date getEMPLOYEE_BDATE() {
			return EMPLOYEE_BDATE;
		}

		public String getEMPLOYEE_DESIGNATION() {
			return EMPLOYEE_DESIGNATION;
		}

		public String getEMPLOYEE_EMPNAME() {
			return EMPLOYEE_EMPNAME;
		}

		public Float getEMPLOYEE_SALARY() {
			return EMPLOYEE_SALARY;
		}
	}

	// Sample method to generate data
	private static List<Employee> getSampleEmployees() {
		List<Employee> list = new ArrayList<>();
		list.add(new Employee(1, new Date(), "Developer", "Alice", 50000f));
		list.add(new Employee(2, new Date(), "Manager", "Bob", 75000f));
		return list;
	}
}
