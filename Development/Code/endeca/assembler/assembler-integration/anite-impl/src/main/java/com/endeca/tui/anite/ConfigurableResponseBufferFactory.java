//$Rev: 494 $
package com.endeca.tui.anite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.tui.anite.exceptions.AniteException;


// TODO: Auto-generated Javadoc
/**
 * A factory for creating ConfigurableResponseBuffer objects.
 */
public class ConfigurableResponseBufferFactory implements ResponseBufferFactory
{

	private final class DefaultPersistStreamFactory implements PersistStreamFactory
	{
		@Override
		public OutputStream newPersistOutputStream() throws IOException
		{
			return newPersistOutputStream(null);
		}

		@Override
		public OutputStream newPersistOutputStream(final String name) throws IOException
		{
			FileOutputStream fileOutputStream = null;
			try
			{
				fileOutputStream = new FileOutputStream(createTempFile(name));
			}
			catch (final IOException e)
			{
				final String msg = LOG_MSG_ERROR_CREATING_PERSIST_OUT_PUT_STREAM;
				LOGGER.error(msg, e);
			}
			catch (final AniteException e)
			{
				final String msg = LOG_MSG_ERROR_CREATING_PERSIST_OUT_PUT_STREAM;
				LOGGER.error(msg, e);
			}
			return fileOutputStream;
		}
	}

	private static final String LOG_MSG_ERROR_CREATING_PERSIST_OUT_PUT_STREAM = "Error creating PersistOutPutStream";

	/** The memory backed. */
	protected boolean memoryBacked = true;

	/** The persist stream factory. */
	protected PersistStreamFactory persistStreamFactory;

	/** The tmp file directory. */
	protected File tmpFileDirectory;

	/** The tmp file suffix. */
	protected String tmpFileSuffix = ".xml";

	/** The tmp file prefix. */
	protected String tmpFilePrefix = "aniteresponse";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurableResponseBufferFactory.class);

	/** The create temp filemsg. */
	String createTempFilemsg = "Error creating tmp file for response buffer in directory ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBufferFactory#newResponseBuffer(java.lang.String)
	 */
	@Override
	public ResponseBuffer newResponseBuffer(final String name)
	{
		ResponseBuffer rb;
		if (isMemoryBacked())
		{
			final MemoryBackedResponseBuffer b = new MemoryBackedResponseBuffer();
			b.setPersistStreamFactory(getPersistStreamFactory());
			rb = b;
		}
		else
		{
			final FileBackedResponseBuffer b = new FileBackedResponseBuffer();
			try
			{
				b.setBackingFile(createTempFile(name));
			}
			catch (final AniteException e)
			{
				LOGGER.error(createTempFilemsg, e);
			}
			rb = b;
		}
		if (null != name)
		{
			rb.setName(name);
		}
		return rb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBufferFactory#newResponseBuffer()
	 */
	@Override
	public ResponseBuffer newResponseBuffer()
	{
		return newResponseBuffer(null);
	}

	/**
	 * Creates a new ConfigurableResponseBuffer object.
	 * 
	 * @param name
	 *           the name
	 * @return the file
	 * @throws AniteException
	 *            the anite exception
	 */
	protected File createTempFile(final String name) throws AniteException
	{
		try
		{
			String prefix = getTmpFilePrefix();
			if (null != name)
			{
				prefix = prefix + name;
			}
			final File tmpFile = File.createTempFile(prefix, getTmpFileSuffix(), getTmpFileDirectory());
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Saving response to file:{} at Path:{}",tmpFile,tmpFile.getAbsolutePath());
			}
			return tmpFile;
		}
		catch (final IOException e)
		{
			LOGGER.error(createTempFilemsg + tmpFileDirectory, e);
			throw new AniteException(createTempFilemsg + tmpFileDirectory, e);
		}
	}

	/**
	 * Checks if is memory backed.
	 * 
	 * @return true, if is memory backed
	 */
	public boolean isMemoryBacked()
	{
		return memoryBacked;
	}

	/**
	 * Sets the memory backed.
	 * 
	 * @param memoryBacked
	 *           the new memory backed
	 */
	public void setMemoryBacked(final boolean memoryBacked)
	{
		this.memoryBacked = memoryBacked;
	}

	/**
	 * Gets the persist stream factory.
	 * 
	 * @return the persist stream factory
	 */
	public PersistStreamFactory getPersistStreamFactory()
	{
		if (null == persistStreamFactory)
		{
			persistStreamFactory = new DefaultPersistStreamFactory();
		}
		return persistStreamFactory;
	}

	/**
	 * Sets the persist stream factory.
	 * 
	 * @param persistStreamFactory
	 *           the new persist stream factory
	 */
	public void setPersistStreamFactory(final PersistStreamFactory persistStreamFactory)
	{
		this.persistStreamFactory = persistStreamFactory;
	}

	/**
	 * Gets the tmp file directory.
	 * 
	 * @return the tmp file directory
	 */
	public File getTmpFileDirectory()
	{
		return tmpFileDirectory;
	}

	/**
	 * Sets the tmp file directory.
	 * 
	 * @param tmpFileDirectory
	 *           the new tmp file directory
	 */
	public void setTmpFileDirectory(final File tmpFileDirectory)
	{
		this.tmpFileDirectory = tmpFileDirectory;
	}

	/**
	 * Gets the tmp file suffix.
	 * 
	 * @return the tmp file suffix
	 */
	public String getTmpFileSuffix()
	{
		return tmpFileSuffix;
	}

	/**
	 * Sets the tmp file suffix.
	 * 
	 * @param tmpFileSuffix
	 *           the new tmp file suffix
	 */
	public void setTmpFileSuffix(final String tmpFileSuffix)
	{
		this.tmpFileSuffix = tmpFileSuffix;
	}

	/**
	 * Gets the tmp file prefix.
	 * 
	 * @return the tmp file prefix
	 */
	public String getTmpFilePrefix()
	{
		return tmpFilePrefix;
	}

	/**
	 * Sets the tmp file prefix.
	 * 
	 * @param tmpFilePrefix
	 *           the new tmp file prefix
	 */
	public void setTmpFilePrefix(final String tmpFilePrefix)
	{
		this.tmpFilePrefix = tmpFilePrefix;
	}
}
