package com.sample.app;

import java.math.BigDecimal;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RealEstatePropertyReport {

    public static void main(String[] args) {
        try {
            String jrxmlFile = "src/main/resources/real_estate_property.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            List<PropertyListing> properties = getSamplePropertyData();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(properties);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Real Estate Property Listings Report");
            parameters.put("AGENCY_NAME", "Prime Realty Group");
            parameters.put("REPORT_DATE", new Date());
            parameters.put("BROKER", "Michael Harrison, Licensed Broker");
            parameters.put("TOTAL_LISTINGS", properties.size());
            parameters.put("AVERAGE_PRICE", calculateAveragePrice(properties));
            parameters.put("TOTAL_VALUE", calculateTotalValue(properties));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "real_estate_property_report.pdf");
            System.out.println("Real Estate Property Report generated: real_estate_property_report.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class PropertyListing {
        private String listingId;
        private String address;
        private String city;
        private String state;
        private String zipCode;
        private String propertyType;
        private Integer bedrooms;
        private Integer bathrooms;
        private Integer squareFootage;
        private BigDecimal lotSize;
        private Integer yearBuilt;
        private BigDecimal listPrice;
        private String status;
        private Date listingDate;
        private String agent;
        private String description;
        private BigDecimal pricePerSqFt;
        private Integer daysOnMarket;
        private String features;
        private BigDecimal estimatedTaxes;
        private String schoolDistrict;

        public PropertyListing(String listingId, String address, String city, String state, String zipCode,
                             String propertyType, Integer bedrooms, Integer bathrooms, Integer squareFootage,
                             BigDecimal lotSize, Integer yearBuilt, BigDecimal listPrice, String status,
                             Date listingDate, String agent, String description, String features,
                             String schoolDistrict) {
            this.listingId = listingId;
            this.address = address;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
            this.propertyType = propertyType;
            this.bedrooms = bedrooms;
            this.bathrooms = bathrooms;
            this.squareFootage = squareFootage;
            this.lotSize = lotSize;
            this.yearBuilt = yearBuilt;
            this.listPrice = listPrice;
            this.status = status;
            this.listingDate = listingDate;
            this.agent = agent;
            this.description = description;
            this.features = features;
            this.schoolDistrict = schoolDistrict;

            // Calculate price per square foot
            this.pricePerSqFt = squareFootage > 0 ? 
                listPrice.divide(new BigDecimal(squareFootage), 2, BigDecimal.ROUND_HALF_UP) : 
                BigDecimal.ZERO;

            // Calculate days on market
            long diffMs = new Date().getTime() - listingDate.getTime();
            this.daysOnMarket = (int) (diffMs / (24 * 60 * 60 * 1000));

            // Estimate property taxes (1.2% of list price annually)
            this.estimatedTaxes = listPrice.multiply(new BigDecimal("0.012"));
        }

        // Getters
        public String getListingId() { return listingId; }
        public String getAddress() { return address; }
        public String getCity() { return city; }
        public String getState() { return state; }
        public String getZipCode() { return zipCode; }
        public String getPropertyType() { return propertyType; }
        public Integer getBedrooms() { return bedrooms; }
        public Integer getBathrooms() { return bathrooms; }
        public Integer getSquareFootage() { return squareFootage; }
        public BigDecimal getLotSize() { return lotSize; }
        public Integer getYearBuilt() { return yearBuilt; }
        public BigDecimal getListPrice() { return listPrice; }
        public String getStatus() { return status; }
        public Date getListingDate() { return listingDate; }
        public String getAgent() { return agent; }
        public String getDescription() { return description; }
        public BigDecimal getPricePerSqFt() { return pricePerSqFt; }
        public Integer getDaysOnMarket() { return daysOnMarket; }
        public String getFeatures() { return features; }
        public BigDecimal getEstimatedTaxes() { return estimatedTaxes; }
        public String getSchoolDistrict() { return schoolDistrict; }
        
        public String getFullAddress() {
            return address + ", " + city + ", " + state + " " + zipCode;
        }
    }

    private static List<PropertyListing> getSamplePropertyData() {
        List<PropertyListing> properties = new ArrayList<>();
        Random random = new Random();

        String[] streets = {"Main St", "Oak Ave", "Elm Dr", "Pine Rd", "Maple Ln", "Cedar Blvd", "Birch Way", "Willow Ct"};
        String[] cities = {"Springfield", "Riverside", "Greenwood", "Fairfield", "Highland Park", "Oakville"};
        String[] propertyTypes = {"Single Family", "Condo", "Townhouse", "Multi-Family", "Land"};
        String[] agents = {"Jennifer Smith", "Michael Brown", "Sarah Davis", "Robert Wilson", "Lisa Anderson", "David Martinez"};
        String[] statuses = {"Active", "Pending", "Sold", "Withdrawn"};
        String[] features = {
            "Pool, Garage, Fireplace",
            "Updated Kitchen, Hardwood Floors",
            "Central AC, Walk-in Closets",
            "Garden, Patio, Storage Shed",
            "Basement, Attic, Deck"
        };
        String[] schoolDistricts = {"District A", "District B", "District C", "Metro District", "County Schools"};

        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 20; i++) {
            cal.add(Calendar.DAY_OF_YEAR, -random.nextInt(180)); // Listed in last 6 months

            int streetNum = 100 + random.nextInt(900);
            String street = streets[random.nextInt(streets.length)];
            String city = cities[random.nextInt(cities.length)];
            String zipCode = String.format("%05d", 10000 + random.nextInt(89999));

            int sqft = 1000 + random.nextInt(3000);
            int bedrooms = 2 + random.nextInt(4);
            int bathrooms = 1 + random.nextInt(3);

            properties.add(new PropertyListing(
                "MLS-" + String.format("%08d", 10000000 + i),
                streetNum + " " + street,
                city,
                "CA",
                zipCode,
                propertyTypes[random.nextInt(propertyTypes.length)],
                bedrooms,
                bathrooms,
                sqft,
                new BigDecimal(0.1 + random.nextDouble() * 0.9), // 0.1 to 1.0 acres
                1970 + random.nextInt(54), // 1970-2024
                new BigDecimal(200000 + random.nextInt(800000)), // $200K-$1M
                statuses[random.nextInt(statuses.length)],
                cal.getTime(),
                agents[random.nextInt(agents.length)],
                "Beautiful " + propertyTypes[random.nextInt(propertyTypes.length)].toLowerCase() + 
                " in desirable " + city + " neighborhood.",
                features[random.nextInt(features.length)],
                schoolDistricts[random.nextInt(schoolDistricts.length)]
            ));
        }

        return properties;
    }

    private static BigDecimal calculateAveragePrice(List<PropertyListing> properties) {
        return properties.stream()
            .map(PropertyListing::getListPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(new BigDecimal(properties.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    private static BigDecimal calculateTotalValue(List<PropertyListing> properties) {
        return properties.stream()
            .map(PropertyListing::getListPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
