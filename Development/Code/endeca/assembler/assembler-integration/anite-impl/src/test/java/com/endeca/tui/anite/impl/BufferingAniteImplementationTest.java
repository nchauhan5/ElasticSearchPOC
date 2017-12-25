package com.endeca.tui.anite.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import sun.security.action.GetPropertyAction;

import com.endeca.tui.anite.ConfigurableResponseBufferFactory;
import com.endeca.tui.anite.MemoryBackedResponseBuffer;
import com.endeca.tui.anite.ResponseBuffer;
import com.endeca.tui.anite.ResponseBufferFactory;
import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteCommunicationException;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.io.file.AniteResponseStreamBuilderFromFile;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;
import com.endeca.tui.anite.unmarshall.impl.AniteJaxbResponseStreamUnmarshaller;


/**
 * Test class for {@link BufferingAniteImplementation}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BufferingAniteImplementationTest
{

	/** The anite response stream builder from file. */
	@Mock
	private AniteResponseStreamBuilderFromFile mockAniteResponseStreamBuilderFromFile;

	/** The anite jaxb response stream unmarshaller. */
	@Mock
	private AniteJaxbResponseStreamUnmarshaller mockAniteJaxbResponseStreamUnmarshaller;

	@Mock
	private ResponseBufferFactory responseBufferFactory;

	/** The default anite implementation. */
	@InjectMocks
	private final static BufferingAniteImplementation bufferingAniteImplementation = new BufferingAniteImplementation();

	/** The response dir. */
	private static File responseDir;

	/**
	 * Setup method. Creating temporary directory where anite responses will be logged.
	 */
	@BeforeClass
	public static void setUp()
	{
		final File tmpdir = new File(AccessController.doPrivileged(new GetPropertyAction("java.io.tmpdir")));

		final File responseDirectory = new File(tmpdir, "responses");
		responseDirectory.mkdir();
		responseDirectory.setWritable(true);
		responseDir = responseDirectory;

	}

	/**
	 * Setup method. Deleting the directory created during setup.
	 * 
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	@AfterClass
	public static void tearDown() throws IOException
	{
		FileUtils.deleteDirectory(responseDir);
	}

	/**
	 * Before each test case empty the previous responses logged.
	 * 
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	@After
	public void beforeEach() throws IOException
	{
		FileUtils.cleanDirectory(responseDir);
	}

	/**
	 * Passing date anite query type to check return object is date response.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void aniteResponseBuilderIsCalledWithQueryTypeAndRequiredParams() throws AniteException
	{
		final AniteRequiredParameters mockAniteParameters = Mockito.mock(AniteRequiredParameters.class);
		bufferingAniteImplementation.query(AniteQueryType.CALENDAR, mockAniteParameters);
		verify(mockAniteResponseStreamBuilderFromFile).buildQueryInputStream(AniteQueryType.CALENDAR, mockAniteParameters);
	}


	/**
	 * Verify response buffer factor is called for particular anite query type.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void verifyResponseBufferFactorIsCalledForParticularAniteQueryType() throws AniteException
	{
		final ConfigurableResponseBufferFactory mockConfigurableResponseBufferFactory = Mockito
				.mock(ConfigurableResponseBufferFactory.class);
		bufferingAniteImplementation.setResponseBufferFactory(mockConfigurableResponseBufferFactory);
		final AniteRequiredParameters mockAniteParameters = Mockito.mock(AniteRequiredParameters.class);
		bufferingAniteImplementation.query(AniteQueryType.CALENDAR, mockAniteParameters);
		verify(mockConfigurableResponseBufferFactory).newResponseBuffer(AniteQueryType.CALENDAR.toString());
	}

	/**
	 * Unmarshaller is called with input stream from builder.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	@Test
	public void unmarshallerIsCalledWithInputStreamFromBuilder() throws AniteException, IOException
	{
		final ByteArrayInputStream mockInputStream = new ByteArrayInputStream("Mock Input Stream".getBytes());

		final AniteRequiredParameters mockAniteParameters = Mockito.mock(AniteRequiredParameters.class);
		final ConfigurableResponseBufferFactory configurableResponseBufferFactory = new ConfigurableResponseBufferFactory();
		configurableResponseBufferFactory.setMemoryBacked(true);
		configurableResponseBufferFactory.setTmpFileDirectory(responseDir);
		bufferingAniteImplementation.setResponseBufferFactory(configurableResponseBufferFactory);
		when(mockAniteResponseStreamBuilderFromFile.buildQueryInputStream(AniteQueryType.CALENDAR, mockAniteParameters))
				.thenReturn(mockInputStream);
		bufferingAniteImplementation.query(AniteQueryType.CALENDAR, mockAniteParameters);
		verify(mockAniteJaxbResponseStreamUnmarshaller).unmarshall(Mockito.any(AniteQueryType.class),
				Mockito.any(InputStream.class));
		mockInputStream.close();
	}

	/**
	 * Verify io exception in case of null response buffer.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test(expected = AniteCommunicationException.class)
	public void verifyIOExceptionNullResponseBuffer() throws AniteException, IOException, URISyntaxException
	{
		final ResponseBuffer responseBuffer = Mockito.mock(MemoryBackedResponseBuffer.class);
		when(responseBuffer.getBufferOutputStream()).thenReturn(new ByteArrayOutputStream(1024 * 1024 * 1024));
		when(responseBufferFactory.newResponseBuffer(Mockito.anyString())).thenReturn(responseBuffer);
		final URL resource = getClass().getClassLoader().getResource("aniteResponses/aniteCalendarResponse.xml");
		final File file = new File(resource.toURI());
		final FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.close();
		final AniteRequiredParameters mockAniteParameters = Mockito.mock(AniteRequiredParameters.class);
		when(mockAniteResponseStreamBuilderFromFile.buildQueryInputStream(AniteQueryType.CALENDAR, mockAniteParameters))
				.thenReturn(fileInputStream);
		bufferingAniteImplementation.query(AniteQueryType.CALENDAR, mockAniteParameters);
	}

}
