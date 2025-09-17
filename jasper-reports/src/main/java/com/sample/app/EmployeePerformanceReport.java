package com.sample.app;

import java.math.BigDecimal;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EmployeePerformanceReport {

    public static void main(String[] args) {
        try {
            String jrxmlFile = "src/main/resources/employee_performance.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            List<EmployeePerformance> performances = getSamplePerformanceData();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(performances);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Employee Performance Review Report");
            parameters.put("REVIEW_PERIOD", "Q3 2024 (July - September)");
            parameters.put("REPORT_DATE", new Date());
            parameters.put("HR_MANAGER", "Sarah Johnson, HR Director");
            parameters.put("TOTAL_EMPLOYEES", performances.size());
            parameters.put("AVG_PERFORMANCE", calculateAveragePerformance(performances));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            String outputFile = "employee_performance_report.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

            System.out.println("Employee Performance Report generated: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class EmployeePerformance {
        private String employeeId;
        private String employeeName;
        private String department;
        private String position;
        private String manager;
        private Date hireDate;
        private BigDecimal currentSalary;
        private Integer performanceScore;
        private String performanceRating;
        private Integer goalsAchieved;
        private Integer totalGoals;
        private String strengths;
        private String areasForImprovement;
        private String careerGoals;
        private BigDecimal salaryRecommendation;
        private Date nextReviewDate;

        public EmployeePerformance(String employeeId, String employeeName, String department, String position,
                                 String manager, Date hireDate, BigDecimal currentSalary, Integer performanceScore,
                                 Integer goalsAchieved, Integer totalGoals, String strengths,
                                 String areasForImprovement, String careerGoals) {
            this.employeeId = employeeId;
            this.employeeName = employeeName;
            this.department = department;
            this.position = position;
            this.manager = manager;
            this.hireDate = hireDate;
            this.currentSalary = currentSalary;
            this.performanceScore = performanceScore;
            this.goalsAchieved = goalsAchieved;
            this.totalGoals = totalGoals;
            this.strengths = strengths;
            this.areasForImprovement = areasForImprovement;
            this.careerGoals = careerGoals;

            // Calculate rating based on score
            if (performanceScore >= 90) {
                this.performanceRating = "Outstanding";
                this.salaryRecommendation = currentSalary.multiply(new BigDecimal("1.08")); // 8% increase
            } else if (performanceScore >= 80) {
                this.performanceRating = "Exceeds Expectations";
                this.salaryRecommendation = currentSalary.multiply(new BigDecimal("1.05")); // 5% increase
            } else if (performanceScore >= 70) {
                this.performanceRating = "Meets Expectations";
                this.salaryRecommendation = currentSalary.multiply(new BigDecimal("1.03")); // 3% increase
            } else if (performanceScore >= 60) {
                this.performanceRating = "Below Expectations";
                this.salaryRecommendation = currentSalary; // No increase
            } else {
                this.performanceRating = "Unsatisfactory";
                this.salaryRecommendation = currentSalary; // No increase
            }

            // Next review date (6 months from now)
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 6);
            this.nextReviewDate = cal.getTime();
        }

        // Getters
        public String getEmployeeId() { return employeeId; }
        public String getEmployeeName() { return employeeName; }
        public String getDepartment() { return department; }
        public String getPosition() { return position; }
        public String getManager() { return manager; }
        public Date getHireDate() { return hireDate; }
        public BigDecimal getCurrentSalary() { return currentSalary; }
        public Integer getPerformanceScore() { return performanceScore; }
        public String getPerformanceRating() { return performanceRating; }
        public Integer getGoalsAchieved() { return goalsAchieved; }
        public Integer getTotalGoals() { return totalGoals; }
        public String getStrengths() { return strengths; }
        public String getAreasForImprovement() { return areasForImprovement; }
        public String getCareerGoals() { return careerGoals; }
        public BigDecimal getSalaryRecommendation() { return salaryRecommendation; }
        public Date getNextReviewDate() { return nextReviewDate; }
        
        public Double getGoalCompletionRate() {
            return totalGoals > 0 ? (double) goalsAchieved / totalGoals * 100 : 0.0;
        }
        
        public BigDecimal getSalaryIncrease() {
            return salaryRecommendation.subtract(currentSalary);
        }
    }

    private static List<EmployeePerformance> getSamplePerformanceData() {
        List<EmployeePerformance> performances = new ArrayList<>();
        Random random = new Random();
        
        String[] departments = {"Engineering", "Sales", "Marketing", "HR", "Finance", "Operations"};
        String[] positions = {"Senior Developer", "Account Manager", "Marketing Specialist", "HR Coordinator", 
                             "Financial Analyst", "Operations Manager", "Team Lead", "Product Manager"};
        String[] managers = {"John Smith", "Alice Johnson", "Bob Wilson", "Carol Brown", "David Lee", "Eva Martinez"};
        
        String[][] employees = {
            {"EMP001", "Michael Anderson", "Strong problem-solving, leadership skills", "Time management, delegation"},
            {"EMP002", "Sarah Davis", "Excellent communication, client relations", "Technical skills, process improvement"},
            {"EMP003", "James Wilson", "Creative thinking, strategic planning", "Detail orientation, follow-up"},
            {"EMP004", "Lisa Thompson", "Team collaboration, analytical skills", "Public speaking, conflict resolution"},
            {"EMP005", "Robert Johnson", "Technical expertise, mentoring", "Documentation, meeting deadlines"},
            {"EMP006", "Jennifer Miller", "Customer service, multitasking", "Technical training, system knowledge"},
            {"EMP007", "William Brown", "Project management, innovation", "Budget planning, risk assessment"},
            {"EMP008", "Jessica Garcia", "Data analysis, reporting", "Presentation skills, stakeholder management"},
            {"EMP009", "Christopher Martinez", "Quality assurance, attention to detail", "Speed of delivery, automation"},
            {"EMP010", "Amanda Rodriguez", "Training and development, coaching", "Performance metrics, digital tools"}
        };
        
        String[] careerGoalsList = {
            "Senior Management Role", "Technical Leadership", "Product Ownership", "Team Management",
            "Specialization in AI/ML", "International Assignment", "Executive Position", "Consulting Role"
        };

        Calendar hireDate = Calendar.getInstance();
        for (String[] emp : employees) {
            hireDate.add(Calendar.YEAR, -random.nextInt(5) - 1); // 1-6 years ago
            
            performances.add(new EmployeePerformance(
                emp[0],
                emp[1],
                departments[random.nextInt(departments.length)],
                positions[random.nextInt(positions.length)],
                managers[random.nextInt(managers.length)],
                hireDate.getTime(),
                new BigDecimal(60000 + random.nextInt(80000)), // 60k-140k salary
                60 + random.nextInt(40), // 60-100 performance score
                3 + random.nextInt(7), // 3-9 goals achieved
                5 + random.nextInt(5), // 5-9 total goals
                emp[2],
                emp[3],
                careerGoalsList[random.nextInt(careerGoalsList.length)]
            ));
        }
        
        return performances;
    }

    private static Double calculateAveragePerformance(List<EmployeePerformance> performances) {
        return performances.stream()
            .mapToInt(EmployeePerformance::getPerformanceScore)
            .average()
            .orElse(0.0);
    }
}
