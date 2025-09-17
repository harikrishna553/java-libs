package com.sample.app;

import java.math.BigDecimal;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class InventoryManagementReport {

    public static void main(String[] args) {
        try {
            String jrxmlFile = "src/main/resources/inventory_management.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            List<InventoryItem> inventory = getSampleInventoryData();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(inventory);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Inventory Management Report");
            parameters.put("WAREHOUSE_NAME", "Main Distribution Center");
            parameters.put("REPORT_DATE", new Date());
            parameters.put("TOTAL_VALUE", calculateTotalValue(inventory));
            parameters.put("LOW_STOCK_COUNT", countLowStockItems(inventory));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            String outputFile = "inventory_management_report.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

            System.out.println("Inventory Management Report generated: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class InventoryItem {
        private String itemCode;
        private String itemName;
        private String category;
        private String supplier;
        private Integer currentStock;
        private Integer minStock;
        private Integer maxStock;
        private BigDecimal unitCost;
        private BigDecimal unitPrice;
        private String location;
        private Date lastRestocked;
        private String status;
        private String barcode;

        public InventoryItem(String itemCode, String itemName, String category, String supplier,
                           Integer currentStock, Integer minStock, Integer maxStock,
                           BigDecimal unitCost, BigDecimal unitPrice, String location, Date lastRestocked) {
            this.itemCode = itemCode;
            this.itemName = itemName;
            this.category = category;
            this.supplier = supplier;
            this.currentStock = currentStock;
            this.minStock = minStock;
            this.maxStock = maxStock;
            this.unitCost = unitCost;
            this.unitPrice = unitPrice;
            this.location = location;
            this.lastRestocked = lastRestocked;
            this.barcode = "123456" + itemCode; // Simple barcode generation
            
            // Determine status
            if (currentStock <= minStock) {
                this.status = "LOW STOCK";
            } else if (currentStock >= maxStock) {
                this.status = "OVERSTOCK";
            } else {
                this.status = "NORMAL";
            }
        }

        // Getters
        public String getItemCode() { return itemCode; }
        public String getItemName() { return itemName; }
        public String getCategory() { return category; }
        public String getSupplier() { return supplier; }
        public Integer getCurrentStock() { return currentStock; }
        public Integer getMinStock() { return minStock; }
        public Integer getMaxStock() { return maxStock; }
        public BigDecimal getUnitCost() { return unitCost; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public String getLocation() { return location; }
        public Date getLastRestocked() { return lastRestocked; }
        public String getStatus() { return status; }
        public String getBarcode() { return barcode; }
        
        public BigDecimal getTotalValue() {
            return unitCost.multiply(new BigDecimal(currentStock));
        }
        
        public BigDecimal getPotentialProfit() {
            return unitPrice.subtract(unitCost).multiply(new BigDecimal(currentStock));
        }
    }

    private static List<InventoryItem> getSampleInventoryData() {
        List<InventoryItem> inventory = new ArrayList<>();
        Random random = new Random();
        
        String[] categories = {"Electronics", "Office Supplies", "Furniture", "Software", "Hardware", "Accessories"};
        String[] suppliers = {"TechSupply Co", "Office Depot", "FurniCorp", "SoftwareHouse", "HardwarePlus", "AccessoryWorld"};
        String[] locations = {"A1-01", "A1-02", "B2-01", "B2-02", "C3-01", "C3-02", "D4-01", "D4-02"};
        
        String[][] items = {
            {"LAPTOP001", "Gaming Laptop Pro", "Electronics"},
            {"MOUSE001", "Wireless Optical Mouse", "Electronics"},
            {"DESK001", "Executive Office Desk", "Furniture"},
            {"CHAIR001", "Ergonomic Office Chair", "Furniture"},
            {"PAPER001", "A4 Copy Paper Ream", "Office Supplies"},
            {"PEN001", "Blue Ink Ballpoint Pen", "Office Supplies"},
            {"SOFT001", "Office Suite License", "Software"},
            {"MONI001", "27-inch 4K Monitor", "Electronics"},
            {"KEYB001", "Mechanical Keyboard", "Electronics"},
            {"PRINT001", "Laser Printer", "Electronics"},
            {"CABLE001", "USB-C Cable 6ft", "Accessories"},
            {"STAND001", "Monitor Stand Adjustable", "Accessories"},
            {"HEADPH001", "Noise Canceling Headphones", "Electronics"},
            {"WEBCAM001", "HD Video Conference Camera", "Electronics"},
            {"TABLET001", "Business Tablet 10-inch", "Electronics"}
        };

        Calendar cal = Calendar.getInstance();
        for (String[] item : items) {
            cal.add(Calendar.DAY_OF_YEAR, -random.nextInt(30)); // Last 30 days
            
            BigDecimal cost = new BigDecimal(10 + random.nextInt(1000));
            BigDecimal price = cost.multiply(new BigDecimal(1.2 + random.nextDouble() * 0.5)); // 20-70% markup
            
            inventory.add(new InventoryItem(
                item[0],
                item[1],
                item[2],
                suppliers[random.nextInt(suppliers.length)],
                random.nextInt(100) + 1,
                10 + random.nextInt(20),
                80 + random.nextInt(50),
                cost,
                price,
                locations[random.nextInt(locations.length)],
                cal.getTime()
            ));
        }
        
        return inventory;
    }

    private static BigDecimal calculateTotalValue(List<InventoryItem> inventory) {
        return inventory.stream()
            .map(InventoryItem::getTotalValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static Long countLowStockItems(List<InventoryItem> inventory) {
        return inventory.stream()
            .filter(item -> "LOW STOCK".equals(item.getStatus()))
            .count();
    }
}
