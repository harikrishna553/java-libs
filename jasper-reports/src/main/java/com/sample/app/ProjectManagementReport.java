package com.sample.app;

import java.math.BigDecimal;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ProjectManagementReport {

    public static void main(String[] args) {
        try {
            String jrxmlFile = "src/main/resources/project_management.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            List<ProjectTask> tasks = getSampleProjectData();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tasks);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Project Management Dashboard");
            parameters.put("REPORT_DATE", new Date());
            parameters.put("PROJECT_MANAGER", "Alex Rodriguez");
            parameters.put("TOTAL_PROJECTS", getUniqueProjectCount(tasks));
            parameters.put("COMPLETION_RATE", calculateOverallCompletion(tasks));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "project_management_report.pdf");
            System.out.println("Project Management Report generated: project_management_report.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ProjectTask {
        private String projectId;
        private String projectName;
        private String taskId;
        private String taskName;
        private String assignedTo;
        private String status;
        private Date startDate;
        private Date dueDate;
        private Date completedDate;
        private Integer progressPercent;
        private String priority;
        private BigDecimal estimatedHours;
        private BigDecimal actualHours;
        private BigDecimal budget;
        private BigDecimal spent;

        public ProjectTask(String projectId, String projectName, String taskId, String taskName,
                          String assignedTo, String status, Date startDate, Date dueDate,
                          Integer progressPercent, String priority, BigDecimal estimatedHours,
                          BigDecimal actualHours, BigDecimal budget) {
            this.projectId = projectId;
            this.projectName = projectName;
            this.taskId = taskId;
            this.taskName = taskName;
            this.assignedTo = assignedTo;
            this.status = status;
            this.startDate = startDate;
            this.dueDate = dueDate;
            this.progressPercent = progressPercent;
            this.priority = priority;
            this.estimatedHours = estimatedHours;
            this.actualHours = actualHours;
            this.budget = budget;
            this.spent = actualHours.multiply(new BigDecimal("75")); // $75/hour rate
            
            if ("Completed".equals(status)) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dueDate);
                cal.add(Calendar.DAY_OF_YEAR, -new Random().nextInt(5));
                this.completedDate = cal.getTime();
            }
        }

        // Getters
        public String getProjectId() { return projectId; }
        public String getProjectName() { return projectName; }
        public String getTaskId() { return taskId; }
        public String getTaskName() { return taskName; }
        public String getAssignedTo() { return assignedTo; }
        public String getStatus() { return status; }
        public Date getStartDate() { return startDate; }
        public Date getDueDate() { return dueDate; }
        public Date getCompletedDate() { return completedDate; }
        public Integer getProgressPercent() { return progressPercent; }
        public String getPriority() { return priority; }
        public BigDecimal getEstimatedHours() { return estimatedHours; }
        public BigDecimal getActualHours() { return actualHours; }
        public BigDecimal getBudget() { return budget; }
        public BigDecimal getSpent() { return spent; }
        
        public String getHealthStatus() {
            if (spent.compareTo(budget) > 0) return "Over Budget";
            if (progressPercent < 50 && getDaysUntilDue() < 7) return "At Risk";
            if ("Completed".equals(status)) return "Complete";
            return "On Track";
        }
        
        private int getDaysUntilDue() {
            long diff = dueDate.getTime() - new Date().getTime();
            return (int) (diff / (24 * 60 * 60 * 1000));
        }
    }

    private static List<ProjectTask> getSampleProjectData() {
        List<ProjectTask> tasks = new ArrayList<>();
        Random random = new Random();
        
        String[][] projects = {
            {"PROJ001", "E-Commerce Platform Redesign"},
            {"PROJ002", "Mobile App Development"},
            {"PROJ003", "Data Analytics Dashboard"},
            {"PROJ004", "Cloud Migration Project"},
            {"PROJ005", "Security Audit Implementation"}
        };
        
        String[] assignees = {"John Doe", "Jane Smith", "Bob Johnson", "Alice Brown", "Charlie Wilson"};
        String[] statuses = {"Not Started", "In Progress", "In Review", "Completed", "On Hold"};
        String[] priorities = {"Low", "Medium", "High", "Critical"};
        
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -3);
        
        for (String[] project : projects) {
            for (int i = 1; i <= 8; i++) {
                Calendar taskStart = (Calendar) startDate.clone();
                taskStart.add(Calendar.DAY_OF_YEAR, i * 7);
                Calendar taskDue = (Calendar) taskStart.clone();
                taskDue.add(Calendar.DAY_OF_YEAR, 7 + random.nextInt(14));
                
                tasks.add(new ProjectTask(
                    project[0],
                    project[1],
                    project[0] + "-T" + String.format("%03d", i),
                    "Task " + i + " - " + project[1].substring(0, Math.min(15, project[1].length())),
                    assignees[random.nextInt(assignees.length)],
                    statuses[random.nextInt(statuses.length)],
                    taskStart.getTime(),
                    taskDue.getTime(),
                    random.nextInt(101),
                    priorities[random.nextInt(priorities.length)],
                    new BigDecimal(10 + random.nextInt(40)),
                    new BigDecimal(8 + random.nextInt(50)),
                    new BigDecimal(500 + random.nextInt(2000))
                ));
            }
        }
        
        return tasks;
    }

    private static Integer getUniqueProjectCount(List<ProjectTask> tasks) {
        return (int) tasks.stream().map(ProjectTask::getProjectId).distinct().count();
    }

    private static Double calculateOverallCompletion(List<ProjectTask> tasks) {
        return tasks.stream().mapToInt(ProjectTask::getProgressPercent).average().orElse(0.0);
    }
}
