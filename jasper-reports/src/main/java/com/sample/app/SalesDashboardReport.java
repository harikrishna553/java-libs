package com.sample.app;

import java.math.BigDecimal;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;

public class SalesDashboardReport {

    public static void main(String[] args) {
        try {
            String jrxmlFile = "src/main/resources/sales_dashboard.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            // Create sample sales data
            List<SalesData> salesList = getSampleSalesData();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(salesList);

            // Parameters for the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Sales Performance Dashboard - Q3 2024");
            parameters.put("TOTAL_SALES", calculateTotalSales(salesList));
            parameters.put("REPORT_DATE", new Date());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export to PDF
            String pdfFile = "sales_dashboard_report.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile);
            
            // Export to Excel
            String xlsxFile = "sales_dashboard_report.xlsx";
            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsxFile));
            xlsxExporter.exportReport();

            System.out.println("Sales Dashboard Report generated successfully:");
            System.out.println("PDF: " + pdfFile);
            System.out.println("Excel: " + xlsxFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class SalesData {
        private String region;
        private String product;
        private String salesperson;
        private Date saleDate;
        private BigDecimal amount;
        private Integer quantity;
        private String category;
        private String customer;
        private BigDecimal commission;

        public SalesData(String region, String product, String salesperson, Date saleDate, 
                        BigDecimal amount, Integer quantity, String category, String customer, BigDecimal commission) {
            this.region = region;
            this.product = product;
            this.salesperson = salesperson;
            this.saleDate = saleDate;
            this.amount = amount;
            this.quantity = quantity;
            this.category = category;
            this.customer = customer;
            this.commission = commission;
        }

        // Getters
        public String getRegion() { return region; }
        public String getProduct() { return product; }
        public String getSalesperson() { return salesperson; }
        public Date getSaleDate() { return saleDate; }
        public BigDecimal getAmount() { return amount; }
        public Integer getQuantity() { return quantity; }
        public String getCategory() { return category; }
        public String getCustomer() { return customer; }
        public BigDecimal getCommission() { return commission; }
    }

    private static List<SalesData> getSampleSalesData() {
        List<SalesData> list = new ArrayList<>();
        Random random = new Random();
        String[] regions = {"North", "South", "East", "West", "Central"};
        String[] products = {"Laptop Pro", "Smartphone X", "Tablet Ultra", "Headphones", "Smart Watch", "Gaming Console"};
        String[] salespeople = {"John Smith", "Alice Johnson", "Bob Wilson", "Carol Brown", "David Lee", "Eva Martinez"};
        String[] categories = {"Electronics", "Computers", "Mobile", "Audio", "Gaming"};
        String[] customers = {"TechCorp Ltd", "InnoSoft Inc", "DataSys Co", "CloudTech", "NextGen Solutions", "Digital Dynamics"};

        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 50; i++) {
            cal.add(Calendar.DAY_OF_YEAR, -random.nextInt(90)); // Last 90 days
            BigDecimal amount = new BigDecimal(1000 + random.nextInt(9000));
            BigDecimal commission = amount.multiply(new BigDecimal("0.05"));
            
            list.add(new SalesData(
                regions[random.nextInt(regions.length)],
                products[random.nextInt(products.length)],
                salespeople[random.nextInt(salespeople.length)],
                cal.getTime(),
                amount,
                1 + random.nextInt(10),
                categories[random.nextInt(categories.length)],
                customers[random.nextInt(customers.length)],
                commission
            ));
        }
        return list;
    }

    private static BigDecimal calculateTotalSales(List<SalesData> salesList) {
        return salesList.stream()
                .map(SalesData::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
