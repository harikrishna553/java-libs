package com.sample.app;

import java.math.BigDecimal;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class CustomerInvoiceReport {

    public static void main(String[] args) {
        try {
            String jrxmlFile = "src/main/resources/customer_invoice.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            List<InvoiceDetail> invoiceDetails = getSampleInvoiceData();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(invoiceDetails);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("INVOICE_NUMBER", "INV-2024-001234");
            parameters.put("INVOICE_DATE", new Date());
            parameters.put("DUE_DATE", getDatePlusDays(new Date(), 30));
            parameters.put("COMPANY_NAME", "TechSolutions Inc.");
            parameters.put("COMPANY_ADDRESS", "123 Business Ave, Tech City, TC 12345");
            parameters.put("COMPANY_PHONE", "(555) 123-4567");
            parameters.put("COMPANY_EMAIL", "billing@techsolutions.com");
            parameters.put("CUSTOMER_NAME", "ABC Corporation");
            parameters.put("CUSTOMER_ADDRESS", "456 Corporate Blvd, Business Town, BT 67890");
            parameters.put("CUSTOMER_CONTACT", "John Manager, (555) 987-6543");
            parameters.put("SUBTOTAL", calculateSubtotal(invoiceDetails));
            parameters.put("TAX_RATE", new BigDecimal("0.08"));
            parameters.put("TAX_AMOUNT", calculateTaxAmount(invoiceDetails));
            parameters.put("TOTAL_AMOUNT", calculateTotalAmount(invoiceDetails));
            parameters.put("QR_CODE_DATA", generateQRData("INV-2024-001234", calculateTotalAmount(invoiceDetails)));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            String outputFile = "customer_invoice_report.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

            System.out.println("Customer Invoice Report generated: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class InvoiceDetail {
        private Integer lineNumber;
        private String productCode;
        private String description;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal discount;
        private BigDecimal lineTotal;
        private String taxCategory;

        public InvoiceDetail(Integer lineNumber, String productCode, String description, 
                           Integer quantity, BigDecimal unitPrice, BigDecimal discount, String taxCategory) {
            this.lineNumber = lineNumber;
            this.productCode = productCode;
            this.description = description;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.discount = discount;
            this.taxCategory = taxCategory;
            
            BigDecimal subtotal = unitPrice.multiply(new BigDecimal(quantity));
            BigDecimal discountAmount = subtotal.multiply(discount);
            this.lineTotal = subtotal.subtract(discountAmount);
        }

        // Getters
        public Integer getLineNumber() { return lineNumber; }
        public String getProductCode() { return productCode; }
        public String getDescription() { return description; }
        public Integer getQuantity() { return quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public BigDecimal getDiscount() { return discount; }
        public BigDecimal getLineTotal() { return lineTotal; }
        public String getTaxCategory() { return taxCategory; }
        
        public BigDecimal getDiscountAmount() {
            return unitPrice.multiply(new BigDecimal(quantity)).multiply(discount);
        }
    }

    private static List<InvoiceDetail> getSampleInvoiceData() {
        List<InvoiceDetail> details = new ArrayList<>();
        
        details.add(new InvoiceDetail(1, "LAPTOP-PRO-001", "Professional Laptop with 16GB RAM", 
            2, new BigDecimal("1299.99"), new BigDecimal("0.05"), "Standard"));
        details.add(new InvoiceDetail(2, "MONITOR-4K-027", "27-inch 4K Professional Monitor", 
            2, new BigDecimal("399.99"), new BigDecimal("0.00"), "Standard"));
        details.add(new InvoiceDetail(3, "KEYBOARD-MECH", "Mechanical Keyboard RGB", 
            2, new BigDecimal("149.99"), new BigDecimal("0.10"), "Standard"));
        details.add(new InvoiceDetail(4, "MOUSE-WIRELESS", "Wireless Ergonomic Mouse", 
            2, new BigDecimal("79.99"), new BigDecimal("0.00"), "Standard"));
        details.add(new InvoiceDetail(5, "SOFTWARE-OFFICE", "Office Suite Professional License", 
            2, new BigDecimal("249.99"), new BigDecimal("0.15"), "Software"));
        details.add(new InvoiceDetail(6, "SETUP-SERVICE", "Professional Setup and Configuration", 
            1, new BigDecimal("199.99"), new BigDecimal("0.00"), "Service"));
        details.add(new InvoiceDetail(7, "WARRANTY-EXT", "Extended Warranty Coverage (2 Years)", 
            1, new BigDecimal("299.99"), new BigDecimal("0.00"), "Service"));
        
        return details;
    }

    private static BigDecimal calculateSubtotal(List<InvoiceDetail> details) {
        return details.stream()
            .map(InvoiceDetail::getLineTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal calculateTaxAmount(List<InvoiceDetail> details) {
        BigDecimal subtotal = calculateSubtotal(details);
        return subtotal.multiply(new BigDecimal("0.08"));
    }

    private static BigDecimal calculateTotalAmount(List<InvoiceDetail> details) {
        return calculateSubtotal(details).add(calculateTaxAmount(details));
    }

    private static Date getDatePlusDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    private static String generateQRData(String invoiceNumber, BigDecimal amount) {
        return "INVOICE:" + invoiceNumber + "|AMOUNT:" + amount + "|PAY:techsolutions.com/pay";
    }
}
