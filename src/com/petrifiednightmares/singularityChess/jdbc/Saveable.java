package com.petrifiednightmares.singularityChess.jdbc;

public interface Saveable
{
	public String serialize();
	public void deserialize(String input);
}
