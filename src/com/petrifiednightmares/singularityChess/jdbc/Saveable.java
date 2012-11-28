package com.petrifiednightmares.singularityChess.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Saveable
{
	
	/**
	 * Deserializes the Writable based on input from the InputStream
	 * 
	 * @param in
	 */
	public void deserialize(InputStream in) throws IOException;

	/**
	 * Serializes the Writable out over the OutputStream
	 * 
	 * @param out
	 */
	public void serialize(OutputStream out) throws IOException;
}
