package com.sample.app;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sample.app.util.FileUtil;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class Watermark {
	
	public static void main(String[] args) throws IOException {
		String sourceImageFilePath = FileUtil.resourceFilePath("taj-mahal.png");
		String watermarkImageFilePath = FileUtil.resourceFilePath("logo_1.jpg");
		String destinationFilePath = "/Users/Shared/thumbnails/watermarked_image.png";
		
		Thumbnails.of((sourceImageFilePath))
        .size(500, 500)
        .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(watermarkImageFilePath)), 0.5f)
        .outputQuality(0.8)
        .toFile(new File(destinationFilePath));
	}

}
