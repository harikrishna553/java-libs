package com.sample.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

public class MapBasedSalesReport {

    public static void main(String[] args) {
        try {
            // Create JRXML file path for Map-based data
            String jrxmlFile = "src/main/resources/simple_map_based_sales.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            // Create sales data using Maps instead of POJOs
            List<Map<String, Object>> salesData = getSampleSalesDataAsMaps();
            
            // Convert to Collection<Map<String, ?>> for JRMapCollectionDataSource
            Collection<Map<String, ?>> mapCollection = new ArrayList<>();
            for (Map<String, Object> map : salesData) {
                mapCollection.add(map);
            }
            JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(mapCollection);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("COMPANY_NAME", "TechnoSoft Corporation");
            parameters.put("REPORT_TITLE", "Quarterly Sales Report - Map Based Data");
            parameters.put("REPORT_DATE", new Date());
            parameters.put("TOTAL_SALES", calculateTotalSales(salesData));
            parameters.put("TOTAL_ORDERS", salesData.size());
            parameters.put("AVERAGE_ORDER", calculateAverageOrder(salesData));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            String outputFile = "map_based_sales_report.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

            System.out.println("Map-Based Sales Report generated: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Map<String, Object>> getSampleSalesDataAsMaps() {
        List<Map<String, Object>> salesData = new ArrayList<>();
        
        // Each Map represents a sales record
        Map<String, Object> sale1 = new HashMap<>();
        sale1.put("orderId", "ORD-2024-001");
        sale1.put("customerName", "ABC Corporation");
        sale1.put("customerEmail", "contact@abc-corp.com");
        sale1.put("productName", "Enterprise Software License");
        sale1.put("category", "Software");
        sale1.put("quantity", 5);
        sale1.put("unitPrice", new BigDecimal("2500.00"));
        sale1.put("totalAmount", new BigDecimal("12500.00"));
        sale1.put("orderDate", new Date(System.currentTimeMillis() - 86400000 * 10)); // 10 days ago
        sale1.put("salesRep", "John Smith");
        sale1.put("region", "North");
        sale1.put("status", "Completed");
        sale1.put("discount", new BigDecimal("5.0")); // 5% discount
        salesData.add(sale1);

        Map<String, Object> sale2 = new HashMap<>();
        sale2.put("orderId", "ORD-2024-002");
        sale2.put("customerName", "XYZ Industries");
        sale2.put("customerEmail", "orders@xyz-ind.com");
        sale2.put("productName", "Cloud Storage Package");
        sale2.put("category", "Services");
        sale2.put("quantity", 10);
        sale2.put("unitPrice", new BigDecimal("150.00"));
        sale2.put("totalAmount", new BigDecimal("1500.00"));
        sale2.put("orderDate", new Date(System.currentTimeMillis() - 86400000 * 8)); // 8 days ago
        sale2.put("salesRep", "Sarah Johnson");
        sale2.put("region", "South");
        sale2.put("status", "Shipped");
        sale2.put("discount", new BigDecimal("0.0"));
        salesData.add(sale2);

        Map<String, Object> sale3 = new HashMap<>();
        sale3.put("orderId", "ORD-2024-003");
        sale3.put("customerName", "Global Tech Solutions");
        sale3.put("customerEmail", "procurement@globaltech.com");
        sale3.put("productName", "Hardware Maintenance");
        sale3.put("category", "Services");
        sale3.put("quantity", 1);
        sale3.put("unitPrice", new BigDecimal("8500.00"));
        sale3.put("totalAmount", new BigDecimal("8500.00"));
        sale3.put("orderDate", new Date(System.currentTimeMillis() - 86400000 * 5)); // 5 days ago
        sale3.put("salesRep", "Mike Davis");
        sale3.put("region", "East");
        sale3.put("status", "In Progress");
        sale3.put("discount", new BigDecimal("10.0")); // 10% discount
        salesData.add(sale3);

        Map<String, Object> sale4 = new HashMap<>();
        sale4.put("orderId", "ORD-2024-004");
        sale4.put("customerName", "Innovative Systems Ltd");
        sale4.put("customerEmail", "sales@innovative-sys.com");
        sale4.put("productName", "Database Server License");
        sale4.put("category", "Software");
        sale4.put("quantity", 3);
        sale4.put("unitPrice", new BigDecimal("4200.00"));
        sale4.put("totalAmount", new BigDecimal("12600.00"));
        sale4.put("orderDate", new Date(System.currentTimeMillis() - 86400000 * 3)); // 3 days ago
        sale4.put("salesRep", "Emily Wilson");
        sale4.put("region", "West");
        sale4.put("status", "Completed");
        sale4.put("discount", new BigDecimal("7.5")); // 7.5% discount
        salesData.add(sale4);

        Map<String, Object> sale5 = new HashMap<>();
        sale5.put("orderId", "ORD-2024-005");
        sale5.put("customerName", "Digital Marketing Pro");
        sale5.put("customerEmail", "admin@digitalmarketing.com");
        sale5.put("productName", "Analytics Dashboard");
        sale5.put("category", "Software");
        sale5.put("quantity", 2);
        sale5.put("unitPrice", new BigDecimal("1800.00"));
        sale5.put("totalAmount", new BigDecimal("3600.00"));
        sale5.put("orderDate", new Date(System.currentTimeMillis() - 86400000 * 1)); // 1 day ago
        sale5.put("salesRep", "David Brown");
        sale5.put("region", "North");
        sale5.put("status", "Pending");
        sale5.put("discount", new BigDecimal("2.5")); // 2.5% discount
        salesData.add(sale5);

        Map<String, Object> sale6 = new HashMap<>();
        sale6.put("orderId", "ORD-2024-006");
        sale6.put("customerName", "Manufacturing Corp");
        sale6.put("customerEmail", "it@manufacturing.com");
        sale6.put("productName", "ERP System Integration");
        sale6.put("category", "Services");
        sale6.put("quantity", 1);
        sale6.put("unitPrice", new BigDecimal("25000.00"));
        sale6.put("totalAmount", new BigDecimal("25000.00"));
        sale6.put("orderDate", new Date(System.currentTimeMillis() - 86400000 * 12)); // 12 days ago
        sale6.put("salesRep", "Lisa Anderson");
        sale6.put("region", "South");
        sale6.put("status", "Completed");
        sale6.put("discount", new BigDecimal("15.0")); // 15% discount
        salesData.add(sale6);

        return salesData;
    }

    private static BigDecimal calculateTotalSales(List<Map<String, Object>> salesData) {
        return salesData.stream()
            .map(sale -> (BigDecimal) sale.get("totalAmount"))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal calculateAverageOrder(List<Map<String, Object>> salesData) {
        if (salesData.isEmpty()) return BigDecimal.ZERO;
        
        BigDecimal total = calculateTotalSales(salesData);
        return total.divide(new BigDecimal(salesData.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    // Utility method to demonstrate accessing nested data from Maps
    public static void printSalesData(List<Map<String, Object>> salesData) {
        System.out.println("=== MAP-BASED SALES DATA ===");
        for (Map<String, Object> sale : salesData) {
            System.out.printf("Order: %s | Customer: %s | Product: %s | Amount: %s | Rep: %s | Region: %s%n",
                sale.get("orderId"),
                sale.get("customerName"),
                sale.get("productName"),
                sale.get("totalAmount"),
                sale.get("salesRep"),
                sale.get("region")
            );
        }
        System.out.println("========================");
    }

    // Additional utility method to group data by region (demonstrating Map manipulation)
    public static Map<String, List<Map<String, Object>>> groupByRegion(List<Map<String, Object>> salesData) {
        Map<String, List<Map<String, Object>>> groupedData = new HashMap<>();
        
        for (Map<String, Object> sale : salesData) {
            String region = (String) sale.get("region");
            groupedData.computeIfAbsent(region, k -> new ArrayList<>()).add(sale);
        }
        
        return groupedData;
    }

    // Method to calculate regional totals
    public static Map<String, BigDecimal> calculateRegionalTotals(List<Map<String, Object>> salesData) {
        Map<String, BigDecimal> regionalTotals = new HashMap<>();
        
        for (Map<String, Object> sale : salesData) {
            String region = (String) sale.get("region");
            BigDecimal amount = (BigDecimal) sale.get("totalAmount");
            
            regionalTotals.merge(region, amount, BigDecimal::add);
        }
        
        return regionalTotals;
    }
}
