package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class SmileDataFrameExample {
    public static void main(String[] args) {
        // Create a DataFrame
        String[] names = {"Alice", "Bob", "Charlie"};
        int[] ages = {24, 30, 28};
        String[] cities = {"New York", "San Francisco", "Los Angeles"};

        DataFrame df = DataFrame.of(
                StringVector.of("Name", names),
                IntVector.of("Age", ages),
                StringVector.of("City", cities)
        );

        System.out.println("Dataframe : \n" + df);
        
        DataFrame summaryOfDataframe = df.summary();
        System.out.println(summaryOfDataframe);
        
    }

    
}
