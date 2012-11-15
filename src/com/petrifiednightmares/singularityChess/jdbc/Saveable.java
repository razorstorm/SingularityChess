package com.petrifiednightmares.singularityChess.jdbc;

public interface Saveable
{
	public void serialize(Object output);
	public void deserialize(Object input);
}
