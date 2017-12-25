package com.endeca.tui.anite;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class MemoryBackedResponseBuffer.
 */
public class MemoryBackedResponseBuffer implements ResponseBuffer
{

	/** The Constant DEFAULT_INITIAL_BUFFER_SIZE. */
	public static final int DEFAULT_INITIAL_BUFFER_SIZE = 1024 * 1024 * 1024; // 1MB

	/** The out. */
	protected ByteArrayOutputStream out;

	/** The persist stream factory. */
	protected PersistStreamFactory persistStreamFactory;

	/** The persist error fatal. */
	protected boolean persistErrorFatal;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MemoryBackedResponseBuffer.class);

	/** The initial size. */
	protected int initialSize = DEFAULT_INITIAL_BUFFER_SIZE;

	/** The name. */
	protected String name;

	/**
	 * Instantiates a new memory backed response buffer.
	 */
	public MemoryBackedResponseBuffer()
	{
	}

	/**
	 * Instantiates a new memory backed response buffer.
	 * 
	 * @param initialSize
	 *           the initial size
	 * @param persistStreamFactory
	 *           the persist stream factory
	 * @param persistErrorFatal
	 *           the persist error fatal
	 */
	public MemoryBackedResponseBuffer(final int initialSize, final PersistStreamFactory persistStreamFactory,
			final boolean persistErrorFatal)
	{
		super();
		this.persistStreamFactory = persistStreamFactory;
		this.persistErrorFatal = persistErrorFatal;
		this.initialSize = initialSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBuffer#getBufferOutputStream()
	 */
	@Override
	public OutputStream getBufferOutputStream()
	{
		out = new ByteArrayOutputStream(initialSize);
		return out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBuffer#getBufferInputStream()
	 */
	@Override
	public InputStream getBufferInputStream()
	{
		if (null == out)
		{
			return null;
		}
		else
		{
			return new ByteArrayInputStream(out.toByteArray());
		}
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
			try
			{
				final OutputStream persistOutputStream = getPersistOutputStream();
				if (null == persistOutputStream)
				{
					final String msg = "Cannot get a persist output stream - not persisting";
					LOGGER.error(msg);

				}
				else
				{
					persistOutputStream.write(out.toByteArray());
					persistOutputStream.close();
				}
			}
			catch (final IOException e)
			{
				final String msg = "IO Exception while persisting";
				LOGGER.error(msg);
				if (isPersistErrorFatal())
				{
					throw e;
				}
			}
		}
	}

	/**
	 * Gets the persist output stream.
	 * 
	 * @return the persist output stream
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	protected OutputStream getPersistOutputStream() throws IOException
	{
		if (null == getPersistStreamFactory())
		{
			return null;
		}
		else
		{
			return getPersistStreamFactory().newPersistOutputStream(getName());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.ResponseBuffer#dispose()
	 */
	@Override
	public void dispose()
	{
		IOUtils.closeQuietly(out);
		out = null;
	}

	/**
	 * Gets the persist stream factory.
	 * 
	 * @return the persist stream factory
	 */
	public PersistStreamFactory getPersistStreamFactory()
	{
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
	 * Checks if is persist error fatal.
	 * 
	 * @return true, if is persist error fatal
	 */
	public boolean isPersistErrorFatal()
	{
		return persistErrorFatal;
	}

	/**
	 * Sets the persist error fatal.
	 * 
	 * @param persistErrorFatal
	 *           the new persist error fatal
	 */
	public void setPersistErrorFatal(final boolean persistErrorFatal)
	{
		this.persistErrorFatal = persistErrorFatal;
	}

	/**
	 * Gets the initial size.
	 * 
	 * @return the initial size
	 */
	public int getInitialSize()
	{
		return initialSize;
	}

	/**
	 * Sets the initial size.
	 * 
	 * @param initialSize
	 *           the new initial size
	 */
	public void setInitialSize(final int initialSize)
	{
		this.initialSize = initialSize;
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
