package com.sample.app;

import java.math.BigDecimal;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class QualityControlReport {

    public static void main(String[] args) {
        try {
            String jrxmlFile = "src/main/resources/quality_control.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            List<QualityInspection> inspections = getSampleQualityData();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(inspections);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Quality Control Inspection Report");
            parameters.put("REPORT_DATE", new Date());
            parameters.put("QC_MANAGER", "Patricia Wilson");
            parameters.put("TOTAL_INSPECTIONS", inspections.size());
            parameters.put("PASS_RATE", calculatePassRate(inspections));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "quality_control_report.pdf");
            System.out.println("Quality Control Report generated: quality_control_report.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class QualityInspection {
        private String batchNumber;
        private String productCode;
        private String productName;
        private Date inspectionDate;
        private String inspector;
        private String shift;
        private Integer unitsInspected;
        private Integer defectsFound;
        private String defectType;
        private String severity;
        private String result;
        private String correctionAction;
        private BigDecimal defectRate;
        private String qualityScore;

        public QualityInspection(String batchNumber, String productCode, String productName,
                               Date inspectionDate, String inspector, String shift,
                               Integer unitsInspected, Integer defectsFound, String defectType, String severity) {
            this.batchNumber = batchNumber;
            this.productCode = productCode;
            this.productName = productName;
            this.inspectionDate = inspectionDate;
            this.inspector = inspector;
            this.shift = shift;
            this.unitsInspected = unitsInspected;
            this.defectsFound = defectsFound;
            this.defectType = defectType;
            this.severity = severity;

            // Calculate defect rate
            this.defectRate = unitsInspected > 0 ? 
                new BigDecimal(defectsFound).divide(new BigDecimal(unitsInspected), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)) :
                BigDecimal.ZERO;

            // Determine result and quality score
            if (defectRate.compareTo(new BigDecimal("1.0")) <= 0) {
                this.result = "PASS";
                this.qualityScore = "A";
                this.correctionAction = "None Required";
            } else if (defectRate.compareTo(new BigDecimal("3.0")) <= 0) {
                this.result = "CONDITIONAL PASS";
                this.qualityScore = "B";
                this.correctionAction = "Minor Adjustments";
            } else if (defectRate.compareTo(new BigDecimal("5.0")) <= 0) {
                this.result = "FAIL";
                this.qualityScore = "C";
                this.correctionAction = "Process Review Required";
            } else {
                this.result = "CRITICAL FAIL";
                this.qualityScore = "F";
                this.correctionAction = "Full Investigation & Rework";
            }
        }

        // Getters
        public String getBatchNumber() { return batchNumber; }
        public String getProductCode() { return productCode; }
        public String getProductName() { return productName; }
        public Date getInspectionDate() { return inspectionDate; }
        public String getInspector() { return inspector; }
        public String getShift() { return shift; }
        public Integer getUnitsInspected() { return unitsInspected; }
        public Integer getDefectsFound() { return defectsFound; }
        public String getDefectType() { return defectType; }
        public String getSeverity() { return severity; }
        public String getResult() { return result; }
        public String getCorrectionAction() { return correctionAction; }
        public BigDecimal getDefectRate() { return defectRate; }
        public String getQualityScore() { return qualityScore; }
    }

    private static List<QualityInspection> getSampleQualityData() {
        List<QualityInspection> inspections = new ArrayList<>();
        Random random = new Random();

        String[] products = {
            "WIDGET-A", "Widget Assembly Type A",
            "WIDGET-B", "Widget Assembly Type B",
            "GEAR-X1", "Precision Gear Model X1",
            "CIRCUIT-PCB", "Circuit Board Assembly",
            "MOTOR-V2", "Electric Motor Version 2"
        };

        String[] inspectors = {"Alice Chen", "Bob Martinez", "Carol Zhang", "David Kumar", "Eva Rodriguez"};
        String[] shifts = {"Morning", "Afternoon", "Night"};
        String[] defectTypes = {"Dimensional", "Surface Finish", "Electrical", "Mechanical", "Visual", "None"};
        String[] severities = {"Minor", "Major", "Critical"};

        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 40; i++) {
            cal.add(Calendar.DAY_OF_YEAR, -random.nextInt(30));
            
            String batchNum = "BATCH-" + String.format("%04d", random.nextInt(9999));
            int productIndex = (random.nextInt(products.length / 2)) * 2;
            int unitsInspected = 50 + random.nextInt(200);
            int defectsFound = random.nextInt(Math.max(1, unitsInspected / 20)); // 0-5% defect rate typically

            inspections.add(new QualityInspection(
                batchNum,
                products[productIndex],
                products[productIndex + 1],
                cal.getTime(),
                inspectors[random.nextInt(inspectors.length)],
                shifts[random.nextInt(shifts.length)],
                unitsInspected,
                defectsFound,
                defectsFound > 0 ? defectTypes[random.nextInt(defectTypes.length - 1)] : "None",
                defectsFound > 0 ? severities[random.nextInt(severities.length)] : "None"
            ));
        }

        return inspections;
    }

    private static Double calculatePassRate(List<QualityInspection> inspections) {
        long passCount = inspections.stream().filter(i -> "PASS".equals(i.getResult())).count();
        return (double) passCount / inspections.size() * 100;
    }
}
