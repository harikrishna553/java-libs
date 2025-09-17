package com.sample.app;

import java.math.BigDecimal;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class HospitalPatientReport {

    public static void main(String[] args) {
        try {
            String jrxmlFile = "src/main/resources/hospital_patient.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            List<PatientRecord> patients = getSamplePatientData();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(patients);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Hospital Patient Status Report");
            parameters.put("HOSPITAL_NAME", "City General Medical Center");
            parameters.put("REPORT_DATE", new Date());
            parameters.put("CHIEF_MEDICAL_OFFICER", "Dr. Sarah Thompson, MD");
            parameters.put("TOTAL_PATIENTS", patients.size());
            parameters.put("OCCUPANCY_RATE", calculateOccupancyRate(patients));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "hospital_patient_report.pdf");
            System.out.println("Hospital Patient Report generated: hospital_patient_report.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class PatientRecord {
        private String patientId;
        private String patientName;
        private Integer age;
        private String gender;
        private Date admissionDate;
        private String department;
        private String roomNumber;
        private String attendingPhysician;
        private String diagnosis;
        private String status;
        private String insurance;
        private BigDecimal dailyCharge;
        private Integer lengthOfStay;
        private BigDecimal totalCharges;
        private Date expectedDischarge;
        private String emergencyContact;
        private String bloodType;
        private String allergies;

        public PatientRecord(String patientId, String patientName, Integer age, String gender,
                           Date admissionDate, String department, String roomNumber,
                           String attendingPhysician, String diagnosis, String status,
                           String insurance, BigDecimal dailyCharge, String emergencyContact,
                           String bloodType, String allergies) {
            this.patientId = patientId;
            this.patientName = patientName;
            this.age = age;
            this.gender = gender;
            this.admissionDate = admissionDate;
            this.department = department;
            this.roomNumber = roomNumber;
            this.attendingPhysician = attendingPhysician;
            this.diagnosis = diagnosis;
            this.status = status;
            this.insurance = insurance;
            this.dailyCharge = dailyCharge;
            this.emergencyContact = emergencyContact;
            this.bloodType = bloodType;
            this.allergies = allergies;

            // Calculate length of stay
            long diffMs = new Date().getTime() - admissionDate.getTime();
            this.lengthOfStay = (int) (diffMs / (24 * 60 * 60 * 1000)) + 1;

            // Calculate total charges
            this.totalCharges = dailyCharge.multiply(new BigDecimal(lengthOfStay));

            // Expected discharge (3-7 days typically)
            Calendar cal = Calendar.getInstance();
            cal.setTime(admissionDate);
            cal.add(Calendar.DAY_OF_YEAR, 3 + new Random().nextInt(5));
            this.expectedDischarge = cal.getTime();
        }

        // Getters
        public String getPatientId() { return patientId; }
        public String getPatientName() { return patientName; }
        public Integer getAge() { return age; }
        public String getGender() { return gender; }
        public Date getAdmissionDate() { return admissionDate; }
        public String getDepartment() { return department; }
        public String getRoomNumber() { return roomNumber; }
        public String getAttendingPhysician() { return attendingPhysician; }
        public String getDiagnosis() { return diagnosis; }
        public String getStatus() { return status; }
        public String getInsurance() { return insurance; }
        public BigDecimal getDailyCharge() { return dailyCharge; }
        public Integer getLengthOfStay() { return lengthOfStay; }
        public BigDecimal getTotalCharges() { return totalCharges; }
        public Date getExpectedDischarge() { return expectedDischarge; }
        public String getEmergencyContact() { return emergencyContact; }
        public String getBloodType() { return bloodType; }
        public String getAllergies() { return allergies; }
    }

    private static List<PatientRecord> getSamplePatientData() {
        List<PatientRecord> patients = new ArrayList<>();
        Random random = new Random();

        String[] firstNames = {"John", "Jane", "Michael", "Sarah", "David", "Lisa", "Robert", "Emily", "James", "Anna"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Wilson"};
        String[] departments = {"Cardiology", "Emergency", "Surgery", "Pediatrics", "Neurology", "Orthopedics", "ICU"};
        String[] physicians = {"Dr. Anderson", "Dr. Thompson", "Dr. Martinez", "Dr. Chen", "Dr. Kumar", "Dr. Roberts"};
        String[] diagnoses = {"Chest Pain", "Pneumonia", "Fracture", "Hypertension", "Diabetes", "Cardiac Arrhythmia", "Post-Operative"};
        String[] statuses = {"Stable", "Critical", "Improving", "Discharged", "Transferred"};
        String[] insurances = {"Blue Cross", "Aetna", "Medicare", "Medicaid", "United Healthcare", "Self Pay"};
        String[] bloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        String[] allergiesList = {"None", "Penicillin", "Latex", "Shellfish", "Peanuts", "Iodine"};

        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 25; i++) {
            cal.add(Calendar.DAY_OF_YEAR, -random.nextInt(14)); // Last 2 weeks

            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];

            patients.add(new PatientRecord(
                "PT-" + String.format("%06d", 100000 + i),
                firstName + " " + lastName,
                18 + random.nextInt(70),
                random.nextBoolean() ? "Male" : "Female",
                cal.getTime(),
                departments[random.nextInt(departments.length)],
                String.format("%d%02d", 1 + random.nextInt(5), 1 + random.nextInt(50)),
                physicians[random.nextInt(physicians.length)],
                diagnoses[random.nextInt(diagnoses.length)],
                statuses[random.nextInt(statuses.length)],
                insurances[random.nextInt(insurances.length)],
                new BigDecimal(250 + random.nextInt(1500)),
                firstName + " " + lastNames[random.nextInt(lastNames.length)] + " (" + String.format("555-%03d-%04d", random.nextInt(999), random.nextInt(9999)) + ")",
                bloodTypes[random.nextInt(bloodTypes.length)],
                allergiesList[random.nextInt(allergiesList.length)]
            ));
        }

        return patients;
    }

    private static Double calculateOccupancyRate(List<PatientRecord> patients) {
        long activePatients = patients.stream().filter(p -> !"Discharged".equals(p.getStatus())).count();
        return (double) activePatients / 100 * 100; // Assuming 100 bed capacity
    }
}
