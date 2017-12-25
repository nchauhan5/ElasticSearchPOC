//$Rev: 494 $
package com.endeca.tui.anite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class FileBackedResponseBuffer.
 */
public class FileBackedResponseBuffer implements ResponseBuffer
{

	/** The backing file. */
	protected File backingFile;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FileBackedResponseBuffer.class);

	/** The out. */
	protected FileOutputStream out;

	/** The name. */
	protected String name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBuffer#getBufferOutputStream()
	 */
	@Override
	public OutputStream getBufferOutputStream() throws IOException
	{
		if (null != out)
		{
			throw new IllegalStateException("Output stream has already been created");
		}
		LOGGER.debug("Buffering output to file " + getBackingFile().getAbsolutePath());
		out = new FileOutputStream(getBackingFile());
		return out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBuffer#getBufferInputStream()
	 */
	@Override
	public InputStream getBufferInputStream() throws IOException
	{
		return new FileInputStream(getBackingFile());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBuffer#persist()
	 */
	@Override
	public void persist() throws IOException
	{
		if (null != out)
		{
			out.close();
		}
	}

	/**
	 * Gets the backing file.
	 * 
	 * @return the backing file
	 */
	public File getBackingFile()
	{
		return backingFile;
	}

	/**
	 * Sets the backing file.
	 * 
	 * @param backingFile
	 *           the new backing file
	 */
	public void setBackingFile(final File backingFile)
	{
		this.backingFile = backingFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBuffer#dispose()
	 */
	@Override
	public void dispose()
	{

		boolean result = true;
		result = getBackingFile().delete();
		if (!result)
		{
			LOGGER.error("file not deleted");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBuffer#getName()
	 */
	public String getName()
	{
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBuffer#setName(java.lang.String)
	 */
	public void setName(final String name)
	{
		this.name = name;
	}
}
