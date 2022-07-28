package com.sample.app.recrods;

public sealed interface Operation permits Add, Mul{

	public Long operation();
}
