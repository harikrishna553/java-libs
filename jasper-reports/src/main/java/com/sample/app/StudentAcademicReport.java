package com.sample.app;

import java.math.BigDecimal;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class StudentAcademicReport {

    public static void main(String[] args) {
        try {
            String jrxmlFile = "src/main/resources/student_academic.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            List<StudentGrade> grades = getSampleStudentData();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(grades);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Student Academic Performance Report");
            parameters.put("SCHOOL_NAME", "Riverside High School");
            parameters.put("SEMESTER", "Fall 2024");
            parameters.put("REPORT_DATE", new Date());
            parameters.put("PRINCIPAL", "Dr. Robert Johnson");
            parameters.put("TOTAL_STUDENTS", getUniqueStudentCount(grades));
            parameters.put("CLASS_AVERAGE", calculateClassAverage(grades));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "student_academic_report.pdf");
            System.out.println("Student Academic Report generated: student_academic_report.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class StudentGrade {
        private String studentId;
        private String studentName;
        private String grade;
        private String subject;
        private String teacher;
        private Integer semester;
        private BigDecimal quiz1;
        private BigDecimal quiz2;
        private BigDecimal midterm;
        private BigDecimal finalExam;
        private BigDecimal homework;
        private BigDecimal participation;
        private BigDecimal finalGrade;
        private String letterGrade;
        private String status;
        private String parentContact;
        private Integer absences;
        private String comments;

        public StudentGrade(String studentId, String studentName, String grade, String subject,
                          String teacher, Integer semester, BigDecimal quiz1, BigDecimal quiz2,
                          BigDecimal midterm, BigDecimal finalExam, BigDecimal homework,
                          BigDecimal participation, String parentContact, Integer absences) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.grade = grade;
            this.subject = subject;
            this.teacher = teacher;
            this.semester = semester;
            this.quiz1 = quiz1;
            this.quiz2 = quiz2;
            this.midterm = midterm;
            this.finalExam = finalExam;
            this.homework = homework;
            this.participation = participation;
            this.parentContact = parentContact;
            this.absences = absences;

            // Calculate weighted final grade: Quiz(20%), Midterm(25%), Final(30%), HW(15%), Participation(10%)
            this.finalGrade = quiz1.multiply(new BigDecimal("0.10"))
                .add(quiz2.multiply(new BigDecimal("0.10")))
                .add(midterm.multiply(new BigDecimal("0.25")))
                .add(finalExam.multiply(new BigDecimal("0.30")))
                .add(homework.multiply(new BigDecimal("0.15")))
                .add(participation.multiply(new BigDecimal("0.10")));

            // Determine letter grade and status
            if (finalGrade.compareTo(new BigDecimal("97")) >= 0) {
                this.letterGrade = "A+";
                this.status = "Excellent";
                this.comments = "Outstanding performance. Keep up the excellent work!";
            } else if (finalGrade.compareTo(new BigDecimal("93")) >= 0) {
                this.letterGrade = "A";
                this.status = "Excellent";
                this.comments = "Excellent work. Continue to challenge yourself.";
            } else if (finalGrade.compareTo(new BigDecimal("90")) >= 0) {
                this.letterGrade = "A-";
                this.status = "Very Good";
                this.comments = "Very good performance. Small improvements needed.";
            } else if (finalGrade.compareTo(new BigDecimal("87")) >= 0) {
                this.letterGrade = "B+";
                this.status = "Good";
                this.comments = "Good work. Focus on consistent performance.";
            } else if (finalGrade.compareTo(new BigDecimal("83")) >= 0) {
                this.letterGrade = "B";
                this.status = "Good";
                this.comments = "Satisfactory progress. More effort needed.";
            } else if (finalGrade.compareTo(new BigDecimal("80")) >= 0) {
                this.letterGrade = "B-";
                this.status = "Satisfactory";
                this.comments = "Meets expectations. Room for improvement.";
            } else if (finalGrade.compareTo(new BigDecimal("77")) >= 0) {
                this.letterGrade = "C+";
                this.status = "Needs Improvement";
                this.comments = "Below expectations. Additional support needed.";
            } else if (finalGrade.compareTo(new BigDecimal("73")) >= 0) {
                this.letterGrade = "C";
                this.status = "Needs Improvement";
                this.comments = "Significant improvement required.";
            } else if (finalGrade.compareTo(new BigDecimal("70")) >= 0) {
                this.letterGrade = "C-";
                this.status = "At Risk";
                this.comments = "At risk of failing. Immediate intervention needed.";
            } else if (finalGrade.compareTo(new BigDecimal("60")) >= 0) {
                this.letterGrade = "D";
                this.status = "Failing";
                this.comments = "Failing grade. Parent conference required.";
            } else {
                this.letterGrade = "F";
                this.status = "Failing";
                this.comments = "Failing grade. Remediation required.";
            }
        }

        // Getters
        public String getStudentId() { return studentId; }
        public String getStudentName() { return studentName; }
        public String getGrade() { return grade; }
        public String getSubject() { return subject; }
        public String getTeacher() { return teacher; }
        public Integer getSemester() { return semester; }
        public BigDecimal getQuiz1() { return quiz1; }
        public BigDecimal getQuiz2() { return quiz2; }
        public BigDecimal getMidterm() { return midterm; }
        public BigDecimal getFinalExam() { return finalExam; }
        public BigDecimal getHomework() { return homework; }
        public BigDecimal getParticipation() { return participation; }
        public BigDecimal getFinalGrade() { return finalGrade; }
        public String getLetterGrade() { return letterGrade; }
        public String getStatus() { return status; }
        public String getParentContact() { return parentContact; }
        public Integer getAbsences() { return absences; }
        public String getComments() { return comments; }
    }

    private static List<StudentGrade> getSampleStudentData() {
        List<StudentGrade> grades = new ArrayList<>();
        Random random = new Random();

        String[] firstNames = {"Alex", "Emma", "Liam", "Olivia", "Noah", "Ava", "Ethan", "Sophia", "Mason", "Isabella"};
        String[] lastNames = {"Anderson", "Brown", "Davis", "Garcia", "Johnson", "Lee", "Martinez", "Miller", "Smith", "Wilson"};
        String[] gradeLevel = {"9th", "10th", "11th", "12th"};
        String[] subjects = {"Mathematics", "English Literature", "Biology", "Chemistry", "Physics", "History", "Art", "Spanish"};
        String[] teachers = {"Mr. Thompson", "Ms. Rodriguez", "Dr. Chen", "Mrs. Johnson", "Mr. Kumar", "Ms. Williams"};

        for (int i = 0; i < 30; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];

            grades.add(new StudentGrade(
                "STU-" + String.format("%05d", 10000 + i),
                firstName + " " + lastName,
                gradeLevel[random.nextInt(gradeLevel.length)],
                subjects[random.nextInt(subjects.length)],
                teachers[random.nextInt(teachers.length)],
                1,
                new BigDecimal(60 + random.nextInt(40)), // Quiz 1: 60-100
                new BigDecimal(60 + random.nextInt(40)), // Quiz 2: 60-100
                new BigDecimal(55 + random.nextInt(45)), // Midterm: 55-100
                new BigDecimal(50 + random.nextInt(50)), // Final: 50-100
                new BigDecimal(70 + random.nextInt(30)), // Homework: 70-100
                new BigDecimal(75 + random.nextInt(25)), // Participation: 75-100
                firstName.toLowerCase() + "." + lastName.toLowerCase() + "@email.com",
                random.nextInt(8) // 0-7 absences
            ));
        }

        return grades;
    }

    private static Integer getUniqueStudentCount(List<StudentGrade> grades) {
        return (int) grades.stream().map(StudentGrade::getStudentId).distinct().count();
    }

    private static Double calculateClassAverage(List<StudentGrade> grades) {
        return grades.stream().mapToDouble(g -> g.getFinalGrade().doubleValue()).average().orElse(0.0);
    }
}
