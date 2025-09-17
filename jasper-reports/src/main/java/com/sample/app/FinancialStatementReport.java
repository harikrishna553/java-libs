package com.sample.app;

import java.math.BigDecimal;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FinancialStatementReport {

    public static void main(String[] args) {
        try {
            String jrxmlFile = "src/main/resources/financial_statement.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            // Create financial data
            List<FinancialAccount> accounts = getSampleFinancialData();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(accounts);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("COMPANY_NAME", "TechnoSoft Corporation");
            parameters.put("STATEMENT_PERIOD", "For the Year Ended December 31, 2024");
            parameters.put("REPORT_DATE", new Date());
            parameters.put("TOTAL_ASSETS", calculateTotalAssets(accounts));
            parameters.put("TOTAL_LIABILITIES", calculateTotalLiabilities(accounts));
            parameters.put("TOTAL_EQUITY", calculateTotalEquity(accounts));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            String outputFile = "financial_statement_report.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

            System.out.println("Financial Statement Report generated: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class FinancialAccount {
        private String accountCode;
        private String accountName;
        private String accountType;
        private String category;
        private BigDecimal currentBalance;
        private BigDecimal previousBalance;
        private BigDecimal variance;
        private String description;

        public FinancialAccount(String accountCode, String accountName, String accountType, String category,
                              BigDecimal currentBalance, BigDecimal previousBalance, String description) {
            this.accountCode = accountCode;
            this.accountName = accountName;
            this.accountType = accountType;
            this.category = category;
            this.currentBalance = currentBalance;
            this.previousBalance = previousBalance;
            this.variance = currentBalance.subtract(previousBalance);
            this.description = description;
        }

        // Getters
        public String getAccountCode() { return accountCode; }
        public String getAccountName() { return accountName; }
        public String getAccountType() { return accountType; }
        public String getCategory() { return category; }
        public BigDecimal getCurrentBalance() { return currentBalance; }
        public BigDecimal getPreviousBalance() { return previousBalance; }
        public BigDecimal getVariance() { return variance; }
        public String getDescription() { return description; }
    }

    private static List<FinancialAccount> getSampleFinancialData() {
        List<FinancialAccount> accounts = new ArrayList<>();
        
        // Assets
        accounts.add(new FinancialAccount("1001", "Cash and Cash Equivalents", "Asset", "Current Assets", 
            new BigDecimal("125000"), new BigDecimal("98000"), "Operating cash and short-term investments"));
        accounts.add(new FinancialAccount("1002", "Accounts Receivable", "Asset", "Current Assets",
            new BigDecimal("87500"), new BigDecimal("72000"), "Outstanding customer invoices"));
        accounts.add(new FinancialAccount("1003", "Inventory", "Asset", "Current Assets",
            new BigDecimal("156000"), new BigDecimal("134000"), "Product inventory at cost"));
        accounts.add(new FinancialAccount("1101", "Property, Plant & Equipment", "Asset", "Fixed Assets",
            new BigDecimal("450000"), new BigDecimal("475000"), "Buildings, equipment, and vehicles"));
        accounts.add(new FinancialAccount("1102", "Accumulated Depreciation", "Asset", "Fixed Assets",
            new BigDecimal("-125000"), new BigDecimal("-95000"), "Accumulated depreciation on fixed assets"));

        // Liabilities
        accounts.add(new FinancialAccount("2001", "Accounts Payable", "Liability", "Current Liabilities",
            new BigDecimal("45000"), new BigDecimal("38000"), "Outstanding supplier invoices"));
        accounts.add(new FinancialAccount("2002", "Short-term Debt", "Liability", "Current Liabilities",
            new BigDecimal("25000"), new BigDecimal("30000"), "Short-term loans and credit facilities"));
        accounts.add(new FinancialAccount("2101", "Long-term Debt", "Liability", "Long-term Liabilities",
            new BigDecimal("200000"), new BigDecimal("225000"), "Long-term loans and mortgages"));

        // Equity
        accounts.add(new FinancialAccount("3001", "Common Stock", "Equity", "Stockholders' Equity",
            new BigDecimal("100000"), new BigDecimal("100000"), "Issued common stock"));
        accounts.add(new FinancialAccount("3002", "Retained Earnings", "Equity", "Stockholders' Equity",
            new BigDecimal("323500"), new BigDecimal("291000"), "Accumulated retained earnings"));

        return accounts;
    }

    private static BigDecimal calculateTotalAssets(List<FinancialAccount> accounts) {
        return accounts.stream()
            .filter(a -> "Asset".equals(a.getAccountType()))
            .map(FinancialAccount::getCurrentBalance)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal calculateTotalLiabilities(List<FinancialAccount> accounts) {
        return accounts.stream()
            .filter(a -> "Liability".equals(a.getAccountType()))
            .map(FinancialAccount::getCurrentBalance)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal calculateTotalEquity(List<FinancialAccount> accounts) {
        return accounts.stream()
            .filter(a -> "Equity".equals(a.getAccountType()))
            .map(FinancialAccount::getCurrentBalance)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
