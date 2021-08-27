package com.sample.app.service.impl;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sample.app.constants.Constant;
import com.sample.app.dto.SqlQueryRequestDto;
import com.sample.app.dto.TransofrmationClasses;
import com.sample.app.exception.TransformationPayloadException;
import com.sample.app.service.QueryService;
import com.sample.app.transformation.Transformation;
import com.sample.app.transformation.impl.LowerTransformationImpl;
import com.sample.app.transformation.impl.MergeTransformationImpl;
import com.sample.app.transformation.impl.ReverseTransformationImpl;
import com.sample.app.transformation.impl.TransformationImpl;
import com.sample.app.util.FileUtil;
import com.sample.app.util.JsonUtil;

public class QueryServiceImpl implements QueryService {
	private static TransofrmationClasses transofrmationClasses = null;
	private static Map<String, String> keyAndClass = null;

	static {
		try {
			String json = FileUtil.readFromClassPath(Constant.TRANSFORMATION_CONFIG_FILE);
			transofrmationClasses = JsonUtil.getObject(json, TransofrmationClasses.class);

			keyAndClass = transofrmationClasses.getKeyAndClassMap();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

	@Override
	public String generateQuery(SqlQueryRequestDto sqlQueryRequestDto) throws TransformationPayloadException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT").append(" ");

		Map<String, Object> transformations = sqlQueryRequestDto.getTransformations();

		int noOfTransformations = transformations.size();
		int count = 0;

		for (String transformation : transformations.keySet()) {
			count++;
			Object obj = transformations.get(transformation);
			String result = null;

			if (!keyAndClass.containsKey(transformation.toUpperCase())) {
				throw new RuntimeException(transformation + " is not supported");
			}
			try {
				result = getQuery(obj, keyAndClass.get(transformation.toUpperCase()));
			} catch (ClassNotFoundException | TransformationPayloadException e) {
				throw new RuntimeException(e);
			}

			stringBuilder.append(result);
			if (count != noOfTransformations) {
				stringBuilder.append(",");
			}
		}

		stringBuilder.append(" ").append("FROM").append(" ").append(sqlQueryRequestDto.getTableName());
		return stringBuilder.toString();
	}

	private String getQuery(Object obj, String className)
			throws ClassNotFoundException, TransformationPayloadException {
		Class clazz = Class.forName(className);
		return getQuery(obj, clazz);
	}

	private String getQuery(Object obj, Class clazz) throws TransformationPayloadException {
		StringBuilder stringBuilder = new StringBuilder();
		String json = JsonUtil.getJson(obj);

		JsonElement jsonElement = JsonParser.parseString(json);
		JsonArray jsonArray = jsonElement.getAsJsonArray();

		int size = jsonArray.size();

		for (int i = 0; i < size; i++) {
			JsonElement jsonElem = jsonArray.get(i);
			JsonObject jsonObject = jsonElem.getAsJsonObject();

			Transformation transform = getTransformation(jsonObject, clazz);
			String queryString = transform.getQueryString();
			stringBuilder.append(queryString);

			if (i != size - 1) {
				stringBuilder.append(",");
			}

		}

		return stringBuilder.toString();

	}

	private static Transformation getTransformation(JsonObject obj, Class clazz) {
		
		try {
			TransformationImpl transformation = (TransformationImpl) clazz.newInstance();
			transformation.setJsonObject(obj);
			return transformation;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		/*if (clazz.isAssignableFrom(MergeTransformationImpl.class)) {
			return new MergeTransformationImpl(obj);
		}

		if (clazz.isAssignableFrom(LowerTransformationImpl.class)) {
			return new LowerTransformationImpl(obj);
		}

		if (clazz.isAssignableFrom(ReverseTransformationImpl.class)) {
			return new ReverseTransformationImpl(obj);
		}

		return null;*/
	}

}
